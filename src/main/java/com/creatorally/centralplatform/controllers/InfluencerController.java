package com.creatorally.centralplatform.controllers;

import com.creatorally.centralplatform.models.entities.Media;
import com.creatorally.centralplatform.models.requests.CreateInfluencerRequest;
import com.creatorally.centralplatform.models.requests.UploadVideoRequest;
import com.creatorally.centralplatform.services.InfluencerService;
import com.creatorally.centralplatform.services.YoutubeUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/influencer")
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

    @PostMapping(path = "/api/1.0/uploadVideo")
    public String uploadVideo(@ModelAttribute UploadVideoRequest uploadVideoRequest) {
        youtubeUploadServiceImpl.uploadVideo(uploadVideoRequest);
        return "Video uploaded successfully!";
    }

    @PostMapping(path = "/api/1.0/createCredential")
    public String createCredential(@RequestParam Integer creatorId) {
        youtubeUploadServiceImpl.getCredentials(creatorId);
        return "Video uploaded successfully!";
    }

    @GetMapping(path = "/api/1.0/media/{id}")
    public List<Media> getMediaByCreatorId(@PathVariable(name="id") @Validated Integer id) {
        return influencerService.getAllMediaByCreatorId(id);
    }

}
