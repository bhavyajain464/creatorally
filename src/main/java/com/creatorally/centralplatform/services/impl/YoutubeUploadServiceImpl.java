package com.creatorally.centralplatform.services.impl;

import static com.creatorally.centralplatform.models.enums.MediaType.VIDEO;

import com.creatorally.centralplatform.helper.MediaUploadHelper;
import com.creatorally.centralplatform.models.entities.Media;
import com.creatorally.centralplatform.models.requests.UploadVideoRequest;
import com.creatorally.centralplatform.repository.MediaRepository;
import com.creatorally.centralplatform.services.YoutubeUploadService;
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
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class YoutubeUploadServiceImpl  implements YoutubeUploadService {

    private final MediaRepository mediaRepository;
    private final MediaUploadHelper mediaUploadHelper;

    private static final String APPLICATION_NAME = "YouTube Upload Service";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Arrays.asList(YouTubeScopes.YOUTUBE_UPLOAD);
    private static final String CLIENT_SECRETS_FILE = "src/main/resources/client_secrets.json";
    private static final String CREDENTIALS_DIRECTORY = "/Users/bhavyajain/Downloads/oauth-credentials";

    public YoutubeUploadServiceImpl(MediaRepository mediaRepository, MediaUploadHelper mediaUploadHelper) {
        this.mediaRepository = mediaRepository;
        this.mediaUploadHelper = mediaUploadHelper;
    }


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

    public void publishVideo(String mediaId) throws IOException {

        Media media = mediaRepository.findById(Integer.valueOf(mediaId)).orElseThrow(() -> new RuntimeException("Media not found"));
        YouTube youtubeService = getService();

        Video video = new Video();
        VideoSnippet snippet = new VideoSnippet();
        snippet.setTitle(media.getTitle());
        snippet.setDescription(media.getDescription());
        snippet.setCategoryId(media.getCategory());
        snippet.setTags(Arrays.asList(media.getKeywords().split(",")));

        VideoStatus status = new VideoStatus();
        status.setPrivacyStatus(media.getPrivacyStatus());

        video.setSnippet(snippet);
        video.setStatus(status);

        YouTube.Videos.Insert videoInsert = youtubeService.videos().insert(
                "snippet,status",video,
                new FileContent("video/*", new java.io.File(media.getEditedUrl()))
        );

        Video returnedVideo = videoInsert.execute();
        System.out.println("Video uploaded: " + returnedVideo.getId());
    }

    @SneakyThrows
    public void uploadVideo(UploadVideoRequest uploadVideoRequest) {

        Media media = mediaRepository.save(Media.builder()
                        .creatorId(uploadVideoRequest.getCreatorId())
                        .editorId(uploadVideoRequest.getEditorId())
                        .mediaType(VIDEO)
                        .scheduledTime(uploadVideoRequest.getScheduledTime().atZone(ZoneOffset.ofHoursMinutes(5, 30)).toEpochSecond())
                        .category(uploadVideoRequest.getVideoData().getCategory())
                        .description(uploadVideoRequest.getVideoData().getDescription())
                        .keywords(uploadVideoRequest.getVideoData().getKeywords())
                        .privacyStatus(uploadVideoRequest.getVideoData().getPrivacyStatus())
                        .title(uploadVideoRequest.getVideoData().getTitle())
                .build());
        mediaUploadHelper.uploadMediaToServer(uploadVideoRequest.getVideoData().getFile(), media);

    }
}
