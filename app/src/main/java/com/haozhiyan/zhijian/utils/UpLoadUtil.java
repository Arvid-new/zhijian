package com.haozhiyan.zhijian.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.UpLoadCallBack;
import com.haozhiyan.zhijian.listener.UpLoadListCallBack;
import com.haozhiyan.zhijian.model.ParameterMap;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.widget.LoadingDialog;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.haozhiyan.zhijian.utils.ListUtils.arrayEmpty;

public class UpLoadUtil {
    private Context context;
    private List<LocalMedia> selectList;
    private List<List<LocalMedia>> selectLists;
    private UpLoadCallBack callBack;
    private UpLoadListCallBack listCallBack;
    private boolean isList = false;
    private boolean isMustPic = false;//是否必须上传图片

    private UpLoadUtil(Context context, List<List<LocalMedia>> selectList, boolean isList, boolean isMustPic) {
        this.context = context;
        this.selectLists = selectList;
        this.isList = isList;
        this.isMustPic = isMustPic;
    }

    private UpLoadUtil(Context context, List<LocalMedia> selectList, boolean isMustPic) {
        this.context = context;
        this.selectList = selectList;
        this.isMustPic = isMustPic;
    }

    public static UpLoadUtil init(Context context, List<LocalMedia> selectList) {
        return new UpLoadUtil(context, selectList, false);
    }

    /**
     * @param context
     * @param selectList
     * @param isMustPic  是否必传图片 false 可以不应穿   true  必须上传图片  默认为false
     * @return
     */
    public static UpLoadUtil init(Context context, List<LocalMedia> selectList, boolean isMustPic) {
        return new UpLoadUtil(context, selectList, isMustPic);
    }

    public static UpLoadUtil initList(Context context, List<List<LocalMedia>> selectList) {
        return new UpLoadUtil(context, selectList, true, false);
    }

    public static UpLoadUtil initList(Context context, List<List<LocalMedia>> selectList, boolean isMustPic) {
        return new UpLoadUtil(context, selectList, true, isMustPic);
    }

    public void Call(@NonNull UpLoadCallBack callBack) {
        this.callBack = callBack;
        if (isMustPic) {
            if (selectList != null && selectList.size() > 0) {
                showLoadView("上传中...");
                upload();
            } else {
                ToastUtils.myToast(context, "请按要求上传图片");
            }
        } else {
            if (selectList != null && selectList.size() > 0) {
                showLoadView("上传中...");
                upload();
            } else {
                callBack.onComplete(null);
            }
        }

    }

    public void Call(@NonNull UpLoadListCallBack callBack) {
        this.listCallBack = callBack;
        if (isMustPic) {
            if (selectLists != null && selectLists.size() > 0) {
                for (int i = 0; i < selectLists.size(); i++) {
                    if (selectLists.get(i).size() <= 0) {
                        ToastUtils.myToast(context, "请按要求上传图片");
                        return;
                    }
                }
                showLoadView("上传中...");
                startNum = 0;
                for (int i = 0; i < selectLists.size(); i++) {
                    upload(i, selectLists.get(i));
                }
            } else {
                ToastUtils.myToast(context, "请按要求上传图片");
            }
        } else {
            if (selectLists != null && selectLists.size() > 0) {
                showLoadView("上传中...");
                startNum = 0;
                for (int i = 0; i < selectLists.size(); i++) {
                    upload(i, selectLists.get(i));
                }
            } else {
                callBack.onComplete(null);
            }
        }
    }

    //上传图片 单张 多张
    private void upload() {

        List<File> filePaths = new ArrayList<>();
        for (int i = 0; i < selectList.size(); i++) {
            int pictureType = PictureMimeType.isPictureType(selectList.get(i).getPictureType());
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
            if (pictureType == PictureConfig.TYPE_IMAGE) {//图片
                if (selectList.get(i).isCompressed()) {
                    filePaths.add(new File(selectList.get(i).getCompressPath()));
                } else {
                    if (selectList.get(i).isCut()) {
                        filePaths.add(new File(selectList.get(i).getCutPath()));
                    } else {
                        filePaths.add(new File(selectList.get(i).getPath()));
                    }
                }
            } else if (pictureType == PictureConfig.TYPE_VIDEO) {//视频
                filePaths.add(new File(selectList.get(i).getPath()));
            }
        }
        HttpRequest.get(context)
                .url(ServerInterface.uploadFile)
                .params(ParameterMap.put("fileList", filePaths))
                .doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                hideLoadView();
                LogUtils.i("uploadJson==", result.toString());
                try {
                    JSONObject object = new JSONObject(result.toString());
                    if (object.optInt("code") == 0) {
                        JSONArray imageArray = object.optJSONArray("fileName");
                        if (arrayEmpty(imageArray)) {
                            StringBuffer buffer = new StringBuffer();
                            for (int i = 0; i < imageArray.length(); i++) {
                                if (i > 0) {
                                    buffer.append(",");
                                }
                                buffer.append(imageArray.optString(i));
                            }
                            callBack.onComplete(buffer.toString());
                        } else {
                        }
                    } else {
                        LogUtils.i("upload_error==", "请求错误");
                        ToastUtils.myToast(context, object.optString("msg"));
                    }
                } catch (JSONException ignored) {
                    ignored.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtils.i("upload_fail==", "请求失败");
                ToastUtils.myToast(context, msg);
                hideLoadView();
            }
        });

    }

    //上传图片 单张 多张
    private void upload(final int position, List<LocalMedia> selectList) {
        List<File> filePaths = new ArrayList<>();
        for (int i = 0; i < selectList.size(); i++) {
            int pictureType = PictureMimeType.isPictureType(selectList.get(i).getPictureType());
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
            if (pictureType == PictureConfig.TYPE_IMAGE) {//图片
                if (selectList.get(i).isCompressed()) {
                    filePaths.add(new File(selectList.get(i).getCompressPath()));
                } else {
                    if (selectList.get(i).isCut()) {
                        filePaths.add(new File(selectList.get(i).getCutPath()));
                    } else {
                        filePaths.add(new File(selectList.get(i).getPath()));
                    }
                }
            } else if (pictureType == PictureConfig.TYPE_VIDEO) {//视频
                filePaths.add(new File(selectList.get(i).getPath()));
            }
        }
        HttpRequest.get(context)
                .url(ServerInterface.uploadFile)
                .params(ParameterMap.put("fileList", filePaths))
                .doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LogUtils.i("uploadJson==", result.toString());
                try {
                    JSONObject object = new JSONObject(result.toString());
                    if (object.optInt("code") == 0) {
                        JSONArray imageArray = object.optJSONArray("fileName");
                        List<String> strings = new ArrayList<>();
                        for (int i = 0; i < imageArray.length(); i++) {
                            strings.add(imageArray.optString(i));
                        }
                        Next(position, strings);
                    } else {
                        LogUtils.i("upload_error==", "请求错误");
                        ToastUtils.myToast(context, object.optString("msg"));
                    }
                } catch (JSONException ignored) {
                    ignored.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtils.i("upload_fail==", "请求失败");
                ToastUtils.myToast(context, msg);
                OkGo.getInstance().cancelAll();
                hideLoadView();
            }
        });
    }

    //    private List<List<String>> paths;
    private List<String>[] paths2;
    private int startNum = 0;

    /**
     * 多个集合的集合上传图片 进行结果汇聚 与进行正确排序
     * @param position
     * @param strings
     */
    private void Next(int position, List<String> strings) {
        startNum++;
        if (paths2 == null)
            paths2 = new List[selectLists.size()];
        paths2[position] = strings;
        if (startNum == selectLists.size()) {
            hideLoadView();
            if (listCallBack != null) {
                listCallBack.onComplete(Arrays.asList(paths2));
            }
        }
    }


    private LoadingDialog dialog;

    protected void showLoadView(String str) {
        try {
            if (dialog == null) {
                dialog = new LoadingDialog(context);
            }
            dialog.setTitle(str);
            dialog.show();
        } catch (Exception ignored) {

        }
    }

    protected void hideLoadView() {
        try {
            dialog.dismiss();
        } catch (Exception ignored) {

        }
    }

}
