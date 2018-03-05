package com.xx.im.im.controller.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.xx.im.im.R;
import com.xx.im.im.model.Model;
import com.xx.im.im.model.bean.UserInfo;


public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        RadioButton imageView_m = (RadioButton) findViewById(R.id.man);
        RadioButton imageView_w = (RadioButton) findViewById(R.id.woman);
        imageView_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                String sign = intent.getStringExtra("sign");
                Model.getInstance().getUserAccountDao().addAccount(new UserInfo(name,"m",sign));
                Intent intent1 = new Intent(SelectActivity.this, LoginActivity.class);
                startActivity(intent1);
            }
        });
        imageView_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String name = intent.getStringExtra("name");
                String sign = intent.getStringExtra("sign");
                Model.getInstance().getUserAccountDao().addAccount(new UserInfo(name,"w",sign));
                Intent intent1 = new Intent(SelectActivity.this, LoginActivity.class);
                startActivity(intent1);
            }
        });

    }
}
