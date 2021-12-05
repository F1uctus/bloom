package com.f1uctus.bloom.application.common.controls;

import javafx.scene.control.TreeCell;

import java.util.Map;
import java.util.function.Supplier;

public class DelegatingTreeCell<T> extends TreeCell<T> {
    Map<Class<?>, Supplier<TreeCellDelegate<?>>> delegateCandidates;
    TreeCellDelegate<T> delegate;

    public DelegatingTreeCell(
        Map<Class<?>, Supplier<TreeCellDelegate<?>>> delegateCandidates
    ) {
        this.delegateCandidates = delegateCandidates;
    }

    @Override public void startEdit() {
        super.startEdit();
        delegate.startEdit();
    }

    @Override public void cancelEdit() {
        super.cancelEdit();
        delegate.cancelEdit();
    }

    @SuppressWarnings("unchecked")
    @Override public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (delegate == null) {
                delegate = (TreeCellDelegate<T>) delegateCandidates.get(getItem().getClass()).get();
                delegateCandidates = null;
                delegate.setCell(this);
            }
            delegate.updateItem(item);
        }
    }
}
