package com.creatorally.centralplatform.repository;

import com.creatorally.centralplatform.models.entities.Influencer;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InfluencerRepository extends JpaRepository<Influencer, Id>{
}
