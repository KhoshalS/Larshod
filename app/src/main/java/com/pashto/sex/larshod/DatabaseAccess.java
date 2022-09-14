package com.pashto.sex.larshod;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pashto.sex.larshod.Models.AllDataList;
import com.pashto.sex.larshod.Models.CategoryModel;
import com.pashto.sex.larshod.Models.ItemModel;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static com.pashto.sex.larshod.DatabaseAccess instance;
    private int n=1;
    Cursor c = null;

    private DatabaseAccess(Context contxt){
        this.openHelper = new com.pashto.sex.larshod.DBHelper(contxt);
    }

    public static com.pashto.sex.larshod.DatabaseAccess getInstance(Context context){
        if (instance==null){
            instance=new com.pashto.sex.larshod.DatabaseAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db=openHelper.getWritableDatabase();

    }

    public void close(){
        if (db!=null){
            this.db.close();
        }

    }

    public int getLenthOfDB() {

        c = db.rawQuery("SELECT  * FROM " + "words", null);
        int count = c.getCount();
        c.close();
        return count;
    }


    public ArrayList<String> getWomen(){
       ArrayList<String> words = new ArrayList<String>();

        c=db.rawQuery("select content from pashto where cat = 1 order by (rowid)" , new String[]{});
        StringBuffer buffer = new StringBuffer();

        int n=0;
        while (c.moveToNext()){
            n++;
            //String sms = c.getString(0);
            //buffer.append(""+sms);
            words.add((c.getString(0)));
        }
        return words ; //buffer.toString();
    }

    public ArrayList<String> getCouple(){
        ArrayList<String> words = new ArrayList<String>();

        c=db.rawQuery("select content from pashto where cat = 2 order by (rowid)" , new String[]{});
        StringBuffer buffer = new StringBuffer();

        int n=0;
        while (c.moveToNext()){
            n++;
            //String sms = c.getString(0);
            //buffer.append(""+sms);
            words.add((c.getString(0)));
        }
        return words ; //buffer.toString();
    }


    public ArrayList<String> getmen(){
        ArrayList<String> words = new ArrayList<String>();

        c=db.rawQuery("select content from pashto where cat = 3 order by (rowid)" , new String[]{});
        StringBuffer buffer = new StringBuffer();

        int n=0;
        while (c.moveToNext()){
            n++;
            //String sms = c.getString(0);
            //buffer.append(""+sms);
            words.add((c.getString(0)));
        }
        return words ; //buffer.toString();
    }

    public String[] getTitleOne(){
        String[] words = new String[26];

        c=db.rawQuery("select title from pashto WHERE cat = 1 order by (rowid)" , new String[]{});
        StringBuffer buffer = new StringBuffer();

        int n=0;
        while (c.moveToNext()){

            //String sms = c.getString(0);
            //buffer.append(""+sms);
            words[n]=((c.getString(0)));
            n++;
        }
        return words ; //buffer.toString();
    }


    public String[] getTitleTwo(){
        String[] words = new String[39];

        c=db.rawQuery("select title from pashto WHERE cat = 2 order by (rowid)" , new String[]{});
        StringBuffer buffer = new StringBuffer();

        int n=0;
        while (c.moveToNext()){

            //String sms = c.getString(0);
            //buffer.append(""+sms);
            words[n]=((c.getString(0)));
            n++;
        }
        return words ; //buffer.toString();
    }

    public String[] getTitleThree(){
        String[] words = new String[32];

        c=db.rawQuery("select title from pashto WHERE cat = 3 order by (rowid)" , new String[]{});
        StringBuffer buffer = new StringBuffer();

        int n=0;
        while (c.moveToNext()){

            //String sms = c.getString(0);
            //buffer.append(""+sms);
            words[n]=((c.getString(0)));
            n++;
        }
        return words ; //buffer.toString();
    }

    public String[] getImgOne(int i){
        String[] words = new String[26];

        c=db.rawQuery("select pic from pashto WHERE cat = "+i+" order by (rowid)" , new String[]{});
        StringBuffer buffer = new StringBuffer();

        int n=0;
        while (c.moveToNext()){
            words[n]=((c.getString(0)));
            n++;
        }
        return words ; //buffer.toString();
    }


    public String[] getImgTwo(){
        String[] words = new String[39];

        c=db.rawQuery("select pic from pashto WHERE cat = 2 order by (rowid)" , new String[]{});
        StringBuffer buffer = new StringBuffer();

        int n=0;
        while (c.moveToNext()){

            //String sms = c.getString(0);
            //buffer.append(""+sms);
            words[n]=((c.getString(0)));
            n++;
        }
        return words ; //buffer.toString();
    }

    public String[] getImgThree(){
        String[] words = new String[32];

        c=db.rawQuery("select pic from pashto WHERE cat = 3 order by (rowid)" , new String[]{});
        StringBuffer buffer = new StringBuffer();

        int n=0;
        while (c.moveToNext()){

            //String sms = c.getString(0);
            //buffer.append(""+sms);
            words[n]=((c.getString(0)));
            n++;
        }
        return words ; //buffer.toString();
    }


    public void generateData(){
        c=db.rawQuery("select * from pashto order by (rowid)" , new String[]{});
        ArrayList<ItemModel> cat1 = new ArrayList<ItemModel>();
        ArrayList<ItemModel> cat2 = new ArrayList<ItemModel>();
        ArrayList<ItemModel> cat3 = new ArrayList<ItemModel>();
        int n=0;
        while (c.moveToNext()){
            ItemModel itemModel = new ItemModel(c.getString(0),c.getString(1),c.getString(4),c.getInt(2),c.getInt(3));
            switch (itemModel.getCat()){
                case 1: cat1.add(itemModel); break;
                case 2: cat2.add(itemModel); break;
                case 3: cat3.add(itemModel); break;
            }
        }
        ArrayList<CategoryModel> catList = new ArrayList<>();
        catList.add(new CategoryModel(cat1));
        catList.add(new CategoryModel(cat2));
        catList.add(new CategoryModel(cat3));

        AllDataList.getInstance(catList); //buffer.toString();
    }

}