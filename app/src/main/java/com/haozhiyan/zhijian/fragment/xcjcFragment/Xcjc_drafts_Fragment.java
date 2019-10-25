package com.haozhiyan.zhijian.fragment.xcjcFragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.workXcjc.AddTroubleActivity;
import com.haozhiyan.zhijian.adapter.JCDraftAdapter;
import com.haozhiyan.zhijian.application.MyApp;
import com.haozhiyan.zhijian.bean.xcjc.XCJCSaveBean;
import com.haozhiyan.zhijian.fragment.BaseFragment;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.myDao.DaoSession;
import com.haozhiyan.zhijian.myDao.XCJCSaveBeanDao;
import com.haozhiyan.zhijian.widget.MyDividerItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/26.
 * Describe: 草稿
 */
public class Xcjc_drafts_Fragment extends BaseFragment {

    private RecyclerView rvDataList;
    private JCDraftAdapter jcDraftAdapter;
    //    private ACache aCache;
//    private List<XCJC_TroubleBean> xcJcBeans = new ArrayList<>();
    private String sectionId;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_layout;
    }

    @Override
    public void initView(View view) {
        rvDataList = getOutView(view, R.id.rv_dataList);
        rvDataList.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, true));
        rvDataList.addItemDecoration(new MyDividerItem(ctx, MyDividerItem.VERTICAL));
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if ("zancunxcjcBeanSuccess".equals(event)) {
            getxcjcbeanlist();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void initListener() {
//        aCache = ACache.get(ctx, "XCJC_trouble");
        jcDraftAdapter = new JCDraftAdapter(getContext(), null);
        rvDataList.setAdapter(jcDraftAdapter);
        jcDraftAdapter.setEmptyView(emptyView);
        getxcjcbeanlist();
//        setDraftList();
    }

    @Override
    public void initData(boolean isNetWork) {
//        jcDraftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if (listEmpty(xcJcBeans)) {
////                    Bundle bundle = new Bundle();
////                    bundle.putInt("position", position);
////                    bundle.putSerializable("troubleBean", xcJcBeans.get(position));
////                    bundle.putString("sectionId", sectionId);
////                    bundle.putString("pieceType", "xcJc");
////                    bundle.putString("iscaogao", true);
////                    jumpToActivity(AddTroubleActivity.class, bundle);
//                    Intent intent=new Intent(getContext(),AddTroubleActivity.class);
//                    intent.putExtra("iscaogao", true);
//
//                    startActivity(intent);
//                }
//            }
//        });
    }

    @Override
    protected void lazyLoad() {

    }

    private void getxcjcbeanlist() {
        try {
            DaoSession daoSession = MyApp.getInstance().getDaoSession();
            QueryBuilder<XCJCSaveBean> qb = daoSession.queryBuilder(XCJCSaveBean.class);
            Query<XCJCSaveBean> query = qb.where(XCJCSaveBeanDao.Properties.ProjectId.eq(Constant.projectId + "")).build();
            final List<XCJCSaveBean> xcjcSaveBeans = query.list();
            jcDraftAdapter.setNewData(xcjcSaveBeans);
            jcDraftAdapter.notifyDataSetChanged();
            jcDraftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(getContext(), AddTroubleActivity.class);
                    intent.putExtra("iscaogao", true);
                    intent.putExtra("pieceType", "xcJc");
                    intent.putExtra("timeId", xcjcSaveBeans.get(position).timeId);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public void setDraftList() {
//        try {
//            if (aCache != null) {
//                JSONArray array = aCache.getAsJSONArray("xcJc_trouble");
//                //JSONArray array = new DraftUtils<>(XCJC_TroubleBean.class, ctx).getList("XCJC_Trouble");
//                if (arrayEmpty(array)) {
//                    List<XCJC_TroubleBean> troubleBeanList = new ArrayList<>();
//                    List<String> imgUrl = null;
//                    List<String> videoUrl = null;
//                    for (int i = 0; i < array.length(); i++) {
//                        JSONObject object = array.optJSONObject(i);
//                        //拿到图片path
//                        String images = object.optString("images");
//                        if (!StringUtils.isEmpty(images)) {
//                            imgUrl = new ArrayList<>();
//                            String[] icon = images.replace("[", "").replace("]", "").split(",");
//                            for (int j = 0; j < icon.length; j++) {
//                                imgUrl.add(icon[j]);
//                            }
//                        }
//                        //拿到视频path
//                        String videos = object.optString("videos");
//                        if (!StringUtils.isEmpty(videos)) {
//                            videoUrl = new ArrayList<>();
//                            String[] icon = videos.replace("[", "").replace("]", "").split(",");
//                            for (int j = 0; j < icon.length; j++) {
//                                videoUrl.add(icon[j]);
//                            }
//                        }
//                        XCJC_TroubleBean bean = new XCJC_TroubleBean(imgUrl, videoUrl, object.optString("buWei"), object.optString("JianChaX"), object.optString("desc"), object.optString("instructContent")
//                                , object.optInt("troubleChengDu"), object.optString("zgQxTimes"), object.optString("zhengGaiRen"), object.optString("zeRenDanWei"), object.optString("fuYanRen"), object.optString("chaoSongRen")
//                                , object.optString("createTime"));
//                        troubleBeanList.add(bean);
//                        xcJcBeans = troubleBeanList;
//                    }
//                    jcDraftAdapter.setNewData(troubleBeanList);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

}
