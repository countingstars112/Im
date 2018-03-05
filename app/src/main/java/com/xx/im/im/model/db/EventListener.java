package com.xx.im.im.model.db;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.xx.im.im.model.Model;
import com.xx.im.im.model.bean.InvationInfo;
import com.xx.im.im.model.bean.UserInfo;
import com.xx.im.im.utils.Constant;
import com.xx.im.im.utils.SpUtils;

/**
 * Created by msi on 12/24/2017.
 */

public class EventListener {

    private Context mContext;
    private final LocalBroadcastManager mLBM;

    public EventListener(Context context) {
        mContext = context;
        // 创建一个发送广播的管理者对象
        mLBM = LocalBroadcastManager.getInstance(mContext);
        // 注册一个联系人变化的监听
        EMClient.getInstance().contactManager().setContactListener(emContactListener);
    }

    /**
     * 注册一个联系人变化的监听
     */
    private final EMContactListener emContactListener = new EMContactListener() {
        /**
         *  联系人增加后执行的方法
         * @param hxid
         */
        @Override
        public void onContactAdded(String hxid) {
            // 数据更新
            Model.getInstance().getDbManager().getContactTableDao().saveContact(new UserInfo(hxid), true);
            // 发送联系人变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        /**
         * 接受到联系人的新邀请
         * @param hxid
         * @param reason
         */
        @Override
        public void onContactInvited(String hxid, String reason) {
            // 数据库更新
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setUser(new UserInfo(hxid));
            invitationInfo.setReason(reason);
            invitationInfo.setStatus(InvationInfo.InvitationStatus.NEW_INVITE);// 新邀请
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);
            // 红点的处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);
            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }

        /**
         * 联系人删除后执行的方法
         */
        @Override
        public void onContactDeleted(String hxid) {
            // 数据更新
            Model.getInstance().getDbManager().getContactTableDao().deleteContactByHxId(hxid);
            Model.getInstance().getDbManager().getInviteTableDao().removeInvitation(hxid);
            // 发送联系人变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }
        /**
         * 别人同意了你的好友邀请
         * @param hxid
         */
        @Override
        public void onContactAgreed(String hxid) {
            // 数据库更新
            InvationInfo invitationInfo = new InvationInfo();
            invitationInfo.setUser(new UserInfo(hxid));
            invitationInfo.setStatus(InvationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);// 别人同意了你的邀请
            Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);
            // 红点的处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);
            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }
        /**
         *被拒绝了
         * @param s
         */
        @Override
        public void onContactRefused(String s) {
            // 红点的处理
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);
            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }
    };
}
