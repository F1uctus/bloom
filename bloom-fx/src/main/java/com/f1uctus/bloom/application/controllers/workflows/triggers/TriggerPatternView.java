package com.f1uctus.bloom.application.controllers.workflows.triggers;

import com.f1uctus.bloom.application.common.controls.CustomControl;
import com.f1uctus.bloom.core.persistence.models.Trigger;
import com.f1uctus.bloom.plugins.coreinterface.events.ActivationPattern;
import com.f1uctus.bloom.plugins.coreinterface.events.EventPlugin;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.*;

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
        eventType.setConverter(new TriggerTypeConverter());
        eventType.setCellFactory(new Callback<>() {
            @Override
            public ListCell<ActivationPattern<?>> call(ListView<ActivationPattern<?>> l) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(ActivationPattern<?> item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });
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

    public static class TriggerTypeConverter extends StringConverter<ActivationPattern<?>> {
        static Map<String, ActivationPattern<?>> cache = new HashMap<>();

        @Override
        public ActivationPattern<?> fromString(String s) {
            return cache.get(s);
        }

        @Override
        public String toString(ActivationPattern<?> object) {
            if (object == null) {
                return null;
            }
            cache.put(object.getName(), object);
            return object.getName();
        }
    }
}
