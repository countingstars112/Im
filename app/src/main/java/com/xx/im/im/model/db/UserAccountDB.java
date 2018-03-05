package com.xx.im.im.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xx.im.im.model.dao.UserAccountTable;

/**
 * Created by Administrator on 2017-12-23.
 */

public class UserAccountDB extends SQLiteOpenHelper{

    public UserAccountDB(Context context) {
        super(context,"account.db",null,1);

    }
//数据库创建时调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        //数据库更新时使用创建数据库表
        db.execSQL(UserAccountTable.CREATE_TAB);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
