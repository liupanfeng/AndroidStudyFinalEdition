package com.meishe.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import com.meishe.base.interfaces.OnScrollViewListener;

/**
 * Created by CaoZhiChao on 2020/8/10 13:42
 * 水平滚动视图类
 * Scroll the view class horizontally
 */
public class MYHorizontalScrollView extends HorizontalScrollView {
    private OnScrollViewListener mOnScrollViewListener;

    public MYHorizontalScrollView(Context context) {
        super(context);
    }

    public MYHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MYHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
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
     * 获取滚动视图监听器
     * @return the on scroll view listener 滚动视图监听器
     */
    public OnScrollViewListener getOnScrollViewListener() {
        return mOnScrollViewListener;
    }

    /**
     * Sets on scroll view listener.
     * 设置在滚动视图监听器
     * @param onScrollViewListener the on scroll view listener 滚动视图监听器
     */
    public void setOnScrollViewListener(OnScrollViewListener onScrollViewListener) {
        mOnScrollViewListener = onScrollViewListener;
    }
}
