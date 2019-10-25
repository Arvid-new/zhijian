package com.haozhiyan.zhijian.activity.workXcjc;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.activity.MainActivity;
import com.haozhiyan.zhijian.adapter.LabelTextAdapter;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.view.MyListView;
import com.haozhiyan.zhijian.widget.CancelOrOkDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//批次详情
public class PiCiDetailActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.iv_close)
    ImageView ivClose;
    @ViewInject(R.id.tv_piCiYongTu)
    TextView tv_piCiYongTu;//批次用途
    @ViewInject(R.id.tv_piCiName)
    TextView tv_piCiName;//批次名称
    @ViewInject(R.id.tv_biaoDuan)
    TextView tv_biaoDuan;//所属标段
    @ViewInject(R.id.tv_zhengGaiTime)
    TextView tv_zhengGaiTime;//整改期限
    @ViewInject(R.id.tv_fuZeRen)
    TextView tv_fuZeRen;//负责人
    @ViewInject(R.id.tv_date)
    TextView tvDate;//时间
    @ViewInject(R.id.examineList)
    MyListView examineList;//检查人
    @ViewInject(R.id.chaoSongList)
    MyListView chaoSongList;//抄送人
    @ViewInject(R.id.btn_delete)
    Button btn_delete;
    @ViewInject(R.id.btn_close)
    Button btn_close;
    @ViewInject(R.id.btn_update)
    Button btn_update;
    @ViewInject(R.id.ll_do_info)
    LinearLayout llDoInfo;//是否打开删除，编辑，关闭功能
    //private LabelTextAdapter textAdapter;
    private Dialog dialog;
    private String batchId = "2", fuZeRenId = "", zgTime = "";
    private ArrayList<ItemBean> jcRenList;
    private ArrayList<ItemBean> csRenList;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_pi_ci_detail;
    }

    @Override
    protected void initView() {
        tv_title.setText("批次详情");
        ivClose.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isNetWork) {
        Bundle bundle = getIntent().getBundleExtra("data");
        batchId = bundle == null ? "2" : bundle.getString("batchId");
        LogUtils.i("batchId===", batchId);
        getDetail();
    }

    @OnClick({R.id.rl_back, R.id.iv_close, R.id.btn_delete, R.id.btn_close, R.id.btn_update})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.iv_close:
                jumpToActivity(MainActivity.class);
                finish();
                break;
            case R.id.btn_delete://删除
                dialog = new CancelOrOkDialog(this, getString(R.string.delete_str)) {
                    @Override
                    public void ok() {
                        super.ok();
                        deletePiCi();
                        dialog.dismiss();
                    }
                };
                dialog.show();
                break;
            case R.id.btn_close://关闭
                dialog = new CancelOrOkDialog(this, getString(R.string.close_str)) {
                    @Override
                    public void ok() {
                        super.ok();
                        closePiCi();
                        dialog.dismiss();
                    }
                };
                dialog.show();
                break;
            case R.id.btn_update://修改
                String string = UiUtils.getContent(tv_piCiYongTu) + "|" + UiUtils.getContent(tv_piCiName) + "|" + UiUtils.getContent(tv_biaoDuan) + "|" + UiUtils.getContent(tv_fuZeRen);
                LogUtils.i("修改==", string);
                AddOrEditPiCi.doPiCi(this, Constant.EDIT_PICI_RESULT_CODE, batchId, fuZeRenId,
                        UiUtils.getContent(tv_piCiYongTu), UiUtils.getContent(tv_piCiName), UiUtils.getContent(tv_biaoDuan),
                        jcRenList, zgTime, UiUtils.getContent(tv_fuZeRen), csRenList);
                break;
            default:
                break;
        }
    }

    //详情
    private void getDetail() {
        HttpRequest.get(this).url(ServerInterface.queryPiciDetail).params("batchId", batchId).doGet(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LogUtils.i("json==", result.toString());
                try {
                    JSONObject json = new JSONObject(result.toString());
                    if (json != null) {
                        if (json.optInt("code") == 0) {
                            JSONObject data = json.optJSONObject("data");
                            if (data != null) {
                                tv_piCiName.setText(data.optString("batchName"));
                                tv_zhengGaiTime.setText(data.optString("rectifyDate") + "天");
                                zgTime = data.optString("rectifyDate");
                                tv_piCiYongTu.setText(data.optString("batchUse"));
                                tv_biaoDuan.setText(data.optString("section"));
                                tvDate.setText(data.optString("createDate"));
                                //负责人
                                JSONObject fzrObject = data.optJSONObject("review");
                                if (fzrObject != null) {
                                    fuZeRenId = fzrObject.optString("userId");
                                    if(StringUtils.isEmpty(fzrObject.optString("peopleuser"))){
                                        tv_fuZeRen.setText("");
                                    }else{
                                        tv_fuZeRen.setText(fzrObject.optString("peopleuser"));
                                    }
                                }
                                //检查人
                                JSONArray examineArray = data.optJSONArray("rectifyList");
                                if (arrayEmpty(examineArray)) {
                                    jcRenList = new ArrayList<>();
                                    for (int i = 0; i < examineArray.length(); i++) {
                                        JSONObject examineObject = examineArray.optJSONObject(i);
                                        jcRenList.add(new ItemBean(examineObject.optString("username"), examineObject.optString("userId")
                                                , examineObject.optString("mobile"), examineObject.optString("peopleuser"), examineObject.optString("userAppTag"), false));
                                    }
                                    LabelTextAdapter adapter = new LabelTextAdapter(act, jcRenList, "xcJc_piCi_detail");
                                    examineList.setAdapter(adapter);
                                    adapter.setOnChatCallListener(new LabelTextAdapter.OnChatCallListener() {
                                        @Override
                                        public void chat(int position) {

                                        }

                                        @Override
                                        public void call(int position) {
                                            SystemUtils.callPage(jcRenList.get(position).mobile, act);
                                        }
                                    });
                                }
                                //抄送人
                                JSONArray chaoSongArray = data.optJSONArray("ccList");
                                if (arrayEmpty(chaoSongArray)) {
                                    csRenList = new ArrayList<>();
                                    for (int i = 0; i < chaoSongArray.length(); i++) {
                                        JSONObject chaoSongObject = chaoSongArray.optJSONObject(i);
                                        csRenList.add(new ItemBean(chaoSongObject.optString("username"), chaoSongObject.optString("userId")
                                                , chaoSongObject.optString("mobile"), chaoSongObject.optString("peopleuser"), chaoSongObject.optString("userAppTag"), false));
                                    }
                                    LabelTextAdapter adapter = new LabelTextAdapter(act, csRenList, "xcJc_problemDetail_OtherPeople");
                                    chaoSongList.setAdapter(adapter);
                                    adapter.setOnChatCallListener(new LabelTextAdapter.OnChatCallListener() {
                                        @Override
                                        public void chat(int position) {

                                        }

                                        @Override
                                        public void call(int position) {
                                            SystemUtils.callPage(csRenList.get(position).mobile, act);
                                        }
                                    });
                                }
                                //是否显示功能按钮 tagShow  1显示  0不显示
                                String tagShow = data.optString("tagShow");
                                if (tagShow.equals("1")) {
                                    llDoInfo.setVisibility(View.VISIBLE);
                                }
                                if (tagShow.equals("0")) {
                                    llDoInfo.setVisibility(View.GONE);
                                }
                            } else {
                                llDoInfo.setVisibility(View.GONE);
                            }
                        } else {
                            llDoInfo.setVisibility(View.GONE);
                            ToastUtils.myToast(act, json.optString("msg"));
                            //finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });
    }

    //删除
    private void deletePiCi() {
        HttpRequest.get(this).url(ServerInterface.deletePiCi).params("batchId", batchId).doGet(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject object = new JSONObject(result.toString());
                    if (object.optInt("code") == 0) {
                        //ToastUtils.myToast(act, object.optString("msg"));
                        ToastUtils.myToast(act, "删除成功!");
                        finish();
                    } else {
                        ToastUtils.myToast(act, object.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.myToast(act, msg);
            }
        });
    }

    //关闭
    private void closePiCi() {
        HttpRequest.get(this).url(ServerInterface.closePici).params("batchId", batchId).doGet(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject object = new JSONObject(result.toString());
                    if (object.optInt("code") == 0) {
                        //ToastUtils.myToast(act, object.optString("msg"));
                        ToastUtils.myToast(act, "关闭成功!");
                        finish();
                    } else {
                        ToastUtils.myToast(act, object.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.myToast(act, msg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.EDIT_PICI_RESULT_CODE) {
            getDetail();
        }
    }
}
