package com.meishe.base.view.dragview;

import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The type Custom item touch handler.
 * 自定义项目触摸处理程序类
 */
public class CustomItemTouchHandler extends ItemTouchHelper.Callback {
    ItemTouchAdapterImpl adapter;

    public CustomItemTouchHandler(@NonNull ItemTouchAdapterImpl adapter) {
        this.adapter = adapter;
    }

    /**
     * 设置 允许拖拽
     * Settings allow drag and drop
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        /*
        * 指定可 拖拽方向 和 滑动消失的方向
        * Specifies the drag-able direction and the direction in which the slide disappears
        * */
        int dragFlags, swipeFlags;
        /*
        * 可以上下拖动
        * You can drag it up and down
        * */
        dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        /*
        * 可以左右方向滑动消失
        * It can slide left and right to disappear
        * */
        swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        /*
        * 如果某个值传 0 , 表示不支持该功能
        * If a value passes 0, this function is not supported
        * */
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 拖拽后回调,一般通过接口暴露给adapter, 让adapter去处理数据的交换
     * The drag and drop callback is typically exposed to the Adapter through the interface and the adapter is asked to handle the exchange of data
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//        // 相同 viewType 之间才能拖动交换
//        if (viewHolder.getItemViewType() == target.getItemViewType()) {
//            int fromPosition = viewHolder.getAdapterPosition();
//            int toPosition = target.getAdapterPosition();
//
//
//            if (fromPosition < toPosition) {
//                //途中所有的item位置都要移动
//                for (int i = fromPosition; i < toPosition; i++) {
//                    adapter.onItemMove(i, i + 1);
//                }
//            } else {
//                for (int i = fromPosition; i > toPosition; i--) {
//                    adapter.onItemMove(i, i - 1);
//                }
//            }
//            adapter.notifyItemMoved(fromPosition, toPosition);
//            return true;
//        }
        return false;
    }

    /**
     * 拖拽后回调,一般通过接口暴露给adapter, 让adapter去处理数据的交换
     * The drag and drop callback is typically exposed to the Adapter through the interface and the adapter is asked to handle the exchange of data
     */
    private int mToPosition;
    public boolean onMove(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return false;
        }
        if (mToPosition == toPosition) {
            return false;
        }
        mToPosition = toPosition;
        if ((fromPosition < 1 || fromPosition > adapter.getItemCount() - 1) || (toPosition < 1 || toPosition > adapter.getItemCount() - 1)) {
            return false;
        }
        /*
        * 相同 viewType 之间才能拖动交换
        * Drag exchanges between the same viewTypes
        * */
        if (fromPosition < toPosition) {
            /*
            * 途中所有的item位置都要移动
            * All item positions have to be moved along the way
            * */
            for (int i = fromPosition; i < toPosition; i++) {
                adapter.onItemMove(i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                adapter.onItemMove(i, i - 1);
            }
        }
        adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        /*
        * adapter 刷新
        *  refresh adapter
        * */
        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            /*
            * 滑动时改变Item的透明度
            * Change the Item's transparency as you swipe
            * */
            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        }
    }


    /**
     * 是否支持长按开始拖拽,默认开启     * 可以不开启,然后在长按 item 的时候,手动 调用 mItemTouchHelper.startDrag(myHolder) 开启,更加灵活
     * Whether to support long press began to drag and drop, the default open * can not open, and then in the long item, manual call mItemTouchHelper. StartDrag (myHolder) open, more flexible
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return adapter.autoOpenDrag();
    }


    /**
     * The type Item touch adapter.
     * 项目联系适配器
     * 建议让 adapter 实现该接口
     * It is recommended that adapter implement this interface
     */

    public static abstract class ItemTouchAdapterImpl extends RecyclerView.Adapter {
        public abstract void onItemMove(int fromPosition, int toPosition);

       /*
       * 是否自动开启拖拽
       * Whether drag and drop is enabled automatically
       * */

        protected boolean autoOpenDrag() {
            return true;
        }
    }
}
