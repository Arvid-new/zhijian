package com.haozhiyan.zhijian.jpush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.haozhiyan.zhijian.activity.DaiBanTypeVPActivity;
import com.haozhiyan.zhijian.activity.MainActivity;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class PushReceiver extends JPushMessageReceiver {
    private static final String TAG = "PushMessageReceiver";
    public static int count;

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        LogUtils.e(TAG, "[onNotifyMessageOpened] " + message.notificationExtras);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(message.notificationExtras);
            String action = jsonObject.optString("message");
            String projectId = jsonObject.optString("projectId");
            String projectName = jsonObject.optString("projectName");
            String dikuaiId = jsonObject.optString("dikuaiId");
            String dikuaiName = jsonObject.optString("dikuaiName");
            Constant.projectId = Integer.valueOf(dikuaiId);//地块id
            Constant.projectName = dikuaiName + projectName;//项目名称+地块名称
            Constant.diKuaiName = dikuaiName;//地块名称
            Constant.parentProjectId = Integer.valueOf(projectId);//项目id
            Constant.parentProjectName = projectName;//项目名称

            if(TextUtils.equals(action,"4")){//新建批次
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("selection","1");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                context.startActivity(intent);
            }else{
                Intent i = new Intent(context, DaiBanTypeVPActivity.class);
                Bundle bundle = new Bundle();
                String name = "";
                switch (action) {
                    case "0":
                        name = "现场检查";
                        break;
                    case "1":
                        name = "实测实量";
                        break;
                    case "2":
                        name = "工序移交";
                        break;
                    case "3":
                        name = "材料验收";
                        break;
                }
                bundle.putString("type", action);
                bundle.putString("name", name);
                i.putExtra("data", bundle);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        EventBus.getDefault().post(Constant.REFRESH_DAIBAN);
    }


    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        LogUtils.e(TAG, "[onNotifyMessageArrived] " + message);
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        LogUtils.e(TAG, "[onNotifyMessageDismiss] " + message);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        LogUtils.e(TAG, "[onRegister] " + registrationId);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        LogUtils.e(TAG, "[onConnected] " + isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        LogUtils.e(TAG, "[onCommandResult] " + cmdMessage);
    }
}
