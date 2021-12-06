package com.f1uctus.bloom.application.controllers.main;

import com.f1uctus.bloom.application.controllers.ReactiveController;
import com.f1uctus.bloom.application.controllers.settings.SettingsController;
import com.f1uctus.bloom.application.controllers.settings.SettingsStageReady;
import com.f1uctus.bloom.application.controllers.workflows.WorkflowsStageReady;
import com.f1uctus.bloom.core.persistence.models.User;
import com.f1uctus.bloom.plugins.coreinterface.events.EventPlugin;
import com.f1uctus.bloom.plugins.fxinterface.common.Reactives;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MainController extends ReactiveController {
    @FXML TextField commandBox;

    @Setter User user;

    @Override public void initialize() {
        subscriptions.add(context.getBeansOfType(EventPlugin.class).values().stream()
            .map(ep -> (EventPlugin<?>) ep)
            .map(EventPlugin::events)
            .map(Reactives::toObservable)
            .reduce(Observable.empty(), Observable::concat)
            .subscribeOn(Schedulers.io())
            .doOnNext(context::publishEvent)
            .observeOn(JavaFxScheduler.platform())
            .subscribe(
                event -> commandBox.setText("Last event: " + event),
                System.err::println
            ));
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
