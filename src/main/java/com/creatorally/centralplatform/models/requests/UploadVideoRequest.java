package com.creatorally.centralplatform.models.requests;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties
public class UploadVideoRequest {


    private VideoData videoData;
    private Integer creatorId;
    private Integer editorId;
    private LocalDateTime scheduledTime;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonIgnoreProperties
    public static class VideoData {
        @JsonProperty("file")
        private MultipartFile file;
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
}
