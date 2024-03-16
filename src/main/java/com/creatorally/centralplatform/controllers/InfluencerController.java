package com.creatorally.centralplatform.controllers;

import com.creatorally.centralplatform.models.entities.Influencer;
import com.creatorally.centralplatform.models.requests.CreateInfluencerRequest;
import com.creatorally.centralplatform.models.requests.UploadVideoRequest;
import com.creatorally.centralplatform.services.InfluencerService;
import com.creatorally.centralplatform.services.YoutubeUploadService;
import com.creatorally.centralplatform.services.impl.YoutubeUploadServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class InfluencerController {

    private final InfluencerService influencerService;
    private final YoutubeUploadService youtubeUploadServiceImpl;

    public InfluencerController(InfluencerService influencerService, YoutubeUploadServiceImpl youtubeUploadServiceImpl) {

        this.influencerService = influencerService;
        this.youtubeUploadServiceImpl = youtubeUploadServiceImpl;
    }

    @PostMapping("/api/1.0/influencer")
    public Integer createInfluencer(@RequestBody CreateInfluencerRequest createInfluencerRequest) {
        log.info("Creating influencer");
        return influencerService.createInfluencer(createInfluencerRequest);
    }

    @PostMapping(path = "/uploadVideo")
    public String uploadVideo(@ModelAttribute UploadVideoRequest uploadVideoRequest) {
        youtubeUploadServiceImpl.uploadVideo(uploadVideoRequest);
        return "Video uploaded successfully!";
    }

    //    @PostMapping("/publish")
//    public String publish(@RequestBody VideoUploadRequest videoUploadRequest) throws IOException {
//        youtubeUploadService.uploadVideo(videoUploadRequest);
//        return "Video uploaded successfully!";
//    }

}
