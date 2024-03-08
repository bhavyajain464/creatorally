package com.creatorally.centralplatform.controllers;


import com.creatorally.centralplatform.models.requests.VideoUploadRequest;
import com.creatorally.centralplatform.services.YoutubeUploadService;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YoutubeUploadController {


    private final YoutubeUploadService youtubeUploadService;

    public YoutubeUploadController(YoutubeUploadService youtubeUploadService) {
        this.youtubeUploadService = youtubeUploadService;
    }

    @PostMapping("/uploadVideo")
    public String uploadVideo(
            @RequestBody VideoUploadRequest videoUploadRequest) throws IOException {
        youtubeUploadService.uploadVideo(videoUploadRequest);
        return "Video uploaded successfully!";
    }

}
