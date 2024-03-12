package com.creatorally.centralplatform.controllers;

import com.creatorally.centralplatform.models.entities.Editor;
import com.creatorally.centralplatform.models.requests.CreateEditorRequest;
import com.creatorally.centralplatform.models.requests.UploadVideoRequest;
import com.creatorally.centralplatform.models.responses.GetEditorResponse;
import com.creatorally.centralplatform.repository.EditorRespository;
import com.creatorally.centralplatform.services.EditorService;
import com.creatorally.centralplatform.services.YoutubeUploadService;
import com.creatorally.centralplatform.services.impl.YoutubeUploadServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController("/editor")
@Slf4j
public class EditorController {

    private final EditorService editorService;
    private final YoutubeUploadServiceImpl youtubeUploadServiceImp;

    public EditorController(EditorService editorService, YoutubeUploadServiceImpl youtubeUploadServiceImp) {

        this.editorService = editorService;
        this.youtubeUploadServiceImp = youtubeUploadServiceImp;
    }

    @PostMapping("/api/1.0/editor")
    public GetEditorResponse createEditor(@RequestBody CreateEditorRequest createEditorRequest) {
        return editorService.createEditor(createEditorRequest);
    }
    @GetMapping(value = {"/api/1.0/editors", "/api/1.0/editors/{id}"}, produces = "application/json")
    public Object getEditor(@PathVariable(name="id", required = false) @Validated Integer id) {
        if(Objects.isNull(id)){
            return editorService.getAllEditors();
        }
        return editorService.getEditorById(id);
    }

    @PostMapping("/api/1.0/editor/upload/{id}")
    public String uploadVideo(@PathVariable Integer id, @ModelAttribute UploadVideoRequest uploadVideoRequest){
        youtubeUploadServiceImp.uploadVideoByEditor(id, uploadVideoRequest);
        return "Editor uploaded video successfully";
    }
}
