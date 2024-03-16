package com.creatorally.centralplatform.controllers;

import com.creatorally.centralplatform.models.requests.CreateInfluencerRequest;
import com.creatorally.centralplatform.models.requests.UploadVideoRequest;
import com.creatorally.centralplatform.services.InfluencerService;
import com.creatorally.centralplatform.services.YoutubeUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class InfluencerController {

    private final InfluencerService influencerService;
    private final YoutubeUploadService youtubeUploadServiceImpl;

    public InfluencerController(InfluencerService influencerService, YoutubeUploadService youtubeUploadServiceImpl) {

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

    @PostMapping(path = "/createCredential")
    public String createCredential(@RequestParam Integer creatorId) {
        youtubeUploadServiceImpl.getCredentials(creatorId);
        return "Video uploaded successfully!";
    }


}
