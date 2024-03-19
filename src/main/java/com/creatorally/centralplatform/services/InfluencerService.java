package com.creatorally.centralplatform.services;

import com.creatorally.centralplatform.models.entities.Influencer;
import com.creatorally.centralplatform.models.entities.Media;
import com.creatorally.centralplatform.models.requests.CreateInfluencerRequest;
import com.creatorally.centralplatform.repository.InfluencerRepository;
import com.creatorally.centralplatform.repository.MediaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InfluencerService {

    private final InfluencerRepository influencerRepository;
    private final MediaRepository mediaRepository;

    public InfluencerService(InfluencerRepository influencerRepository, MediaRepository mediaRepository) {
        this.influencerRepository = influencerRepository;
        this.mediaRepository = mediaRepository;
    }

    public Integer createInfluencer(CreateInfluencerRequest createInfluencerRequest) {
        Influencer influencer = Influencer.builder().username(createInfluencerRequest.getUsername())
                .password(createInfluencerRequest.getPassword())
                .accessUrl(createInfluencerRequest.getAccessUrl())
                .email(createInfluencerRequest.getEmail())
                .build();
        Influencer influencerId = influencerRepository.save(influencer);
        log.info("Influencer created successfully");
        return influencerId.getId();
    }

    public List<Media> getAllMediaByCreatorId(Integer id) {
        return mediaRepository.getAllMediaByInfluencer(id);
    }
}
