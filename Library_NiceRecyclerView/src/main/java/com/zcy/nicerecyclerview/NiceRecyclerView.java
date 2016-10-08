package com.zcy.nicerecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zcy.nicerecyclerview.adapter.HeaderAndFooterWrapper;
import com.zcy.nicerecyclerview.adapter.LoadMoreWrapper;
import com.zcy.nicerecyclerview.swipe.OnSwipeMenuItemClickListener;
import com.zcy.nicerecyclerview.swipe.SwipeMenuCreator;
import com.zcy.nicerecyclerview.swipe.SwipeMenuRecyclerView;
import com.zcy.nicerecyclerview.swipe.SwipeRefreshLayout;
import com.zcy.nicerecyclerview.swipe.touch.OnItemMoveListener;
import com.zcy.nicerecyclerview.swipe.touch.OnItemMovementListener;
import com.zcy.nicerecyclerview.swipe.touch.OnItemStateChangedListener;

/**
 * Created by zcy on 2016/10/8.
 */

public class NiceRecyclerView extends FrameLayout {
    public static final String TAG = "NiceRecyclerView";
    public static boolean DEBUG = false;
    protected SwipeMenuRecyclerView mRecycler;
    protected ViewGroup mProgressView;
    protected ViewGroup mEmptyView;
    protected ViewGroup mErrorView;
    private int mProgressId;
    private int mEmptyId;
    private int mErrorId;
    protected boolean mClipToPadding;
    protected int mPadding;
    protected int mPaddingTop;
    protected int mPaddingBottom;
    protected int mPaddingLeft;
    protected int mPaddingRight;
    protected int mScrollbarStyle;
    protected int mScrollbar;
    private boolean hasHeaderOrFooter;
    private boolean canLoadMore;
    private RecyclerView.Adapter mInnerAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mInnerAdapter);
    private LoadMoreWrapper mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
    protected RecyclerView.OnScrollListener mInternalOnScrollListener;
    protected RecyclerView.OnScrollListener mExternalOnScrollListener;

    protected SwipeRefreshLayout mPtrLayout;
    protected android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener mRefreshListener;


    public SwipeRefreshLayout getSwipeToRefresh() {
        return mPtrLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecycler;
    }

    public NiceRecyclerView(Context context) {
        super(context);
        initView();
    }

    public void setSwipeMenuCreator(SwipeMenuCreator swipeMenuCreator) {
        mRecycler.setSwipeMenuCreator(swipeMenuCreator);
    }

    public void setSwipeMenuItemClickListener(OnSwipeMenuItemClickListener swipeMenuItemClickListener) {
        mRecycler.setSwipeMenuItemClickListener(swipeMenuItemClickListener);
    }

    /**
     * open menu on right.
     *
     * @param position position.
     */
    public void smoothOpenRightMenu(int position) {
        mRecycler.smoothOpenRightMenu(position);
    }


    /**
     * open menu on right.
     *
     * @param position position.
     * @param duration time millis.
     */
    public void smoothOpenRightMenu(int position, int duration) {
        mRecycler.smoothOpenRightMenu(position, duration);
    }


    /**
     * open menu on left.
     *
     * @param position position.
     */
    public void smoothOpenLeftMenu(int position) {
        mRecycler.smoothOpenLeftMenu(position);
    }

    /**
     * open menu on left.
     *
     * @param position position.
     * @param duration time millis.
     */
    public void smoothOpenLeftMenu(int position, int duration) {
        mRecycler.smoothOpenLeftMenu(position, duration);
    }

    /**
     * Close menu.
     */
    public void smoothCloseMenu() {
        mRecycler.smoothCloseMenu();
    }

    public void smoothOpenMenu(int position, @SwipeMenuRecyclerView.DirectionMode int direction, int duration) {
        mRecycler.smoothOpenMenu(position, direction, duration);

    }

    public void setLongPressDragEnabled(boolean canDrag) {
        mRecycler.setLongPressDragEnabled(canDrag);
    }

    public void setOnItemMoveListener(OnItemMoveListener onItemMoveListener) {
        mRecycler.setOnItemMoveListener(onItemMoveListener);
    }

    public void setItemViewSwipeEnabled(boolean canSwipe) {
        mRecycler.setItemViewSwipeEnabled(canSwipe);
    }

    public void setOnItemStateChangedListener(OnItemStateChangedListener onItemStateChangedListener) {
        mRecycler.setOnItemStateChangedListener(onItemStateChangedListener);
    }

    public void setOnItemMovementListener(OnItemMovementListener onItemMovementListener) {
        mRecycler.setOnItemMovementListener(onItemMovementListener);
    }

    /**
     * @deprecated Use {@link #setProgressBackgroundColorSchemeResource(int)}
     */
    @Deprecated
    public void setProgressBackgroundColor(int colorRes) {
        mPtrLayout.setProgressBackgroundColorSchemeResource(colorRes);
    }

    /**
     * Set the background color of the progress spinner disc.
     *
     * @param colorRes Resource id of the color.
     */
    public void setProgressBackgroundColorSchemeResource(@ColorRes int colorRes) {
        mPtrLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(colorRes));
    }

    /**
     * Set the background color of the progress spinner disc.
     *
     * @param color
     */
    public void setProgressBackgroundColorSchemeColor(@ColorInt int color) {
        mPtrLayout.setProgressBackgroundColor(color);
    }

    /**
     * @deprecated Use {@link #setColorSchemeResources(int...)}
     */
    @Deprecated
    public void setColorScheme(@ColorInt int... colors) {
        mPtrLayout.setColorSchemeResources(colors);
    }

    /**
     * Set the color resources used in the progress animation from color resources.
     * The first color will also be the color of the bar that grows in response
     * to a user swipe gesture.
     *
     * @param colorResIds
     */
    public void setColorSchemeResources(@ColorRes int... colorResIds) {
        mPtrLayout.setColorSchemeResources(colorResIds);
    }

    /**
     * Set the colors used in the progress animation. The first
     * color will also be the color of the bar that grows in response to a user
     * swipe gesture.
     *
     * @param colors
     */
    public void setColorSchemeColors(@ColorInt int... colors) {
        mPtrLayout.setColorSchemeColors(colors);
    }


    public void setHasFixedSize(boolean hasFixedSize) {
        mRecycler.setHasFixedSize(hasFixedSize);
    }

    public NiceRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView();
    }

    public NiceRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs);
        initView();
    }

    protected void initAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NiceRecyclerView);
        try {
            mClipToPadding = a.getBoolean(R.styleable.NiceRecyclerView_recyclerClipToPadding, false);
            mPadding = (int) a.getDimension(R.styleable.NiceRecyclerView_recyclerPadding, -1.0f);
            mPaddingTop = (int) a.getDimension(R.styleable.NiceRecyclerView_recyclerPaddingTop, 0.0f);
            mPaddingBottom = (int) a.getDimension(R.styleable.NiceRecyclerView_recyclerPaddingBottom, 0.0f);
            mPaddingLeft = (int) a.getDimension(R.styleable.NiceRecyclerView_recyclerPaddingLeft, 0.0f);
            mPaddingRight = (int) a.getDimension(R.styleable.NiceRecyclerView_recyclerPaddingRight, 0.0f);
            mScrollbarStyle = a.getInteger(R.styleable.NiceRecyclerView_scrollbarStyle, -1);
            mScrollbar = a.getInteger(R.styleable.NiceRecyclerView_scrollbars, -1);

            mEmptyId = a.getResourceId(R.styleable.NiceRecyclerView_layout_empty, 0);
            mProgressId = a.getResourceId(R.styleable.NiceRecyclerView_layout_progress, 0);
            mErrorId = a.getResourceId(R.styleable.NiceRecyclerView_layout_error, 0);
        } finally {
            a.recycle();
        }
    }

    private void initView() {
        if (isInEditMode()) {
            return;
        }
        //生成主View
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_nice_recyclerview, this);
        mPtrLayout = (SwipeRefreshLayout) v.findViewById(R.id.ptr_layout);
        mPtrLayout.setEnabled(false);
        mProgressView = (ViewGroup) v.findViewById(R.id.progress);
        if (mProgressId != 0) LayoutInflater.from(getContext()).inflate(mProgressId, mProgressView);
        mEmptyView = (ViewGroup) v.findViewById(R.id.empty);
        if (mEmptyId != 0) LayoutInflater.from(getContext()).inflate(mEmptyId, mEmptyView);
        mErrorView = (ViewGroup) v.findViewById(R.id.error);
        if (mErrorId != 0) LayoutInflater.from(getContext()).inflate(mErrorId, mErrorView);
        initRecyclerView(v);
        setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.green_normal,
                R.color.purple_pressed
        );
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mPtrLayout.dispatchTouchEvent(ev);
    }

    /**
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setRecyclerPadding(int left, int top, int right, int bottom) {
        this.mPaddingLeft = left;
        this.mPaddingTop = top;
        this.mPaddingRight = right;
        this.mPaddingBottom = bottom;
        mRecycler.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
    }

    public void setClipToPadding(boolean isClip) {
        mRecycler.setClipToPadding(isClip);
    }


    public void setEmptyView(View emptyView) {
        mEmptyView.removeAllViews();
        mEmptyView.addView(emptyView);
    }

    public void setProgressView(View progressView) {
        mProgressView.removeAllViews();
        mProgressView.addView(progressView);
    }


    public void setErrorView(View errorView) {
        mErrorView.removeAllViews();
        mErrorView.addView(errorView);
    }

    public void setEmptyView(int emptyView) {
        mEmptyView.removeAllViews();
        LayoutInflater.from(getContext()).inflate(emptyView, mEmptyView);
    }

    public void setProgressView(int progressView) {
        mProgressView.removeAllViews();
        LayoutInflater.from(getContext()).inflate(progressView, mProgressView);
    }

    public void setErrorView(int errorView) {
        mErrorView.removeAllViews();
        LayoutInflater.from(getContext()).inflate(errorView, mErrorView);
    }

    public void scrollToPosition(int position) {
        getRecyclerView().scrollToPosition(position);
    }

    /**
     * Implement this method to customize the AbsListView
     */
    protected void initRecyclerView(View view) {
        mRecycler = (SwipeMenuRecyclerView) view.findViewById(android.R.id.list);
        setItemAnimator(null);
        if (mRecycler != null) {
            mRecycler.setHasFixedSize(true);
            mRecycler.setClipToPadding(mClipToPadding);
            mInternalOnScrollListener = new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (mExternalOnScrollListener != null)
                        mExternalOnScrollListener.onScrolled(recyclerView, dx, dy);

                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (mExternalOnScrollListener != null)
                        mExternalOnScrollListener.onScrollStateChanged(recyclerView, newState);

                }
            };
            mRecycler.addOnScrollListener(mInternalOnScrollListener);

            if (mPadding != -1.0f) {
                mRecycler.setPadding(mPadding, mPadding, mPadding, mPadding);
            } else {
                mRecycler.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
            }
            if (mScrollbarStyle != -1) {
                mRecycler.setScrollBarStyle(mScrollbarStyle);
            }
            switch (mScrollbar) {
                case 0:
                    setVerticalScrollBarEnabled(false);
                    break;
                case 1:
                    setHorizontalScrollBarEnabled(false);
                    break;
                case 2:
                    setVerticalScrollBarEnabled(false);
                    setHorizontalScrollBarEnabled(false);
                    break;
            }
        }
    }

    @Override
    public void setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled) {
        mRecycler.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
    }

    @Override
    public void setHorizontalScrollBarEnabled(boolean horizontalScrollBarEnabled) {
        mRecycler.setHorizontalScrollBarEnabled(horizontalScrollBarEnabled);
    }

    /**
     * Set the layout manager to the recycler
     *
     * @param manager
     */
    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecycler.setLayoutManager(manager);
    }


    public static class EasyDataObserver extends RecyclerView.AdapterDataObserver {
        private NiceRecyclerView recyclerView;

        public EasyDataObserver(NiceRecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            update();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            update();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            update();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            update();
        }

        @Override
        public void onChanged() {
            super.onChanged();
            update();
        }

        //自动更改Container的样式
        private void update() {
            log("update");
            int count = recyclerView.getAdapter().getItemCount();
            if (count == 0) {
                log("no data:" + "show empty");
                recyclerView.showEmpty();
            } else {
                log("has data");
                recyclerView.showRecycler();
            }
        }
    }

    /**
     * 设置适配器，关闭所有副view。展示recyclerView
     * 适配器有更新，自动关闭所有副view。根据条数判断是否展示EmptyView
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        this.mInnerAdapter = adapter;
        mHeaderAndFooterWrapper.setInnerAdapter(mInnerAdapter);
        mLoadMoreWrapper.setInnerAdapter(mHeaderAndFooterWrapper);
        mRecycler.setAdapter(mLoadMoreWrapper);
        mLoadMoreWrapper.registerAdapterDataObserver(new EasyDataObserver(this));
        showRecycler();
    }

    public void addLoadMoreView(View loadMoreView) {
        if (null == loadMoreView) {
            throw new NullPointerException("can not add a null project \n" +
                    "reference to this SuperRecyclerView");
        }
        canLoadMore = true;
        mHeaderAndFooterWrapper.setInnerAdapter(mInnerAdapter);
        mLoadMoreWrapper.setLoadMoreView(loadMoreView);
        mRecycler.setAdapter(mLoadMoreWrapper);
        mLoadMoreWrapper.notifyDataSetChanged();
    }

    public void addLoadMoreView(int layoutId) {
        addLoadMoreView(LayoutInflater.from(getContext()).inflate(layoutId, null));
    }

    public void notifyItemMoved(int fromPosition, int toPosition) {
        mLoadMoreWrapper.notifyItemMoved(fromPosition, toPosition);
    }

    public void notifyItemRemoved(int position) {
        mLoadMoreWrapper.notifyItemRemoved(position);
    }

    public void notifyDataSetChanged() {
        mLoadMoreWrapper.notifyDataSetChanged();
    }


    public int getHeadersCount() {
        return mHeaderAndFooterWrapper.getHeadersCount();
    }

    public int getFootersCount() {
        return mHeaderAndFooterWrapper.getFootersCount();
    }

    public void addHeaderView(View headerView) {
        if (null == headerView) {
            throw new NullPointerException("can not add a null project \n" +
                    "reference to this SuperRecyclerView");
        }
        hasHeaderOrFooter = true;
        mHeaderAndFooterWrapper.setInnerAdapter(mInnerAdapter);
        mHeaderAndFooterWrapper.addHeaderView(headerView);
        mLoadMoreWrapper.setInnerAdapter(mHeaderAndFooterWrapper);
        mRecycler.setAdapter(mLoadMoreWrapper);
    }

    public void addHeaderView(int layoutId) {
        addHeaderView(LayoutInflater.from(getContext()).inflate(layoutId, null));
    }

    public void addFooterView(View footerView) {
        if (null == footerView) {
            throw new NullPointerException("can not add a null project \n" +
                    "reference to this SuperRecyclerView");
        }
        hasHeaderOrFooter = true;
        mHeaderAndFooterWrapper.setInnerAdapter(mInnerAdapter);
        mHeaderAndFooterWrapper.addFootView(footerView);
        mLoadMoreWrapper.setInnerAdapter(mHeaderAndFooterWrapper);
        mRecycler.setAdapter(mLoadMoreWrapper);
    }

    public void addFooterView(int layoutId) {
        addFooterView(LayoutInflater.from(getContext()).inflate(layoutId, null));
    }

    public void setOnLoadMoreListener(LoadMoreWrapper.OnLoadMoreListener loadMoreListener) {
        mLoadMoreWrapper.setOnLoadMoreListener(loadMoreListener);
    }

    /**
     * 设置适配器，关闭所有副view。展示进度条View
     * 适配器有更新，自动关闭所有副view。根据条数判断是否展示EmptyView
     *
     * @param adapter
     */
    public void setAdapterWithProgress(RecyclerView.Adapter adapter) {
        this.mInnerAdapter = adapter;
        mHeaderAndFooterWrapper.setInnerAdapter(mInnerAdapter);
        mLoadMoreWrapper.setInnerAdapter(mHeaderAndFooterWrapper);
        mRecycler.setAdapter(mLoadMoreWrapper);
        mLoadMoreWrapper.registerAdapterDataObserver(new EasyDataObserver(this));
        //只有mLoadMoreWrapper为空时才显示ProgressView
        if (mLoadMoreWrapper.getItemCount() == 0) {
            showProgress();
        } else {
            showRecycler();
        }
    }

    /**
     * Remove the adapter from the recycler
     */
    public void clear() {
        mRecycler.setAdapter(null);
    }


    private void hideAll() {
        mEmptyView.setVisibility(View.GONE);
        mProgressView.setVisibility(View.GONE);
        mErrorView.setVisibility(GONE);
        mPtrLayout.setRefreshing(false);
        mRecycler.setVisibility(View.INVISIBLE);
    }


    public void showError() {
        log("showError");
        if (mErrorView.getChildCount() > 0) {
            hideAll();
            mErrorView.setVisibility(View.VISIBLE);
        } else {
            showRecycler();
        }

    }

    public void showEmpty() {
        log("showEmpty");
        if (mEmptyView.getChildCount() > 0) {
            hideAll();
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            showRecycler();
        }
    }


    public void showProgress() {
        log("showProgress");
        if (mProgressView.getChildCount() > 0) {
            hideAll();
            mProgressView.setVisibility(View.VISIBLE);
        } else {
            showRecycler();
        }
    }

    /**
     * 当前是否在进行刷新操作
     *
     * @return
     */
    public boolean isRefreshing() {
        return mPtrLayout.isRefreshing();
    }

    public void showRecycler() {
        log("showRecycler");
        hideAll();
        mRecycler.setVisibility(View.VISIBLE);
    }


    /**
     * Set the listener when refresh is triggered and enable the SwipeRefreshLayout
     *
     * @param listener
     */
    public void setRefreshListener(android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener listener) {
        mPtrLayout.setEnabled(true);
        mPtrLayout.setOnRefreshListener(listener);
        this.mRefreshListener = listener;
    }

    public void setRefreshing(final boolean isRefreshing) {
        mPtrLayout.post(new Runnable() {
            @Override
            public void run() {
                mPtrLayout.setRefreshing(isRefreshing);
            }
        });
    }

    public void setRefreshing(final boolean isRefreshing, final boolean isCallbackListener) {
        mPtrLayout.post(new Runnable() {
            @Override
            public void run() {
                mPtrLayout.setRefreshing(isRefreshing);
                if (isRefreshing && isCallbackListener && mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
            }
        });
    }

    /**
     * Set the colors for the SwipeRefreshLayout states
     *
     * @param colRes
     */
    public void setRefreshingColorResources(@ColorRes int... colRes) {
        mPtrLayout.setColorSchemeResources(colRes);
    }

    /**
     * Set the colors for the SwipeRefreshLayout states
     *
     * @param col
     */
    public void setRefreshingColor(int... col) {
        mPtrLayout.setColorSchemeColors(col);
    }

    /**
     * Set the scroll listener for the recycler
     *
     * @param listener
     */
    public void setOnScrollListener(RecyclerView.OnScrollListener listener) {
        mExternalOnScrollListener = listener;
    }

    /**
     * Add the onItemTouchListener for the recycler
     *
     * @param listener
     */
    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecycler.addOnItemTouchListener(listener);
    }

    /**
     * Remove the onItemTouchListener for the recycler
     *
     * @param listener
     */
    public void removeOnItemTouchListener(RecyclerView.OnItemTouchListener listener) {
        mRecycler.removeOnItemTouchListener(listener);
    }

    /**
     * @return the recycler adapter
     */
    public RecyclerView.Adapter getAdapter() {
        return mRecycler.getAdapter();
    }


    public void setOnTouchListener(OnTouchListener listener) {
        mRecycler.setOnTouchListener(listener);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        mRecycler.setItemAnimator(animator);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecycler.addItemDecoration(itemDecoration);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration, int index) {
        mRecycler.addItemDecoration(itemDecoration, index);
    }

    public void removeItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecycler.removeItemDecoration(itemDecoration);
    }


    /**
     * @return inflated error view or null
     */
    public View getErrorView() {
        if (mErrorView.getChildCount() > 0) return mErrorView.getChildAt(0);
        return null;
    }

    /**
     * @return inflated progress view or null
     */
    public View getProgressView() {
        if (mProgressView.getChildCount() > 0) return mProgressView.getChildAt(0);
        return null;
    }


    /**
     * @return inflated empty view or null
     */
    public View getEmptyView() {
        if (mEmptyView.getChildCount() > 0) return mEmptyView.getChildAt(0);
        return null;
    }

    private static void log(String content) {
        if (DEBUG) {
            Log.i(TAG, content);
        }
    }

}
