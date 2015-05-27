package com.i.serve.iservesystem.dto;

import android.graphics.Color;

/**
 * Created by Admin on 5/27/2015.
 */
public class TableItem {

    private int tableId;
    private int status;

    public TableItem(int tableId, int status) {
        this.tableId = tableId;
        this.status = status;
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

    public int getColor(){
        switch (this.status){
            case 1:
                return Color.BLUE;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.MAGENTA;
            default:
                return Color.BLACK;
        }
    }

}
