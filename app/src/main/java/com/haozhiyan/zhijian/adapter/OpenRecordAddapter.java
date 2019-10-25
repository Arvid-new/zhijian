package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.facebook.drawee.view.SimpleDraweeView;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.AngleJsonBean;
import com.haozhiyan.zhijian.utils.DataTest;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/30.
 * Describe: 此适配器用于只展示双层嵌套列表数据
 */
public class OpenRecordAddapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public static final int ITEM_TITLE = 1;
    public static final int ITEM_CONTENT = 2;
    private View view1,view2;
    public OpenRecordAddapter(@Nullable List<Object> data, Context context) {
        super(data);
        setMultiTypeDelegate(new MultiTypeDelegate<Object>() {
            @Override
            protected int getItemType(Object o) {
                // 当前例子中只有两种类型
                if (o instanceof String) {
                    // 加载布局1
                    return ITEM_TITLE;
                } else if (o instanceof AngleJsonBean.DataBean.LogDOListBean) {
                    // 加载布局2
                    return ITEM_CONTENT;
                }
                return 0;
            }
        });
        getMultiTypeDelegate().registerItemType(ITEM_TITLE, R.layout.angle_item01)
                .registerItemType(ITEM_CONTENT, R.layout.angle_item02);
    }

    @Override
    protected void convert(final BaseViewHolder helper, Object item) {
        switch (helper.getItemViewType()) {
            case ITEM_TITLE://标题赋值
                helper.setText(R.id.tv_danWei, (String) item);
                break;
            case ITEM_CONTENT://内容赋值
                helper.setText(R.id.name, ((AngleJsonBean.DataBean.LogDOListBean) item).deviceName);
                ((SimpleDraweeView) helper.getView(R.id.angleView)).setImageURI(DataTest.imgUrl);
                break;
        }
    }

   /* //适配器使用方法
    //1.获取服务器数据,以object形式保存设置
    RecyclerView rlvAngleList = findViewById(R.id.rlvAngleList);
    rlvAngleList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    AngleJsonBean angleJsonBean = new Gson().fromJson(DataTest.angleJson,AngleJsonBean.class);
    List<Object> list = sortData(angleJsonBean);
    OpenRecordAddapter addapter = new OpenRecordAddapter(list,this);
    rlvAngleList.setAdapter(addapter);
    // 数据拆分重新组装的方法
    private List<Object> sortData(AngleJsonBean bean) {
        List<AngleJsonBean.DataBean> arrays = bean.data;
        // 用来进行数据重组的新的集合arrays_obj，之所以泛型设为Object，是因为该例中的集合元素既可能为String有可能是一个bean
        List<Object> arrays_obj = new ArrayList<>();
        for (AngleJsonBean.DataBean array : arrays) {
            List<AngleJsonBean.DataBean.LogDOListBean> logs = array.logDOList;
            // 拿到String值添加进集合arrays_obj
            arrays_obj.add(array.dateTitle);
            // 如果该标题下的集合里面有数据的话，遍历拿到添加进新集合arrays_obj
            if (logs != null && logs.size() > 0) {
                for (AngleJsonBean.DataBean.LogDOListBean log : logs) {
                    arrays_obj.add(log);
                }
            }
        }
        return arrays_obj;
    }*/
}
