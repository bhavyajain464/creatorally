package com.creatorally.centralplatform.services.impl;

import com.creatorally.centralplatform.models.entities.Editor;
import com.creatorally.centralplatform.models.entities.Media;
import com.creatorally.centralplatform.models.requests.CreateEditorRequest;
import com.creatorally.centralplatform.models.responses.GetAllEditorsResponse;
import com.creatorally.centralplatform.models.responses.GetEditorResponse;
import com.creatorally.centralplatform.repository.EditorRespository;
import com.creatorally.centralplatform.repository.MediaRepository;
import com.creatorally.centralplatform.services.EditorService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EditorServiceImpl implements EditorService {

    private final EditorRespository editorRespository;
    private final MediaRepository mediaRepository;

    public EditorServiceImpl(EditorRespository editorRespository, MediaRepository mediaRepository) {
        this.editorRespository = editorRespository;
        this.mediaRepository = mediaRepository;
    }

    @Override
    public GetAllEditorsResponse getAllEditors() {
        List<Editor> editors = editorRespository.findAll();
        List<GetAllEditorsResponse.EditorInfo> editorList = editors.stream()
                .map(e -> GetAllEditorsResponse.EditorInfo.builder().id(e.getId()).username(e.getUsername()).build()).toList();
        return GetAllEditorsResponse.builder().getEditorResponses(editorList).build();
    }

    @Override
    @SneakyThrows
    public GetEditorResponse getEditorById(int id) {
        Optional<Editor> editorOp = editorRespository.findById(id);
        if(editorOp.isEmpty()){
            throw new Exception("nhi hai bhaiya");
        }
        Editor editor = editorOp.get();
        List<Media> allMedia = mediaRepository.getAllMedia(id);

        return GetEditorResponse.builder().name(editor.getName()).username(editor.getUsername()).media(allMedia).id(editor.getId()).build();
    }

    @Override
    public GetEditorResponse createEditor(CreateEditorRequest createEditorRequest) {
        Editor editor = Editor.builder().name(createEditorRequest.getName())
                .password(createEditorRequest.getPassword())
                .username(createEditorRequest.getUsername())
                .build();

        Editor editorId = editorRespository.save(editor);
        return GetEditorResponse.builder().media(Collections.emptyList())
                .id(editorId.getId())
                .name(editorId.getName())
                .username(editorId.getUsername())
                .build();
//        return null;
    }
}
