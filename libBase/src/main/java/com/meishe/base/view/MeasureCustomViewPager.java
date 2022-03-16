package com.meishe.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author: ChuChenGuang
 * @CreateDate: 2021/4/15 12:02
 * @Description:
 * @Copyright: www.meishesdk.com Inc. All rights reserved.
 */
public class MeasureCustomViewPager extends CustomViewPager {
    public MeasureCustomViewPager(Context context) {
        super(context);
    }

    public MeasureCustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)
                height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
