package com.creatorally.centralplatform.models.responses;

import com.creatorally.centralplatform.models.entities.Media;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAllEditorsResponse {
    List<EditorInfo> getEditorResponses;

    @Data
    @JsonIgnoreProperties
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class EditorInfo {
        private Integer id;
        private String username;
    }
}
