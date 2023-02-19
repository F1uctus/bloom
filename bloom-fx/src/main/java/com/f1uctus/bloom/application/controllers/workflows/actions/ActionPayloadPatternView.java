package com.f1uctus.bloom.application.controllers.workflows.actions;

import com.dooapp.fxform.FXForm;
import com.dooapp.fxform.builder.FXFormBuilder;
import com.f1uctus.bloom.core.persistence.models.Action;
import com.f1uctus.bloom.plugins.coreinterface.actions.ActionPayloadPattern;
import com.f1uctus.bloom.plugins.coreinterface.actions.ActionPlugin;
import com.google.common.collect.Iterators;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static javafx.beans.binding.Bindings.createObjectBinding;
import static javafx.collections.FXCollections.observableArrayList;

public class ActionPayloadPatternView extends HBox {
    @Getter final Property<ActionPayloadPattern> pattern = new SimpleObjectProperty<>();
    final Action action;

    public ActionPayloadPatternView(List<ActionPlugin<?>> actionPlugins, Action action) {
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(5);

        this.action = action;

        var eventType = new ComboBox<ActionPayloadPattern>();
        eventType.setItems(observableArrayList(actionPlugins.stream()
            .map(ActionPlugin::payloadTemplate)
            .map(pattern -> action.getProperties() != null
                && pattern.getClass().isInstance(action.getProperties())
                ? action.getProperties()
                : pattern)
            .collect(toList())));
        eventType.setConverter(new ActionTypeConverter());
        eventType.setCellFactory(new Callback<>() {
            @Override
            public ListCell<ActionPayloadPattern> call(ListView<ActionPayloadPattern> l) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(ActionPayloadPattern item, boolean empty) {
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
        if (action.getProperties() != null) {
            eventType.getSelectionModel().select(action.getProperties());
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
            action.setProperties(pattern.getValue());
        });
        stage.show();
    }

    public static class ActionTypeConverter extends StringConverter<ActionPayloadPattern> {
        static Map<String, ActionPayloadPattern> cache = new HashMap<>();

        @Override
        public ActionPayloadPattern fromString(String s) {
            return cache.get(s);
        }

        @Override
        public String toString(ActionPayloadPattern object) {
            if (object == null) {
                return null;
            }
            cache.put(object.getName(), object);
            return object.getName();
        }
    }
}
