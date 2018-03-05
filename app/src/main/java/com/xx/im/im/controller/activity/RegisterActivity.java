package com.xx.im.im.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.xx.im.im.R;
import com.xx.im.im.model.Model;

public class RegisterActivity extends Activity {
    private EditText register_name;
    private EditText register_pwd;
    private EditText sign;
    private Button register_login;
    private Button register_register;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化控件
        initView();
        //初始化监听
        initListener();
    }
    private void initListener(){
        register_login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent= new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        register_register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent= new Intent(RegisterActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                register();
            }
        });
    }
        private void register(){
            //获取输入的用户名和密码，校验输入的用户名和密码，去服务器注册账号
            final String registName=register_name.getText().toString();
            final String registPwd=register_pwd.getText().toString();
            if(TextUtils.isEmpty(registName)|| TextUtils.isEmpty(registPwd)){
                Toast.makeText(RegisterActivity.this,"输入的登录名和密码不能为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            //去服务器注册账号
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().createAccount(registName,registPwd);
                        //更新页面显示
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(RegisterActivity.this, SelectActivity.class);
                                intent.putExtra("name",registName);
                                intent.putExtra("sign",sign.getText().toString());
                                startActivity(intent);
                                Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }

    /**
     *
     */
    private void initView(){
            register_name=(EditText)findViewById(R.id.register_name);
            register_pwd=(EditText)findViewById(R.id.register_pwd);
            sign=(EditText)findViewById(R.id.sign);
            register_login=(Button)findViewById(R.id.register_login);
            register_register=(Button)findViewById(R.id.register_register);
            register=(Button)findViewById(R.id.register);

        }
    }

