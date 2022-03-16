package com.meishe.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

import com.meishe.base.utils.LogUtils;


/**
 * The type Custom view pager.
 * 自定义视图类
 */
public class CustomViewPager extends ViewPager {
    private boolean isCanScroll = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置其是否能滑动换页
     * Sets whether it can slide to page change
     * @param isCanScroll false 不能换页， true 可以滑动换页
     */
    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return isCanScroll && super.onInterceptTouchEvent(ev);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onTouchEvent(ev);

    }
}
