package com.example.libbase.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/21 13:46
 * @Description : 流式布局
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量所有的子View child.getMeasuredHeight()才能获取到值，否则为0
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //计算子view所占的宽度，这里传的需要自身减去paddingRight
        Map<String, Integer> compute = compute(widthSize - getPaddingRight());

        //固定值或者match_parent
        if (widthMode == MeasureSpec.EXACTLY) {
            measureWidth = widthSize;

            //对于warp_content(需要手动计算大小，否则相当于match_parent)
        } else if (widthMode == MeasureSpec.AT_MOST) {
            measureWidth = compute.get("allChildWidth");
        }

        //给定大小或者
        if (heightMode == MeasureSpec.EXACTLY) {
            measureHeight = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            measureHeight = compute.get("allChildHeight");
        }

        setMeasuredDimension(measureWidth, measureHeight);

    }


    /**
     * 测量方法
     *
     * @param flowWidth 该view的宽度
     * @return 返回子元素所占的宽度和高度（用于计算FlowLayout AT_MOST模式下设置的宽高）
     */
    private Map<String, Integer> compute(int flowWidth) {

        boolean row = true;

        MarginLayoutParams marginParams = null;  //子view的margin
        int rowsWidth = getPaddingLeft();    //当前行已经占的宽度
        int columnHeight = getPaddingTop();   //当前行底部所占的高度

        int rowsMaxHeight=0;     //当前行所有子元素所占的最大的高度

        for (int i = 0; i <  getChildCount(); i++) {

            View child = getChildAt(i);
            //获取元素测量宽度和高度
            int measuredWidth = child.getMeasuredWidth();
            int measuredHeight = child.getMeasuredHeight();
            //获取元素的margin
            marginParams = (MarginLayoutParams) child.getLayoutParams();
            //子元素所占宽度 = MarginLeft+ child.getMeasuredWidth+MarginRight  注意此时不能child.getWidth,因为界面没有绘制完成，此时wdith为0
            int childWidth = marginParams.leftMargin + marginParams.rightMargin + measuredWidth;
            int childHeight = marginParams.topMargin + marginParams.bottomMargin + measuredHeight;
            //判断是否换行： 该行已占大小+该元素大小>父容器宽度  则换行

            rowsMaxHeight = Math.max(rowsMaxHeight, childHeight);
            //换行
            if (rowsWidth + childWidth > flowWidth) {
                //重置行宽度
                rowsWidth = getPaddingLeft()+getPaddingRight();
                //累加上该行子元素最大高度
                columnHeight += rowsMaxHeight;
                //重置该行最大高度
                rowsMaxHeight = childHeight;
                row = false;
            }
            //累加上该行子元素宽度
            rowsWidth += childWidth;
            //判断时占的宽段时加上margin计算，设置顶点位置时不包括margin位置，不然margin会不起作用，这是给View设置tag,在onlayout给子元素设置位置再遍历取出
            child.setTag(new Rect(rowsWidth - childWidth + marginParams.leftMargin, columnHeight + marginParams.topMargin, rowsWidth - marginParams.rightMargin, columnHeight + childHeight - marginParams.bottomMargin));
        }

        //返回子元素总所占宽度和高度（用于计算Flowlayout的AT_MOST模式设置宽高）
        Map<String, Integer> flowMap = new HashMap<>();
        //单行
        if (row) {
            flowMap.put("allChildWidth", rowsWidth);
        } else {
            //多行
            flowMap.put("allChildWidth", flowWidth);
        }
        //FlowLayout测量高度 = 当前行顶部已占高度 +当前行内子元素最大高度+FlowLayout的PaddingBottom
        flowMap.put("allChildHeight", columnHeight+rowsMaxHeight+getPaddingBottom());
        return  flowMap;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            Rect tag = (Rect) childAt.getTag();
            childAt.layout(tag.left,tag.top,tag.right,tag.bottom);
        }
    }
}
