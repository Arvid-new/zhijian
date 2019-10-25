package com.haozhiyan.zhijian.application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.haozhiyan.zhijian.myDao.DaoMaster;
import com.haozhiyan.zhijian.myDao.DaoSession;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * Created by WangZhenKai on 2019/4/24.
 * Describe:
 */
public class MyApp extends MultiDexApplication {
    private static MyApp context;
    private DaoSession daoSession;
    public static final String buglyAppId = "6e274cab11";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        CrashReport.initCrashReport(getApplicationContext(), buglyAppId, true);//第三个参数为调试模式开关，开发调试时可为true
        //初始化 数据库
        initDB();
        //初始化全局的网络连接对象
        initNetWork();
        //初始化图片加载库
        initFresco();
        //ImageLoader加载图片初始化
        initImageLoader();
        //初始化极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Log.e("registrationID", JPushInterface.getRegistrationID(this) + "");
    }

    public static MyApp getInstance() {
        return context;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    private void initDB() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "My.db");
        SQLiteDatabase liteDatabase = openHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(liteDatabase);
        daoSession = daoMaster.newSession();
    }

    /**
     * 配置全局的网络连接
     */
    private void initNetWork() {
        //创建请求头对象
        //HttpHeaders headers = new HttpHeaders();
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //全局的读取超时时间  基于前面的通道建立完成后，客户端终于可以向服务端发送数据了
            builder.readTimeout(10000, TimeUnit.MILLISECONDS);
            //全局的写入超时时间  服务器发回消息，可是客户端出问题接受不到了
            builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
            //全局的连接超时时间  http建立通道的时间
            builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
            //使用sp保持cookie，如果cookie不过期，则一直有效
            builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
            OkGo.getInstance()
                    //如果使用默认的 60秒,以下三行也不需要传
//                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
//                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    //.setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间

//                    .setOkHttpClient(builder.build())
                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,
                    .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    //可以全局统一设置缓存时间,默认永不过期
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(0)
                    //可以设置https的证书,

//                    .setCertificates();  //方法一：信任所有证书,不安全有风险

                    //这两行同上，不需要就不要加入
                    //.addCommonHeaders(headers); //设置全局公共头
                    .init(this); //必须调用初始化
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFresco() {
        //支持多种格式图片的缩放使用的方式
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(getApplicationContext())
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(getApplicationContext(), config);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    private void initImageLoader() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(getApplicationContext());
        config.memoryCacheExtraOptions(480, 800);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.memoryCache(new WeakMemoryCache());
        config.memoryCacheSize(cacheSize);//设置内存缓存的最大字节数为 App 最大可用内存的 1/8。
        config.diskCacheSize(100 * 1024 * 1024); //100 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.memoryCacheSizePercentage(13); //线程数
        config.diskCacheFileCount(150);//可以缓存的文件数量
        config.defaultDisplayImageOptions(DisplayImageOptions.createSimple());
        config.writeDebugLogs(); // Remove for release app
        ImageLoader.getInstance().init(config.build());
    }

}
