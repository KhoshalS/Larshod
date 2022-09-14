package com.pashto.sex.larshod.Models;

import java.util.ArrayList;
import java.util.Arrays;

public class AllDataList {
    ArrayList<CategoryModel> list;
    static AllDataList instance;
    public static ArrayList<String> colors = new ArrayList<>(Arrays.asList("#ff2d55", "#9E01FF", "#ecbc10"));
    public static ArrayList<String> catTitles = new ArrayList<>(Arrays.asList("ښځینه لارښوني", "کورنۍ لارښوني", "نارینه لارښوني"));

    public static AllDataList getInstance(){
        return instance;
    }
    public static void getInstance(ArrayList<CategoryModel> list){
        instance = new AllDataList(list);
    }

    private AllDataList(ArrayList<CategoryModel> list) {
        this.list = list;
    }

    public ArrayList<CategoryModel> getList() {
        return list;
    }
}
