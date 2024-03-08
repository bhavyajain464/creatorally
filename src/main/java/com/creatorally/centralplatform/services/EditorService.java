package com.creatorally.centralplatform.services;

import com.creatorally.centralplatform.models.entities.Editor;
import com.creatorally.centralplatform.models.requests.CreateEditorRequest;
import com.creatorally.centralplatform.models.responses.GetAllEditorsResponse;
import com.creatorally.centralplatform.models.responses.GetEditorResponse;


public interface EditorService {

    GetAllEditorsResponse getAllEditors();


    GetEditorResponse getEditorById(int id);

    GetEditorResponse createEditor(CreateEditorRequest createEditorRequest);
}
