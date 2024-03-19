package com.creatorally.centralplatform.scheduler;

import com.creatorally.centralplatform.models.data.ScheduledJob;
import com.creatorally.centralplatform.services.YoutubeUploadService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import redis.clients.jedis.Jedis;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class JobScheduler {
    private static final String SCHEDULED_JOBS_PREFIX = "scheduled_jobs:";
    private final Jedis jedis;

    private final YoutubeUploadService youtubeUploadService;

    public JobScheduler(YoutubeUploadService youtubeUploadService) {
        this.youtubeUploadService = youtubeUploadService;
        jedis = new Jedis("localhost", 6379);
    }

    public void scheduleJob(String jobId, long scheduledEpochTime) {
        jedis.zadd(SCHEDULED_JOBS_PREFIX, (scheduledEpochTime), jobId);
    }

    public Set<String> getJobsScheduledBefore(long scheduledEpochTime) {
        return jedis.zrangeByScore(SCHEDULED_JOBS_PREFIX, scheduledEpochTime - 5000.0, scheduledEpochTime);
    }

    public void addJobs(List<ScheduledJob> scheduledJobs) {

        scheduledJobs.forEach(scheduledJob -> scheduleJob(scheduledJob.getJobValue().toString(), scheduledJob.getJobTime()));
    }

    @SneakyThrows
//    @Scheduled(fixedRate = 1000) // Run every second
    public void runJobs() {

        long scheduledTime = LocalDateTime.now().atZone(ZoneOffset.ofHoursMinutes(5, 30)).toEpochSecond();
        Set<String> jobs = getJobsScheduledBefore(scheduledTime); // 2022-03-17 12:00:00
        log.info("running jobs: {}",jobs);
        for (String jobId : jobs) {
            youtubeUploadService.publishVideo(jobId);
            jedis.zrem(SCHEDULED_JOBS_PREFIX, jobId);
        }
    }



}
