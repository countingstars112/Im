package com.xx.im.im.controller.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.xx.im.im.R;
import com.xx.im.im.controller.activity.LoginActivity;
import com.xx.im.im.model.Model;
import com.xx.im.im.model.bean.UserInfo;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 設置
 * Created by msi on 12/24/2017.
 */


public class SettingFragment extends Fragment {

    private Button bt_setting_out;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_setting, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        bt_setting_out = (Button) view.findViewById(R.id.bt_setting_out);
        CircleImageView set_image = (CircleImageView) view.findViewById(R.id.set_image);
        TextView user_name_text = (TextView) view.findViewById(R.id.username);
        TextView set_sign_text = (TextView) view.findViewById(R.id.set_sign);
        UserInfo userInfo = Model.getInstance().getUserAccountDao().getAccountByHxId(EMClient.getInstance().getCurrentUser());
        if(userInfo!= null) {
            String sign = userInfo.getSign();
            String photo = userInfo.getPhoto();
            user_name_text.setText(EMClient.getInstance().getCurrentUser());
            if ("w".equals(photo)) {
                set_image.setImageResource(R.drawable.woman);
            } else {
                set_image.setImageResource(R.drawable.ease_default_avatar);
            }
            if (sign != null) {
                set_sign_text.setText(sign);
            }
        }else{
            user_name_text.setText(EMClient.getInstance().getCurrentUser());
            set_sign_text.setText("可爱的男孩子：）");
            set_image.setImageResource(R.drawable.ease_default_avatar);
        }
    }

    /**
     * 当活动创建的时候执行这个方法
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDate();
    }

    private void initDate() {
        //退出登录的逻辑处理
        bt_setting_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //环信服务器退出
                EMClient.getInstance().logout(false, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        //关闭DBHelper
                        Model.getInstance().getDbManager().close();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //更新ui显示
                                Toast.makeText(getActivity(),"退出成功",Toast.LENGTH_LONG).show();;
                                //回到登陆页面
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                //关闭当前页面
                                getActivity().finish();
                            }
                        });

                    }
                    @Override
                    public void onError(int i, final String s) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //更新ui显示
                                Toast.makeText(getActivity(),"退出失败"+s,Toast.LENGTH_LONG).show();;
                            }
                        });

                    }
                    @Override
                    public void onProgress(int i, String s) {
                    }
                });
            }
        });
    }
}
