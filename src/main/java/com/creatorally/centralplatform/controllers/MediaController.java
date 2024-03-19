package com.creatorally.centralplatform.controllers;

import com.creatorally.centralplatform.models.entities.Media;
import com.creatorally.centralplatform.services.YoutubeUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MediaController {

    private final YoutubeUploadService youtubeUploadService;

    public MediaController(YoutubeUploadService youtubeUploadService) {
        this.youtubeUploadService = youtubeUploadService;
    }

    @GetMapping("/verify/{id}")
    public ResponseEntity<Media> verifyMedia(@PathVariable("id") @Validated Integer mediaId,
                                               @RequestHeader Integer creatorId) {
        return ResponseEntity.ok().body(youtubeUploadService.verifyMedia(mediaId, creatorId));
    }

}
