package com.i.serve.iservesystem.service;

import android.graphics.Color;

import com.i.serve.iservesystem.dto.TableItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/27/2015.
 */
public class TableService {

    public static List<TableItem> getTables(){
        List<TableItem> list = new ArrayList<>();
        list.add(new TableItem(1, 1, Color.BLUE));
        list.add(new TableItem(2, 2, Color.RED));
        list.add(new TableItem(3, 3, Color.GREEN));
        list.add(new TableItem(4, 4, Color.YELLOW));
        list.add(new TableItem(5, 1, Color.DKGRAY));
        list.add(new TableItem(6, 2, Color.BLUE));
        return list;
    }
}
