package com.f1uctus.bloom.application.controllers.workflows;

import com.f1uctus.bloom.application.controllers.StageReadyEvent;
import com.f1uctus.bloom.core.persistence.models.User;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Value;

@Value
public class WorkflowsStageReady implements StageReadyEvent<WorkflowsController> {
    Class<WorkflowsController> controllerClass = WorkflowsController.class;
    Stage stage;
    User user;

    public WorkflowsStageReady(User user) {
        stage = new Stage();
        stage.setTitle("Workflows");
        stage.initModality(Modality.APPLICATION_MODAL);
        this.user = user;
    }

    @Override public void setupController(WorkflowsController controller) {
        controller.setUser(user);
        controller.afterSetup();
    }
}
