package com.example.libbase.view;

import android.content.Context;
import android.widget.TextView;

import com.example.libbase.R;
import com.meishe.base.utils.ScreenUtils;
import com.meishe.third.pop.XPopup;
import com.meishe.third.pop.core.BasePopupView;
import com.meishe.third.pop.core.CenterPopupView;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/21 17:04
 * @Description : 居中弹窗
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class PopCenterView extends CenterPopupView {


    private OnCenterClickListener onCenterClickListener;
    private TextView mTvContent;
    private String mContent;

    public static PopCenterView create(Context context, OnCenterClickListener onCancelCroupClickListener) {
        return (PopCenterView) new XPopup.Builder(context).asCustom(new
                PopCenterView(context).setOnCenterClickListener(onCancelCroupClickListener));
    }


    public PopCenterView(Context context) {
        super(context);
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.common_pop_cneter_view;
    }

    @Override
    protected int getPopupHeight() {
        return ScreenUtils.getScreenHeight()/2;
    }

    @Override
    protected int getMaxHeight() {
        return getPopupHeight();
    }


    @Override
    protected void onCreate() {
        super.onCreate();
        mTvContent = findViewById(R.id.tv_content);
        if (mTvContent != null) {
            mTvContent.setText(mContent);
        }
    }


    public BasePopupView show(String content) {
        mContent = content;
        if (mTvContent != null) {
            mTvContent.setText(mContent);
        }
        return super.show();
    }

    public PopCenterView setOnCenterClickListener(OnCenterClickListener onCenterClickListener) {
        this.onCenterClickListener = onCenterClickListener;
        return this;
    }

    public interface OnCenterClickListener {
        void onCenterClick();
    }

}
