package com.meishe.base.view.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.meishe.base.R;
import com.meishe.base.utils.ScreenUtils;
import com.meishe.base.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 平分一屏的宽度 https://blog.csdn.net/lovext4098477/article/details/80419201
 * 间距的最大值不会超出均分给他的宽度减去item的宽度
 *  网格间距项目装饰类
 * Grid spacing item decorates the class
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int screenWidth;
    private int itemWidth;
    private List<Integer> spanRightList;
    /**
     * The Margin size start.
     * Margin大小。
     */
    int marginSizeStart;
    int marginSizeMiddle;

    public GridItemDecoration(Context context, int spanCount) {
        this.spanCount = spanCount;
        int marginSizeLeftAndRight = (int) context.getResources().getDimension(R.dimen.dp_px_30);
        marginSizeStart = (int) context.getResources().getDimension(R.dimen.dp_px_9);
        marginSizeMiddle = (int) context.getResources().getDimension(R.dimen.dp_px_15);
        screenWidth = ScreenUtils.getScreenWidth() - marginSizeLeftAndRight * 2;
        spanRightList = new ArrayList<>();
        this.itemWidth = (screenWidth - marginSizeStart * 2 - marginSizeMiddle * (spanCount - 1)) / spanCount;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int surplus = screenWidth - spanCount * itemWidth;
        if (surplus <= 0) {
            return;
        }
        int startSpace = SizeUtils.dp2px(3);
        int column = position % spanCount; // item column

        int itemWidthInScreen = screenWidth / spanCount;
        int spaceBetweenItems = marginSizeMiddle;
        if (column == 0) {
            outRect.left = startSpace;
            outRect.right = itemWidthInScreen - itemWidth - outRect.left;
            spanRightList.add(outRect.right);
        } else if (column == (spanCount - 1)) {
            outRect.left = spaceBetweenItems - spanRightList.get(column - 1);
            outRect.right = itemWidthInScreen - itemWidth - outRect.left;
            spanRightList.add(outRect.right);
        } else {
            outRect.left = spaceBetweenItems - spanRightList.get(column - 1);
            outRect.right = itemWidthInScreen - itemWidth - outRect.left;
            spanRightList.add(outRect.right);
        }

    }
}