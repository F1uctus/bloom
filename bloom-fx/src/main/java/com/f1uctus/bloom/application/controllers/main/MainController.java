package com.f1uctus.bloom.application.controllers.main;

import com.f1uctus.bloom.application.controllers.ReactiveController;
import com.f1uctus.bloom.application.controllers.settings.SettingsController;
import com.f1uctus.bloom.application.controllers.settings.SettingsStageReady;
import com.f1uctus.bloom.application.controllers.workflows.WorkflowsStageReady;
import com.f1uctus.bloom.core.persistence.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainController extends ReactiveController {
    @FXML TextField commandBox;

    @Setter User user;

    @Override public void initialize() {
//        subscriptions.add(Reactives.toObservable(plugin.events())
//            .subscribeOn(Schedulers.io())
//            .filter(e -> !e.getText().equals(commandBox.getText()))
//            .observeOn(JavaFxScheduler.platform())
//            .subscribe(event -> {
//                var value = event.toString();
//                if (value.contains("создать")) {
//                    var f = new FXForm<>(event);
//                    var dialog = new Stage();
//                    dialog.initOwner(getStage());
//                    dialog.setScene(new Scene(f));
//                    dialog.show();
//                    dialog.setOnCloseRequest(e -> {
//                        f.commit();
//                    });
//                }
//                if (value.contains("выход")) {
//                    terminate();
//                }
//                commandBox.setText(value);
//            }, System.err::println));
    }

    public void onEditWorkflowsItemClick(ActionEvent event) {
        context.publishEvent(new WorkflowsStageReady(user));
    }

    public void onEditMenuPluginsItemClick(ActionEvent event) {
        context.publishEvent(new SettingsStageReady(user, SettingsController.Tab.PLUGINS));
    }

    @Override public Stage getStage() {
        return (Stage) commandBox.getScene().getWindow();
    }
}
