package com.meishe.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.meishe.base.interfaces.OnScrollViewListener;

/**
 * Created by CaoZhiChao on 2020/8/10 13:38
 * 滚动视图类
 * CScrollView
 */
public class MYScrollView extends ScrollView {
    private OnScrollViewListener mOnScrollViewListener;

    public MYScrollView(Context context) {
        super(context);
    }


    public MYScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public MYScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollViewListener != null) {
            mOnScrollViewListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    /**
     * Gets on scroll view listener.
     *
     * @return the on scroll view listener
     */
    public OnScrollViewListener getOnScrollViewListener() {
        return mOnScrollViewListener;
    }

    /**
     * Sets on scroll view listener.
     *
     * @param onScrollViewListener the on scroll view listener
     */
    public void setOnScrollViewListener(OnScrollViewListener onScrollViewListener) {
        mOnScrollViewListener = onScrollViewListener;
    }
}
