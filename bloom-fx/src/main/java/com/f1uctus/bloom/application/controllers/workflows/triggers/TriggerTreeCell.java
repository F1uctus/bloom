package com.f1uctus.bloom.application.controllers.workflows.triggers;

import com.f1uctus.bloom.application.common.controls.DelegatingTreeCell;
import com.f1uctus.bloom.application.common.controls.TreeCellDelegate;
import com.f1uctus.bloom.core.persistence.models.Trigger;
import com.f1uctus.bloom.core.plugins.PluginRepository;
import javafx.scene.Node;
import lombok.Getter;

public class TriggerTreeCell implements TreeCellDelegate<Trigger> {
    @Getter final Class<?> itemClass = Trigger.class;
    final PluginRepository plugins;
    DelegatingTreeCell<Trigger> cell;
    Node properties;

    public TriggerTreeCell(PluginRepository plugins) {
        this.plugins = plugins;
    }

    @Override public void setCell(DelegatingTreeCell<Trigger> cell) {
        this.cell = cell;
    }

    @Override public void startEdit() {
        if (properties == null) {
            properties = new TriggerPatternView(plugins, cell.getItem());
        }
        cell.setText(null);
        cell.setGraphic(properties);
    }

    @Override public void cancelEdit() {
        cell.setText(getString());
        cell.setGraphic(cell.getTreeItem().getGraphic());
    }

    @Override public void updateItem(Trigger item) {
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
        return item == null ? "" : item.getName();
    }
}
