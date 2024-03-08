package com.creatorally.centralplatform.services;

import com.creatorally.centralplatform.models.requests.VideoUploadRequest;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class YoutubeUploadService {

    private static final String APPLICATION_NAME = "YouTube Upload Service";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Arrays.asList(YouTubeScopes.YOUTUBE_UPLOAD);
    private static final String CLIENT_SECRETS_FILE = "src/main/resources/client_secrets.json";
    private static final String CREDENTIALS_DIRECTORY = "C:/Users/Dell/OneDrive/Desktop/To-new-beginnings/oauth-credentials";


    @SneakyThrows
    public static YouTube getService() {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @SneakyThrows
    private static Credential getCredentials(final NetHttpTransport httpTransport) {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(CLIENT_SECRETS_FILE)));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_DIRECTORY)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public void uploadVideo(VideoUploadRequest videoUploadRequest)
            throws IOException {
        YouTube youtubeService = getService();

        Video video = new Video();
        VideoSnippet snippet = new VideoSnippet();
        snippet.setTitle(videoUploadRequest.getTitle());
        snippet.setDescription(videoUploadRequest.getDescription());
        snippet.setCategoryId(videoUploadRequest.getCategory());
        snippet.setTags(Arrays.asList(videoUploadRequest.getKeywords().split(",")));

        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus(videoUploadRequest.getPrivacyStatus());

        video.setSnippet(snippet);
        video.setStatus(status);

        YouTube.Videos.Insert videoInsert = youtubeService.videos().insert(
                "snippet,status",video,
                new FileContent("video/*", new java.io.File(videoUploadRequest.getFile()))
        );

        Video returnedVideo = videoInsert.execute();
        System.out.println("Video uploaded: " + returnedVideo.getId());
    }
}
