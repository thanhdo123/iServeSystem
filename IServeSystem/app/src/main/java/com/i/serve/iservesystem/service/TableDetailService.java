package com.i.serve.iservesystem.service;

import com.i.serve.iservesystem.dto.TableDetailItem;
import com.i.serve.iservesystem.dto.TableItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/29/2015.
 */
public class TableDetailService {
    public static List<TableDetailItem> getTableDetailById(int tableId){
        List<TableDetailItem> list = new ArrayList<>();
        list.add(new TableDetailItem(1,"Ga nuong lu", 1, 460000, 1));
        list.add(new TableDetailItem(2,"Bo xao cai mam", 1, 80000, 1));
        list.add(new TableDetailItem(3,"Ca lang", 1, 620000, 2));
        list.add(new TableDetailItem(4, "Bo nuong ngoi", 1, 120000, 2));
        list.add(new TableDetailItem(5, "Dau hu chien gion", 1, 60000, 2));
        list.add(new TableDetailItem(6, "Lau hai san", 0, 360000, 2));
        return list;
    }
}
