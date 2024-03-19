package com.creatorally.centralplatform.services;

import com.creatorally.centralplatform.models.entities.Media;
import com.creatorally.centralplatform.models.requests.UploadVideoRequest;
import java.io.IOException;

public interface YoutubeUploadService {

    void uploadVideo(UploadVideoRequest uploadVideoRequest);

    void publishVideo(String mediaId) throws IOException;

    void getCredentials(Integer creatorId);

    Media verifyMedia(Integer mediaId, Integer creatorId);
}
