package com.f1uctus.bloom.application.controllers.workflows.cells;

import com.f1uctus.bloom.application.common.controls.DelegatingTreeCell;
import com.f1uctus.bloom.application.common.controls.TreeCellDelegate;
import com.f1uctus.bloom.core.persistence.models.Workflow;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorkflowTreeCell implements TreeCellDelegate<Workflow<?>> {
    DelegatingTreeCell<Workflow<?>> cell;
    TextField textField;

    @Override public void setCell(DelegatingTreeCell<Workflow<?>> cell) {
        this.cell = cell;
    }

    @Override public void startEdit() {
        if (textField == null) {
            createTextField();
        }
        cell.setText(null);
        cell.setGraphic(textField);
        textField.selectAll();
    }

    @Override public void cancelEdit() {
        cell.setText(getString());
        cell.setGraphic(cell.getTreeItem().getGraphic());
    }

    @Override public void updateItem(Workflow<?> item) {
        if (cell.isEditing()) {
            if (textField != null) {
                textField.setText(getString());
            }
            cell.setText(null);
            cell.setGraphic(textField);
        } else {
            cell.setText(getString());
            cell.setGraphic(cell.getTreeItem().getGraphic());
        }
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(ke -> {
            if (ke.getCode() == KeyCode.ENTER) {
                var item = cell.getItem().setName(textField.getText());
                cell.commitEdit(item);
                updateItem(item);
            } else if (ke.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private String getString() {
        var item = cell.getItem();
        return item == null ? "" : item.getName();
    }
}
