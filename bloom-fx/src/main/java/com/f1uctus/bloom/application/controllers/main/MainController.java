package com.f1uctus.bloom.application.controllers.main;

import com.f1uctus.bloom.application.common.collections.ObservableLimitedList;
import com.f1uctus.bloom.application.controllers.ReactiveController;
import com.f1uctus.bloom.application.controllers.settings.SettingsController;
import com.f1uctus.bloom.application.controllers.settings.SettingsStageReady;
import com.f1uctus.bloom.application.controllers.workflows.WorkflowsStageReady;
import com.f1uctus.bloom.core.events.WorkflowMatchEvent;
import com.f1uctus.bloom.core.persistence.models.User;
import com.f1uctus.bloom.plugins.coreinterface.events.Event;
import com.f1uctus.bloom.plugins.coreinterface.events.EventPlugin;
import com.f1uctus.bloom.plugins.fxinterface.common.Reactives;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component @RequiredArgsConstructor public class MainController extends ReactiveController {
    static final DateTimeFormatter logTimeFormat = DateTimeFormatter
        .ofPattern("HH:mm:ss")
        .withZone(ZoneId.systemDefault());

    @FXML TextField commandBox;
    @FXML TableView<Pair<Instant, String>> logArea;

    @Setter User user;

    @Override public void initialize() {
        logArea.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        var logTimeColumn = new TableColumn<Pair<Instant, String>, String>();
        logTimeColumn.setMinWidth(100);
        logTimeColumn.setMaxWidth(100);
        logTimeColumn.setCellValueFactory(cd -> new SimpleStringProperty(
            logTimeFormat.format(cd.getValue().getKey())
        ));

        var logContentColumn = new TableColumn<Pair<Instant, String>, String>();
        logContentColumn.setCellValueFactory(cd -> new SimpleStringProperty(
            cd.getValue().getValue()
        ));

        logArea.getColumns().addAll(logTimeColumn, logContentColumn);

        logArea.setItems(new ObservableLimitedList<>(100));

        subscriptions.add(context.getBeansOfType(EventPlugin.class)
            .values()
            .stream()
            .map(ep -> (EventPlugin<?>) ep)
            .map(EventPlugin::events)
            .map(Reactives::toObservable)
            .reduce(Observable.empty(), Observable::concat)
            .subscribeOn(Schedulers.io())
            .doOnNext(context::publishEvent)
            .observeOn(JavaFxScheduler.platform())
            .subscribe(event -> commandBox.setText("Last event: " + event), System.err::println));
    }

    @EventListener public void on(Event event) {
        logArea.getItems().add(new Pair<>(
            Instant.now(),
            "Received an event: " + event
        ));
    }

    @EventListener public void on(WorkflowMatchEvent event) {
        logArea.getItems().add(new Pair<>(
            Instant.now(),
            "Executed workflow: " + event.getWorkflow().getName()
        ));
    }

    public void onEditWorkflowsItemClick(ActionEvent event) {
        context.publishEvent(new WorkflowsStageReady(user));
    }

    public void onEditMenuPluginsItemClick(ActionEvent event) {
        context.publishEvent(new SettingsStageReady(user, SettingsController.Tab.PLUGINS));
    }

    @Override public void terminate() {
        super.terminate();
    }

    @Override public Stage getStage() {
        return (Stage) commandBox.getScene().getWindow();
    }
}
