package com.pashto.sex.larshod;



import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomCountryList extends ArrayAdapter {
    private ArrayList<String> titles;
    private ArrayList<Integer> imageid;
    private Activity context;

    private int CAT;
    String color;

    private SharedPreferences larShodSP;
    private SharedPreferences.Editor spEditor;

    public CustomCountryList(Activity context, ArrayList<String> titles, ArrayList<Integer> imageid, String color) {
        super(context, R.layout.select_listview_item, titles);
        this.context = context;
        this.titles = titles;
       // this.capitalNames = capitalNames;
        this.imageid = imageid;
        this.color=color;

        larShodSP = context.getSharedPreferences("sp1", Context.MODE_PRIVATE);
        CAT = larShodSP.getInt("cat", 1);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.select_listview_item, null, true);
        TextView textViewCountry = (TextView) row.findViewById(R.id.textViewCountry);

        textViewCountry.setTextColor(Color.parseColor(color));

//        switch (CAT){
//            case 1:
//                textViewCountry.setTextColor(context.getResources().getColor(R.color.color1));
//                break;
//            case 2:
//                textViewCountry.setTextColor(context.getResources().getColor(R.color.color2));
//                break;
//            case 3:
//                textViewCountry.setTextColor(context.getResources().getColor(R.color.color3));
//                break;
//        }


      //  TextView textViewCapital = (TextView) row.findViewById(R.id.textViewCapital);
        ImageView imageFlag = (ImageView) row.findViewById(R.id.imageViewFlag);

        textViewCountry.setText(titles.get(position));
      //  textViewCapital.setText(capitalNames[position]);
        imageFlag.setImageResource(imageid.get(position));
        return  row;
    }
}