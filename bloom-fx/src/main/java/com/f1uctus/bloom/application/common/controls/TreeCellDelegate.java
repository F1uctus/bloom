package com.f1uctus.bloom.application.common.controls;

public interface TreeCellDelegate<T> {
    void setCell(DelegatingTreeCell<T> cell);

    void startEdit();

    void cancelEdit();

    void updateItem(T item);
}
