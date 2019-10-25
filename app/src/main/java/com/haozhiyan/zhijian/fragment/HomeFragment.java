package com.haozhiyan.zhijian.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.adapter.HomeProjectAdapter;
import com.haozhiyan.zhijian.adapter.HomeProjectListAdapter;
import com.haozhiyan.zhijian.bean.HomeProjectBean;
import com.haozhiyan.zhijian.bean.HomeProjectListBean;
import com.haozhiyan.zhijian.utils.AnimationUtil;
import com.haozhiyan.zhijian.utils.SystemUtils;


/**
 * Created by WangZhenKai on 2019/4/24.
 * Describe: 首页
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private MapView mapView;
    private AMap aMap;
    //    public AMapLocationClient mLocationClient = null;
//    public AMapLocationClientOption mLocationOption = null;
    private MyLocationStyle myLocationStyle;
    private TextView tv_select_name;
    private ImageView iv_select_icon;
    private ImageView iv_switch_page;
    private RelativeLayout mHiddenLayout;
    private LinearLayout linear_close;
    private int mHiddenViewMeasuredHeight;
    private float mDensity;
    private int modeleStyle = 0; //0地图模式 1列表模式
    private RecyclerView homeFGProjectList;
    private RecyclerView homeProjectCheckRcv;


//    //声明定位回调监听器
//    public AMapLocationListener mLocationListener = new AMapLocationListener() {
//        @Override
//        public void onLocationChanged(AMapLocation amapLocation) {
//            if (amapLocation != null) {
//                if (amapLocation.getErrorCode() == 0) {
//                    //移动地图中心到当前的定位位置
//
//                    String city = amapLocation.getCity();
////                    requestPopData(city);
//                    String Latitude = amapLocation.getLatitude() + "";
//                    String Longitude = amapLocation.getLongitude() + "";
////                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()), 13));
//                }
//            }
//        }
//    };

    @Override
    public void initViewBundle(Bundle savedInstanceState) {
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);// 此方法必须重写
            initMap();
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        tv_select_name = getOutView(view, R.id.tv_select_name);
        mapView = getOutView(view, R.id.mapView);
        iv_select_icon = getOutView(view, R.id.iv_select_icon);
        iv_switch_page = getOutView(view, R.id.iv_switch_page);
        mHiddenLayout = getOutView(view, R.id.rl_hidden);
        linear_close = getOutView(view, R.id.linear_close);
        homeFGProjectList = getOutView(view, R.id.homeFGProjectList);
        homeProjectCheckRcv = getOutView(view, R.id.homeProjectCheckRcv);
        mDensity = SystemUtils.getPhoneScreenHight(ctx);
        mHiddenViewMeasuredHeight = SystemUtils.getPhoneScreenHight(ctx);
        linear_close.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mHiddenViewMeasuredHeight * 2 / 3));
    }

    @Override
    public void initListener() {
        tv_select_name.setOnClickListener(this);
        iv_switch_page.setOnClickListener(this);
        mHiddenLayout.setOnClickListener(this);
    }

    @Override
    public void initData(boolean isNetWork) {
        homeFGProjectList.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false));
        final HomeProjectListAdapter adapter = new HomeProjectListAdapter(ctx);
        HomeProjectListBean projectListBean = new Gson().fromJson("{\n" +
                "\"data\":[{\n" +
                "\"project\":\"名门地产\",\n" +
                "\"list\":[{\n" +
                "\"id\":\"0\",\n" +
                "\"name\":\"名门紫园\"},\n" +
                "{\n" +
                "\"id\":\"1\",\n" +
                "\"name\":\"名门紫园\"},{\n" +
                "\"id\":\"2\",\n" +
                "\"name\":\"名门紫园\"},{\n" +
                "\"id\":\"3\",\n" +
                "\"name\":\"名门紫园\"}\n" +
                "]\n" +
                "}\n" +
                ",{\n" +
                "\"project\":\"名门地产\",\n" +
                "\"list\":[{\n" +
                "\"id\":\"4\",\n" +
                "\"name\":\"名门紫园\"},\n" +
                "{\n" +
                "\"id\":\"5\",\n" +
                "\"name\":\"名门紫园\"},{\n" +
                "\"id\":\"6\",\n" +
                "\"name\":\"名门紫园\"},{\n" +
                "\"id\":\"7\",\n" +
                "\"name\":\"名门紫园\"}\n" +
                "]\n" +
                "}]\n" +
                "\n" +
                "}", HomeProjectListBean.class);
        adapter.setNewData(projectListBean.getData());
        homeFGProjectList.setAdapter(adapter);


        homeProjectCheckRcv.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false));

        final HomeProjectBean projectBean = new Gson().fromJson("{\n" +
                "\t\"data\": [{\n" +
                "\t\t\"project\": \"名门地产\",\n" +
                "\t\t\"id\": \"0\",\n" +
                "\t\t\"latitude\": \"34.802662\",\n" +
                "\t\t\"longitude\": \"113.771783\"\n" +
                "\n" +
                "\t}, {\n" +
                "\t\t\"project\": \"名门地产2\",\n" +
                "\t\t\"id\": \"1\",\n" +
                "\t\t\"latitude\": \"34.603662\",\n" +
                "\t\t\"longitude\": \"114.781783\"\n" +
                "\t}]\n" +
                "}", HomeProjectBean.class);
        final HomeProjectAdapter projectAdapter = new HomeProjectAdapter();
        projectAdapter.setNewData(projectBean.getData());
        homeProjectCheckRcv.setAdapter(projectAdapter);
        projectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                projectAdapter.setSelectID(position);
                projectAdapter.notifyDataSetChanged();
                tv_select_name.setText(projectBean.getData().get(position).getProject());
                if (mHiddenLayout.getVisibility() == View.VISIBLE) {
                    AnimationUtil.getInstance().animateClose(mHiddenLayout);
                    AnimationUtil.getInstance().animationIvClose(iv_select_icon);
                }

                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(
                                Double.parseDouble(projectBean.getData().get(position).getLatitude()),
                                Double.parseDouble(projectBean.getData().get(position).getLongitude())), 12));

            }
        });

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_hidden:
            case R.id.tv_select_name:
                if (mHiddenLayout.getVisibility() == View.GONE) {
                    AnimationUtil.getInstance().animateOpen(mHiddenLayout, mHiddenViewMeasuredHeight);
                    AnimationUtil.getInstance().animationIvOpen(iv_select_icon);
                } else {
                    AnimationUtil.getInstance().animateClose(mHiddenLayout);
                    AnimationUtil.getInstance().animationIvClose(iv_select_icon);
                }
                break;
            case R.id.iv_switch_page:
                if (modeleStyle == 0) {
                    if (mHiddenLayout.getVisibility() == View.VISIBLE) {
                        AnimationUtil.getInstance().animateClose(mHiddenLayout);
                        AnimationUtil.getInstance().animationIvClose(iv_select_icon);
                    }
                    iv_switch_page.setImageResource(R.drawable.icon_map);
                    homeFGProjectList.setVisibility(View.VISIBLE);
                    mapView.setVisibility(View.GONE);
                    iv_select_icon.clearAnimation();
                    iv_select_icon.setVisibility(View.GONE);
                    tv_select_name.setText("项目列表");
                    tv_select_name.setClickable(false);
                    modeleStyle = 1;
                } else if (modeleStyle == 1) {
                    iv_switch_page.setImageResource(R.drawable.icon_list);
                    homeFGProjectList.setVisibility(View.GONE);
                    mapView.setVisibility(View.VISIBLE);
                    iv_select_icon.setVisibility(View.VISIBLE);
                    tv_select_name.setText("集团");
                    tv_select_name.setClickable(true);
                    modeleStyle = 0;
                }
                break;
            default:
                break;
        }
    }

    /**
     * 地图设置
     */
    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setMapLanguage(AMap.CHINESE);
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
//            AssetManager asset = getContext().getAssets();
//            aMap.setCustomMapStylePath();
//            aMap.setCustomMapStyle();
            UiSettings uiSettings = aMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(false);  //隐藏缩放按钮
//            uiSettings.log(-50);//隐藏logo
            myLocationStyle = new MyLocationStyle();
            myLocationStyle.interval(2000);
            myLocationStyle.radiusFillColor(ContextCompat.getColor(getContext(), R.color.translate));
            myLocationStyle.strokeColor(ContextCompat.getColor(getContext(), R.color.translate));
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
            aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            aMap.setMyLocationEnabled(false);// 设置为true表示启动显示定位蓝点
        }

//        //初始化定位
//        mLocationClient = new AMapLocationClient(getContext());
//        //设置定位回调监听
//        mLocationClient.setLocationListener(mLocationListener);
//
//        //初始化定位参数
//        mLocationOption = new AMapLocationClientOption();
//        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //设置是否返回地址信息（默认返回地址信息）
//        mLocationOption.setNeedAddress(true);
//        //设置是否只定位一次,默认为false
//        mLocationOption.setOnceLocation(true);
//        //设置是否强制刷新WIFI，默认为强制刷新
//        mLocationOption.setWifiActiveScan(true);
//        //设置是否允许模拟位置,默认为false，不允许模拟位置
//        mLocationOption.setMockEnable(false);
//        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
//        //给定位客户端对象设置定位参数
//        mLocationClient.setLocationOption(mLocationOption);
//        //启动定位

    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            mapView.onResume();
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble("34.902662"), Double.parseDouble("113.771783")), 12));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加地图上自定义标点
     */
    private void addMarker() {

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                aMap.clear();
//                for (int i = 0; i < projectListBean.getData().size(); i++) {
//
//                    ProjectListBean.DataBean dataBean = projectListBean.getData().get(i);
//
//                    final View markerView = LayoutInflater.from(act).inflate(R.layout.marker_bg, null);
//                    TextView name = markerView.findViewById(R.id.name);
//                    TextView price = markerView.findViewById(R.id.price);
//
//                    name.setText(dataBean.getName());
//                    price.setText(dataBean.getAverage_price() + "/㎡");
//
//                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(convertViewToBitmap(markerView));
//                    MarkerOptions markerOption = new MarkerOptions();
//                    markerOption.icon(bitmapDescriptor);
//
//
//                    double jing = 0;
//                    try {
//                        jing = Double.parseDouble(dataBean.getJing());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    double wei = 0;
//                    try {
//                        wei = Double.parseDouble(dataBean.getWei());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    LatLng latLng = new LatLng(wei, jing);
//                    aMap.addMarker(markerOption.position(latLng).title(dataBean.getId()).snippet(dataBean.getName()));
//                }
//
//                //设置自定义弹窗
//                aMap.setInfoWindowAdapter(new MapInfoWindowAdapter(act, projectListBean.getData()));
//                //绑定信息窗点击事件
//                aMap.setOnInfoWindowClickListener(new MapInfoWindowAdapter(act, projectListBean.getData()));
//                aMap.setOnMarkerClickListener(new MapInfoWindowAdapter(act, projectListBean.getData()));
//            }
//        });


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        try {
            mapView.onSaveInstanceState(outState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            mapView.onPause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mapView.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
