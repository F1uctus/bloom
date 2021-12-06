package com.f1uctus.bloom.application.controllers.workflows.triggers;

import com.f1uctus.bloom.application.common.controls.DelegatingTreeCell;
import com.f1uctus.bloom.application.common.controls.TreeCellDelegate;
import com.f1uctus.bloom.core.persistence.models.Trigger;
import com.f1uctus.bloom.plugins.coreinterface.events.EventPlugin;
import javafx.scene.Node;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TriggerTreeCell implements TreeCellDelegate<Trigger> {
    @Getter final Class<?> itemClass = Trigger.class;
    final List<EventPlugin<?>> eventPlugins;
    DelegatingTreeCell<Trigger> cell;
    Node properties;

    @Override public void setCell(DelegatingTreeCell<Trigger> cell) {
        this.cell = cell;
    }

    @Override public void startEdit() {
        if (properties == null) {
            properties = new TriggerPatternView(eventPlugins, cell.getItem());
        }
        updateItem();
    }

    @Override public void updateItem() {
        if (cell.isEditing()) {
            cell.setText(null);
            cell.setGraphic(properties);
        } else {
            cell.setText(getString());
            cell.setGraphic(cell.getTreeItem().getGraphic());
        }
    }

    private String getString() {
        var item = cell.getItem();
        return item == null || item.getProperties() == null
            ? "New trigger"
            : item.getProperties().toString();
    }
}
