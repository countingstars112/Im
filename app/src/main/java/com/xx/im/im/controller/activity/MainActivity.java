package com.xx.im.im.controller.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.RadioGroup;
import android.support.v4.app.Fragment;
import com.xx.im.im.R;
import com.xx.im.im.controller.fragment.ChatFragment;
import com.xx.im.im.controller.fragment.ContactListFragment;
import com.xx.im.im.controller.fragment.SettingFragment;

public class MainActivity extends FragmentActivity {

    private RadioGroup rg_main;
    private ChatFragment chatFragment;
    private ContactListFragment contactListFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();

        initData();

        initListener();

    }

    private void initListener() {
        //RadioGroup的选择事件
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Fragment fragment = null;

                switch (checkedId) {
                    // 会话列表页面
                    case R.id.rg_main_chat:
                        fragment = chatFragment;
                        break;

                    // 联系人列表页面
                    case R.id.rg_main_contact:
                        fragment = contactListFragment;
                        break;

                    // 设置页面
                    case R.id.rg_main_my:
                        fragment = settingFragment;
                        break;
                }

                // 实现fragment切换的方法
                switchFragment(fragment);
            }
        });

        // 默认选择会话列表页面
        rg_main.check(R.id.rg_main_chat);
    }

    // 实现fragment切换的方法
    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_main, fragment).commit();
    }

    private void initData() {
        // 创建三个fragment对象
        chatFragment = new ChatFragment();
        contactListFragment = new ContactListFragment();
        settingFragment = new SettingFragment();
    }

    private void initView() {
        rg_main = (RadioGroup)findViewById(R.id.rg_main);
    }
}
