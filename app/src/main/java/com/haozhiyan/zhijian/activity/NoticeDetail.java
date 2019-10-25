package com.haozhiyan.zhijian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

//公告详情
public class NoticeDetail extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tvTitle;
    @ViewInject(R.id.tv_label)
    TextView tvLabel;
    @ViewInject(R.id.tv_date_name)
    TextView tvDateName;
    @ViewInject(R.id.tv_content)
    TextView tvContent;
    private String noticeId = "";

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText("公告详情");
        Bundle bundle = getIntent().getBundleExtra("data");
        noticeId = bundle.getString("noticeId");
        getData(noticeId);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isNetWork) {

    }

    @OnClick({R.id.rl_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            default:
                break;
        }
    }

    /**
     * {
     *     "code": 0,
     *     "msg": "success",
     *     "news": {
     *         "createPeople": "admin",
     *         "createTime": "2019-07-12 16:09:22",
     *         "news": "测非人防发的发",
     *         "newsHead": "123456789",
     *         "newsTag": 1,
     *         "pkId": 50,
     *         "project": "1,2,3,5,17,19,20,21,22,33,46,47,48,50,51,52,53,54,55,56,57"
     *     }
     * }
     * @param noticeId
     */
    private void getData(String noticeId) {
        HttpRequest.get(this).url(ServerInterface.noticeDetail)
                .params("pkId", noticeId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.print("notice==" + result.toString());
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                JSONObject news = object.optJSONObject("news");
                                if (news != null) {
                                    tvLabel.setText(news.optString("newsHead"));
                                    tvDateName.setText(news.optString("createTime") + "\t\t" + news.optString("createPeople"));
                                    tvContent.setText(news.optString("news"));
                                } else {
                                    tvLabel.setText("暂无数据");
                                    tvDateName.setText("无数据");
                                    tvContent.setText("暂无新闻详情");
                                }
                            } else {
                                ToastUtils.myToast(act, object.optString("msg"));
                                ActivityManager.getInstance().removeActivity(act);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(act, "请求失败，请重试");
                        tvLabel.setText("暂无数据");
                        tvDateName.setText("无数据");
                        tvContent.setText("暂无新闻详情");
                    }
                });
    }
}
