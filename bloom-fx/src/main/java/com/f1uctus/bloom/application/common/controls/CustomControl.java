package com.f1uctus.bloom.application.common.controls;

import com.dooapp.fxform.FXForm;
import com.dooapp.fxform.builder.FXFormBuilder;
import com.google.common.collect.Iterators;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public interface CustomControl {
    default void bindFxml() {
        var fl = new FXMLLoader(getClass().getResource(getClass().getSimpleName() + ".fxml"));
        fl.setRoot(this);
        fl.setController(this);
        try {
            fl.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    default <T> void showGenericEditorFor(ObservableValue<T> observable, Consumer<T> onUpdate) {
        var form = (FXForm<?>) new FXFormBuilder<>()
            .buffered(true, true)
            .source(observable.getValue())
            .build();
        var stage = new Stage();
        stage.setScene(new Scene(form));
        stage.setOnCloseRequest(event -> {
            form.commit();
            if (form.isValid()) {
                onUpdate.accept(observable.getValue());
            } else {
                new Alert(Alert.AlertType.ERROR, form.getConstraintViolations()
                    .stream()
                    .map(v -> Iterators.getLast(v.getPropertyPath().iterator())
                        + " "
                        + v.getMessage())
                    .collect(Collectors.joining("\n"))
                ).showAndWait();
            }
        });
        stage.show();
    }
}
