package com.meishe.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * The type Custom horizontal scroll view.
 * 自定义水平滚动视图类
 */
public class CustomHorizontalScrollView extends HorizontalScrollView {


    private OnScrollListener onScrollListener;

    public CustomHorizontalScrollView(Context context) {
        super(context);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScroll(l);
        }
    }

    /**
     * Sets on scroll listener.
     * 设置滚动监听
     * @param onScrollListener the on scroll listener 滚动监听器
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     * The interface On scroll listener.
     * 滚动监听的接口
     */
    public interface OnScrollListener{
        /**
         * 回调方法， 返回MyScrollView滑动的X方向距离
         * Callback method, which returns the x-direction distance MyScrollView slides
         * @param scrollX the scroll x 滚动
         */
        void onScroll(int scrollX);
    }

}
