package com.creatorally.centralplatform.controllers;

import com.creatorally.centralplatform.models.entities.Influencer;
import com.creatorally.centralplatform.models.requests.CreateInfluencerRequest;
import com.creatorally.centralplatform.services.InfluencerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfluencerController {

    private final InfluencerService influencerService;

    InfluencerController(InfluencerService influencerService) {
        this.influencerService = influencerService;
    }

    @PostMapping("/api/1.0/influencer")
    public Integer createInfluencer(@RequestBody CreateInfluencerRequest createInfluencerRequest) {
        System.out.println("Creating influencer");
        return influencerService.createInfluencer(createInfluencerRequest);
    }

}
