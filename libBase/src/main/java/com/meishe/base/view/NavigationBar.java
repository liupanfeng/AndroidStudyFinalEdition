package com.meishe.base.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.base.R;
import com.meishe.base.bean.Navigation;
import com.meishe.base.manager.LinearLayoutManagerWrapper;
import com.meishe.base.utils.CommonUtils;
import com.meishe.base.utils.ScreenUtils;
import com.meishe.base.view.adapter.NavigationAdapter;
import com.meishe.base.view.decoration.ItemDecoration;
import com.meishe.third.adpater.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.meishe.base.bean.Navigation.TYPE_DEFAULT;
import static com.meishe.base.bean.Navigation.TYPE_RATIO;


/**
 * * All rights reserved,Designed by www.meishesdk.com
 * 版权所有www.meishesdk.com
 *
 * @Author : LiHangZhou
 * @CreateDate : 2020/10/21
 * @Description :底部导航栏，支持N层级。两种使用方式：1、直接调用createNavigation方法。2、使用addNavigation + show的方式。
 * Bottom navigation bar, N level support
 * @Copyright :www.meishesdk.com Inc.All rights reserved.
 */
public class NavigationBar extends RelativeLayout {
    private RecyclerView mRvBarList0;
    private RecyclerView mRvBarList1;
    private ImageView mIvBack;
    private NavigationAdapter mBarAdapter0;
    private NavigationAdapter mBarAdapter1;
    private NavigationListener mListener;
    private int mPreNavigationName;
    private Navigation mCurrentNavigation;
    private List<Navigation> mNavigationList;
    private int[] BACK_ICON;

    public NavigationBar(@NonNull Context context) {
        this(context, null);
    }

    public NavigationBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public NavigationBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setBackgroundColor(getResources().getColor(R.color.black));
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.view_navigation_bar, this);
        mRvBarList0 = rootView.findViewById(R.id.rv_bar_list0);
        mRvBarList1 = rootView.findViewById(R.id.rv_bar_list1);
        mIvBack = rootView.findViewById(R.id.iv_back);
        mIvBack.setBackground(CommonUtils.getRadiusDrawable(15, getResources().getColor(R.color.black_2122)));
        mRvBarList0.setLayoutManager(new LinearLayoutManagerWrapper(getContext(), RecyclerView.HORIZONTAL, false));
        int itemSpace = (int) getResources().getDimension(R.dimen.dp_px_40);
        mRvBarList0.addItemDecoration(new ItemDecoration(itemSpace, 0));
        int width = (int) (ScreenUtils.getScreenWidth() - getResources().getDimension(R.dimen.dp_px_140));
        int showMaxNum = 5;
        mBarAdapter0 = new NavigationAdapter(width, itemSpace, showMaxNum);
        mRvBarList0.setAdapter(mBarAdapter0);

        mRvBarList1.setLayoutManager(new LinearLayoutManagerWrapper(getContext(), RecyclerView.HORIZONTAL, false));
        mRvBarList1.addItemDecoration(new ItemDecoration(itemSpace, 0));
        mBarAdapter1 = new NavigationAdapter(width, itemSpace, showMaxNum);
        mRvBarList1.setAdapter(mBarAdapter1);
        BACK_ICON = new int[]{R.mipmap.bar_back_white, R.mipmap.bar_back_white, R.mipmap.bar_back_white};
        initListener();
    }

    private void initListener() {
        mIvBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation navigation = findNavigation(mPreNavigationName);
                if (navigation != null) {
                    if (mListener != null && mListener.onNavigationBack(mCurrentNavigation.getName(), mPreNavigationName)) {
                        return;
                    }
                    //恢复上一层导航栏
                    changeShowView(navigation, mRvBarList1.getVisibility() == VISIBLE);
                }
            }
        });
        mBarAdapter0.setOnItemClickListener(mOnItemClick);
        mBarAdapter0.setOnItemChildClickListener(mOnItemChildClick);
        mBarAdapter1.setOnItemClickListener(mOnItemClick);
        mBarAdapter1.setOnItemChildClickListener(mOnItemChildClick);
    }

    /**
     * 改变展示的导航view
     * change Navigation view
     *
     * @param targetName String 要展示的导航栏
     *                   The target navigation name
     * @param showBar0   boolean true展示在第0层级上，false则展示在第1层级上
     *                   true show 0 level ,false show 1 level
     */
    private boolean changeShowView(int targetName, boolean showBar0) {
        return changeShowView(findNavigation(targetName), showBar0);
    }

    /**
     * 改变展示的导航view
     *
     * @param navigation Navigation 要展示的导航栏
     *                   The target navigation
     * @param showBar0   boolean true展示在第0层级上，false则展示在第1层级上
     *                   true show 0 level ,false show 1 level
     */
    private boolean changeShowView(Navigation navigation, boolean showBar0) {
        if (navigation != null) {
            mPreNavigationName = navigation.getPreName();
            mCurrentNavigation = navigation;
            if (BACK_ICON != null && navigation.getLevel() > 0 && navigation.getLevel() < BACK_ICON.length) {
                mIvBack.setImageResource(BACK_ICON[navigation.getLevel() - 1]);
            }
            if (navigation.getLevel() == Navigation.LEVEL_0) {
                updateLevel0Navigation(true);
                mIvBack.setVisibility(INVISIBLE);
                showBar0 = true;//0级有且仅有一个，强制显示到0层级
            }
            if (showBar0) {
                mBarAdapter0.setNewData(navigation.getItems());
                int selectedPosition = mBarAdapter0.selected(navigation.getSelectedItem());
                mRvBarList0.scrollToPosition(Math.max(selectedPosition, 0));
                mRvBarList0.setVisibility(VISIBLE);
                mRvBarList1.setVisibility(INVISIBLE);
            } else {
                if (mIvBack.getVisibility() != VISIBLE) {
                    mIvBack.setVisibility(VISIBLE);
                }
                mRvBarList1.setVisibility(VISIBLE);
                mRvBarList0.setVisibility(INVISIBLE);
                mBarAdapter1.setNewData(navigation.getItems());
                int selectedPosition = mBarAdapter1.selected(navigation.getSelectedItem());
                mRvBarList1.scrollToPosition(Math.max(selectedPosition, 0));
            }
        }
        return navigation != null;
    }

    /**
     * 更新0层级的导航栏的leftMargin
     * update 0 level navigation
     *
     * @param reset true reset original leftMargin
     */
    private void updateLevel0Navigation(boolean reset) {
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) mRvBarList0.getLayoutParams();
        if (!reset && layoutParams.leftMargin != 0) {
            return;
        }
        layoutParams.leftMargin = reset ? 0 : (int) getResources().getDimension(R.dimen.dp_px_140);
        mRvBarList0.setLayoutParams(layoutParams);
    }

    /**
     * 设置返回按钮
     * Set the back button
     *
     * @param resId int 返回图标资源id,[0-n]代表n+1级返回图标
     *              The resource ids, [0-n]the icon for n+1 level
     */
    public NavigationBar setBackButton(int... resId) {
        BACK_ICON = resId;
        return this;
    }

    /**
     * 显示导航栏,默认会展示唯一的第0层级的导航栏
     * Show navigation,show the only 0 level navigation
     *
     * @param navigationList The navigation list
     */
    public void showNavigation(List<Navigation> navigationList) {
        mNavigationList = navigationList;
        show();
    }

    /**
     * 显示导航栏
     * show navigation
     */
    public void show() {
        if (mNavigationList != null) {
            for (Navigation navigation : mNavigationList) {
                if (navigation.getLevel() == Navigation.LEVEL_0) {
                    //第0层永远只有一个
                    changeShowView(navigation, true);
                    break;
                }
            }
        }
    }

    /**
     * 显示导航栏
     * show navigation
     *
     * @param navigationName The target navigation's name
     */
    public void show(int navigationName) {
        if (isShow(navigationName)) {
            return;
        }
        if (mRvBarList1.getVisibility() == VISIBLE) {
            updateLevel0Navigation(false);
        }
        changeShowView(navigationName, mRvBarList1.getVisibility() == VISIBLE);
    }

    /**
     * 显示导航栏
     * show navigation
     *
     * @param navigation The target navigation
     */
    public void show(Navigation navigation) {
        if (mRvBarList1.getVisibility() == VISIBLE) {
            updateLevel0Navigation(false);
        }
        changeShowView(navigation, mRvBarList1.getVisibility() == VISIBLE);
    }

    /**
     * 目标导航栏是否在显示
     * Whether the target navigation bar is displayed
     *
     * @param navigationName The target navigation's name
     */
    public boolean isShow(int navigationName) {
        return navigationName == mCurrentNavigation.getName();
    }

    /**
     * 获取正在展示的导航栏名称
     * Gets the name of the navigation bar being displayed
     */
    public int getShowingNavigationName() {
        return mCurrentNavigation == null ? 0 : mCurrentNavigation.getName();
    }

    /**
     * 更新某一导航栏的的某一item内容。
     * Update the item content of a navigation bar
     *
     * @param item The target navigation's item
     */
    public void updateNavigationItem(Navigation.Item item) {
        if (mRvBarList0.getVisibility() == VISIBLE) {
            int pos = mBarAdapter0.getData().indexOf(item);
            if (pos >= 0) {
                mBarAdapter0.notifyItemChanged(pos);
            }
        } else {
            int pos = mBarAdapter1.getData().indexOf(item);
            if (pos >= 0) {
                mBarAdapter1.notifyItemChanged(pos);
            }
        }
    }

    /**
     * 选中某一导航栏的的某一item。
     * Selected the item  of a navigation bar
     *
     * @param navigation The target navigation
     * @param item       The target navigation's item
     */
    public void selectedNavigationItem(Navigation navigation, Navigation.Item item) {
        if (mCurrentNavigation != null && navigation != null &&
                mCurrentNavigation.getName() == navigation.getName()) {
            mCurrentNavigation.setSelectedItem(item);
            if (mRvBarList0.getVisibility() == VISIBLE) {
                mBarAdapter0.selected(item);
            } else {
                mBarAdapter1.selected(item);
            }
        } else if (navigation != null) {
            navigation.setSelectedItem(item);
        }

    }

    /**
     * 选中某一导航栏的的某一item。
     * Selected the item  of a navigation bar
     *
     * @param navigationName The target navigation's name
     * @param item           The target navigation's item
     */
    public void selectedNavigationItem(int navigationName, Navigation.Item item) {
        if (mCurrentNavigation != null && mCurrentNavigation.getName() == navigationName) {
            mCurrentNavigation.setSelectedItem(item);
            if (mRvBarList0.getVisibility() == VISIBLE) {
                mBarAdapter0.selected(item);
            } else {
                mBarAdapter1.selected(item);
            }
        } else {
            Navigation navigation = findNavigation(navigationName);
            if (navigation != null) {
                navigation.setSelectedItem(item);
            }
        }

    }

    /**
     * 插入某一导航栏的的某一item。
     * Insert the item  of a navigation bar
     *
     * @param navigationName The target navigation's name 导航栏名称
     * @param titleId        The Navigation item's titleId,新的导航栏条目将会添加大该条目的后边
     * @param item           The new item 新的导航栏条目
     */
    public void insertNavigationItem(int navigationName, int titleId, Navigation.Item item) {
        Navigation navigation = findNavigation(navigationName);
        if (navigation != null) {
            int index = navigation.getIndex(titleId);
            addNavigationItem(navigationName, index, item);
        }
    }

    /**
     * 添加某一导航栏的的某一item。
     * Add the item  of a navigation bar
     *
     * @param navigationName The target navigation's name 导航栏名称
     * @param index          The add index添加的索引
     * @param item           The new item 新的导航栏条目
     */
    public void addNavigationItem(int navigationName, int index, Navigation.Item item) {
        if (index < 0) {
            return;
        }
        if (mCurrentNavigation != null && mCurrentNavigation.getName() == navigationName) {
            mCurrentNavigation.addItem(index, item);
            if (mRvBarList0.getVisibility() == VISIBLE) {
                mBarAdapter0.notifyItemRangeInserted(index, 1);
            } else {
                mBarAdapter1.notifyItemRangeInserted(index, 1);
            }
        } else {
            Navigation navigation = findNavigation(navigationName);
            if (navigation != null) {
                navigation.addItem(index, item);
            }
        }
    }

    /**
     * 删除某一导航栏的的某一item。
     * Delete the item  of a navigation bar
     *
     * @param navigationName The target navigation's name 导航栏名称
     * @param item           The delete item 要删除的item，
     */
    public void deleteNavigationItem(int navigationName, Navigation.Item item) {
        Navigation navigation = findNavigation(navigationName);
        if (navigation != null) {
            int index = navigation.getIndex(item);
            deleteNavigationItem(navigationName, index);
        }
    }

    /**
     * 删除某一导航栏的的某一item。
     * Delete the item  of a navigation bar
     *
     * @param navigationName The target navigation's name 导航栏名称
     * @param index          The delete index删除的的索引
     */
    public void deleteNavigationItem(int navigationName, int index) {
        if (index < 0) {
            return;
        }
        if (mCurrentNavigation != null && mCurrentNavigation.getName() == navigationName) {
            mCurrentNavigation.removeItem(index);
            if (mRvBarList0.getVisibility() == VISIBLE) {
                mBarAdapter0.notifyItemRemoved(index);
            } else {
                mBarAdapter1.notifyItemRemoved(index);
            }
        } else {
            Navigation navigation = findNavigation(navigationName);
            if (navigation != null) {
                navigation.removeItem(index);
            }
        }
    }

    /**
     * 改变某个导航栏
     * Change a navigation bar
     *
     * @param navigationId The navigation id
     **/
    public NavigationBar replaceNavigationItems(int navigationId, List<Navigation.Item> newItems) {
        Navigation navigation = findNavigation(navigationId);
        if (navigation != null) {
            navigation.setItems(newItems);
        }
        if (mCurrentNavigation.getName() == navigationId) {
            changeShowView(navigation, mRvBarList0.getVisibility() == VISIBLE);
        }
        return this;
    }

    /**
     * 根据导航栏名称找到对应的Navigation
     * Locate the corresponding Navigation according to the Navigation bar name
     *
     * @param navigationName The target navigation
     */
    public Navigation findNavigation(int navigationName) {
        if (mNavigationList != null) {
            for (Navigation navigation : mNavigationList) {
                if (navigationName == navigation.getName()) {
                    return navigation;
                }
            }
        }
        return null;
    }

    /**
     * 根据导航栏名称和子项的名称id找到对应的子项
     * Locate the corresponding Navigation's item according to the Navigation bar name and the item title id
     *
     * @param navigationName The target navigation
     * @param navigationName The target item title id
     */
    public Navigation.Item findNavigationItem(int navigationName, int itemTitleId) {
        if (mNavigationList != null) {
            Navigation navigation = findNavigation(navigationName);
            if (navigation != null) {
                List<Navigation.Item> items = navigation.getItems();
                if (items != null) {
                    for (Navigation.Item item : items) {
                        if (item.getTitleId() == itemTitleId) {
                            return item;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 设置导航栏事件监听
     * Set up navigation bar event listening
     *
     * @param listener The listener
     */
    public void setNavigationListener(NavigationListener listener) {
        mListener = listener;
    }

    /**
     * 添加导航栏列表
     * Add navigation
     *
     * @param navigationList The navigation list
     **/
    public NavigationBar addNavigationList(List<Navigation> navigationList) {
        if (mNavigationList == null) {
            mNavigationList = new ArrayList<>();
        }
        mNavigationList.addAll(navigationList);
        return this;
    }

    /**
     * 添加导航栏
     * Add navigation
     *
     * @param navigationName    String  导航栏名称,注意唯一性
     *                          navigation's name ,Notice uniqueness
     * @param preNavigationName String  前一级导航栏名称
     *                          The name of the previous navigation bar
     * @param navigationLevel   int  导航栏层级,大于等于0的数字
     *                          Navigation bar hierarchy,A number greater than or equal to 0
     */
    public Builder addNavigation(int navigationName, int preNavigationName, int navigationLevel) {
        return new Builder(navigationName, preNavigationName, navigationLevel);
    }

    /**
     * 添加导航栏
     * Add navigation
     *
     * @param navigation The navigation
     **/
    private NavigationBar addNavigation(Navigation navigation) {
        if (mNavigationList == null) {
            mNavigationList = new ArrayList<>();
        }
        mNavigationList.add(navigation);
        return this;
    }


    public class Builder {
        /**
         * 导航栏层级,大于等于0的数字
         */
        // private int level;
        /**
         * 导航栏名称,注意唯一性
         */
        //  private String name;
        /**
         * 前一级导航栏名称，注意唯一性
         */
        // private String preName;
        private Navigation navigation;//导航栏
        private Navigation.Item selectedItem;//选中的子项

        /**
         * 导航栏类型,用于一小部分特殊的要求的显示样式
         */
        private int type;
        /**
         * 默认选中位置，默认不会被选中。>= 0则有效
         */
        private int selectedPos = -1;

        private Builder(int name, int preName, int level) {
            // this.name = name;
            // this.preName = preName;
            // this.level = level;
            navigation = new Navigation(name, preName, level);
        }

        /**
         * 导航栏类型,用于一小部分特殊的要求的显示样式，尚待扩展 TODO
         */
        public Builder setType(@Navigation.NavigationType int type) {
            this.type = type;
            return this;
        }

        /**
         * 设置导航栏选中的子项
         */
        public Builder setSelected(int pos) {
            this.selectedPos = pos;
            return this;
        }

        /**
         * 设置导航栏选中的位置
         */
        public Builder setSelected(Navigation.Item item) {
            this.selectedItem = item;
            return this;
        }

        /**
         * 添加导航栏每一项
         *
         * @param item Navigation.Item  每一项的实体类
         */
        public Builder addItem(Navigation.Item item) {
            if (item != null) {
                navigation.addItem(item);
            }
            return this;
        }

        /**
         * 添加导航栏列表
         *
         * @param items List<Navigation.Item>  导航栏item集合
         */
        public Builder setItems(List<Navigation.Item> items) {
            if (items != null) {
                navigation.setItems(items);
            }
            return this;
        }

        /**
         * 添加导航栏每一项
         *
         * @param title              String 该项的标题
         * @param iconResId          String 该项的图标资源id
         * @param nextNavigationName String 该项被点击指向的下一导航栏名称
         */
        public Builder addItem(String title, int iconResId, int nextNavigationName) {
            navigation.addItem(new Navigation.Item()
                    .setTitle(title)
                    .setIconId(iconResId)
                    .setNextName(nextNavigationName));
            return this;
        }

        /**
         * 添加该导航栏
         */
        public NavigationBar build() {
            if (navigation.getItems() == null) {
                throw new NullPointerException("navigation.getItems() is null ,you need add Item!!!");
            }
            if (type > 0) {
                for (Navigation.Item item : navigation.getItems()) {
                    item.setType(type);
                }
                if (selectedItem == null) {
                    if (selectedPos >= 0 && selectedPos < navigation.getItems().size()) {
                        navigation.setSelectedItem(navigation.getItems().get(selectedPos));
                    }
                } else {
                    navigation.setSelectedItem(selectedItem);
                }
            }
            return addNavigation(navigation);
        }
    }

    private void dealWithItemClick(int position) {
        Navigation.Item item = mRvBarList0.getVisibility() == VISIBLE ?
                mBarAdapter0.getItem(position) : mBarAdapter1.getItem(position);
        if (item == null) {
            return;
        }
        mCurrentNavigation.setSelectedItem(item);
        if (mListener != null) {
            if (mListener.onInterceptItemClick(item, mCurrentNavigation.getName(), mPreNavigationName)) {
                //拦截点击事件
                return;
            }
            Navigation.Item rewriteItem = mListener.onNavigationItemClick(item, mCurrentNavigation.getName(), mPreNavigationName);
            if (rewriteItem != null) {
                //重定向点击事件
                item = rewriteItem;
            }
        }
        if (mRvBarList0.getVisibility() == VISIBLE) {
            if (changeShowView(item.getNextName(), false) && mIvBack.getVisibility() != VISIBLE) {
                mIvBack.setVisibility(VISIBLE);
            }
            if (item.getItemType() != TYPE_DEFAULT) {
                mBarAdapter0.selected(position);
            }
        } else {
            updateLevel0Navigation(false);
            changeShowView(item.getNextName(), true);
            if (item.getItemType() != TYPE_DEFAULT) {
                mBarAdapter1.selected(position);
            }
        }
    }

    private BaseQuickAdapter.OnItemChildClickListener mOnItemChildClick = new BaseQuickAdapter.OnItemChildClickListener() {
        @Override
        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            dealWithItemClick(position);
        }
    };
    private BaseQuickAdapter.OnItemClickListener mOnItemClick = new BaseQuickAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            if (TYPE_RATIO == adapter.getItemViewType(position)) {
                /*比例类型，item click 有效*/
                dealWithItemClick(position);
            }
        }
    };

    public static abstract class NavigationListener {

        protected boolean onInterceptItemClick(Navigation.Item item, int navigationName, int preNavigationName) {
            return false;
        }

        protected abstract Navigation.Item onNavigationItemClick(Navigation.Item item, int navigationName, int preNavigationName);

        protected abstract boolean onNavigationBack(int currentNavigationName, int preNavigationName);

    }
}
