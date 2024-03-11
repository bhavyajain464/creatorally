package com.creatorally.centralplatform.models.requests;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties
public class PublishVideoRequest {

    @JsonProperty("file")
    private String file;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("category")
    private String category;
    @JsonProperty("keywords")
    private String keywords;
    @JsonProperty("privacyStatus")
    private String privacyStatus;

}
