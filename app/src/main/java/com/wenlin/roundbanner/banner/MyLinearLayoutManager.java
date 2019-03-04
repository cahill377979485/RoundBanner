package com.wenlin.roundbanner.banner;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 作者：文琳
 * 时间：2017/12/24 15:05
 * 邮箱：377979485@qq.com
 * 说明：
 */
public class MyLinearLayoutManager extends LinearLayoutManager {
    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }

    //系统只将屏幕可见范围之内的元素保存在内存中，在滚动的时候不断的重用这些内存中已经存在的view，而不是新建view）。
    //这个机制在我们这里会导致一个问题，启动应用之后，在屏幕可见范围内，我们只有一张卡片可见（估计作者的屏幕比较小），当我们滚动的时 候，RecyclerView找不到可以重用的view了，它将创建一个新的，因此在滑动到第二个feed的时候就会有一定的延时，但是第二个feed之 后的滚动是流畅的，因为这个时候RecyclerView已经有能重用的view了。
    //根据官方文档的描述 getExtraLayoutSpace将返回LayoutManager应该预留的额外空间（显示范围之外，应该额外缓存的空间）。
//    @Override
//    protected int getExtraLayoutSpace(RecyclerView.State state) {
//        return Values.EXTRA_LAYOUT_SPACE;
//    }
//
//    @Override
//    public void setInitialPrefetchItemCount(int itemCount) {
//        super.setInitialPrefetchItemCount(itemCount);
//    }
//
//    @Override
//    public void collectAdjacentPrefetchPositions(int dx, int dy, RecyclerView.State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
//        super.collectAdjacentPrefetchPositions(dx, dy, state, layoutPrefetchRegistry);
//    }
//
//    @Override
//    public void collectInitialPrefetchPositions(int adapterItemCount, LayoutPrefetchRegistry layoutPrefetchRegistry) {
//        super.collectInitialPrefetchPositions(adapterItemCount, layoutPrefetchRegistry);
//    }
}
