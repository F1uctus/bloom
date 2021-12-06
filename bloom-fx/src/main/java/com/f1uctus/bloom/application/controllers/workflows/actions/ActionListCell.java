package com.f1uctus.bloom.application.controllers.workflows.actions;

import com.f1uctus.bloom.core.persistence.models.Action;
import com.f1uctus.bloom.core.persistence.models.Trigger;
import com.f1uctus.bloom.plugins.coreinterface.actions.ActionPlugin;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import lombok.Getter;

import java.util.List;

public class ActionListCell extends ListCell<Action> {
    @Getter final Class<?> itemClass = Trigger.class;
    final List<ActionPlugin<?>> actionPlugins;
    Node properties;

    public ActionListCell(List<ActionPlugin<?>> actionPlugins) {
        this.actionPlugins = actionPlugins;
    }

    @Override public void startEdit() {
        super.startEdit();
        if (properties == null) {
            properties = new ActionPayloadPatternView(actionPlugins, getItem());
        }
        setText(null);
        setGraphic(properties);
    }

    @Override public void cancelEdit() {
        super.cancelEdit();
        setText(getString());
        setGraphic(null);
    }

    @Override public void updateItem(Action item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            setText(null);
            setGraphic(properties);
        } else {
            setText(getString());
            setGraphic(null);
        }
    }

    private String getString() {
        var item = getItem();
        return item == null || item.getProperties() == null
            ? "New action"
            : item.getProperties().toString();
    }
}
