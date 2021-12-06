package com.f1uctus.bloom.application.controllers.workflows;

import com.f1uctus.bloom.application.controllers.StageReadyEvent;
import com.f1uctus.bloom.core.persistence.models.Workflow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Value;

@Value
public class WorkflowEditStageReady implements StageReadyEvent<WorkflowEditController> {
    Class<WorkflowEditController> controllerClass = WorkflowEditController.class;
    Stage stage;
    Workflow workflow;

    public WorkflowEditStageReady(Workflow workflow) {
        stage = new Stage();
        stage.setTitle("Edit workflow");
        stage.initModality(Modality.APPLICATION_MODAL);
        this.workflow = workflow;
    }

    @Override public void setupController(WorkflowEditController controller) {
        controller.setWorkflow(workflow);
        controller.afterSetup();
    }
}
