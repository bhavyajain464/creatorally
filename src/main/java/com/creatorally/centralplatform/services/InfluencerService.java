package com.creatorally.centralplatform.services;

import com.creatorally.centralplatform.controllers.InfluencerController;
import com.creatorally.centralplatform.models.entities.Influencer;
import com.creatorally.centralplatform.models.requests.CreateInfluencerRequest;
import com.creatorally.centralplatform.repository.InfluencerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InfluencerService {

    private final InfluencerRepository influencerRepository;

    public InfluencerService(InfluencerRepository influencerRepository) {
        this.influencerRepository = influencerRepository;
    }

    public Integer createInfluencer(CreateInfluencerRequest createInfluencerRequest) {
        Influencer influencer = Influencer.builder().username(createInfluencerRequest.getUsername())
                .password(createInfluencerRequest.getPassword())
                .accessUrl(createInfluencerRequest.getAccessUrl())
                .build();
        Influencer influencerId = influencerRepository.save(influencer);
        log.info("Influencer created successfully");
        return influencerId.getId();



    }
}
