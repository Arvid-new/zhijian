package com.haozhiyan.zhijian.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;

import com.haozhiyan.zhijian.model.UserInfo;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * 多图选择框架 PictureSelector的初始化配置
 */

public class PictureSelectorConfig {


    public static final String IMG_LIST = "img_list"; //第几张图片
    public static final String POSITION = "position"; //第几张图片
    public static final String SHOWDELETE = "true"; //是否显示删除按钮 默认显示
    public static final String PIC_PATH = "pic_path"; //图片路径
    public static final int MAX_SELECT_PIC_NUM = 9; // 最多上传5张图片
    public static final int REQUEST_CODE_MAIN = 10; //请求码
    public static final int RESULT_CODE_VIEW_IMG = 11; //查看大图页面的结果码

    private static boolean isCompress=true;//是否压缩

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 初始化多图选择的配置
     *
     * @param activity
     * @param maxTotal
     */
    public static void initMultiConfig(Activity activity, int maxTotal) throws Exception {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                // 进入相册 以下是例子：用不到的api可以不写
                PictureSelector.create(activity)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
//                        .maxSelectNum(maxTotal)// 最大图片选择数量 int
//                        .minSelectNum(0)// 最小选择数量 int
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片 true or false
                        .previewVideo(false)// 是否可预览视频 true or false
                        .enablePreviewAudio(false) // 是否可播放音频 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
//                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                        .enableCrop(false)// 是否裁剪 true or false
                        .compress(isCompress)// 是否压缩 true or false
//                .compressMaxKB(1024)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
//                .compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效  int
                        .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
                        .isGif(false)// 是否显示gif图片 true or false
//                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                        .circleDimmedLayer(false)// 是否圆形裁剪 true or false
//                .showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
//                .showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                        .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> lists
                        .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality()// 裁剪压缩质量 默认90 int
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
//                .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                        .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .videoQuality(1)// 视频录制质量 0 or 1 int
//                .videoSecond()// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化单张图片选择的配置
     *
     * @param activity
     */
    public static void initSingleImgConfig(Activity activity, int resultCode) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                // 进入相册 以下是例子：用不到的api可以不写
                PictureSelector.create(activity)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
//                        .maxSelectNum(1)// 最大图片选择数量 int
//                        .minSelectNum(1)// 最小选择数量 int
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片 true or false
                        .previewVideo(false)// 是否可预览视频 true or false
                        .enablePreviewAudio(false) // 是否可播放音频 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
//                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                        .enableCrop(true)// 是否裁剪 true or false
                        .compress(isCompress)// 是否压缩 true or false
//                .compressMaxKB(500)//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效 int
//                .compressWH(7, 10) // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效  int
                        .glideOverride(130, 130)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                        .isGif(false)// 是否显示gif图片 true or false
                        .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                        .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                        .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                        .openClickSound(false)// 是否开启点击声音 true or false
//                .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> lists
                        .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality()// 裁剪压缩质量 默认90 int
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                        .rotateEnabled(false) // 裁剪是否可旋转图片 true or false
                        .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoSecond()// 显示多少秒以内的视频or音频也可适用 int
                        .recordVideoSecond(10)//视频秒数录制 默认60s int
                        .forResult(resultCode);//结果回调onActivityResult code
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 相册选择
     *
     * @param activity
     */
    public static void initSingleAllConfig(Activity activity, int resultCode, List<LocalMedia> selectList) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                // 进入相册 以下是例子：用不到的api可以不写
                PictureSelector.create(activity)
                        .openGallery(PictureMimeType.ofAll())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
//                        .maxSelectNum(1)// 最大图片选择数量 int
//                        .minSelectNum(1)// 最小选择数量 int
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(false)// 是否可预览图片 true or false
                        .previewVideo(true)// 是否可预览视频 true or false
                        .enablePreviewAudio(false) // 是否可播放音频 true or false
                        .isCamera(false)// 是否显示拍照按钮 true or false
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .compress(isCompress)// 是否压缩 true or false
                        .glideOverride(130, 130)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
//                        .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> lists
                        .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoMaxSecond(10)
//                .videoMinSecond(5)
//                .videoSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                        .recordVideoSecond(10)//视频秒数录制 默认60s int
                        .enableEdit(true)// 是否编辑投片
                        .enableCrop(false)// 是否裁剪
                        .setWatermark(UserInfo.create(activity).getUserName() + "  " + TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))
                        .setWatermarkTextColor(Color.WHITE)
                        .setWatermarkBackGroundColor(0x785d5b59)
                        .setWatermarkTextSize(15)
                        .setWatermarkGravity(Gravity.RIGHT | Gravity.BOTTOM)
                        .forResult(resultCode);//结果回调onActivityResult code
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 相册选择
     *
     * @param fragment
     */
    public static void initSingleAllConfig(android.support.v4.app.Fragment fragment, int resultCode, List<LocalMedia> selectList) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(fragment.getContext(),
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(fragment.getActivity(), PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                // 进入相册 以下是例子：用不到的api可以不写
                PictureSelector.create(fragment)
                        .openGallery(PictureMimeType.ofAll())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
//                        .maxSelectNum(1)// 最大图片选择数量 int
//                        .minSelectNum(0)// 最小选择数量 int
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(false)// 是否可预览图片 true or false
                        .previewVideo(true)// 是否可预览视频 true or false
                        .enablePreviewAudio(false) // 是否可播放音频 true or false
                        .isCamera(false)// 是否显示拍照按钮 true or false
                        .compress(isCompress)// 是否压缩 true or false
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .glideOverride(130, 130)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                        .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
//                        .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> lists
                        .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoMaxSecond(10)
//                .videoMinSecond(5)
//                .videoSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                        .recordVideoSecond(10)//视频秒数录制 默认60s int
                        .enableEdit(true)// 是否编辑投片
                        .enableCrop(false)// 是否裁剪
                        .setWatermark(UserInfo.create(fragment.getContext()).getUserName() + "  " + TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))
                        .setWatermarkTextColor(Color.WHITE)
                        .setWatermarkBackGroundColor(0x785d5b59)
                        .setWatermarkTextSize(15)
                        .setWatermarkGravity(Gravity.RIGHT | Gravity.BOTTOM)
                        .forResult(resultCode);//结果回调onActivityResult code
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化直接拍照单张图片的配置  加水印编辑
     *
     * @param activity
     */
    public static void openCameraImg(Activity activity, int resultCode) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                PictureSelector.create(activity)
                        .openCamera(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
//                        .maxSelectNum(1)// 最大图片选择数量 int
//                        .minSelectNum(1)// 最小选择数量 int
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .recordVideoSecond(10)//视频秒数录制 默认60s int
                        .enableEdit(true)// 是否编辑投片
                        .enableCrop(false)// 是否裁剪
                        .compress(isCompress)// 是否压缩 true or false
                        .setWatermark(UserInfo.create(activity).getUserName() + "  " + TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))
                        .setWatermarkTextColor(Color.WHITE)
                        .setWatermarkBackGroundColor(0x785d5b59)
                        .setWatermarkTextSize(15)
                        .setWatermarkGravity(Gravity.RIGHT | Gravity.BOTTOM)
                        .forResult(resultCode);//结果回调onActivityResult code
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 初始化直接拍照单张图片的配置  不加水印编辑
     *
     * @param activity
     */
    public static void CameraImg(Activity activity, int resultCode) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                PictureSelector.create(activity)
                        .openCamera(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
//                        .maxSelectNum(1)// 最大图片选择数量 int
//                        .minSelectNum(1)// 最小选择数量 int
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .enableEdit(true)// 是否编辑投片
                        .enableCrop(true)// 是否裁剪
                        .compress(isCompress)// 是否压缩 true or false
                        .forResult(resultCode);//结果回调onActivityResult code
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openCameraImg(android.support.v4.app.Fragment fragment, int resultCode) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(fragment.getContext(),
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(fragment.getActivity(), PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                PictureSelector.create(fragment)
                        .openCamera(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
//                        .maxSelectNum(1)// 最大图片选择数量 int
//                        .minSelectNum(1)// 最小选择数量 int
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .recordVideoSecond(10)//视频秒数录制 默认60s int
                        .enableEdit(true)// 是否编辑投片
                        .enableCrop(false)// 是否裁剪
                        .compress(isCompress)// 是否压缩 true or false
                        .setWatermark(UserInfo.create(fragment.getContext()).getUserName() + "  " + TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))
                        .setWatermarkTextColor(Color.WHITE)
                        .setWatermarkBackGroundColor(0x785d5b59)
                        .setWatermarkTextSize(15)
                        .setWatermarkGravity(Gravity.RIGHT | Gravity.BOTTOM)
                        .forResult(resultCode);//结果回调onActivityResult code
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 初始化直接录制视频的配置  加水印编辑
     *
     * @param activity
     */
    public static void openCameraVideo(Activity activity, int resultCode) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                // 进入相册 以下是例子：用不到的api可以不写
                PictureSelector.create(activity)
                        .openCamera(PictureMimeType.ofVideo())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .recordVideoSecond(10)//视频秒数录制 默认60s int
                        .enableEdit(true)// 是否编辑投片
                        .enableCrop(false)// 是否裁剪
                        .compress(isCompress)// 是否压缩 true or false
                        .setWatermark(UserInfo.create(activity).getUserName() + "  " + TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))
                        .setWatermarkTextColor(Color.WHITE)
                        .setWatermarkBackGroundColor(0x785d5b59)
                        .setWatermarkTextSize(15)
                        .setWatermarkGravity(Gravity.RIGHT | Gravity.BOTTOM)
                        .forResult(resultCode);//结果回调onActivityResult code
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 初始化直接录制视频的配置  不加水印编辑
     *
     * @param activity
     */
    public static void CameraVideo(Activity activity, int resultCode) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                // 进入相册 以下是例子：用不到的api可以不写
                PictureSelector.create(activity)
                        .openCamera(PictureMimeType.ofVideo())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .recordVideoSecond(10)//视频秒数录制 默认60s int
                        .enableEdit(true)// 是否编辑投片
                        .enableCrop(false)// 是否裁剪
                        .compress(isCompress)// 是否压缩 true or false
                        .forResult(resultCode);//结果回调onActivityResult code
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void openCameraVideo(android.support.v4.app.Fragment fragment, int resultCode) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(fragment.getContext(),
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(fragment.getActivity(), PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            } else {
                // 进入相册 以下是例子：用不到的api可以不写
                PictureSelector.create(fragment)
                        .openCamera(PictureMimeType.ofVideo())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .recordVideoSecond(10)//视频秒数录制 默认60s int
                        .enableEdit(true)// 是否编辑投片
                        .enableCrop(false)// 是否裁剪
                        .compress(isCompress)// 是否压缩 true or false
                        .setWatermark(UserInfo.create(fragment.getContext()).getUserName() + "  " + TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))
                        .setWatermarkTextColor(Color.WHITE)
                        .setWatermarkBackGroundColor(0x785d5b59)
                        .setWatermarkTextSize(15)
                        .setWatermarkGravity(Gravity.RIGHT | Gravity.BOTTOM)
                        .forResult(resultCode);//结果回调onActivityResult code
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
