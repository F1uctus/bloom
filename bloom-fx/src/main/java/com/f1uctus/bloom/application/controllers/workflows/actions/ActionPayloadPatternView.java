package com.f1uctus.bloom.application.controllers.workflows.actions;

import com.f1uctus.bloom.application.common.CachedBeanStringConverter;
import com.f1uctus.bloom.application.common.controls.CustomControl;
import com.f1uctus.bloom.core.persistence.models.Action;
import com.f1uctus.bloom.plugins.coreinterface.actions.ActionPayloadPattern;
import com.f1uctus.bloom.plugins.coreinterface.actions.ActionPlugin;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static javafx.beans.binding.Bindings.createObjectBinding;
import static javafx.collections.FXCollections.observableArrayList;

public class ActionPayloadPatternView extends HBox implements CustomControl {
    @FXML ComboBox<ActionPayloadPattern> eventType;
    @FXML Button editButton;

    public ActionPayloadPatternView(List<ActionPlugin<?>> actionPlugins, Action action) {
        bindFxml();

        eventType.setItems(observableArrayList(actionPlugins.stream()
            .map(ActionPlugin::payloadTemplate)
            .map(t -> action.getProperties() != null
                && t.getClass().isInstance(action.getProperties())
                ? action.getProperties()
                : t)
            .collect(toList())));
        eventType.setConverter(new CachedBeanStringConverter<>(ActionPayloadPattern::getName));
        eventType.setCellFactory(lv -> makeEmptyByDefaultCell(lv, ActionPayloadPattern::getName));
        var pattern = new SimpleObjectProperty<ActionPayloadPattern>();
        pattern.bind(eventType.getSelectionModel().selectedItemProperty());
        if (action.getProperties() != null) {
            eventType.getSelectionModel().select(action.getProperties());
        }
        editButton.visibleProperty().bind(createObjectBinding(
            () -> pattern.getValue() != null,
            pattern
        ));
        editButton.setOnAction(e -> showGenericEditorFor(pattern, action::setProperties));
    }
}
