package com.xx.im.im.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.xx.im.im.R;
import com.xx.im.im.model.Model;
import com.xx.im.im.model.bean.UserInfo;

public class LoginActivity extends Activity {
    private EditText login_name;
    private EditText login_pwd;
    private Button login_login;
    private Button login_register;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        initView();
        //初始化监听
        initListener();
    }
    private void initListener(){
        login_login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent= new Intent(LoginActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        login_register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent= new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                // register();
            }
        });
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                login();
            }
        });
    }
    // 登录按钮的页面逻辑处理
    private void login() {
        // 1 获取输入的用户名和密码
        final String loginName = login_name.getText().toString();
        final String loginPwd = login_pwd.getText().toString();

        // 2 校验输入的用户名和密码
        if(TextUtils.isEmpty(loginName) || TextUtils.isEmpty(loginPwd)) {
            Toast.makeText(LoginActivity.this, "输入的用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3 登录逻辑处理
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                // 去环信服务器登录
                EMClient.getInstance().login(loginName, loginPwd, new EMCallBack() {
                    // 登录成功后的处理
                    @Override
                    public void onSuccess() {
                        // 对模型层数据的处理
                        Model.getInstance().loginSuccess(new UserInfo(loginName));

                        // 保存用户账号信息到本地数据库
                        //Model.getInstance().getUserAccountDao().addAccount(new UserInfo(loginName));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 提示登录成功
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                                // 跳转到主页面
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                startActivity(intent);

                                finish();
                            }
                        });
                    }

                    // 登录失败的处理
                    @Override
                    public void onError(int i, final String s) {
                        // 提示登录失败
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录失败"+s, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    // 登录过程中的处理
                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });
    }

    private void initView(){
        login_name=(EditText)findViewById(R.id.login_name);
        login_pwd=(EditText)findViewById(R.id.login_pwd);
        login_login=(Button)findViewById(R.id.login_login);
        login_register=(Button)findViewById(R.id.login_register);
        login=(Button)findViewById(R.id.login);
    }
}
