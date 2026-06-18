package com.creatorally.centralplatform.models.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateInfluencerRequest {

    private String username;
    private String password;
    private String accessUrl;  // Need to take this using google api, need to check
    private String email;

}
