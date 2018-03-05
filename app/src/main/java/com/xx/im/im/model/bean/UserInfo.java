package com.xx.im.im.model.bean;

/**
 * Created by Administrator on 2017-12-21.
 */
//用户账号信息的bean类
public class UserInfo {
    private String name;
    private String nick;
    private String hxid;
    private String pw;
    private String sign;
    private String photo;
    public UserInfo(){
    }

    public UserInfo(String name, String photo,String sign) {
        this.name = name;
        this.photo = photo;
        this.sign = sign;
        this.hxid = name;
        this.nick = name;
    }

    public UserInfo(String name){
        this.name = name;
        this.hxid = name;
        this.nick = name;
    }

    public UserInfo(String name, String nick, String hxid, String pw, String sign, String photo) {
        this.name = name;
        this.nick = nick;
        this.hxid = hxid;
        this.pw = pw;
        this.sign = sign;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getHxid() {
        return name;
    }

    public void setHxid(String hxid) {
        this.hxid = hxid;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
