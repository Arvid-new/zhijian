package com.haozhiyan.zhijian.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.UpLoadCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.utils.UpLoadUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加工程亮点
 */
public class AddLDActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.et_instruct_content)
    EditText etInstructContent;
    @ViewInject(R.id.et_piciName)
    EditText et_piciName;
    @ViewInject(R.id.id_editor_detail_font_count)
    TextView editorFontCount;
    @ViewInject(R.id.projectName)
    TextView projectName;
    @ViewInject(R.id.lightspotType)
    TextView lightspotType;
    @ViewInject(R.id.picRcv)
    RecyclerView picRcv;
    @ViewInject(R.id.btn_commit)
    Button btn_commit;

    private String lightspotTypeName;
    private String projectNameSt;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_add_ld;
    }

    @Override
    protected void initView() {
        tv_title.setText("编辑亮点");
    }

    @Override
    protected void initListener() {
        etInstructContent.addTextChangedListener(textWatcher);
    }

    @Override
    protected void initData(boolean isNetWork) {


        setPictureSelectRcv(picRcv);
    }

    @OnClick({R.id.rl_back, R.id.btn_commit, R.id.lightspotType, R.id.projectName})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.lightspotType://亮点类型
                GCLDSelectProjectOrType.select(this, lightspotTypeName, GCLDSelectProjectOrType.STAR_TYPE);
                break;
            case R.id.projectName://项目
                GCLDSelectProjectOrType.select(this, projectNameSt, GCLDSelectProjectOrType.PROJECT);
                break;
            case R.id.btn_commit:
                final String theme = UiUtils.getContent(et_piciName);
                if (TextUtils.isEmpty(theme)) {
                    ToastUtils.myToast(this, "请输入主题");
                    return;
                }
                if (TextUtils.isEmpty(lightspotTypeName)) {
                    ToastUtils.myToast(this, "请选择亮点类型");
                    return;
                }
                if (TextUtils.isEmpty(projectNameSt)) {
                    ToastUtils.myToast(this, "请选择项目");
                    return;
                }
                UpLoadUtil.init(this, selectList, true).Call(new UpLoadCallBack() {
                    @Override
                    public void onComplete(String paths) {
                        saveLightspot(paths, theme);
                    }
                });
                break;
            default:
                break;
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int detailLength = etInstructContent.length();
            editorFontCount.setText(detailLength + "/200");
            if (detailLength >= 200) {
                ToastUtils.myToast(act, "已输入达到200字界限了");
            }
        }
    };


    private PictureOrVideoListNewAdapter adapter;
    private List<LocalMedia> selectList;

    /**
     * 设置 选择照片的那个 RCV 控件设置数据 可复用
     */
    private void setPictureSelectRcv(RecyclerView pictureSelectRcv) {
        try {
            pictureSelectRcv.setLayoutManager(new GridLayoutManager(this, 4,
                    LinearLayoutManager.VERTICAL, false));
            pictureSelectRcv.setNestedScrollingEnabled(true);
            if (selectList == null)
                selectList = new ArrayList<>();
            //adapter = new PictureOrVideoListAdapter(this);
            adapter = new PictureOrVideoListNewAdapter(this);
            PhotoCameraUtils.init(this).photoCameraDialogListAdapter(adapter, pictureSelectRcv, selectList, 1);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectLi = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(selectLi);
                    adapter.setNewData(selectList);
                    break;
            }
        } else if (resultCode == GCLDSelectProjectOrType.STAR_TYPE) {
            lightspotTypeName = data.getStringExtra("name");
            lightspotType.setText(lightspotTypeName);
        } else if (resultCode == GCLDSelectProjectOrType.PROJECT) {
            projectNameSt = data.getStringExtra("name");
            projectName.setText(projectNameSt);
        }
    }


    private void saveLightspot(String paths, String theme) {
        HttpRequest.get(this).url(ServerInterface.saveLightspot)
                .params("theme", theme)//主题
                .params("picture", paths)//照片
                .params("replenishExplain", UiUtils.getContent(etInstructContent))//补充说明
                .params("lightspotType", lightspotTypeName)//亮点类型
                .params("projectName", projectNameSt)//项目名
                .params("creators", UserInfo.create(this).getUserId())//创建人ID
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        JSONObject jsonObject = JSON.parseObject(result.toString());
                        if (jsonObject.getString("code").equals("0")) {
                            EventBus.getDefault().post("addGCLDSuccess");
                            finish();
                        } else {
                            ToastUtils.myToast(AddLDActivity.this, jsonObject.getString("msg"));
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }
}
