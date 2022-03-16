package com.meishe.base.view;


/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Android开源项目版权所有
 *
 *使用Apache许可证2.0版本(以下简称“许可证”);
 *您不能使用此文件，除非符合许可证。
 *你可以在以下网址获得许可证的副本
 *
 * http://www.apache.org/licenses/license - 2.0
 *
 *除非适用法律要求或书面同意，软件
 *根据许可进行的发行是基于“现状”进行的，
 *不提供任何明示或暗示的保证或条件。
 *请参阅许可证中特定语言的管理权限和
 *限制
 */


import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import androidx.core.os.ParcelableCompat;
import androidx.core.os.ParcelableCompatCreatorCallbacks;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.VelocityTrackerCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewConfigurationCompat;
import androidx.core.widget.EdgeEffectCompat;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Layout manager that allows the user to flip left and right
 * through pages of data.  You supply an implementation of a
 *
 * <p>Note this class is currently under early design and
 * development.  The API will likely change in later updates of
 * the compatibility library, requiring changes to the source code
 * of apps when they are compiled against the newer version.</p>
 * 允许用户左右翻转的布局管理器
 * *通过页面的数据。您提供的实现
 * *
 * 注意:这个类目前处于早期设计和开发阶段
 * *发展。API可能会在以后的更新中改变
 * *兼容性库，需要更改源代码
 * *当应用程序在较新的版本上编译时。</p
 */
public class MSNoPreloadViewPager extends ViewGroup {
    private static final String TAG = "NoPreLoadViewPager";
    private static final boolean DEBUG = false;

    private static final boolean USE_CACHE = false;

    private static final int DEFAULT_OFFSCREEN_PAGES = 0;//默认是1
    private static final int MAX_SETTLE_DURATION = 600; // ms

    /**
     * The type Item info.
     * 条目信息类
     */
    static class ItemInfo {

        Object object;

        int position;

        boolean scrolling;
    }

    private static final Comparator<ItemInfo> COMPARATOR = new Comparator<ItemInfo>() {
        @Override
        public int compare(ItemInfo lhs, ItemInfo rhs) {
            return lhs.position - rhs.position;
        }
    };

    private static final Interpolator sInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            // _o(t) = t * t * ((tension + 1) * t + tension)
            // o(t) = _o(t - 1) + 1
            t -= 1.0f;
            return t * t * t + 1.0f;
        }
    };

    private final ArrayList<ItemInfo> mItems = new ArrayList<ItemInfo>();

    private PagerAdapter mAdapter;
    /*
     * Index of currently displayed page.
     * 当前显示页面的索引
     * */
    private int mCurItem;
    private int mRestoredCurItem = -1;
    private Parcelable mRestoredAdapterState = null;
    private ClassLoader mRestoredClassLoader = null;
    private Scroller mScroller;
    private PagerObserver mObserver;

    private int mPageMargin;
    private Drawable mMarginDrawable;

    private int mChildWidthMeasureSpec;
    private int mChildHeightMeasureSpec;
    private boolean mInLayout;

    private boolean mScrollingCacheEnabled;

    private boolean mPopulatePending;
    private boolean mScrolling;
    private int mOffscreenPageLimit = DEFAULT_OFFSCREEN_PAGES;

    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private int mTouchSlop;
    private float mInitialMotionX;

    private float mLastMotionX;
    private float mLastMotionY;
    /**
     * ID of the active pointer. This is used to retain consistency during
     * drags/flings if multiple pointers are used.
     * 活动指针的ID。这是用来保持一致性期间
     * *如果使用多个指针，则进行拖放。
     */
    private int mActivePointerId = INVALID_POINTER;
    /**
     * Sentinel value for no current active pointer.
     * Used by {@link #mActivePointerId}.
     * 当前没有活动指针的标记值。
     * *由{@link #mActivePointerId}使用。
     */
    private static final int INVALID_POINTER = -1;

    /**
     * Determines speed during touch scrolling
     * 在触摸滚动时决定速度
     */
    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private float mBaseLineFlingVelocity;
    private float mFlingVelocityInfluence;

    private boolean mFakeDragging;
    private long mFakeDragBeginTime;

    private EdgeEffectCompat mLeftEdge;
    private EdgeEffectCompat mRightEdge;

    private boolean mFirstLayout = true;

    private OnPageChangeListener mOnPageChangeListener;


    public static final int SCROLL_STATE_IDLE = 0;


    public static final int SCROLL_STATE_DRAGGING = 1;


    public static final int SCROLL_STATE_SETTLING = 2;

    private int mScrollState = SCROLL_STATE_IDLE;

    /**
     * Callback interface for responding to changing state of the selected page.
     * 回调接口，用于响应所选页面状态的变化。
     */
    public interface OnPageChangeListener {

        /**
         * This method will be invoked when the current page is scrolled, either as part
         * of a programmatically initiated smooth scroll or a user initiated touch scroll.
         * 此方法将在滚动当前页时调用，可以作为滚动的一部分
         * *程序启动的平滑滚动或用户启动的触摸滚动
         *
         * @param position             Position index of the first page currently being displayed.         当前显示的第一页的位置索引         Page position+1 will be visible if positionOffset is nonzero. 如果positionOffset非零，页面位置+1将可见。
         * @param positionOffset       Value from [0, 1) indicating the offset from the page at position. 值来自[0,1)，指示与页面位置的偏移量
         * @param positionOffsetPixels Value in pixels indicating the offset from position. 值(以像素为单位)，指示与位置的偏移量。
         */
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        /**
         * This method will be invoked when a new page becomes selected. Animation is not
         * necessarily complete.
         *
         * @param position Position index of the new selected page.
         *                 当选择新页面时，将调用此方法。动画不是一定完成
         *                 <p>
         *                 <p>
         *                 *@param位置新选定页面的位置索引。
         */
        public void onPageSelected(int position);

        /**
         * Called when the scroll state changes. Useful for discovering when the user
         * begins dragging, when the pager is automatically settling to the current page,
         * or when it is fully stopped/idle.
         * 当滚动状态更改时调用。有用的发现当用户
         * 当分页器自动调整到当前页面时，开始拖动，
         * 或当它完全停止/空闲。
         *
         * @param state The new scroll state. 新的滚动状态
         */
        public void onPageScrollStateChanged(int state);
    }

    /**
     * The type Simple on page change listener.
     * 简单的页面更改监听类
     */
    public static class SimpleOnPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public MSNoPreloadViewPager(Context context) {
        super(context);
        initViewPager();
    }

    public MSNoPreloadViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViewPager();
    }

    void initViewPager() {
        setWillNotDraw(false);
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setFocusable(true);
        final Context context = getContext();
        mScroller = new Scroller(context, sInterpolator);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mLeftEdge = new EdgeEffectCompat(context);
        mRightEdge = new EdgeEffectCompat(context);

        float density = context.getResources().getDisplayMetrics().density;
        mBaseLineFlingVelocity = 2500.0f * density;
        mFlingVelocityInfluence = 0.4f;
    }

    private void setScrollState(int newState) {
        if (mScrollState == newState) {
            return;
        }

        mScrollState = newState;
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(newState);
        }
    }

    /**
     * Sets adapter.
     * 设置适配器
     *
     * @param adapter the adapter
     */
    public void setAdapter(PagerAdapter adapter) {
        if (mAdapter != null) {
//            mAdapter.unregisterDataSetObserver(mObserver);
            mAdapter.startUpdate(this);
            for (int i = 0; i < mItems.size(); i++) {
                final ItemInfo ii = mItems.get(i);
                mAdapter.destroyItem(this, ii.position, ii.object);
            }
            mAdapter.finishUpdate(this);
            mItems.clear();
            removeAllViews();
            mCurItem = 0;
            scrollTo(0, 0);
        }

        mAdapter = adapter;

        if (mAdapter != null) {
            if (mObserver == null) {
                mObserver = new PagerObserver();
            }
//            mAdapter.registerDataSetObserver(mObserver);
            mPopulatePending = false;
            if (mRestoredCurItem >= 0) {
                mAdapter.restoreState(mRestoredAdapterState, mRestoredClassLoader);
                setCurrentItemInternal(mRestoredCurItem, false, true);
                mRestoredCurItem = -1;
                mRestoredAdapterState = null;
                mRestoredClassLoader = null;
            } else {
                populate();
            }
        }
    }

    /**
     * Gets adapter.
     * 获取适配器
     *
     * @return the adapter
     */
    public PagerAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * Set the currently selected page. If the ViewPager has already been through its first
     * layout there will be a smooth animated transition between the current item and the
     * specified item.
     * 设置当前选择的页面。如果ViewPager已经通过它的第一个
     * 将有一个平滑的动画过渡之间的当前项目和
     * 指定项
     *
     * @param item Item index to select
     */
    public void setCurrentItem(int item) {
        mPopulatePending = false;
        setCurrentItemInternal(item, !mFirstLayout, false);
    }

    /**
     * Set the currently selected page.
     * 设置当前选择的页面
     *
     * @param item         Item index to select 要选择的项索引
     * @param smoothScroll True to smoothly scroll to the new item, false to transition immediately 真则顺利滚动到新项目，假则立即过渡
     */
    public void setCurrentItem(int item, boolean smoothScroll) {
        mPopulatePending = false;
        setCurrentItemInternal(item, smoothScroll, false);
    }

    public int getCurrentItem() {
        return mCurItem;
    }

    void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
        setCurrentItemInternal(item, smoothScroll, always, 0);
    }

    void setCurrentItemInternal(int item, boolean smoothScroll, boolean always, int velocity) {
        if (mAdapter == null || mAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (!always && mCurItem == item && mItems.size() != 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (item < 0) {
            item = 0;
        } else if (item >= mAdapter.getCount()) {
            item = mAdapter.getCount() - 1;
        }
        final int pageLimit = mOffscreenPageLimit;
        if (item > (mCurItem + pageLimit) || item < (mCurItem - pageLimit)) {
            // We are doing a jump by more than one page.  To avoid
            // glitches, we want to keep all current pages in the view
            // until the scroll ends.
            for (int i = 0; i < mItems.size(); i++) {
                mItems.get(i).scrolling = true;
            }
        }

        final boolean dispatchSelected = mCurItem != item;
        mCurItem = item;
        populate();
        final int destX = (getWidth() + mPageMargin) * item;
        if (smoothScroll) {
            smoothScrollTo(destX, 0, velocity);
            if (dispatchSelected && mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageSelected(item);
            }
        } else {
            if (dispatchSelected && mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageSelected(item);
            }
            completeScroll();
            scrollTo(destX, 0);
        }
    }

    /**
     * Sets on page change listener.
     * 设置在页面更改的监听器
     *
     * @param listener the listener
     */
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    /**
     * Returns the number of pages that will be retained to either side of the
     * current page in the view hierarchy in an idle state. Defaults to 1.
     * 属性的任意一边保留的页数
     * 视图层次结构中的当前页面处于空闲状态。默认为1。
     *
     * @return How many pages will be kept offscreen on either side 有多少页将保持在屏幕之外的任何一边
     * @see #setOffscreenPageLimit(int) #setOffscreenPageLimit(int)
     */
    public int getOffscreenPageLimit() {
        return mOffscreenPageLimit;
    }

    /**
     * Set the number of pages that should be retained to either side of the
     * current page in the view hierarchy in an idle state. Pages beyond this
     * limit will be recreated from the adapter when needed.
     *
     * <p>This is offered as an optimization. If you know in advance the number
     * of pages you will need to support or have lazy-loading mechanisms in place
     * on your pages, tweaking this setting can have benefits in perceived smoothness
     * of paging animations and interaction. If you have a small number of pages (3-4)
     * that you can keep active all at once, less time will be spent in layout for
     * newly created view subtrees as the user pages back and forth.</p>
     *
     * <p>You should keep this limit low, especially if your pages have complex layouts.
     * This setting defaults to 1.</p>
     * <p>
     * <p>
     * 页的任意一边应保留的页数
     * *视图层次结构中的当前页面处于空闲状态。页面除此之外
     * *当需要时，将从适配器重新创建限制。
     * *
     * 这是一个优化。如果你提前知道号码的话
     * *你将需要支持或有延迟加载机制的页面
     * *在你的页面上，调整这个设置可以在平滑度上有好处
     * *分页动画和交互。如果你的页数很少(3-4页)
     * 你可以随时保持活跃
     *
     * @param limit How many pages will be kept offscreen in an idle state.
     */
    public void setOffscreenPageLimit(int limit) {
        if (limit < DEFAULT_OFFSCREEN_PAGES) {
            Log.w(TAG, "Requested offscreen page limit " + limit + " too small; defaulting to " +
                    DEFAULT_OFFSCREEN_PAGES);
            limit = DEFAULT_OFFSCREEN_PAGES;
        }
        if (limit != mOffscreenPageLimit) {
            mOffscreenPageLimit = limit;
            populate();
        }
    }

    /**
     * Set the margin between pages.
     * 设置页面之间的空白
     *
     * @param marginPixels Distance between adjacent pages in pixels 相邻页面之间的距离(以像素为单位)
     * @see #getPageMargin() #getPageMargin()
     * @see #setPageMarginDrawable(Drawable) #setPageMarginDrawable(Drawable)
     * @see #setPageMarginDrawable(int) #setPageMarginDrawable(int)
     */
    public void setPageMargin(int marginPixels) {
        final int oldMargin = mPageMargin;
        mPageMargin = marginPixels;

        final int width = getWidth();
        recomputeScrollPosition(width, width, marginPixels, oldMargin);

        requestLayout();
    }

    /**
     * Return the margin between pages.
     * 返回页面之间的空白
     *
     * @return The size of the margin in pixels 边界的大小，以像素为单位
     */
    public int getPageMargin() {
        return mPageMargin;
    }

    /**
     * Set a drawable that will be used to fill the margin between pages.
     * 设置用于填充页面之间空白的可绘制格式
     *
     * @param d Drawable to display between pages
     */
    public void setPageMarginDrawable(Drawable d) {
        mMarginDrawable = d;
        if (d != null) {
            refreshDrawableState();
        }
        setWillNotDraw(d == null);
        invalidate();
    }

    public void setPageMarginDrawable(int resId) {
        setPageMarginDrawable(getContext().getResources().getDrawable(resId));
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == mMarginDrawable;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        final Drawable d = mMarginDrawable;
        if (d != null && d.isStateful()) {
            d.setState(getDrawableState());
        }
    }

/*
*    我们希望页面snap动画的持续时间受该距离的影响
    屏幕必须移动，但是，我们不希望这个持续时间在a中受到影响
    纯粹的线性风格。相反，我们用这种方法来缓和距离的影响
     的旅行已对整体的snap持续时间
*    We want the duration of the page snap animation to be influenced by the distance that
     the screen has to travel, however, we don't want this duration to be effected in a
     purely linear fashion. Instead, we use this method to moderate the effect that the distance
     of travel has on the overall snap duration.
* */

    float distanceInfluenceForSnapDuration(float f) {
        f -= 0.5f; // center the values about 0.
        f *= 0.3f * Math.PI / 2.0f;
        return (float) Math.sin(f);
    }


    void smoothScrollTo(int x, int y) {
        smoothScrollTo(x, y, 0);
    }


    void smoothScrollTo(int x, int y, int velocity) {
        if (getChildCount() == 0) {
            // Nothing to do.
            setScrollingCacheEnabled(false);
            return;
        }
        int sx = getScrollX();
        int sy = getScrollY();
        int dx = x - sx;
        int dy = y - sy;
        if (dx == 0 && dy == 0) {
            completeScroll();
            setScrollState(SCROLL_STATE_IDLE);
            return;
        }

        setScrollingCacheEnabled(true);
        mScrolling = true;
        setScrollState(SCROLL_STATE_SETTLING);

        final float pageDelta = (float) Math.abs(dx) / (getWidth() + mPageMargin);
        int duration = (int) (pageDelta * 100);

        velocity = Math.abs(velocity);
        if (velocity > 0) {
            duration += (duration / (velocity / mBaseLineFlingVelocity)) * mFlingVelocityInfluence;
        } else {
            duration += 100;
        }
        duration = Math.min(duration, MAX_SETTLE_DURATION);

        mScroller.startScroll(sx, sy, dx, dy, duration);
        invalidate();
    }

    /**
     * Add new item.
     * 添加新条目
     *
     * @param position the position 位置
     * @param index    the index 索引
     */
    void addNewItem(int position, int index) {
        ItemInfo ii = new ItemInfo();
        ii.position = position;
        ii.object = mAdapter.instantiateItem(this, position);
        if (index < 0) {
            mItems.add(ii);
        } else {
            mItems.add(index, ii);
        }
    }

    /**
     * Data set changed.
     * 数据集的变化
     */
    void dataSetChanged() {
        /*
         * This method only gets called if our observer is attached, so mAdapter is non-null.
         * 此方法仅在观察者被附加时被调用，因此mAdapter是非空的。
         * */

        boolean needPopulate = mItems.size() < 3 && mItems.size() < mAdapter.getCount();
        int newCurrItem = -1;

        for (int i = 0; i < mItems.size(); i++) {
            final ItemInfo ii = mItems.get(i);
            final int newPos = mAdapter.getItemPosition(ii.object);

            if (newPos == PagerAdapter.POSITION_UNCHANGED) {
                continue;
            }

            if (newPos == PagerAdapter.POSITION_NONE) {
                mItems.remove(i);
                i--;
                mAdapter.destroyItem(this, ii.position, ii.object);
                needPopulate = true;

                if (mCurItem == ii.position) {
                    /*
                     * Keep the current item in the valid range
                     *  保持当前项在有效范围内
                     * */
                    newCurrItem = Math.max(0, Math.min(mCurItem, mAdapter.getCount() - 1));
                }
                continue;
            }

            if (ii.position != newPos) {
                if (ii.position == mCurItem) {
                    /*
                     * Our current item changed position. Follow it.
                     * 我们当前的项目改变了位置。跟随它。
                     * */
                    newCurrItem = newPos;
                }

                ii.position = newPos;
                needPopulate = true;
            }
        }

        Collections.sort(mItems, COMPARATOR);

        if (newCurrItem >= 0) {
            // TODO This currently causes a jump.
            setCurrentItemInternal(newCurrItem, false, true);
            needPopulate = true;
        }
        if (needPopulate) {
            populate();
            requestLayout();
        }
    }

    /**
     * Populate.
     * 填充
     */
    void populate() {
        if (mAdapter == null) {
            return;
        }

        /*
        * Bail now if we are waiting to populate.  This is to hold off
         on creating views from the time the user releases their finger to
         fling to a new position until we have finished the scroll to
         that position, avoiding glitches from happening at that point.
        * 如果我们等待移民，现在就保释。这是拖延
         从用户松开手指开始创建视图
         抛到一个新的位置，直到我们完成卷轴
         那个位置，避免在那个点发生故障。
        * */
        if (mPopulatePending) {
            if (DEBUG) {
                Log.i(TAG, "populate is pending, skipping for now...");
            }
            return;
        }

        /*
        *  Also, don't populate until we are attached to a window.  This is to
         avoid trying to populate before we have restored our view hierarchy
         state and conflicting with what is restored.
        * 另外，在我们附加到一个窗口之前不要填充。这是为了
         在恢复视图层次结构之前，避免尝试填充
          与恢复的状态相冲突。
        * */
        if (getWindowToken() == null) {
            return;
        }

        mAdapter.startUpdate(this);

        final int pageLimit = mOffscreenPageLimit;
        final int startPos = Math.max(0, mCurItem - pageLimit);
        final int N = mAdapter.getCount();
        final int endPos = Math.min(N - 1, mCurItem + pageLimit);

        if (DEBUG) {
            Log.v(TAG, "populating: startPos=" + startPos + " endPos=" + endPos);
        }

        // Add and remove pages in the existing list.
        int lastPos = -1;
        for (int i = 0; i < mItems.size(); i++) {
            ItemInfo ii = mItems.get(i);
            if ((ii.position < startPos || ii.position > endPos) && !ii.scrolling) {
                if (DEBUG) {
                    Log.i(TAG, "removing: " + ii.position + " @ " + i);
                }
                mItems.remove(i);
                i--;
                mAdapter.destroyItem(this, ii.position, ii.object);
            } else if (lastPos < endPos && ii.position > startPos) {
                // The next item is outside of our range, but we have a gap
                // between it and the last item where we want to have a page
                // shown.  Fill in the gap.
                lastPos++;
                if (lastPos < startPos) {
                    lastPos = startPos;
                }
                while (lastPos <= endPos && lastPos < ii.position) {
                    if (DEBUG) {
                        Log.i(TAG, "inserting: " + lastPos + " @ " + i);
                    }
                    addNewItem(lastPos, i);
                    lastPos++;
                    i++;
                }
            }
            lastPos = ii.position;
        }

        /*
         * Add any new pages we need at the end.
         * 在最后添加我们需要的任何新页面
         * */
        lastPos = mItems.size() > 0 ? mItems.get(mItems.size() - 1).position : -1;
        if (lastPos < endPos) {
            lastPos++;
            lastPos = lastPos > startPos ? lastPos : startPos;
            while (lastPos <= endPos) {
                if (DEBUG) {
                    Log.i(TAG, "appending: " + lastPos);
                }
                addNewItem(lastPos, -1);
                lastPos++;
            }
        }

        if (DEBUG) {
            Log.i(TAG, "Current page list:");
            for (int i = 0; i < mItems.size(); i++) {
                Log.i(TAG, "#" + i + ": page " + mItems.get(i).position);
            }
        }

        ItemInfo curItem = null;
        for (int i = 0; i < mItems.size(); i++) {
            if (mItems.get(i).position == mCurItem) {
                curItem = mItems.get(i);
                break;
            }
        }
        mAdapter.setPrimaryItem(this, mCurItem, curItem != null ? curItem.object : null);

        mAdapter.finishUpdate(this);

        if (hasFocus()) {
            View currentFocused = findFocus();
            ItemInfo ii = currentFocused != null ? infoForAnyChild(currentFocused) : null;
            if (ii == null || ii.position != mCurItem) {
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    ii = infoForChild(child);
                    if (ii != null && ii.position == mCurItem) {
                        if (child.requestFocus(FOCUS_FORWARD)) {
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * The type Saved state.
     * 保存状态类
     */
    public static class SavedState extends BaseSavedState {

        int position;

        Parcelable adapterState;

        ClassLoader loader;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(position);
            out.writeParcelable(adapterState, flags);
        }

        @Override
        public String toString() {
            return "FragmentPager.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " position=" + position + "}";
        }

        public static final Creator<SavedState> CREATOR
                = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        });

        SavedState(Parcel in, ClassLoader loader) {
            super(in);
            if (loader == null) {
                loader = getClass().getClassLoader();
            }
            position = in.readInt();
            adapterState = in.readParcelable(loader);
            this.loader = loader;
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.position = mCurItem;
        if (mAdapter != null) {
            ss.adapterState = mAdapter.saveState();
        }
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        if (mAdapter != null) {
            mAdapter.restoreState(ss.adapterState, ss.loader);
            setCurrentItemInternal(ss.position, false, true);
        } else {
            mRestoredCurItem = ss.position;
            mRestoredAdapterState = ss.adapterState;
            mRestoredClassLoader = ss.loader;
        }
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        if (mInLayout) {
            addViewInLayout(child, index, params);
            child.measure(mChildWidthMeasureSpec, mChildHeightMeasureSpec);
        } else {
            super.addView(child, index, params);
        }

        if (USE_CACHE) {
            if (child.getVisibility() != GONE) {
                child.setDrawingCacheEnabled(mScrollingCacheEnabled);
            } else {
                child.setDrawingCacheEnabled(false);
            }
        }
    }

    /**
     * Info for child item info.
     * 子项目信息
     *
     * @param child the child  子
     * @return the item info 条目信息
     */
    ItemInfo infoForChild(View child) {
        for (int i = 0; i < mItems.size(); i++) {
            ItemInfo ii = mItems.get(i);
            if (mAdapter.isViewFromObject(child, ii.object)) {
                return ii;
            }
        }
        return null;
    }

    /**
     * Info for any child item info.
     * 任何子项目信息
     *
     * @param child the child 子
     * @return the item info 条目信息
     */
    ItemInfo infoForAnyChild(View child) {
        ViewParent parent;
        while ((parent = child.getParent()) != this) {
            if (parent == null || !(parent instanceof View)) {
                return null;
            }
            child = (View) parent;
        }
        return infoForChild(child);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mFirstLayout = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*For simple implementation, or internal size is always 0.
         We depend on the container to specify the layout size of
         our view.  We can't really know what it is since we will be
         adding and removing different arbitrary views and do not
         want the layout to change as this happens.
        *  对于简单的实现，或内部大小始终为0。
           我们依赖容器来指定布局大小
           我们的观点。我们不能真正知道它是什么，因为我们将会
           添加和删除不同的任意视图和不
           希望在这种情况下改变布局。
        * */
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));

        /*
         * Children are just made to fill our space.
         * 只是为了填满我们的空间
         * */
        mChildWidthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth() -
                getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY);
        mChildHeightMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight() -
                getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY);

        /*
         * Make sure we have created all fragments that we need to have shown.
         * 确保我们已经创建了所有需要显示的片段。
         * */
        mInLayout = true;
        populate();
        mInLayout = false;

        /*
         * Make sure all children have been properly measured.
         * 确保所有的孩子都被适当地测量过
         * */
        final int size = getChildCount();
        for (int i = 0; i < size; ++i) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                if (DEBUG) {
                    Log.v(TAG, "Measuring #" + i + " " + child
                            + ": " + mChildWidthMeasureSpec);
                }
                child.measure(mChildWidthMeasureSpec, mChildHeightMeasureSpec);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        /*
         * Make sure scroll position is set correctly.
         *  确保滚动的位置设置正确
         * */
        if (w != oldw) {
            recomputeScrollPosition(w, oldw, mPageMargin, mPageMargin);
        }
    }

    private void recomputeScrollPosition(int width, int oldWidth, int margin, int oldMargin) {
        final int widthWithMargin = width + margin;
        if (oldWidth > 0) {
            final int oldScrollPos = getScrollX();
            final int oldwwm = oldWidth + oldMargin;
            final int oldScrollItem = oldScrollPos / oldwwm;
            final float scrollOffset = (float) (oldScrollPos % oldwwm) / oldwwm;
            final int scrollPos = (int) ((oldScrollItem + scrollOffset) * widthWithMargin);
            scrollTo(scrollPos, getScrollY());
            if (!mScroller.isFinished()) {
                // We now return to your regularly scheduled scroll, already in progress.
                final int newDuration = mScroller.getDuration() - mScroller.timePassed();
                mScroller.startScroll(scrollPos, 0, mCurItem * widthWithMargin, 0, newDuration);
            }
        } else {
            int scrollPos = mCurItem * widthWithMargin;
            if (scrollPos != getScrollX()) {
                completeScroll();
                scrollTo(scrollPos, getScrollY());
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mInLayout = true;
        populate();
        mInLayout = false;

        final int count = getChildCount();
        final int width = r - l;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            ItemInfo ii;
            if (child.getVisibility() != GONE && (ii = infoForChild(child)) != null) {
                int loff = (width + mPageMargin) * ii.position;
                int childLeft = getPaddingLeft() + loff;
                int childTop = getPaddingTop();
                if (DEBUG) {
                    Log.v(TAG, "Positioning #" + i + " " + child + " f=" + ii.object
                            + ":" + childLeft + "," + childTop + " " + child.getMeasuredWidth()
                            + "x" + child.getMeasuredHeight());
                }
                child.layout(childLeft, childTop,
                        childLeft + child.getMeasuredWidth(),
                        childTop + child.getMeasuredHeight());
            }
        }
        mFirstLayout = false;
    }

    @Override
    public void computeScroll() {
        if (DEBUG) {
            Log.i(TAG, "computeScroll: finished=" + mScroller.isFinished());
        }
        if (!mScroller.isFinished()) {
            if (mScroller.computeScrollOffset()) {
                if (DEBUG) {
                    Log.i(TAG, "computeScroll: still scrolling");
                }
                int oldX = getScrollX();
                int oldY = getScrollY();
                int x = mScroller.getCurrX();
                int y = mScroller.getCurrY();

                if (oldX != x || oldY != y) {
                    scrollTo(x, y);
                }

                if (mOnPageChangeListener != null) {
                    final int widthWithMargin = getWidth() + mPageMargin;
                    final int position = x / widthWithMargin;
                    final int offsetPixels = x % widthWithMargin;
                    final float offset = (float) offsetPixels / widthWithMargin;
                    mOnPageChangeListener.onPageScrolled(position, offset, offsetPixels);
                }

                // Keep on drawing until the animation has finished.
                invalidate();
                return;
            }
        }

        /*
         * Done with scroll, clean up state
         *  完成了滚动，清理状态
         * */
        completeScroll();
    }

    private void completeScroll() {
        boolean needPopulate = mScrolling;
        if (needPopulate) {
            /*
             *  Done with scroll, no longer want to cache view drawing.
             *  完成了滚动，不再想要缓存视图绘图
             * */
            setScrollingCacheEnabled(false);
            mScroller.abortAnimation();
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();
            if (oldX != x || oldY != y) {
                scrollTo(x, y);
            }
            setScrollState(SCROLL_STATE_IDLE);
        }
        mPopulatePending = false;
        mScrolling = false;
        for (int i = 0; i < mItems.size(); i++) {
            ItemInfo ii = mItems.get(i);
            if (ii.scrolling) {
                needPopulate = true;
                ii.scrolling = false;
            }
        }
        if (needPopulate) {
            populate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*
         * This method JUST determines whether we want to intercept the motion.
         * If we return true, onMotionEvent will be called and we do the actual
         * scrolling there.
         * 这个方法只决定我们是否要截取运动。
         *如果我们返回true, onMotionEvent会被调用，我们做实际
         *滚动。
         */

        final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;

        /*
         * Always take care of the touch gesture being complete
         *  始终注意触摸手势的完整性
         * */
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            /*
             * Release the drag.
             * 释放
             * */
            if (DEBUG) {
                Log.v(TAG, "Intercept done!");
            }
            mIsBeingDragged = false;
            mIsUnableToDrag = false;
            mActivePointerId = INVALID_POINTER;
            return false;
        }

        /*
         * Nothing more to do here if we have decided whether or not we are dragging
         * 如果我们已经决定了是否拖拽，就没有什么可做的了
         * .
         * */

        if (action != MotionEvent.ACTION_DOWN) {
            if (mIsBeingDragged) {
                if (DEBUG) {
                    Log.v(TAG, "Intercept returning true!");
                }
                return true;
            }
            if (mIsUnableToDrag) {
                if (DEBUG) {
                    Log.v(TAG, "Intercept returning false!");
                }
                return false;
            }
        }

        if (action == MotionEvent.ACTION_MOVE) {/*
         * mIsBeingDragged == false, otherwise the shortcut would have caught it. Check
         * whether the user has moved far enough from his original down touch.
         misbeingdrag == false，否则快捷键会捕获它。检查
          *用户是否已经远离他最初的向下触控。
         */

            /*
             * Locally do absolute value. mLastMotionY is set to the y value
             * of the down event.
             * 局部求绝对值。mLastMotionY被设置为y值
                的down事件。
             */
            final int activePointerId = mActivePointerId;
            if (activePointerId == INVALID_POINTER) {
                /*
                 * If we don't have a valid id, the touch down wasn't on content.
                 * 如果我们没有有效id，触摸就不会在内容上。
                 * */
                return mIsBeingDragged;
            }

            final int pointerIndex = MotionEventCompat.findPointerIndex(ev, activePointerId);
            final float x = MotionEventCompat.getX(ev, pointerIndex);
            final float dx = x - mLastMotionX;
            final float xDiff = Math.abs(dx);
            final float y = MotionEventCompat.getY(ev, pointerIndex);
            final float yDiff = Math.abs(y - mLastMotionY);
            final int scrollX = getScrollX();
            final boolean atEdge = (dx > 0 && scrollX == 0) || (dx < 0 && mAdapter != null &&
                    scrollX >= (mAdapter.getCount() - 1) * getWidth() - 1);
            if (DEBUG) {
                Log.v(TAG, "Moved x to " + x + "," + y + " diff=" + xDiff + "," + yDiff);
            }

            if (canScroll(this, false, (int) dx, (int) x, (int) y)) {
                /*
                 *  Nested view has scrollable area under this point. Let it be handled there.
                 * 嵌套视图在此点下有可滚动区域。让它在那里处理。
                 * */
                mInitialMotionX = mLastMotionX = x;
                mLastMotionY = y;
                return false;
            }
            if (xDiff > mTouchSlop && xDiff > yDiff) {
                if (DEBUG) {
                    Log.v(TAG, "Starting drag!");
                }
                mIsBeingDragged = true;
                setScrollState(SCROLL_STATE_DRAGGING);
                mLastMotionX = x;
                setScrollingCacheEnabled(true);
            } else {
                if (yDiff > mTouchSlop) {
                    /*
                    * The finger has moved enough in the vertical
                    direction to be counted as a drag...  abort
                     any attempt to drag horizontally, to work correctly
                     with children that have scrolling containers.
                    * 手指在垂直方向上已经移动得够多了
                         方向被视为拖累…中止
                        任何水平拖动的尝试，以正确工作
                          带有滚动容器的子元素。
                    * */
                    if (DEBUG) {
                        Log.v(TAG, "Starting unable to drag!");
                    }
                    mIsUnableToDrag = true;
                }
            }
        } else if (action == MotionEvent.ACTION_DOWN) {/*
         * Remember location of down touch.
         * ACTION_DOWN always refers to pointer index 0.
         记住降落点的位置。
          * ACTION_DOWN总是指向指针索引0
         */
            mLastMotionX = mInitialMotionX = ev.getX();
            mLastMotionY = ev.getY();
            mActivePointerId = MotionEventCompat.getPointerId(ev, 0);

            if (mScrollState == SCROLL_STATE_SETTLING) {
                // Let the user 'catch' the pager as it animates.
                mIsBeingDragged = true;
                mIsUnableToDrag = false;
                setScrollState(SCROLL_STATE_DRAGGING);
            } else {
                completeScroll();
                mIsBeingDragged = false;
                mIsUnableToDrag = false;
            }

            if (DEBUG) {
                Log.v(TAG, "Down at " + mLastMotionX + "," + mLastMotionY
                        + " mIsBeingDragged=" + mIsBeingDragged
                        + "mIsUnableToDrag=" + mIsUnableToDrag);
            }
        } else if (action == MotionEventCompat.ACTION_POINTER_UP) {
            onSecondaryPointerUp(ev);
        }

        /*
         * The only time we want to intercept motion events is if we are in the
         * drag mode.
         * 我们唯一想要截取运动事件的时候是在
         *拖动模式
         */
        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mFakeDragging) {
            /*
            * A fake drag is in progress already, ignore this real one
             but still eat the touch events.
            (It is likely that the user is multi-touching the screen.)
            * 一个假拖拽已经在进行中，忽略这个真实的拖拽
                但仍然吃触摸事件。
                (很可能是用户多触屏。)
            * */
            return true;
        }

        if (ev.getAction() == MotionEvent.ACTION_DOWN && ev.getEdgeFlags() != 0) {
            /*
            * Don't handle edge touches immediately -- they may actually belong to one of our
            descendants.
            * 不要立即处理边缘触摸——它们可能实际上属于我们的一个
              的后代
            * */
            return false;
        }

        if (mAdapter == null || mAdapter.getCount() == 0) {
            /*
             * Nothing to present or scroll; nothing to touch.
             *  没有什么可以展示或滚动的;没有联系。
             * */
            return false;
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        final int action = ev.getAction();
        boolean needsInvalidate = false;

        if ((action & MotionEventCompat.ACTION_MASK) == MotionEvent.ACTION_DOWN) {/*
         * If being flinged and user touches, stop the fling. isFinished
         * will be false if being flinged.
         如果被投掷和用户触摸，停止投掷。结束
         *如果被抛出，将为假
         */
            completeScroll();

            /*
             *  Remember where the motion event started
             *  记住运动事件是从哪里开始的
             * */
            mLastMotionX = mInitialMotionX = ev.getX();
            mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
        } else if ((action & MotionEventCompat.ACTION_MASK) == MotionEvent.ACTION_MOVE) {
            if (!mIsBeingDragged) {
                final int pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float xDiff = Math.abs(x - mLastMotionX);
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float yDiff = Math.abs(y - mLastMotionY);
                if (DEBUG) {
                    Log.v(TAG, "Moved x to " + x + "," + y + " diff=" + xDiff + "," + yDiff);
                }
                if (xDiff > mTouchSlop && xDiff > yDiff) {
                    if (DEBUG) {
                        Log.v(TAG, "Starting drag!");
                    }
                    mIsBeingDragged = true;
                    mLastMotionX = x;
                    setScrollState(SCROLL_STATE_DRAGGING);
                    setScrollingCacheEnabled(true);
                }
            }
            if (mIsBeingDragged) {
                /*
                 *  Scroll to follow the motion event
                 *  滚动跟踪运动事件
                 * */
                final int activePointerIndex = MotionEventCompat.findPointerIndex(
                        ev, mActivePointerId);
                final float x = MotionEventCompat.getX(ev, activePointerIndex);
                final float deltaX = mLastMotionX - x;
                mLastMotionX = x;
                float oldScrollX = getScrollX();
                float scrollX = oldScrollX + deltaX;
                final int width = getWidth();
                final int widthWithMargin = width + mPageMargin;

                final int lastItemIndex = mAdapter.getCount() - 1;
                final float leftBound = Math.max(0, (mCurItem - 1) * widthWithMargin);
                final float rightBound =
                        Math.min(mCurItem + 1, lastItemIndex) * widthWithMargin;
                if (scrollX < leftBound) {
                    if (leftBound == 0) {
                        float over = -scrollX;
                        needsInvalidate = mLeftEdge.onPull(over / width);
                    }
                    scrollX = leftBound;
                } else if (scrollX > rightBound) {
                    if (rightBound == lastItemIndex * widthWithMargin) {
                        float over = scrollX - rightBound;
                        needsInvalidate = mRightEdge.onPull(over / width);
                    }
                    scrollX = rightBound;
                }
                /*
                 *  Don't lose the rounded component
                 *  不要丢失圆角部分
                 * */
                mLastMotionX += scrollX - (int) scrollX;
                scrollTo((int) scrollX, getScrollY());
                if (mOnPageChangeListener != null) {
                    final int position = (int) scrollX / widthWithMargin;
                    final int positionOffsetPixels = (int) scrollX % widthWithMargin;
                    final float positionOffset = (float) positionOffsetPixels / widthWithMargin;
                    mOnPageChangeListener.onPageScrolled(position, positionOffset,
                            positionOffsetPixels);
                }
            }
        } else if ((action & MotionEventCompat.ACTION_MASK) == MotionEvent.ACTION_UP) {
            if (mIsBeingDragged) {
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int initialVelocity = (int) VelocityTrackerCompat.getXVelocity(
                        velocityTracker, mActivePointerId);
                mPopulatePending = true;
                final int widthWithMargin = getWidth() + mPageMargin;
                final int scrollX = getScrollX();
                final int currentPage = scrollX / widthWithMargin;
                int nextPage = initialVelocity > 0 ? currentPage : currentPage + 1;
                setCurrentItemInternal(nextPage, true, true, initialVelocity);

                mActivePointerId = INVALID_POINTER;
                endDrag();
                needsInvalidate = mLeftEdge.onRelease() | mRightEdge.onRelease();
            }
        } else if ((action & MotionEventCompat.ACTION_MASK) == MotionEvent.ACTION_CANCEL) {
            if (mIsBeingDragged) {
                setCurrentItemInternal(mCurItem, true, true);
                mActivePointerId = INVALID_POINTER;
                endDrag();
                needsInvalidate = mLeftEdge.onRelease() | mRightEdge.onRelease();
            }
        } else if ((action & MotionEventCompat.ACTION_MASK) == MotionEventCompat.ACTION_POINTER_DOWN) {
            final int index = MotionEventCompat.getActionIndex(ev);
            final float x = MotionEventCompat.getX(ev, index);
            mLastMotionX = x;
            mActivePointerId = MotionEventCompat.getPointerId(ev, index);
        } else if ((action & MotionEventCompat.ACTION_MASK) == MotionEventCompat.ACTION_POINTER_UP) {
            onSecondaryPointerUp(ev);
            mLastMotionX = MotionEventCompat.getX(ev,
                    MotionEventCompat.findPointerIndex(ev, mActivePointerId));
        }
        if (needsInvalidate) {
            invalidate();
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        boolean needsInvalidate = false;

        final int overScrollMode = ViewCompat.getOverScrollMode(this);
        if (overScrollMode == ViewCompat.OVER_SCROLL_ALWAYS ||
                (overScrollMode == ViewCompat.OVER_SCROLL_IF_CONTENT_SCROLLS &&
                        mAdapter != null && mAdapter.getCount() > 1)) {
            if (!mLeftEdge.isFinished()) {
                final int restoreCount = canvas.save();
                final int height = getHeight() - getPaddingTop() - getPaddingBottom();

                canvas.rotate(270);
                canvas.translate(-height + getPaddingTop(), 0);
                mLeftEdge.setSize(height, getWidth());
                needsInvalidate |= mLeftEdge.draw(canvas);
                canvas.restoreToCount(restoreCount);
            }
            if (!mRightEdge.isFinished()) {
                final int restoreCount = canvas.save();
                final int width = getWidth();
                final int height = getHeight() - getPaddingTop() - getPaddingBottom();
                final int itemCount = mAdapter != null ? mAdapter.getCount() : 1;

                canvas.rotate(90);
                canvas.translate(-getPaddingTop(),
                        -itemCount * (width + mPageMargin) + mPageMargin);
                mRightEdge.setSize(height, width);
                needsInvalidate |= mRightEdge.draw(canvas);
                canvas.restoreToCount(restoreCount);
            }
        } else {
            mLeftEdge.finish();
            mRightEdge.finish();
        }

        if (needsInvalidate) {
            // Keep animating
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (mPageMargin > 0 && mMarginDrawable != null) {
            final int scrollX = getScrollX();
            final int width = getWidth();
            final int offset = scrollX % (width + mPageMargin);
            if (offset != 0) {
                // Pages fit completely when settled; we only need to draw when in between
                final int left = scrollX - offset + width;
                mMarginDrawable.setBounds(left, 0, left + mPageMargin, getHeight());
                mMarginDrawable.draw(canvas);
            }
        }
    }

    /**
     * Start a fake drag of the pager.
     *
     * <p>A fake drag can be useful if you want to synchronize the motion of the ViewPager
     * with the touch scrolling of another view, while still letting the ViewPager
     * control the snapping motion and fling behavior. (e.g. parallax-scrolling tabs.)
     * Call {@link #fakeDragBy(float)} to simulate the actual drag motion. Call
     * {@link #endFakeDrag()} to complete the fake drag and fling as necessary.
     *
     * <p>During a fake drag the ViewPager will ignore all touch events. If a real drag
     * is already in progress, this method will return false.
     * 开始假拖拉。
     * *
     * 如果你想要同步ViewPager的移动，假拖动可能是有用的
     * *触摸滚动的另一个视图，而仍然让ViewPager
     * *控制折断动作和投掷行为。(如parallax-scrolling选项卡。)
     * *调用{@link #fakeDragBy(float)}来模拟实际的拖动动作。调用
     * * {@link #endFakeDrag()}来完成假拖放。
     * *
     * 在假拖动期间，ViewPager将忽略所有的触摸事件。如果是真的
     * *已经在进行中
     * @return true if the fake drag began successfully, false if it could not be started.
     * @see #fakeDragBy(float) #fakeDragBy(float)
     * @see #endFakeDrag() #endFakeDrag()
     */
    public boolean beginFakeDrag() {
        if (mIsBeingDragged) {
            return false;
        }
        mFakeDragging = true;
        setScrollState(SCROLL_STATE_DRAGGING);
        mInitialMotionX = mLastMotionX = 0;
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
        final long time = SystemClock.uptimeMillis();
        final MotionEvent ev = MotionEvent.obtain(time, time, MotionEvent.ACTION_DOWN, 0, 0, 0);
        mVelocityTracker.addMovement(ev);
        ev.recycle();
        mFakeDragBeginTime = time;
        return true;
    }

    /**
     * End a fake drag of the pager.
     * 结束假拖拉的pager
     * @see #beginFakeDrag() #beginFakeDrag()
     * @see #fakeDragBy(float) #fakeDragBy(float)
     */
    public void endFakeDrag() {
        if (!mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }

        final VelocityTracker velocityTracker = mVelocityTracker;
        velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
        int initialVelocity = (int) VelocityTrackerCompat.getYVelocity(
                velocityTracker, mActivePointerId);
        mPopulatePending = true;
        if ((Math.abs(initialVelocity) > mMinimumVelocity)
                || Math.abs(mInitialMotionX - mLastMotionX) >= (getWidth() / 3)) {
            if (mLastMotionX > mInitialMotionX) {
                setCurrentItemInternal(mCurItem - 1, true, true);
            } else {
                setCurrentItemInternal(mCurItem + 1, true, true);
            }
        } else {
            setCurrentItemInternal(mCurItem, true, true);
        }
        endDrag();

        mFakeDragging = false;
    }

    /**
     * Fake drag by an offset in pixels. You must have called {@link #beginFakeDrag()} first.
     * 假拖动的偏移像素。您必须首先调用{@link #beginFakeDrag()}。
     * @param xOffset Offset in pixels to drag by. 偏移像素以拖动
     * @see #beginFakeDrag() #beginFakeDrag()
     * @see #endFakeDrag() #endFakeDrag()
     */
    public void fakeDragBy(float xOffset) {
        if (!mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }

        mLastMotionX += xOffset;
        float scrollX = getScrollX() - xOffset;
        final int width = getWidth();
        final int widthWithMargin = width + mPageMargin;

        final float leftBound = Math.max(0, (mCurItem - 1) * widthWithMargin);
        final float rightBound =
                Math.min(mCurItem + 1, mAdapter.getCount() - 1) * widthWithMargin;
        if (scrollX < leftBound) {
            scrollX = leftBound;
        } else if (scrollX > rightBound) {
            scrollX = rightBound;
        }
        /*
        * Don't lose the rounded component
        * 不要丢失圆角部分
        * */
        mLastMotionX += scrollX - (int) scrollX;
        scrollTo((int) scrollX, getScrollY());
        if (mOnPageChangeListener != null) {
            final int position = (int) scrollX / widthWithMargin;
            final int positionOffsetPixels = (int) scrollX % widthWithMargin;
            final float positionOffset = (float) positionOffsetPixels / widthWithMargin;
            mOnPageChangeListener.onPageScrolled(position, positionOffset,
                    positionOffsetPixels);
        }

        /*
        * Synthesize an event for the VelocityTracker.
        *  为速度跟踪器合成一个事件
        * */
        final long time = SystemClock.uptimeMillis();
        final MotionEvent ev = MotionEvent.obtain(mFakeDragBeginTime, time, MotionEvent.ACTION_MOVE,
                mLastMotionX, 0, 0);
        mVelocityTracker.addMovement(ev);
        ev.recycle();
    }

    /**
     * Returns true if a fake drag is in progress.
     *  如果正在进行假拖动，则返回true
     * @return true if currently in a fake drag, false otherwise. 如果当前是假拖放，为真，否则为假
     * @see #beginFakeDrag() #beginFakeDrag()
     * @see #fakeDragBy(float) #fakeDragBy(float)
     * @see #endFakeDrag() #endFakeDrag()
     */
    public boolean isFakeDragging() {
        return mFakeDragging;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {

            /*
            * This was our active pointer going up. Choose a new
            *  active pointer and adjust accordingly.
            * 这是活动指针向上。选择一个新的
             active指针和相应调整。
            * */

            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastMotionX = MotionEventCompat.getX(ev, newPointerIndex);
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
            if (mVelocityTracker != null) {
                mVelocityTracker.clear();
            }
        }
    }

    private void endDrag() {
        mIsBeingDragged = false;
        mIsUnableToDrag = false;

        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void setScrollingCacheEnabled(boolean enabled) {
        if (mScrollingCacheEnabled != enabled) {
            mScrollingCacheEnabled = enabled;
            if (USE_CACHE) {
                final int size = getChildCount();
                for (int i = 0; i < size; ++i) {
                    final View child = getChildAt(i);
                    if (child.getVisibility() != GONE) {
                        child.setDrawingCacheEnabled(enabled);
                    }
                }
            }
        }
    }

    /**
     * Tests scrollability within child views of v given a delta of dx.
     * 在给定(dx)的情况下测试v的子视图的滚动性。
     * @param v      View to test for horizontal scrollability 查看以测试水平滚动性
     * @param checkV Whether the view v passed should itself be checked for scrollability (true),     是否应该检查所传递的视图v本身是否具有滚动性(true)          or just its children (false).
     * @param dx     Delta scrolled in pixels 以像素为单位滚动的Delta
     * @param x      X coordinate of the active touch point 主动接触点的X坐标
     * @param y      Y coordinate of the active touch point 主动接触点的Y坐标
     * @return true if child views of v can be scrolled by delta of dx. 如果v的子视图可以被(dx)滚动，则为真。
     */
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) v;
            final int scrollX = v.getScrollX();
            final int scrollY = v.getScrollY();
            final int count = group.getChildCount();
            /*
            * Count backwards - let topmost views consume scroll distance first.
            * 向后计数-让最顶端的视图消耗滚动距离第一。
            * */
            for (int i = count - 1; i >= 0; i--) {
                // TODO: Add versioned support here for transformed views.
                /*
                * This will not work for transformed views in Honeycomb+
                * 这在蜂巢+的转换视图中不起作用
                * */
                final View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() &&
                        y + scrollY >= child.getTop() && y + scrollY < child.getBottom() &&
                        canScroll(child, true, dx, x + scrollX - child.getLeft(),
                                y + scrollY - child.getTop())) {
                    return true;
                }
            }
        }

        return checkV && ViewCompat.canScrollHorizontally(v, -dx);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        /*
        * Let the focused view and/or our descendants get the key first
        * 让有焦点的视图和/或我们的后代首先获得密钥
        * */
        return super.dispatchKeyEvent(event) || executeKeyEvent(event);
    }

    /**
     * You can call this function yourself to have the scroll view perform
     * scrolling from a key event, just as if the event had been dispatched to
     * it by the view hierarchy.
     * 您可以自己调用这个函数来执行滚动视图
     * *从一个关键事件滚动，就好像事件已经被分派到
     * *它由视图层次结构。
     * @param event The key event to execute. 要执行的键事件。
     * @return Return true if the event was handled, else false. 如果事件已处理，则返回true，否则为false
     */
    public boolean executeKeyEvent(KeyEvent event) {
        boolean handled = false;
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            int keyCode = event.getKeyCode();
            if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                handled = arrowScroll(FOCUS_LEFT);
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                handled = arrowScroll(FOCUS_RIGHT);
            } else if (keyCode == KeyEvent.KEYCODE_TAB) {
                handled = arrowScroll(FOCUS_FORWARD);
                    /*if (KeyEventCompat.hasNoModifiers(event)) {
                    } else if (KeyEventCompat.hasModifiers(event, KeyEvent.META_SHIFT_ON)) {
                        handled = arrowScroll(FOCUS_BACKWARD);
                    }*/
            }
        }
        return handled;
    }

    /**
     * Arrow scroll boolean.
     * 箭头滚动
     * @param direction the direction 工作方向
     * @return the boolean
     */
    public boolean arrowScroll(int direction) {
        View currentFocused = findFocus();
        if (currentFocused == this) {
            currentFocused = null;
        }

        boolean handled = false;

        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused,
                direction);
        if (nextFocused != null && nextFocused != currentFocused) {
            if (direction == View.FOCUS_LEFT) {
                /*
                * If there is nothing to the left, or this is causing us to
                *  jump to the right, then what we really want to do is page left.
                * 如果左边什么都没有，或者这导致我们
                *跳到右边，然后我们真正想做的是页面左边。
                * */

                if (currentFocused != null && nextFocused.getLeft() >= currentFocused.getLeft()) {
                    handled = pageLeft();
                } else {
                    handled = nextFocused.requestFocus();
                }
            } else if (direction == View.FOCUS_RIGHT) {
                /*
                * If there is nothing to the right, or this is causing us to
                *  jump to the left, then what we really want to do is page right.
                * 如果没有任何东西在右边，或者这导致我们
                *跳到左边，然后我们真正想做的是页面右边
                * */

                if (currentFocused != null && nextFocused.getLeft() <= currentFocused.getLeft()) {
                    handled = pageRight();
                } else {
                    handled = nextFocused.requestFocus();
                }
            }
        } else if (direction == FOCUS_LEFT || direction == FOCUS_BACKWARD) {
            /*
            * Trying to move left and nothing there; try to page.
            * 试图向左移动，却什么也没有;尝试页面。
            * */
            handled = pageLeft();
        } else if (direction == FOCUS_RIGHT || direction == FOCUS_FORWARD) {
            /*
            * Trying to move right and nothing there; try to page.
            * 试着向右移动，却什么也没有;尝试页面。
            * */
            handled = pageRight();
        }
        if (handled) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
        }
        return handled;
    }

    /**
     * Page left boolean.
     * 页面左
     * @return the boolean
     */
    boolean pageLeft() {
        if (mCurItem > 0) {
            setCurrentItem(mCurItem - 1, true);
            return true;
        }
        return false;
    }

    /**
     * Page right boolean.
     * 页面右
     * @return the boolean
     */
    boolean pageRight() {
        if (mAdapter != null && mCurItem < (mAdapter.getCount() - 1)) {
            setCurrentItem(mCurItem + 1, true);
            return true;
        }
        return false;
    }

    /**
     * We only want the current page that is being shown to be focusable.
     * 我们只希望显示的当前页面是可定焦的。
     */
    @Override
    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        final int focusableCount = views.size();

        final int descendantFocusability = getDescendantFocusability();

        if (descendantFocusability != FOCUS_BLOCK_DESCENDANTS) {
            for (int i = 0; i < getChildCount(); i++) {
                final View child = getChildAt(i);
                if (child.getVisibility() == VISIBLE) {
                    ItemInfo ii = infoForChild(child);
                    if (ii != null && ii.position == mCurItem) {
                        child.addFocusables(views, direction, focusableMode);
                    }
                }
            }
        }

        /*
        * we add ourselves (if focusable) in all cases except for when we are
        FOCUS_AFTER_DESCENDANTS and there are some descendants focusable.  this is
        to avoid the focus search finding layouts when a more precise search
        among the focusable children would be more interesting.
        * 除了可调焦的情况外，我们在所有情况下都添加了自己(如果可调焦)
          FOCUS_AFTER_DESCENDANTS，还有一些后代是可调焦的。这是
          避免焦点搜索时查找布局更精确的搜索
          可聚焦的孩子会更有趣。
        * */
        if (
                descendantFocusability != FOCUS_AFTER_DESCENDANTS ||
                        // No focusable descendants
                        (focusableCount == views.size())) {
            /*
            * Note that we can't call the superclass here, because it will
            * add all views in.  So we need to do the same thing View does.
            * 注意，我们不能在这里调用超类，因为它会调用
               添加所有视图。我们需要和视图做同样的事情
            * */

            if (!isFocusable()) {
                return;
            }
            if ((focusableMode & FOCUSABLES_TOUCH_MODE) == FOCUSABLES_TOUCH_MODE &&
                    isInTouchMode() && !isFocusableInTouchMode()) {
                return;
            }
            if (views != null) {
                views.add(this);
            }
        }
    }

    /**
     * We only want the current page that is being shown to be touchable.
     * 我们只希望显示的当前页面是可触摸的。
     */
    @Override
    public void addTouchables(ArrayList<View> views) {
        /*
        * Note that we don't call super.addTouchables(), which means that
         *  we don't call View.addTouchables().  This is okay because a ViewPager
         * is itself not touchable.
         * 注意，我们没有调用super.addTouchables()，这意味着
          我们没有调用View.addTouchables()。这是可以的，因为ViewPager
          *本身是不可触摸的。
        * */

        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                ItemInfo ii = infoForChild(child);
                if (ii != null && ii.position == mCurItem) {
                    child.addTouchables(views);
                }
            }
        }
    }

    /**
     * We only want the current page that is being shown to be focusable.
     * 我们只希望显示的当前页面是可定焦的
     */
    @Override
    protected boolean onRequestFocusInDescendants(int direction,
                                                  Rect previouslyFocusedRect) {
        int index;
        int increment;
        int end;
        int count = getChildCount();
        if ((direction & FOCUS_FORWARD) != 0) {
            index = 0;
            increment = 1;
            end = count;
        } else {
            index = count - 1;
            increment = -1;
            end = -1;
        }
        for (int i = index; i != end; i += increment) {
            View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                ItemInfo ii = infoForChild(child);
                if (ii != null && ii.position == mCurItem) {
                    if (child.requestFocus(direction, previouslyFocusedRect)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        /*
        * ViewPagers should only report accessibility info for the current page,
        * otherwise things get very confusing.
        * ViewPagers应该只报告当前页面的可访问性信息，
            否则事情会变得非常混乱。
        * */


        // TODO: Should this note something about the paging container?

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                final ItemInfo ii = infoForChild(child);
                if (ii != null && ii.position == mCurItem &&
                        child.dispatchPopulateAccessibilityEvent(event)) {
                    return true;
                }
            }
        }

        return false;
    }

    private class PagerObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            dataSetChanged();
        }

        @Override
        public void onInvalidated() {
            dataSetChanged();
        }
    }
}
