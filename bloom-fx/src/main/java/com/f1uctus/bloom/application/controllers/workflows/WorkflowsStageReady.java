package com.f1uctus.bloom.application.controllers.workflows;

import com.f1uctus.bloom.application.controllers.StageReadyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Value;

@Value
public class WorkflowsStageReady implements StageReadyEvent<WorkflowsController> {
    Class<WorkflowsController> controllerClass = WorkflowsController.class;
    Stage stage;

    public WorkflowsStageReady() {
        stage = new Stage();
        stage.setTitle("Workflows");
        stage.initModality(Modality.APPLICATION_MODAL);
    }
}
