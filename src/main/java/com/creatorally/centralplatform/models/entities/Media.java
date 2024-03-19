package com.creatorally.centralplatform.models.entities;

import com.creatorally.centralplatform.models.enums.MediaType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean isVerified = false;
    private String editedUrl;
    private String uneditedUrl;
    private Integer editorId;
    private Integer creatorId;
    private Enum<MediaType> mediaType;
    private Long scheduledTime;
    private String title;
    private String description;
    private String category;
    private String keywords;
    private String privacyStatus;
}
