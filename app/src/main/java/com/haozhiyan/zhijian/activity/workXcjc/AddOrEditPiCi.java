package com.haozhiyan.zhijian.activity.workXcjc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.activity.MainActivity;
import com.haozhiyan.zhijian.activity.SelectTRPOrAU;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ParameterMap;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.utils.VerifyUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//添加批次
public class AddOrEditPiCi extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.iv_close)
    ImageView ivClose;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.cb_real)
    CheckBox cbReal;
    @ViewInject(R.id.cb_test)
    CheckBox cbTest;
    @ViewInject(R.id.et_piciName)
    EditText et_piciName;
    @ViewInject(R.id.tv_biaoDuan)
    TextView tvBiaoDuan;
    @ViewInject(R.id.tv_jianChaRen)
    TextView tv_jianChaRen;
    @ViewInject(R.id.tv_responsible)
    TextView tv_responsible;
    @ViewInject(R.id.tv_chaoSongRen)
    TextView tv_chaoSongRen;
    @ViewInject(R.id.btn_commit)
    Button btn_commit;
    @ViewInject(R.id.tv_reduce)
    TextView tv_reduce;
    @ViewInject(R.id.tv_add)
    TextView tv_add;
    @ViewInject(R.id.tv_nums)
    EditText tvNum;
    @ViewInject(R.id.tv_piCiPath)
    TextView tvPiCiPath;//批次用途
    @ViewInject(R.id.ll_piCiPath)
    LinearLayout ll_piCiPath;
    @ViewInject(R.id.ll_piCiType)
    LinearLayout ll_piCiType;
    private int num = 7;
    private int code, realBd = 2;
    private String piCiPath, piCiName, biaoDuan, fuzeRen, rectify = "", review = "", cc = "", batchId = "", zgTime = "", sectionId = "";

    public static void doPiCi(Activity context, int requestCode, String batchId, String fuZeRenId, String piCiTitle, String piCiName, String biaoDuan,
                              ArrayList<ItemBean> jcRenList, String zgTime, String fuZeRen, ArrayList<ItemBean> csRenList) {
        String string = piCiTitle + "|" + piCiName + "|" + biaoDuan + "|" + zgTime + "|" + fuZeRen;
        LogUtils.i("接收==", string);
        Intent intent = new Intent(context, AddOrEditPiCi.class);
        intent.putExtra("code", requestCode);
        intent.putExtra("batchId", batchId);
        intent.putExtra("fuZeRenId", fuZeRenId);
        intent.putExtra("piCiPath", piCiTitle);
        intent.putExtra("piCiName", piCiName);
        intent.putExtra("biaoDuan", biaoDuan);
        intent.putExtra("jcRen", jcRenList);
        intent.putExtra("zgTime", zgTime);
        intent.putExtra("fuZeRen", fuZeRen);
        intent.putExtra("csRen", csRenList);
        context.startActivityForResult(intent, requestCode);
    }

    public static void doPiCi(Activity context, int requestCode) {
        LogUtils.i("接收2==", requestCode + "");
        Intent intent = new Intent(context, AddOrEditPiCi.class);
        intent.putExtra("code", requestCode);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_add_pi_ci;
    }

    @Override
    protected void initView() {
        ivClose.setVisibility(View.VISIBLE);
        code = getIntent().getIntExtra("code", 0);
        if (code == Constant.ADD_PICI_RESULT_CODE) {//添加批次
            tv_title.setText("新建批次");
            ll_piCiPath.setVisibility(View.GONE);
            ll_piCiType.setVisibility(View.VISIBLE);
            tv_responsible.setText(userInfo.getUserName());
            review = userInfo.getUserId();
        } else if (code == Constant.EDIT_PICI_RESULT_CODE) {//编辑批次
            tv_title.setText("编辑批次");
            ll_piCiPath.setVisibility(View.VISIBLE);
            ll_piCiType.setVisibility(View.GONE);
            piCiPath = getIntent().getStringExtra("piCiPath");
            piCiName = getIntent().getStringExtra("piCiName");
            biaoDuan = getIntent().getStringExtra("biaoDuan");
            zgTime = getIntent().getStringExtra("zgTime");
            fuzeRen = getIntent().getStringExtra("fuZeRen");
            batchId = getIntent().getStringExtra("batchId");
            review = getIntent().getStringExtra("fuZeRenId");
            tvPiCiPath.setText(piCiPath);
            et_piciName.setText(piCiName);
            tvBiaoDuan.setText(biaoDuan);
            tvNum.setText(zgTime + "");
            tv_responsible.setText(fuzeRen);
            ArrayList<ItemBean> jcrList = (ArrayList<ItemBean>) getIntent().getSerializableExtra("jcRen");
            if (listEmpty(jcrList)) {
                tv_jianChaRen.setText(StringUtils.listToStr(jcrList, 1));
                rectify = StringUtils.listToStr(jcrList, 2);
                LogUtils.i("rectify===", rectify);
            }
            ArrayList<ItemBean> csrList = (ArrayList<ItemBean>) getIntent().getSerializableExtra("csRen");
            if (listEmpty(csrList)) {
                tv_chaoSongRen.setText(StringUtils.listToStr(csrList, 1));
                cc = StringUtils.listToStr(csrList, 2);
                LogUtils.i("cc===", cc);
            }
        }
    }

    @Override
    protected void initListener() {
        cbReal.setOnCheckedChangeListener(this);
        cbTest.setOnCheckedChangeListener(this);
        tvNum.addTextChangedListener(textWatcher);
        tvNum.setText(num + "");
    }

    @Override
    protected void initData(boolean isNetWork) {

    }

    @OnClick({R.id.rl_back, R.id.iv_close, R.id.tv_biaoDuan, R.id.tv_jianChaRen, R.id.tv_responsible, R.id.tv_chaoSongRen, R.id.tv_add, R.id.tv_reduce, R.id.btn_commit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.iv_close:
                jumpToActivity(MainActivity.class);
                finish();
                break;
            case R.id.tv_biaoDuan://选择标段
                Constant.OTHER_REN_TYPE = 4;
                Bundle bundle = new Bundle();
                bundle.putString("bdName", UiUtils.getContent(tvBiaoDuan));
                jumpActivityForResult(FunctionActivity.class, Constant.BIAO_DUAN_RESULT_CODE, bundle);
                break;
            case R.id.tv_jianChaRen://选择检查人
                Constant.REN_TYPE = 1;
                Bundle bundle1 = new Bundle();
                bundle1.putString("id", rectify);
                jumpActivityForResult(SelectAngle.class, Constant.JIAN_CHA_REN_CODE, bundle1);
                break;
            case R.id.tv_responsible://选择负责人
                Constant.REN_TYPE = 2;
                SelectTRPOrAU.select(this, review, SelectTRPOrAU.THE_RECTIFICATION_PEOPLE);
                break;
            case R.id.tv_chaoSongRen://选择抄送人
                Constant.REN_TYPE = 3;
                Bundle bundle3 = new Bundle();
                bundle3.putString("id", cc);
                jumpActivityForResult(SelectAngle.class, Constant.CHAO_SONG_REN_CODE, bundle3);
                break;
            case R.id.tv_add://添加整改期限
                num++;
                if (num >= 999) {
                    num = 999;
                    tvNum.setText(num + "");
                } else {
                    tvNum.setText(num + "");
                }
                break;
            case R.id.tv_reduce://减少整改期限
                if (num > 0) {
                    num--;
                    tvNum.setText(num + "");
                }
                break;
            case R.id.btn_commit:
                if (code == Constant.ADD_PICI_RESULT_CODE) {//添加批次
                    if (VerifyUtil.addPiCi(act, UiUtils.getContent(et_piciName), UiUtils.getContent(tvBiaoDuan),
                            UiUtils.getContent(tv_jianChaRen), UiUtils.getContent(tv_responsible))) {
                        commitData();
                    }
                } else if (code == Constant.EDIT_PICI_RESULT_CODE) {//编辑批次
                    editSave();
                }
                break;
            default:
                break;
        }
    }

    private void commitData() {
        showLoadView("添加...");
        LogUtils.i("tijiao=review==", review);
        LogUtils.i("tijiao===", ParameterMap.putPici(UiUtils.getContent(et_piciName), realBd, sectionId, UiUtils.getContent(tvNum), rectify, review, cc).toString());
        HttpRequest.get(this).url(ServerInterface.createPici)
                .params(ParameterMap.putPici(UiUtils.getContent(et_piciName), realBd, sectionId, UiUtils.getContent(tvNum), rectify, review, cc))
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadView();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object != null) {
                                if (object.optInt("code") == 0) {
                                    ToastUtils.myToast(act, "新建成功!");
                                    setResult(Constant.ADD_PICI_RESULT_CODE);
                                    finish();
                                } else {
                                    ToastUtils.myToast(act, "新建失败");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                    }
                });
    }

    private void editSave() {
        LogUtils.i("修改===", ParameterMap.editPiCi(batchId, UiUtils.getContent(et_piciName), rectify, UiUtils.getContent(tvNum), review, cc).toString());
        HttpRequest.get(this).url(ServerInterface.updatePiciDetail).params(ParameterMap.editPiCi(batchId, UiUtils.getContent(et_piciName), rectify, UiUtils.getContent(tvNum), review, cc))
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object != null) {
                                if (object.optInt("code") == 0) {
                                    //ToastUtils.myToast(act, object.optString("msg"));
                                    ToastUtils.myToast(act, "修改成功!");
                                    setResult(Constant.EDIT_PICI_RESULT_CODE);
                                    finish();
                                } else {
                                    ToastUtils.myToast(act, object.optString("msg"));
                                }
                            } else {
                                ToastUtils.myToast(act, "服务器错误");
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

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(UiUtils.getContent(tvNum))) {
                num = Integer.parseInt(UiUtils.getContent(tvNum));
                if (num > 999) {
                    tvNum.setText("999");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            if (buttonView.getId() == R.id.cb_real) {
                cbReal.setChecked(true);
                cbTest.setChecked(false);
                realBd = 1;
            } else if (buttonView.getId() == R.id.cb_test) {
                cbReal.setChecked(false);
                cbTest.setChecked(true);
                realBd = 2;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            LogUtils.i("requestCode====", requestCode + "");
            LogUtils.i("resultCode====", resultCode + "");
            if (requestCode == Constant.BIAO_DUAN_RESULT_CODE) {
                tvBiaoDuan.setText(data.getStringExtra("content"));
                sectionId = data.getStringExtra("id");
                LogUtils.i("接收id====", sectionId);
            } else if (requestCode == Constant.JIAN_CHA_REN_CODE) {
                tv_jianChaRen.setText(data.getStringExtra("selectType"));
                rectify = data.getStringExtra("selectId");
            } else if (requestCode == SelectTRPOrAU.THE_RECTIFICATION_PEOPLE) {//负责人
                Bundle bundle = data.getBundleExtra("bundle");
                tv_responsible.setText(bundle == null ? "" : bundle.getString("name"));
                review = bundle.getString("id");
            } else if (requestCode == Constant.CHAO_SONG_REN_CODE) {
                tv_chaoSongRen.setText(data.getStringExtra("selectType"));
                cc = data.getStringExtra("selectId");
            }
        }
    }
}
