package com.f1uctus.bloom.application.controllers.workflows.triggers;

import com.f1uctus.bloom.application.common.CachedBeanStringConverter;
import com.f1uctus.bloom.application.common.controls.CustomControl;
import com.f1uctus.bloom.core.persistence.models.Trigger;
import com.f1uctus.bloom.plugins.coreinterface.events.ActivationPattern;
import com.f1uctus.bloom.plugins.coreinterface.events.EventPlugin;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static javafx.beans.binding.Bindings.createObjectBinding;
import static javafx.collections.FXCollections.observableArrayList;

public class TriggerPatternView extends HBox implements CustomControl {
    @FXML ComboBox<ActivationPattern<?>> eventType;
    @FXML Button editButton;

    public TriggerPatternView(List<EventPlugin<?>> eventPlugins, Trigger trigger) {
        bindFxml();

        eventType.setItems(observableArrayList(eventPlugins.stream()
            .map(EventPlugin::patternTemplate)
            .map(t -> trigger.getProperties() != null
                && t.getClass().isInstance(trigger.getProperties())
                ? trigger.getProperties()
                : t)
            .collect(toList())));
        eventType.setConverter(new CachedBeanStringConverter<>(ActivationPattern::getName));
        eventType.setCellFactory(lv -> makeEmptyByDefaultCell(lv, ActivationPattern::getName));
        var pattern = new SimpleObjectProperty<ActivationPattern<?>>();
        pattern.bind(eventType.getSelectionModel().selectedItemProperty());
        if (trigger.getProperties() != null) {
            eventType.getSelectionModel().select(trigger.getProperties());
        }
        editButton.visibleProperty().bind(createObjectBinding(
            () -> pattern.getValue() != null,
            pattern
        ));
        editButton.setOnAction(e -> showGenericEditorFor(pattern, trigger::setProperties));
    }
}
