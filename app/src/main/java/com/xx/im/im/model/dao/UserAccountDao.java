package com.xx.im.im.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xx.im.im.model.bean.UserInfo;
import com.xx.im.im.model.db.UserAccountDB;

/**
 * Created by Administrator on 2017-12-23.
 */
//用户账号数据库的操作类
public class UserAccountDao {
    private final  UserAccountDB mHelper;
    public UserAccountDao(Context context){
        mHelper= new UserAccountDB(context);
    }
    //添加用户到数据库

    public void addAccount(UserInfo user){
         //获取数据库对象
        SQLiteDatabase db = mHelper.getReadableDatabase();
        //执行添加操作
        ContentValues values = new ContentValues();
        values.put(UserAccountTable.COL_HXID,user.getHxid());
        values.put(UserAccountTable.COL_NAME,user.getName());
        values.put(UserAccountTable.COL_PW,user.getPw());
        values.put(UserAccountTable.COL_SIGN,user.getSign());
        values.put(UserAccountTable.COL_PHOTO,user.getSign());

        db.replace(UserAccountTable.TAB_NAME,null,values);
    }
    //根据ID获取用户的所有用户信息
    public UserInfo getAccountByHxId(String hxId){
        //获取数据库对象
        SQLiteDatabase db=mHelper.getReadableDatabase();
        //执行查询语句
        String sql="select * from " + UserAccountTable.TAB_NAME + " where "+ UserAccountTable.COL_HXID + "=?";
        Cursor cursor = db.rawQuery(sql,new String[]{hxId});

        UserInfo userInfo=null;
        if(cursor.moveToNext()){
            userInfo= new UserInfo();
        //封装对象
            userInfo.setHxid(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_HXID)));
            userInfo.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
            userInfo.setPw(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PW)));
            userInfo.setSign(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_SIGN)));
            userInfo.setPhoto(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_PHOTO)));
        }
        //关闭资源
       cursor.close();
        //返回数据
        return userInfo;
    }
}
