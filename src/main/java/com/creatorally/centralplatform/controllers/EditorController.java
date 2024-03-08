package com.creatorally.centralplatform.controllers;

import com.creatorally.centralplatform.models.entities.Editor;
import com.creatorally.centralplatform.models.requests.CreateEditorRequest;
import com.creatorally.centralplatform.models.responses.GetEditorResponse;
import com.creatorally.centralplatform.repository.EditorRespository;
import com.creatorally.centralplatform.services.EditorService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController("/editor")
public class EditorController {

    private final EditorService editorService;
    public EditorController(EditorService editorService) {
        this.editorService = editorService;
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
}
