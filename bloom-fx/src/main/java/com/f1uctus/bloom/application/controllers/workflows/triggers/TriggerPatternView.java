package com.f1uctus.bloom.application.controllers.workflows.triggers;

import com.dooapp.fxform.FXForm;
import com.dooapp.fxform.builder.FXFormBuilder;
import com.f1uctus.bloom.core.persistence.models.Trigger;
import com.f1uctus.bloom.core.plugins.PluginRepository;
import com.f1uctus.bloom.plugins.coreinterface.events.ActivationPattern;
import com.f1uctus.bloom.plugins.coreinterface.events.EventPlugin;
import com.google.common.collect.Iterators;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static javafx.beans.binding.Bindings.createObjectBinding;
import static javafx.collections.FXCollections.observableArrayList;

public class TriggerPatternView extends HBox {
    @Getter final Property<ActivationPattern<?>> pattern = new SimpleObjectProperty<>();
    final Trigger trigger;

    public TriggerPatternView(PluginRepository plugins, Trigger trigger) {
        this.trigger = trigger;
        var eventType = new ComboBox<ActivationPattern<?>>();
        eventType.setItems(observableArrayList(plugins.findAllEventPlugins().stream()
            .map(EventPlugin::patternTemplate)
            .map(t -> trigger.getProperties() != null && trigger.getPropertiesType() == t.getClass()
                ? trigger.getProperties() : t)
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
        this.pattern.bind(eventType.getSelectionModel().selectedItemProperty());
        if (trigger.getProperties() != null) {
            eventType.getSelectionModel().select(trigger.getProperties());
        }

        var button = new Button("Edit");
        button.visibleProperty().bind(createObjectBinding(
            () -> this.pattern.getValue() != null,
            this.pattern
        ));
        button.setOnAction(e -> inflate());

        getChildren().addAll(new Label("Type"), eventType, button);
    }

    void inflate() {
        var form = (FXForm<?>) new FXFormBuilder<>()
            .buffered(true, true)
            .source(pattern.getValue())
            .build();
        var stage = new Stage();
        stage.setScene(new Scene(form));
        stage.setOnCloseRequest(event -> {
            if (!form.isValid()) {
                new Alert(Alert.AlertType.ERROR, form.getConstraintViolations()
                    .stream()
                    .map(v -> Iterators.getLast(v.getPropertyPath().iterator())
                              + " "
                              + v.getMessage())
                    .collect(Collectors.joining("\n"))
                ).showAndWait();
                return;
            }
            form.commit();
            trigger.setProperties(pattern.getValue());
        });
        stage.show();
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
