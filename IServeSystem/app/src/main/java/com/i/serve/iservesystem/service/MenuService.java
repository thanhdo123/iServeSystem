package com.i.serve.iservesystem.service;

import com.i.serve.iservesystem.dto.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/27/2015.
 */
public class MenuService {

    static List<MenuItem> list = new ArrayList<>();
    static{
        list.add(new MenuItem(1, "Muc mot nang nuong", "Muc mong nang Phan Thiet, nuong moi, an kem muoi tieu chanh", 230000));
        list.add(new MenuItem(2, "Bo nuong lu", "Bo tai chanh muoi tom", 130000));
        list.add(new MenuItem(3, "Muc sua chien gion", "Muc chien gion an kem rau song", 150000));
        list.add(new MenuItem(4, "Ga nuong lu", "Ga vuon nuong lu", 200000));
        list.add(new MenuItem(5, "Ga len mam", "Ga vuon luoc", 196000));
        list.add(new MenuItem(6, "Chan gio rut xuong", "Chan gio heo, rut xuong, chien gion, an kem ra song", 200000));
    }
    public static List<MenuItem> getMenuItems(){
        return list;
    }
}
