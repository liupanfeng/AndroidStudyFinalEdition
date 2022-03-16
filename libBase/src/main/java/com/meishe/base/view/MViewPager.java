package com.meishe.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

import com.meishe.base.utils.LogUtils;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/4/2 11:02
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class MViewPager extends ViewPager {

    private boolean noScroll = true;

    public MViewPager(Context context) {
        super(context);

    }

    public MViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (noScroll) {
            return false;
        } else {
            return super.onTouchEvent(arg0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        try {
            if (noScroll) {
                return false;
            } else {
                return super.onInterceptTouchEvent(arg0);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return false;
    }

    public void setScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, false);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }
}
