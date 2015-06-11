package com.i.serve.iservesystem.dto;

import android.graphics.Color;

/**
 * Created by Admin on 5/27/2015.
 */
public class TableItem {

    private int tableId;
    private int status;
    private int color;

    public TableItem(int tableId, int status, int color) {
        this.tableId = tableId;
        this.status = status;
        this.color = color;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getColor() {return color;}

    public void setColor(int color) {
        this.color = color;
    }

}
