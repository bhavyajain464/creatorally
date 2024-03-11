package com.creatorally.centralplatform.models.entities;

import com.creatorally.centralplatform.models.enums.MediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
