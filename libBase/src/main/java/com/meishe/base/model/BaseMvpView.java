package com.meishe.base.model;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

/**
 * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : yangtailin
 * @CreateDate :2021/8/5 17:53
 * @Description :基类Mvp的View
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public abstract class BaseMvpView<P extends IPresenter> extends RelativeLayout implements IBaseView {
    protected P mPresenter;

    public BaseMvpView(Context context) {
        this(context, null);
    }

    public BaseMvpView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseMvpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPresenter = createPresenter();
        mPresenter.attachView(this);
        initView();
        initData();
    }

    protected abstract P createPresenter();

    protected abstract void initView();

    protected abstract void initData();


    @Override
    public void onShowDialog() {

    }

    @Override
    public void onDismissDialog() {

    }

    @Override
    public void onError(NvsError error) {

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPresenter.detachView();
    }
}
