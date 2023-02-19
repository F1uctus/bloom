package com.f1uctus.bloom.application.controllers.workflows.actions;

import com.f1uctus.bloom.application.common.controls.DelegatingTreeCell;
import com.f1uctus.bloom.application.common.controls.TreeCellDelegate;
import com.f1uctus.bloom.application.controllers.workflows.WorkflowEditStageReady;
import com.f1uctus.bloom.core.persistence.models.Workflow;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
public class WorkflowTreeCell implements TreeCellDelegate<Workflow> {
    @Getter final Class<?> itemClass = Workflow.class;
    final ApplicationContext context;
    DelegatingTreeCell<Workflow> cell;

    @Override
    public void setCell(DelegatingTreeCell<Workflow> cell) {
        this.cell = cell;
    }

    @Override
    public void startEdit() {
        context.publishEvent(new WorkflowEditStageReady(cell.getItem()));
    }

    @Override
    public void updateItem() {
        cell.setText(getString());
//        cell.setGraphic(cell.getTreeItem().getGraphic());
    }

    private String getString() {
        var item = cell.getItem();
        return item == null ? "" : item.getName();
    }
}
