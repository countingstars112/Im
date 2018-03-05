package com.xx.im.im.controller.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.xx.im.im.R;

//会话详情
public class ChatActivity extends FragmentActivity {
    private String mHxid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initData();
    }

    private void initData() {
        //创建会话的fragment
        EaseChatFragment easechatFragment = new EaseChatFragment();
        mHxid = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
        easechatFragment.setArguments(getIntent().getExtras());
        //替换Fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_chat, easechatFragment).commit();
    }

    @Override
    public void onRequestPermissionsResult
            (int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //权限获取成功
                Toast.makeText(this, "权限获取成功", Toast.LENGTH_LONG).show();
            } else {
                //权限被拒绝
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_LONG).show();
            }


        }
    }
}
