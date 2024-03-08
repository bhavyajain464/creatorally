package com.creatorally.centralplatform.models.entities;

import com.creatorally.centralplatform.models.enums.MediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean isVerified;
    private String editedUrl;
    private String uneditedUrl;
    private Integer editorId;
    private Integer creatorId;
    private Enum<MediaType> mediaType;
    private Long scheduledTime;
}
