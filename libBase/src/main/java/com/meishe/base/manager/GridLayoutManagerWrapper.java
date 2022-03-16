package com.meishe.base.manager;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;

/**
 * All rights reserved,Designed by www.meishesdk.com
 *
 * @Author : yangtailin
 * @CreateDate :2021/9/2 17:50
 * @Description :为了解决系统底层，数组越界问题
 *  * java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid item position 13(offset:13).
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class GridLayoutManagerWrapper extends GridLayoutManager {
    public GridLayoutManagerWrapper(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public GridLayoutManagerWrapper(Context context, int spanCount) {
        super(context, spanCount);
    }

    public GridLayoutManagerWrapper(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }
}
