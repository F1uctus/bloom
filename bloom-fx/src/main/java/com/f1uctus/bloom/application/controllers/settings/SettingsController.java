package com.f1uctus.bloom.application.controllers.settings;

import com.f1uctus.bloom.application.controllers.ReactiveController;
import cyclops.data.Seq;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class SettingsController extends ReactiveController {
    @FXML TabPane tabPane;
    @FXML GridPane pluginGrid;

    Seq<Label> plugins;

    @Override
    public void initialize() {
        plugins = Seq.fromIterable(List.of()).map(p -> new Label(p.getClass().getSimpleName()));
        tabPane.layoutBoundsProperty().addListener((o, old, upd) -> {
            var cols = recomputeGridColumns(upd, plugins.toList());
            pluginGrid.getChildren().clear();
            pluginGrid.getRowConstraints().clear();
            pluginGrid.getColumnConstraints().setAll(cols);
            for (var row : Seq.fromIterable(plugins).grouped(cols.size()).zipWithIndex()) {
                pluginGrid.getRowConstraints().add(fixedHeightRow());
                pluginGrid.addRow(row._2().intValue(), row._1().toArray(Node[]::new));
            }
        });
    }

    public void setTab(Tab tab) {
        tabPane.getSelectionModel().select(tab.ordinal());
    }

    @Override
    public Stage getStage() {
        return (Stage) tabPane.getScene().getWindow();
    }

    private static List<ColumnConstraints> recomputeGridColumns(
        Bounds container,
        List<? extends Region> items
    ) {
        if (items.isEmpty()) {
            return List.of();
        }
        var minNodeWidth = items.get(0).getMinWidth();
        var columnCount = (int) (container.getWidth() / minNodeWidth);
        return Stream.generate(() -> {
            var c = new ColumnConstraints();
            c.setPercentWidth(100d / columnCount);
            c.setHgrow(Priority.ALWAYS);
            return c;
        }).limit(columnCount).toList();
    }

    private static RowConstraints fixedHeightRow() {
        var rc = new RowConstraints();
        rc.setVgrow(Priority.NEVER);
        return rc;
    }

    public enum Tab {
        MAIN,
        PLUGINS;
    }
}
