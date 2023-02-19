package com.f1uctus.bloom.application.controllers.workflows;

import com.f1uctus.bloom.application.controllers.ReactiveController;
import com.f1uctus.bloom.application.controllers.workflows.actions.ActionListCell;
import com.f1uctus.bloom.core.persistence.models.Action;
import com.f1uctus.bloom.core.persistence.models.Workflow;
import com.f1uctus.bloom.plugins.coreinterface.actions.ActionPlugin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableArrayList;

@Component
@RequiredArgsConstructor
public class WorkflowEditController extends ReactiveController {
    @FXML TextField name;
    @FXML ListView<Action> actionsList;

    List<ActionPlugin<?>> actionPlugins;

    @Setter Workflow workflow;

    @Override
    public void initialize() {
        actionPlugins = context.getBeansOfType(ActionPlugin.class).values().stream()
            .map(ap -> (ActionPlugin<?>) ap).collect(toList());
        actionsList.setEditable(true);
        actionsList.setCellFactory(lv -> new ActionListCell(actionPlugins));
    }

    @Override
    public void afterSetup() {
        name.setText(workflow.getName());
        actionsList.setItems(observableArrayList(workflow.getActions()));

        getStage().setOnCloseRequest(we -> {
            workflow.setName(name.getText());
        });
    }

    @Override
    public Stage getStage() {
        return (Stage) actionsList.getScene().getWindow();
    }

    public void onAddAction(ActionEvent event) {
        var action = new Action(workflow.getUser());
        actionsList.getItems().add(action);
        workflow.getActions().add(action);
    }

    public void onRemoveAction(ActionEvent event) {
        var action = actionsList.getSelectionModel().getSelectedItem();
        actionsList.getItems().remove(action);
        workflow.getActions().remove(action);
    }
}
