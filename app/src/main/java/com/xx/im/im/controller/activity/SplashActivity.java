package com.xx.im.im.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.hyphenate.chat.EMClient;
import com.xx.im.im.R;
import com.xx.im.im.model.Model;
import com.xx.im.im.model.bean.UserInfo;

//欢迎页面
public class SplashActivity extends Activity {

    private Handler handler =new Handler(){
        public  void handleMessage(Message msg){
            //如果当前activity已经退出，就不处理handler中的消息
            if(isFinishing()){
                return;
            }
            //判断进入主页面还是登录页面
            toMainOrLogin();
        }
    };
    private void toMainOrLogin(){
        //匿名内部类是用在具体的某个实例上的修改了这个实例所属类的某些属性方法，所修改的内容也只对这个实例有效相当于一个一次性的类主要用在某个特殊事件的处理上
        //创建了一个线程池，减少内存优化的可能性
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                //判断当前用户是否已经登录
                if(EMClient.getInstance().isLoggedInBefore()){

                 //获取到当前登录用户的信息
                    UserInfo account = Model.getInstance().getUserAccountDao().getAccountByHxId(EMClient.getInstance().getCurrentUser());
                   if(account==null){
                       //跳转登录
                       Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                       startActivity(intent);
                   }else{
                       //登录成功
                       Model.getInstance().loginSuccess(account);
                       //跳转主页面
                       Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                       startActivity(intent);
                   }
                }else{
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendMessageDelayed(Message.obtain(),2000);
    }
    protected  void onDestroy(){
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}