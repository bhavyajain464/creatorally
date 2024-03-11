package com.creatorally.centralplatform.services;

import com.creatorally.centralplatform.models.requests.UploadVideoRequest;

public interface YoutubeUploadService {

    void uploadVideo(UploadVideoRequest uploadVideoRequest);
}
