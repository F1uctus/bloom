package com.f1uctus.bloom.application.common.controls;

public interface TreeCellDelegate<T> {
    Class<?> getItemClass();

    void setCell(DelegatingTreeCell<T> cell);

    void startEdit();

    void cancelEdit();

    void updateItem(T item);
}
