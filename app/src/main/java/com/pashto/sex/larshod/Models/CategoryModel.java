package com.pashto.sex.larshod.Models;

import java.util.ArrayList;
import java.util.Arrays;

public class CategoryModel {
    ArrayList<ItemModel> list;

    public CategoryModel(ArrayList<ItemModel> list) {
        this.list = list;
    }

    public ArrayList<ItemModel> getList() {
        return list;
    }


}
