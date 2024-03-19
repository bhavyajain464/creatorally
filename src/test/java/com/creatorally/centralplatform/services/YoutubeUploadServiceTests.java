package com.creatorally.centralplatform.services;


import com.creatorally.centralplatform.models.entities.Media;
import com.creatorally.centralplatform.repository.MediaRepository;
import com.creatorally.centralplatform.services.impl.YoutubeUploadServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class YoutubeUploadServiceTests {

    @InjectMocks
    private YoutubeUploadServiceImpl youtubeUploadService;

    @Mock
    private MediaRepository mediaRepository;
    @Test
    void Test1(){
        when(mediaRepository.getMediaById(4)).thenReturn(Optional.ofNullable(Media.builder().category("Food").creatorId(5).build()));
        Media media = youtubeUploadService.verifyMedia(4, 5);
        assertEquals(true, media.getIsVerified());
        verify(mediaRepository,times(1)).save(any(Media.class));
    }
}
