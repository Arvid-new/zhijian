package com.haozhiyan.zhijian.widget;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.utils.UiUtils;

/**
 * Created by WangZhenKai on 2019/5/5.
 * Describe: Ydzj_project
 */
public class MyEditText extends LinearLayout implements View.OnClickListener {

    private ONclickListener oNclickListener;
    private EditText etSearch;
    private ImageView ivSearch;
    private TextView tvSearchFunction;
    private Handler myHandler = new Handler();

    public MyEditText(Context context) {
        super(context);
        initView(context);
        initListener();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initListener();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initView(Context context) {
        View editView = View.inflate(context,R.layout.my_search_layout,null);
        addView(editView);
        etSearch = editView.findViewById(R.id.et_search);
        ivSearch = editView.findViewById(R.id.iv_search);
        tvSearchFunction = editView.findViewById(R.id.tv_search_function);
        initListener();
    }

    public void initListener() {
        tvSearchFunction.setOnClickListener(this);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    ivSearch.setVisibility(View.VISIBLE);
                    tvSearchFunction.setVisibility(View.GONE);
                    setData();
                } else {
                    tvSearchFunction.setVisibility(View.VISIBLE);
                    ivSearch.setVisibility(View.GONE);
                    myHandler.post(eChanged);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search_function:
                etSearch.setText("");
                ivSearch.setVisibility(View.VISIBLE);
                tvSearchFunction.setVisibility(View.GONE);
                setData();
                break;
        }
    }

    public String getInput() {
        String content = UiUtils.getContent(etSearch);
        return content;
    }

    public void setData() {
        if (oNclickListener != null) {
            oNclickListener.setMyData();
        }
    }

    public void setOnclickListener(ONclickListener onclickListener) {
        this.oNclickListener = onclickListener;
    }

    public interface ONclickListener {
        void setMyData();

        void refreshUi();
    }

    Runnable eChanged = new Runnable() {

        @Override
        public void run() {
            if (oNclickListener != null) {
                oNclickListener.refreshUi();
            }
        }
    };
}
