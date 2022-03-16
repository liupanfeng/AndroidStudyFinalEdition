package com.meishe.base.view.dragview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The type Drag recycler view.
 * 拖动 recycler view类
 */
public class DragRecyclerView extends RecyclerView {

    private WindowManager mWindowManager;

    private WindowManager.LayoutParams mWindowLayoutParams;
    /**
     * 刚开始拖拽的item对应的View
     * The first drag item The corresponding View
     */
    private View mStartDragItemView = null;
    /**
     * 用于拖拽的镜像，这里直接用一个ImageView
     * Image for drag and drop, I'm just going to use an ImageView here
     */
    private ImageView mDragImageView;

    /**
     * 正在拖拽的position
     * Dragging position
     */
    private int mDragPosition;

    private float[] mDownPoint = null;
    private float[] mMovePoint = null;

    public DragRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public DragRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * Sets on touch action move.
     * 设置触控动作移动
     * @param moveX the move x 移动x
     * @param moveY the move y 移动y
     */
    public void setOnTouchActionMove(float moveX, float moveY) {
        if (mMovePoint == null) {
            mMovePoint = new float[2];
        }
        mMovePoint[0] = moveX;
        mMovePoint[1] = moveY;
    }

    public void setOnTouchActionDown( float downX, float downY) {
        mDownPoint = new float[2];
        mDownPoint[0] = downX;
        mDownPoint[1] = downY;
//        createDragImage(bitmap, downX, downY);
    }

//    private void createDragImage(Bitmap bitmap, float downX, float downY) {
//        mWindowLayoutParams = new WindowManager.LayoutParams();
//        mWindowLayoutParams.format = PixelFormat.TRANSLUCENT; //图片之外的其他地方透明
//        mWindowLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
//        mWindowLayoutParams.x = downX - mPoint2ItemLeft + mOffset2Left;
//        mWindowLayoutParams.y = downY - mPoint2ItemTop + mOffset2Top - mStatusHeight;
//        mWindowLayoutParams.alpha = 0.55f; //透明度
//        mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
//
//        mDragImageView = new ImageView(getContext());
//        mDragImageView.setImageBitmap(bitmap);
//        mWindowManager.addView(mDragImageView, mWindowLayoutParams);
//    }


    public void setStartDragItemView(View mStartDragItemView) {
        this.mStartDragItemView = mStartDragItemView;
    }

    /**
     * Sets drag position.
     * 拖动位置
     * @param mDragPosition the m drag position
     */
    public void setDragPosition(int mDragPosition) {
        this.mDragPosition = mDragPosition;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }

}
