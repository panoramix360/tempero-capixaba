package com.creativityloop.android.temperocapixaba.model;

/**
 * Created by lucasreis3000 on 28/06/16.
 */
public enum StatusPedido {
    NAO_ATENDIDO(0), ATENDIDO(1), CANCELADO(2);

    private final int value;
    StatusPedido(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
