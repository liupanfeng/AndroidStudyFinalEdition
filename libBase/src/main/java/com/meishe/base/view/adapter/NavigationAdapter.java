package com.meishe.base.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.meishe.base.R;
import com.meishe.base.bean.Navigation;
import com.meishe.base.utils.CommonUtils;
import com.meishe.third.adpater.BaseMultiItemQuickAdapter;
import com.meishe.third.adpater.BaseViewHolder;

import java.util.List;

import static com.meishe.base.bean.Navigation.TYPE_DEFAULT;
import static com.meishe.base.bean.Navigation.TYPE_RATIO;


/**
 * author : lhz
 * date   : 2020/10/21
 * desc   :导航栏适配器
 * Navigation bar adapter
 */
public class NavigationAdapter extends BaseMultiItemQuickAdapter<Navigation.Item, BaseViewHolder> {
    private int mMaxShowNum;//一行最多显示的数量 The maximum number of rows displayed
    private int mWidth;//总宽度 overall width
    private int mItemWidth;//每个item的宽度 The width of each item
    private int mSelectedPos = -1;
    private int mItemSpace;

    public NavigationAdapter(int width, int itemSpace, int showMaxNum) {
        super(null);
        addItemType(TYPE_DEFAULT, R.layout.item_navigation_default);
        addItemType(TYPE_RATIO, R.layout.item_navigation_ratio);

        mWidth = width;
        mItemSpace = itemSpace;
        mMaxShowNum = showMaxNum;
    }

    @Override
    public void setNewData(@Nullable List<Navigation.Item> list) {
        if (list != null) {
            if (list.size() >= mMaxShowNum) {
                mItemWidth = (mWidth - mItemSpace * mMaxShowNum) / mMaxShowNum;
            } else {
                mItemWidth = (mWidth - mItemSpace * list.size()) / list.size();
            }

        }
        super.setNewData(list);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateViewHolder(parent, viewType);
        if (viewType == TYPE_RATIO) {
            holder.setBackgroundDrawable(R.id.tv_title, CommonUtils.getRadiusDrawable(15, mContext.getResources().getColor(R.color.black_2122)));
        } else {
            holder.addOnClickListener(R.id.ll_item_container);
        }
        return holder;
    }

    /**
     * 因为mItemWidth随Data的大小改变，所以重写onBindViewHolder调整大小。
     * 如果mItemWidth固定，则重写onItemViewHolderCreated调整大小,减小性能消耗。
     * Because mItemWidth varies with the size of the Data, rewrite the onBindViewHolder to resize it.
     * If mItemWidth is fixed, onItemViewHolderCreated is resized to reduce performance consumption
     */
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_DEFAULT) {
            if (holder.itemView.getWidth() == mItemWidth) {
                /*
                 * 已经设置过就不再设置了
                 * It's already set, it's not set
                 * */
                super.onBindViewHolder(holder, position);
                return;
            }
            setLayoutParams(holder.itemView);//根布局 The root layout
        }
        super.onBindViewHolder(holder, position);
    }

    private void setLayoutParams(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.width = mItemWidth;
        view.setLayoutParams(layoutParams);
    }

    /**
     * 设置选中
     * Set the selected
     */
    public int selected(Navigation.Item item) {
        return selected(getData().indexOf(item));
    }

    /**
     * 设置选中
     * Set the selected
     */
    public int selected(int pos) {
        if (mSelectedPos == pos) {
            return pos;
        }
        if (pos >= 0 && pos < getData().size()) {
            notifyItemChanged(mSelectedPos);
            mSelectedPos = pos;
            notifyItemChanged(pos);
        }
        return mSelectedPos;
    }

    @Override
    protected void convert(BaseViewHolder holder, Navigation.Item item) {
        TextView tvTitle = holder.getView(R.id.tv_title);
        if (item.getTitleId() > 0) {
            tvTitle.setText(item.getTitleId());
        } else {
            tvTitle.setText(item.getTitle());
        }
        if (item.getItemType() == TYPE_DEFAULT) {
            ImageView ivIcon = holder.getView(R.id.iv_icon);
            ivIcon.setImageResource(item.getIconId());
            if (item.isEnable()) {
                tvTitle.setAlpha(1);
                ivIcon.setImageAlpha(255);
            } else {
                tvTitle.setAlpha(0.5f);
                ivIcon.setImageAlpha(255 / 2);
            }
        } else if (item.getItemType() == TYPE_RATIO) {
            if (tvTitle.getWidth() != item.getWidth()) {
                tvTitle.setWidth(item.getWidth());
            }
            if (tvTitle.getHeight() != item.getHeight()) {
                tvTitle.setHeight(item.getHeight());
            }
            if (holder.getAdapterPosition() == mSelectedPos) {
                tvTitle.setTextColor(mContext.getResources().getColor(R.color.red_ff365));
            } else {
                tvTitle.setTextColor(mContext.getResources().getColor(R.color.white_8));
            }
        }
    }
}
