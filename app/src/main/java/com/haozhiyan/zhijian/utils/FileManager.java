package com.haozhiyan.zhijian.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Video;

import com.haozhiyan.zhijian.bean.MyVideo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/29.
 * Describe: Ydzj_project
 */
public class FileManager {

    private static FileManager mInstance;
    private static Context mContext;
    private static ContentResolver mContentResolver;
    private static Object mLock = new Object();

    public static FileManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new FileManager();
                    mContext = context;
                    mContentResolver = context.getContentResolver();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取本机视频列表
     *
     * @return
     */
    public List<MyVideo> getVideos() {
        List<MyVideo> videos = new ArrayList<MyVideo>();
        Cursor c = null;
        try {
            c = mContentResolver.query(Video.Media.EXTERNAL_CONTENT_URI, null, null, null, Video.Media.DEFAULT_SORT_ORDER);
            while (c.moveToNext()) {
                String path = c.getString(c.getColumnIndexOrThrow(Video.Media.DATA));// 路径
                if (!FileUtil.isExists(path)) {
                    continue;
                }
                int id = c.getInt(c.getColumnIndexOrThrow(Video.Media._ID));// 视频的id
                String name = c.getString(c.getColumnIndexOrThrow(Video.Media.DISPLAY_NAME)); // 视频名称
                String resolution = c.getString(c.getColumnIndexOrThrow(Video.Media.RESOLUTION)); //分辨率
                long size = c.getLong(c.getColumnIndexOrThrow(Video.Media.SIZE));// 大小
                long duration = c.getLong(c.getColumnIndexOrThrow(Video.Media.DURATION));// 时长
                long date = c.getLong(c.getColumnIndexOrThrow(Video.Media.DATE_MODIFIED));//修改时间
                MyVideo video = new MyVideo(id, path, name, resolution, size, date, duration);
                videos.add(video);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return videos;
    }
}
