package com.haozhiyan.zhijian.fragment.gxyjBackNoteFragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.ShowBigImg;
import com.haozhiyan.zhijian.activity.ShowVideo;
import com.haozhiyan.zhijian.adapter.PicShowListAdapter;
import com.haozhiyan.zhijian.bean.BackNoteBean;
import com.haozhiyan.zhijian.fragment.BaseFragment;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;

import java.util.List;

//退回1次
public class BackOneFragment extends BaseFragment {

    //施工单位
    private TextView tv_back_sgdw;
    private TextView tv_back_shiGongRen;
    private TextView tv_shiGongBackTime;
    private TextView tv_shiGongBackStatus;
    private TextView tv_shiGongBackNote;
    private RecyclerView back_note_list;
    //监理
    private TextView tv_danWei;
    private TextView tv_back_danWeiPeople;
    private TextView tv_jlBack_Date;
    private TextView tv_jlBack_Status;
    private TextView tv_JLBackReason;
    private RecyclerView back_reason_list;
    private String id;
//    private PictureVideoShowAdapter sgVideoShowAdapter, jlVideoShowAdapter;
//    private List<LocalMedia> sgImgList = new ArrayList<>();
//    private List<LocalMedia> jlImgList = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_back_one;
    }

    @Override
    public void initView(View view) {

        tv_back_sgdw = getOutView(view, R.id.tv_back_sgdw);
        tv_back_shiGongRen = getOutView(view, R.id.tv_back_shiGongRen);
        tv_shiGongBackTime = getOutView(view, R.id.tv_shiGongBackTime);
        tv_shiGongBackStatus = getOutView(view, R.id.tv_shiGongBackStatus);
        tv_shiGongBackNote = getOutView(view, R.id.tv_shiGongBackNote);
        back_note_list = getOutView(view, R.id.back_note_list);
        tv_danWei = getOutView(view, R.id.tv_danWei);
        tv_back_danWeiPeople = getOutView(view, R.id.tv_back_danWeiPeople);
        tv_jlBack_Date = getOutView(view, R.id.tv_jlBack_Date);
        tv_jlBack_Status = getOutView(view, R.id.tv_jlBack_Status);
        tv_JLBackReason = getOutView(view, R.id.tv_JLBackReason);
        back_reason_list = getOutView(view, R.id.back_reason_list);
    }

    @Override
    public void initListener() {
//        sgVideoShowAdapter = new PictureVideoShowAdapter(getActivity());
//        PhotoCameraUtils.init(ctx).photoCameraAdapter(sgVideoShowAdapter,back_note_list, sgImgList);
//        jlVideoShowAdapter = new PictureVideoShowAdapter(getActivity());
//        PhotoCameraUtils.init(ctx).photoCameraAdapter(jlVideoShowAdapter,back_reason_list, jlImgList);
    }

    @Override
    public void initData(boolean isNetWork) {
        getData();
    }

    @Override
    protected void lazyLoad() {

    }

    private void getData() {
        LogUtils.print("requestKeyId===" + id);
        HttpRequest.get(ctx).url(ServerInterface.listBackToTheRecord).params("id", id).doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                BackNoteBean bean = new Gson().fromJson(result.toString(), BackNoteBean.class);
                if (bean != null && bean.code == 0) {
                    if (listEmpty(bean.list)) {

                        try {
                            tv_back_sgdw.setText(bean.list.get(0).constructionUnit);
                        } catch (Exception e) {

                        }

                        try {
                            tv_back_shiGongRen.setText(bean.list.get(0).constructionDirector);
                        } catch (Exception e) {

                        }
                        try {
                            tv_shiGongBackTime.setText(bean.list.get(0).creationTime);
                        } catch (Exception e) {

                        }

                        try {
                            tv_shiGongBackNote.setText(bean.list.get(0).transferExplain);
                        } catch (Exception e) {

                        }

                        tv_shiGongBackStatus.setText("申请验收");
//                        if (listEmpty(bean.list.get(0).childTransferRecord)) {
//                            for (String path : bean.list.get(0).childTransferRecord) {
//                                LocalMedia localMedia = new LocalMedia();
//                                localMedia.setPath(ServerInterface.PVUrl() + path);
//                                localMedia.setPictureType(PVAUtils.getFileLastType(path));
//                                sgImgList.add(localMedia);
//                            }
//                            sgVideoShowAdapter.setNewData(sgImgList);
//                        }

                        try {
                            setPicRcv(back_note_list, bean.list.get(0).childTransferRecord);
                        } catch (Exception e) {

                        }

                        tv_danWei.setText(bean.list.get(0).backType);
                        tv_back_danWeiPeople.setText(bean.list.get(0).backUsername);
                        tv_jlBack_Date.setText(bean.list.get(0).backCreationTime);
                        tv_jlBack_Status.setText("已退回");
                        tv_jlBack_Status.setTextColor(setColor(R.color.red2));
                        tv_JLBackReason.setText(bean.list.get(0).backCause);
//                        if (listEmpty(bean.list.get(0).childbackPictureVideo)) {
//                            for (String path : bean.list.get(0).childbackPictureVideo) {
//                                LocalMedia localMedia = new LocalMedia();
//                                localMedia.setPath(ServerInterface.PVUrl() + path);
//                                localMedia.setPictureType(PVAUtils.getFileLastType(path));
//                                jlImgList.add(localMedia);
//                            }
//                            jlVideoShowAdapter.setNewData(jlImgList);
//                        }
                        try {
                            setPicRcv(back_reason_list, bean.list.get(0).childbackPictureVideo);
                        } catch (Exception e) {

                        }
                    }
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.myToast(ctx, msg);
            }
        });
    }

    public void getId(String keyId) {
        id = keyId;
        LogUtils.print("keyId11===" + keyId);
    }

    /**
     * 代办-  照片列表  能复用
     */
    private void setPicRcv(RecyclerView picRcv, final List<String> imgs) {
        try {
            picRcv.setLayoutManager(new GridLayoutManager(getContext(), 4));
            PicShowListAdapter picShowListAdapter = new PicShowListAdapter(imgs, getContext());
            picRcv.setAdapter(picShowListAdapter);
            picShowListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (PVAUtils.getFileLastType(imgs.get(position)).equals("image/jpeg")) {
                        ShowBigImg.build(getContext(), imgs.get(position));
                    } else {
                        ShowVideo.playLineVideo(getContext(), imgs.get(position));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
