package com.creatorally.centralplatform.helper;

import com.creatorally.centralplatform.models.entities.Media;
import com.creatorally.centralplatform.repository.MediaRepository;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import static com.creatorally.centralplatform.constants.CommonConstants.GithubConstants.*;

@Component
@Slf4j
public class MediaUploadHelper {


    private final RestTemplate restTemplate;
    private final MediaRepository mediaRepository;



    public MediaUploadHelper(RestTemplate restTemplate, MediaRepository mediaRepository) {
        this.restTemplate = restTemplate;
        this.mediaRepository = mediaRepository;
    }

    @Async
    public void uploadMediaToServer(MultipartFile video, Media media, boolean isEdited) throws IOException {

        // Read file content
        byte[] fileContent = video.getBytes();
        String fileName = video.getOriginalFilename();
        HashMap<String, String> request = new HashMap<>();
        request.put("message", "added media id"+media.getId());
        request.put("content", Base64.getEncoder().encodeToString(fileContent));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + GITHUB_ACCESS_TOKEN);
        httpHeaders.set("Accept", "application/vnd.github.v3+json");
        httpHeaders.set("X-GitHub-Api-Version", "2022-11-28");

        String url = String.format("https://api.github.com/repos/%s/%s/contents/%s", GITHUB_USERNAME, GITHUB_REPO, media.getId()+fileName.replaceAll(" ", "_"));
        HttpEntity httpEntity = new HttpEntity<>(request, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
        // Check response status
        int statusCode = response.getStatusCode().value();
        if (statusCode == 201) {
            if(!isEdited)
                media.setUneditedUrl(String.format("https://raw.githubusercontent.com/%s/%s/%s/%s/%s", GITHUB_USERNAME, GITHUB_REPO, "main", NON_EDIT_FOLDER, fileName));
            else
                media.setEditedUrl(String.format("https://raw.githubusercontent.com/%s/%s/%s/%s/%s", GITHUB_USERNAME, GITHUB_REPO, "main", EDIT_FOLDER, fileName));

            mediaRepository.save(media);
            log.info("File uploaded successfully.");
        } else {
            log.error("Failed to upload file. Status code: {}, media id" ,statusCode, media.getId());
        }
    }
}
