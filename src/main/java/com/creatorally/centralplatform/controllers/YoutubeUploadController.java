package com.creatorally.centralplatform.controllers;


import com.creatorally.centralplatform.requests.VideoUploadRequest;
import com.creatorally.centralplatform.services.YoutubeUploadService;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class YoutubeUploadController {


    private final YoutubeUploadService youtubeUploadService;

    public YoutubeUploadController(YoutubeUploadService youtubeUploadService) {
        this.youtubeUploadService = youtubeUploadService;
    }

    @PostMapping("/uploadVideo")
    public String uploadVideo(@RequestBody VideoUploadRequest videoUploadRequest) throws IOException {
        youtubeUploadService.uploadVideo(videoUploadRequest);
        return "Video uploaded successfully!";
    }

}
