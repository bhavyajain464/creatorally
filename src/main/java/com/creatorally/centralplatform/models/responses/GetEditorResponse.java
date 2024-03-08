package com.creatorally.centralplatform.models.responses;

import com.creatorally.centralplatform.models.entities.Media;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetEditorResponse {
    private Integer id;
    private String username;
    private String name;
    private List<Media> media;
}
