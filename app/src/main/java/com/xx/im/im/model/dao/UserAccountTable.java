package com.xx.im.im.model.dao;

/**
 * Created by Administrator on 2017-12-23.
 */

public class UserAccountTable {
    public static final String TAB_NAME="tab_account";
    public static final String COL_NAME="name";
    public static final String COL_HXID="hxid";
    public static final String COL_PW="pw";
    public static final String COL_SIGN="sign";
    public static final String COL_PHOTO="photo";
    public static final String CREATE_TAB="create table "
            + TAB_NAME + " ("
            + COL_HXID + " text primary key,"
            + COL_NAME + " text,"
            + COL_PW + " text,"
            + COL_SIGN + " text,"
            + COL_PHOTO + " text);";
}
