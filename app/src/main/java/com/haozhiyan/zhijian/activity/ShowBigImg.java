package com.haozhiyan.zhijian.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.model.MainConstant;
import com.haozhiyan.zhijian.utils.ImageRequest;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.widget.CancelOrOkDialog;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 查看大图
 */
public class ShowBigImg extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager viewPager;
    private TextView positionTv;
    private ArrayList<String> imgList;
    private int mPosition = 0;
    private ViewPagerAdapter mAdapter;
    private boolean mShowDlete = false;
    private ImageView delete_iv;

    public static void build(Context context, String img) {
        Intent intent = new Intent(context, ShowBigImg.class);
        intent.putExtra(MainConstant.PIC_PATH, img);
        context.startActivity(intent);
    }

    public static void build(Context context, ArrayList<String> imgs) {
        Intent intent = new Intent(context, ShowBigImg.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, imgs);
        context.startActivity(intent);
    }

    public static void build(Context context, ArrayList<String> imgs, int selectPage) {
        Intent intent = new Intent(context, ShowBigImg.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, imgs);
        intent.putExtra(MainConstant.POSITION, selectPage);
        context.startActivity(intent);
    }

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, false);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_show_big_img;
    }

    @Override
    protected void initView() {
        try {
            imgList = getIntent().getStringArrayListExtra(MainConstant.IMG_LIST);
            if (imgList == null) {
                imgList = new ArrayList<>();
                String img = getIntent().getStringExtra(MainConstant.PIC_PATH);
                imgList.add(img);
            }
            mPosition = getIntent().getIntExtra(MainConstant.POSITION, 0);
            try {
                mShowDlete = getIntent().getBooleanExtra(MainConstant.SHOWDELETE, false);
            } catch (Exception e) {
                mShowDlete = false;
            }
            viewPager = (ViewPager) findViewById(R.id.viewPager);
            positionTv = (TextView) findViewById(R.id.position_tv);
            findViewById(R.id.back_iv).setOnClickListener(this);
            delete_iv = (ImageView) findViewById(R.id.delete_iv);
            delete_iv.setOnClickListener(this);

            if (mShowDlete) {
                delete_iv.setVisibility(View.VISIBLE);
            } else {
                delete_iv.setVisibility(View.GONE);
            }
            viewPager.addOnPageChangeListener(this);

            mAdapter = new ViewPagerAdapter(this, imgList);
            viewPager.setAdapter(mAdapter);
            positionTv.setText((mPosition + 1) + "/" + imgList.size());
            viewPager.setCurrentItem(mPosition);
        } catch (Exception e) {
            finish();
        }


    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isNetWork) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        positionTv.setText((position + 1) + "/" + imgList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_iv:
                //返回
                back();
                break;
            case R.id.delete_iv:
                //删除图片
                deletePic();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //按下了返回键
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //删除图片
    private void deletePic() {
        Dialog dialog = new CancelOrOkDialog(act, "要删除这张图片吗?") {
            @Override
            public void ok() {
                super.ok();
                imgList.remove(mPosition);
                setPosition();
                dismiss();
            }
        };
        dialog.show();
    }

    //设置当前位置
    private void setPosition() {
        positionTv.setText((mPosition + 1) + "/" + imgList.size());
        viewPager.setCurrentItem(mPosition);
        mAdapter.notifyDataSetChanged();
    }


    //返回上一个页面
    private void back() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, imgList);
        setResult(MainConstant.RESULT_CODE_VIEW_IMG, intent);
        finish();
    }


    class ViewPagerAdapter extends PagerAdapter {
        private Context context;
        private List<String> imgList;

        public ViewPagerAdapter(Context context, List<String> imgList) {
            this.context = context;
            this.imgList = imgList;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = getItemView(R.layout.view_pager_img);
            new ImageRequest(context).get(imgList.get(position), (ImageView) view.findViewById(R.id.img_iv));
            PhotoView photoView =  view.findViewById(R.id.img_iv);
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                   back();
                }

                @Override
                public void onOutsidePhotoTap() {

                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return imgList != null ? imgList.size() : 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        private View getItemView(int layoutID) {
            return LayoutInflater.from(context).inflate(layoutID, null, false);

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);这里调用了...
            container.removeView((View) object);
        }
    }
}
