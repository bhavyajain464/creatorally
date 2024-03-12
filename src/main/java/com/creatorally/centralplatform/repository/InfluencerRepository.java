package com.creatorally.centralplatform.repository;

import com.creatorally.centralplatform.models.entities.Influencer;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfluencerRepository extends JpaRepository<Influencer, Integer>{
}
