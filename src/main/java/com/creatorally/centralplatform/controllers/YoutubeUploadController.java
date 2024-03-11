package com.creatorally.centralplatform.controllers;


import com.creatorally.centralplatform.models.requests.UploadVideoRequest;
import com.creatorally.centralplatform.services.YoutubeUploadService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YoutubeUploadController {


    private final YoutubeUploadService youtubeUploadServiceImpl;

    public YoutubeUploadController(YoutubeUploadService youtubeUploadServiceImpl) {
        this.youtubeUploadServiceImpl = youtubeUploadServiceImpl;
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
