package com.creatorally.centralplatform.controllers;

import com.creatorally.centralplatform.models.entities.Influencer;
import com.creatorally.centralplatform.models.requests.CreateInfluencerRequest;
import com.creatorally.centralplatform.services.InfluencerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class InfluencerController {

    private final InfluencerService influencerService;

    public InfluencerController(InfluencerService influencerService) {
        this.influencerService = influencerService;
    }

    @PostMapping("/api/1.0/influencer")
    public Integer createInfluencer(@RequestBody CreateInfluencerRequest createInfluencerRequest) {
        log.info("Creating influencer");
        return influencerService.createInfluencer(createInfluencerRequest);
    }

}
