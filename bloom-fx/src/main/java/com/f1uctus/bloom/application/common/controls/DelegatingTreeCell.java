package com.f1uctus.bloom.application.common.controls;

import javafx.scene.control.TreeCell;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class DelegatingTreeCell<T> extends TreeCell<T> {
    final Map<Class<?>, Supplier<TreeCellDelegate<?>>> delegateCandidates;
    TreeCellDelegate<T> delegate;

    @Override
    public void startEdit() {
        super.startEdit();
        delegate.startEdit();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        delegate.cancelEdit();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (delegate == null
                || !delegate.getItemClass().equals(item.getClass())) {
                delegate = (TreeCellDelegate<T>) delegateCandidates.get(getItem().getClass()).get();
                delegate.setCell(this);
            }
            delegate.updateItem();
        }
    }
}
