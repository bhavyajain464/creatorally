package com.creatorally.centralplatform.services.impl;

import static com.creatorally.centralplatform.models.enums.MediaType.VIDEO;
import static com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp.browse;

import com.creatorally.centralplatform.helper.MailHelper;
import com.creatorally.centralplatform.helper.MediaUploadHelper;
import com.creatorally.centralplatform.models.entities.Influencer;
import com.creatorally.centralplatform.models.entities.Media;
import com.creatorally.centralplatform.models.requests.UploadVideoRequest;
import com.creatorally.centralplatform.repository.InfluencerRepository;
import com.creatorally.centralplatform.repository.MediaRepository;
import com.creatorally.centralplatform.services.YoutubeUploadService;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
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
import java.util.Objects;
import java.util.Optional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class YoutubeUploadServiceImpl  implements YoutubeUploadService {

    private final MediaRepository mediaRepository;
    private final InfluencerRepository influencerRepository;
    private final MediaUploadHelper mediaUploadHelper;
    private final MailHelper mailHelper;
    private static final String APPLICATION_NAME = "YouTube Upload Service";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Arrays.asList(YouTubeScopes.YOUTUBE_UPLOAD);
    private static final String CLIENT_SECRETS_FILE = "src/main/resources/client_secrets.json";
    private static final String CREDENTIALS_DIRECTORY = "C:\\Users\\Dell\\OneDrive\\Desktop\\To-new-beginnings\\oauth-credentials";

    public YoutubeUploadServiceImpl(MediaRepository mediaRepository, InfluencerRepository influencerRepository, MediaUploadHelper mediaUploadHelper, MailHelper mailHelper) {
        this.mediaRepository = mediaRepository;
        this.influencerRepository = influencerRepository;
        this.mediaUploadHelper = mediaUploadHelper;
        this.mailHelper = mailHelper;
    }


    @SneakyThrows
    public static YouTube getService(Credential credential) {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


    public void getCredentials(Integer creatorId) {

        authorize(creatorId.toString());
    }


    private void sendEmailToUserToCreateCredentials(Integer creatorId, String link) {
        Optional<Influencer> creatorOp = influencerRepository.findById(creatorId);
        if(creatorOp.isEmpty()) {
            log.error("No influencer exists {} ", creatorId);
            return;
        }
        mailHelper.mailUserToCreateCredentials(creatorOp.get().getEmail(), link);
    }

    public void publishVideo(String mediaId) throws IOException {

        Media media = mediaRepository.findById(Integer.valueOf(mediaId)).orElseThrow(() -> new RuntimeException("Media not found"));
        Credential credential = fetchCredentialLocally(media.getCreatorId());
        if (Objects.isNull(credential)) {
            throw new RuntimeException("Credentials not present");
        }
        YouTube youtubeService = getService(credential);

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
        log.info("Video uploaded: " + returnedVideo.getId());
    }

    @SneakyThrows
    private Credential fetchCredentialLocally(Integer creatorId) {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(CLIENT_SECRETS_FILE)));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_DIRECTORY)))
                .setAccessType("offline")
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).getFlow().loadCredential(creatorId.toString());

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
        mediaUploadHelper.uploadMediaToServer(uploadVideoRequest.getVideoData().getFile(), media, false);

    }

    @SneakyThrows
    public void uploadVideoByEditor(Integer id, UploadVideoRequest uploadVideoRequest) {
        Optional<Media> optionalMedia = mediaRepository.findById(id);
        if(optionalMedia.isEmpty()) {
            log.error("No media exists");
        }

        Media media = optionalMedia.get();
        mediaUploadHelper.uploadMediaToServer(uploadVideoRequest.getVideoData().getFile(), media, true);
    }

    @SneakyThrows
    public void authorize(String userId) {

        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(CLIENT_SECRETS_FILE)));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_DIRECTORY)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver();
        Credential credential = flow.loadCredential(userId);
        if (credential == null || credential.getRefreshToken() == null && credential.getExpiresInSeconds() <= 60L) {
            String redirectUri = receiver.getRedirectUri();
            AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().setRedirectUri("http://localhost:8081/oauth2callback");
            sendEmailToUserToCreateCredentials(Integer.valueOf(userId), authorizationUrl.build());
            browse(authorizationUrl.build());
            String code = receiver.waitForCode();
            TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
            flow.createAndStoreCredential(response, userId);
        }
    }

    @Override
    @SneakyThrows
    public Media verifyMedia(Integer mediaId, Integer creatorId) {
        Optional<Media> mediaOp = mediaRepository.getMediaById(mediaId);
        if(mediaOp.isEmpty()){
            throw new Exception("chaunk gye bhaiya?");
        }
        Media media = mediaOp.get();
        if(!Objects.equals(creatorId, mediaOp.get().getCreatorId())){
            throw new Exception("invalid media");
        }
        media.setIsVerified(true);
        return mediaRepository.save(media);
    }

}
