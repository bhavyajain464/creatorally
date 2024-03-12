package com.creatorally.centralplatform.controllers;

import com.creatorally.centralplatform.models.data.ScheduledJob;
import com.creatorally.centralplatform.models.requests.UploadVideoRequest;
import com.creatorally.centralplatform.scheduler.JobScheduler;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SchedulerController {

    private final JobScheduler jobScheduler;

    @PostMapping(path = "/startScheduler")
    public String addJobs(@RequestBody List<ScheduledJob> scheduledJobs) {
        jobScheduler.addJobs(scheduledJobs);
        return "Video uploaded successfully!";
    }

}
