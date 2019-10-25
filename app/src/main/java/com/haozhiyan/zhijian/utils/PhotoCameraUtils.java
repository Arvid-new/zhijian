package com.haozhiyan.zhijian.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.ShowBigImg;
import com.haozhiyan.zhijian.activity.ShowVideo;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListAdapter;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.adapter.PictureVideoShowAdapter;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.widget.PictureVideoDialog;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/13.
 * Describe: Ydzj_project
 */
public class PhotoCameraUtils {

    public static Activity act;
    public static Fragment fragment;
    public static PhotoCameraUtils photoCameraUtils;
    private static PictureVideoDialog pictureVideoDialog;

    public static PhotoCameraUtils init(Activity ctx) {
        if (photoCameraUtils == null) {
            photoCameraUtils = new PhotoCameraUtils();
        }
        pictureVideoDialog = new PictureVideoDialog(ctx, R.style.BottomDialogStyle);
        pictureVideoDialog.setIsHeader(false);
        act = ctx;
        return photoCameraUtils;
    }

    public static PhotoCameraUtils init(Fragment mFragment) {
        if (photoCameraUtils == null) {
            photoCameraUtils = new PhotoCameraUtils();
        }
        pictureVideoDialog = new PictureVideoDialog(mFragment.getActivity(), R.style.BottomDialogStyle);
        pictureVideoDialog.setIsHeader(false);
        fragment = mFragment;
        return photoCameraUtils;
    }


    /**
     * @param pVListAdapter
     * @param recyclerView
     * @param mediaList     fragment 里面操作
     *                      界面直接相册、相机、视频功能操作
     * @param showType      回显类型
     */
    public void photoCameraListAdapter(final PictureOrVideoListAdapter pVListAdapter, RecyclerView recyclerView, final List<LocalMedia> mediaList, final int showType) {
        recyclerView.setAdapter(pVListAdapter);
        pVListAdapter.setNewData(mediaList);
        pVListAdapter.notifyDataSetChanged();
        pVListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == pVListAdapter.getItemCount() - 3) {
                    PictureSelectorConfig.openCameraImg(fragment, PictureConfig.CHOOSE_REQUEST);

                } else if (position == pVListAdapter.getItemCount() - 2) {
                    PictureSelectorConfig.openCameraVideo(fragment, PictureConfig.CHOOSE_REQUEST);

                } else if (position == pVListAdapter.getItemCount() - 1) {
                    try {
                        PictureSelectorConfig.initSingleAllConfig(fragment, PictureConfig.CHOOSE_REQUEST, mediaList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (ListUtils.listEmpty(mediaList)) {
                        if ("image/jpeg".equals(PVAUtils.getFileLastType(mediaList.get(position).getPath()))) {
                            if (mediaList.get(position).isCut()) {
                                ShowBigImg.build(fragment.getActivity(), mediaList.get(position).getCutPath());
                            } else {
                                ShowBigImg.build(fragment.getActivity(), mediaList.get(position).getPath());
                            }
                        } else if ("video/3gp".equals(PVAUtils.getFileLastType(mediaList.get(position).getPath()))) {
                            ShowVideo.playLineVideo(act, mediaList.get(position).getPath());
                        }
                    }
                }
            }
        });
        pVListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                if (view.getId() == R.id.ll_del) {
                    if (showType == 0) {//网络回显操作
                        String fileName = new File(mediaList.get(position).getPath()).getName();
                        HttpRequest.get(act).url(ServerInterface.deleteFile)
                                .params("fileName", fileName)
                                .doPost(new HttpStringCallBack() {
                                    @Override
                                    public void onSuccess(Object result) {
                                        try {
                                            JSONObject object = new JSONObject(result.toString());
                                            if (object.optInt("code") == 0) {
                                                ToastUtils.myToast(act, "删除成功!");
                                                try {
                                                    mediaList.remove(position);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                pVListAdapter.setNewData(mediaList);
                                                pVListAdapter.notifyDataSetChanged();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int code, String msg) {
                                        ToastUtils.myToast(act, "删除失败" + msg);
                                    }
                                });
                    } else {//本地回显操作
                        try {
                            mediaList.remove(position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        adapter.setNewData(mediaList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * @param pVListAdapter
     * @param recyclerView
     * @param mediaList     fragment 里面操作
     *                      文件选择弹窗操作适配器
     * @param showType      回显类型
     */
    public void photoDialogListAdapter(final PictureOrVideoListNewAdapter pVListAdapter, RecyclerView recyclerView,
                                       final List<LocalMedia> mediaList, final int showType) {
        recyclerView.setAdapter(pVListAdapter);
        pVListAdapter.setNewData(mediaList);
        pVListAdapter.notifyDataSetChanged();
        pVListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == pVListAdapter.getItemCount() - 1) {
                    pictureVideoDialog.SetItemClickListener(new PictureVideoDialog.ItemClickListener() {
                        @Override
                        public void photo() {
                            try {
                                if (mediaList.size() >= 6) {
                                    ToastUtils.myToast(act, "最多上传6张图片");
                                } else {
                                    PictureSelectorConfig.initSingleAllConfig(fragment, PictureConfig.CHOOSE_REQUEST,
                                            mediaList);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void camera() {
                            if (mediaList.size() >= 6) {
                                ToastUtils.myToast(act, "最多上传6张图片");
                            } else {
                                PictureSelectorConfig.openCameraImg(fragment, PictureConfig.CHOOSE_REQUEST);
                            }
                        }

                        @Override
                        public void cameraVideo() {
                            if (mediaList.size() >= 6) {
                                ToastUtils.myToast(act, "最多上传6张图片");
                            } else {
                                PictureSelectorConfig.openCameraVideo(fragment, PictureConfig.CHOOSE_REQUEST);
                            }
                        }
                    });
                } else if (ListUtils.listEmpty(mediaList)) {
                    if ("image/jpeg".equals(PVAUtils.getFileLastType(mediaList.get(position).getPath()))) {
                        if (mediaList.get(position).isCut()) {
                            ShowBigImg.build(fragment.getActivity(), mediaList.get(position).getCutPath());
                        } else {
                            ShowBigImg.build(fragment.getActivity(), mediaList.get(position).getPath());
                        }
                    } else if ("video/3gp".equals(PVAUtils.getFileLastType(mediaList.get(position).getPath()))) {
                        ShowVideo.playLineVideo(act, mediaList.get(position).getPath());
                    }
                }
            }
        });
        pVListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                if (view.getId() == R.id.ll_del) {
                    if (showType == 0) {//网络回显操作
                        String fileName = new File(mediaList.get(position).getPath()).getName();
                        HttpRequest.get(act).url(ServerInterface.deleteFile)
                                .params("fileName", fileName)
                                .doPost(new HttpStringCallBack() {
                                    @Override
                                    public void onSuccess(Object result) {
                                        try {
                                            JSONObject object = new JSONObject(result.toString());
                                            if (object.optInt("code") == 0) {
                                                ToastUtils.myToast(act, "删除成功!");
                                                try {
                                                    mediaList.remove(position);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                pVListAdapter.setNewData(mediaList);
                                                pVListAdapter.notifyDataSetChanged();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int code, String msg) {
                                        ToastUtils.myToast(act, "删除失败" + msg);
                                    }
                                });
                    } else {//本地回显操作
                        try {
                            mediaList.remove(position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        adapter.setNewData(mediaList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * @param recyclerView
     * @param mediaList    activity 里面操作
     *                     界面直接相册、相机、视频功能操作
     */
    public void photoCameraListAdapter(final PictureOrVideoListAdapter pVListAdapter, RecyclerView recyclerView, final List<LocalMedia> mediaList) {
        recyclerView.setAdapter(pVListAdapter);
        pVListAdapter.setNewData(mediaList);
        pVListAdapter.notifyDataSetChanged();
        pVListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == pVListAdapter.getItemCount() - 3) {
                    PictureSelectorConfig.openCameraImg(act, PictureConfig.CHOOSE_REQUEST);
                } else if (position == pVListAdapter.getItemCount() - 2) {
                    PictureSelectorConfig.openCameraVideo(act, PictureConfig.CHOOSE_REQUEST);
                } else if (position == pVListAdapter.getItemCount() - 1) {
                    try {
                        PictureSelectorConfig.initSingleAllConfig(act, PictureConfig.CHOOSE_REQUEST, mediaList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (ListUtils.listEmpty(mediaList)) {
                        if ("image/jpeg".equals(PVAUtils.getFileLastType(mediaList.get(position).getPath()))) {
                            if (mediaList.get(position).isCut()) {
                                ShowBigImg.build(act, mediaList.get(position).getCutPath());
                            } else {
                                ShowBigImg.build(act, mediaList.get(position).getPath());
                            }
                        } else if ("video/3gp".equals(PVAUtils.getFileLastType(mediaList.get(position).getPath()))) {
                            ShowVideo.playLineVideo(act, mediaList.get(position).getPath());
                        }
                    }
                }
            }
        });
        pVListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter dd, View view, final int position) {
                if (view.getId() == R.id.ll_del) {
                    String fileName = new File(mediaList.get(position).getPath()).getName();
                    HttpRequest.get(act).url(ServerInterface.deleteFile)
                            .params("fileName", fileName)
                            .doPost(new HttpStringCallBack() {
                                @Override
                                public void onSuccess(Object result) {
                                    try {
                                        JSONObject object = new JSONObject(result.toString());
                                        if (object.optInt("code") == 0) {
                                            try {
                                                ToastUtils.myToast(act, "删除成功!");
                                                mediaList.remove(position);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            pVListAdapter.setNewData(mediaList);
                                            pVListAdapter.notifyDataSetChanged();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(int code, String msg) {
                                    ToastUtils.myToast(act, "删除失败" + msg);
                                }
                            });
                }
            }
        });
    }

    /**
     * @param pVListAdapter
     * @param recyclerView
     * @param mediaList     activity 里面操作
     *                      文件选择弹窗操作适配器
     * @param showType      回显类型
     */
    public void photoCameraDialogListAdapter(final PictureOrVideoListNewAdapter pVListAdapter,
                                             RecyclerView recyclerView, final List<LocalMedia> mediaList, final int showType) {
        recyclerView.setAdapter(pVListAdapter);
        pVListAdapter.setNewData(mediaList);
        pVListAdapter.notifyDataSetChanged();
        pVListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (position == pVListAdapter.getItemCount() - 1) {
                    pictureVideoDialog.SetItemClickListener(new PictureVideoDialog.ItemClickListener() {
                        @Override
                        public void photo() {
                            try {
                                if (mediaList.size() >= 6) {
                                    ToastUtils.myToast(act, "最多上传6张图片");
                                } else {
                                    PictureSelectorConfig.initSingleAllConfig(act, PictureConfig.CHOOSE_REQUEST, mediaList);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void camera() {
                            if (mediaList.size() >= 6) {
                                ToastUtils.myToast(act, "最多上传6张图片");
                            } else {
                                PictureSelectorConfig.openCameraImg(act, PictureConfig.CHOOSE_REQUEST);
                            }
                        }

                        @Override
                        public void cameraVideo() {
                            if (mediaList.size() >= 6) {
                                ToastUtils.myToast(act, "最多上传6张图片");
                            } else {
                                PictureSelectorConfig.openCameraVideo(act, PictureConfig.CHOOSE_REQUEST);
                            }
                        }
                    });
                } else if (ListUtils.listEmpty(mediaList)) {
                    if ("image/jpeg".equals(PVAUtils.getFileLastType(mediaList.get(position).getPath()))) {
                        if (mediaList.get(position).isCut()) {
                            ShowBigImg.build(act, mediaList.get(position).getCutPath());
                        } else {
                            ShowBigImg.build(act, mediaList.get(position).getPath());
                        }
                    } else if ("video/3gp".equals(PVAUtils.getFileLastType(mediaList.get(position).getPath()))) {
                        ShowVideo.playLineVideo(act, mediaList.get(position).getPath());
                    }
                }
            }

        });
        pVListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                if (view.getId() == R.id.ll_del) {
                    if (showType == 0) {//网络回显操作
                        String fileName = new File(mediaList.get(position).getPath()).getName();
                        HttpRequest.get(act).url(ServerInterface.deleteFile)
                                .params("fileName", fileName)
                                .doPost(new HttpStringCallBack() {
                                    @Override
                                    public void onSuccess(Object result) {
                                        try {
                                            JSONObject object = new JSONObject(result.toString());
                                            if (object.optInt("code") == 0) {
                                                ToastUtils.myToast(act, "删除成功!");
                                                try {
                                                    mediaList.remove(position);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                pVListAdapter.setNewData(mediaList);
                                                pVListAdapter.notifyDataSetChanged();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int code, String msg) {
                                        ToastUtils.myToast(act, "删除失败" + msg);
                                    }
                                });
                    } else {//本地回显操作
                        try {
                            mediaList.remove(position);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        adapter.setNewData(mediaList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    /**
     * @param recyclerView
     * @param mediaList    展示图片视频
     *                     Activity 使用
     */
    public void photoCameraAdapter(PictureVideoShowAdapter pVShowAdapter, RecyclerView recyclerView, final List<LocalMedia> mediaList) {
        recyclerView.setAdapter(pVShowAdapter);
        pVShowAdapter.setNewData(mediaList);
        pVShowAdapter.notifyDataSetChanged();
        pVShowAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (ListUtils.listEmpty(mediaList)) {
                    if ("image/jpeg".equals(PVAUtils.getFileLastType(mediaList.get(position).getPath()))) {
                        if (mediaList.get(position).isCut()) {
                            ShowBigImg.build(act, mediaList.get(position).getCutPath());
                        } else {
                            ShowBigImg.build(act, mediaList.get(position).getPath());
                        }
                    } else if ("video/3gp".equals(PVAUtils.getFileLastType(mediaList.get(position).getPath()))) {
                        ShowVideo.playLineVideo(act, mediaList.get(position).getPath());
                    }
                }
            }
        });
    }
    /**
     * @param recyclerView
     * @param mediaList    展示图片视频
     *                     Fragment 使用
     */
    public void photoCameraAdapter(PictureVideoShowAdapter pVShowAdapter, RecyclerView recyclerView, final List<LocalMedia> mediaList,int tt) {
        recyclerView.setAdapter(pVShowAdapter);
        pVShowAdapter.setNewData(mediaList);
        pVShowAdapter.notifyDataSetChanged();
        pVShowAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (ListUtils.listEmpty(mediaList)) {
                    if ("image/jpeg".equals(PVAUtils.getFileLastType(mediaList.get(position).getPath()))) {
                        if (mediaList.get(position).isCut()) {
                            ShowBigImg.build(fragment.getActivity(), mediaList.get(position).getCutPath());
                        } else {
                            ShowBigImg.build(fragment.getActivity(), mediaList.get(position).getPath());
                        }
                    } else if ("video/3gp".equals(PVAUtils.getFileLastType(mediaList.get(position).getPath()))) {
                        ShowVideo.playLineVideo(fragment.getActivity(), mediaList.get(position).getPath());
                    }
                }
            }
        });
    }
}
