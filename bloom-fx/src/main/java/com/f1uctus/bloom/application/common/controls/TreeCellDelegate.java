package com.f1uctus.bloom.application.common.controls;

public interface TreeCellDelegate<T> {
    Class<?> getItemClass();

    void setCell(DelegatingTreeCell<T> cell);

    default void startEdit() {
        updateItem();
    }

    default void cancelEdit() {
        updateItem();
    }

    void updateItem();
}
