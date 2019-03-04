package com.wenlin.roundbanner.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wenlin.roundbanner.R;
import com.xuezj.cardbanner.ImageData;
import com.xuezj.cardbanner.WeakHandler;
import com.xuezj.cardbanner.adapter.BannerAdapter;
import com.xuezj.cardbanner.adapter.CardAdapter;
import com.xuezj.cardbanner.mode.BaseTransformer;
import com.xuezj.cardbanner.mode.ScaleYTransformer;
import com.xuezj.cardbanner.utils.BannerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 文琳
 * @create 2018/7/12 19:10
 * @email 377979485@qq.com
 * @description
 * @history
 */
public class MyCardBanner extends RelativeLayout {
    private WeakHandler handler = new WeakHandler();
    private Context context;
    private int mainTitleTextColor = 0xFFFFFFFF;
    private int subtitleTitleTextColor = 0xFFFFFFFF;
    private int mainTitleTextSize = 15;
    private int subtitleTitleTextSize = 12;
    private int borderWidth = 20;
    private int radius = 8;
    private int dividerWidth = 0;
    private MyLinearLayoutManager mLayoutManager;
    private PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
    private MyCardView cardView;
    private List<ImageData> datas;
    private BaseTransformer baseTransformer;
    private int viewWidth;
    private MyCardBanner.OnItemClickListener onItemClickListener;
    private BannerAdapter bannerAdapter;
    private boolean isPlay = true;
    private int dataCount = 0;
    private int delayTime = 3000;
    private static final int DEFAULT_SELECTION = Integer.MAX_VALUE >> 2;
    private int currentItem = 0;
    private CardAdapter cardAdapter;
    private LinearLayout mPointRealContainerLl;
    private List<View> mViews;

    public MyCardBanner(Context context) {
        this(context, null);
//        this.context = context;
//        borderWidth = BannerUtils.dp2px(context, 30);
//        radius = BannerUtils.dp2px(context, 8);
//        View view = LayoutInflater.from(context).inflate(R.layout.card_banner, this, true);
//        cardBannerView = (CardBannerView) view.findViewById(R.id.card_view);
    }

    public MyCardBanner(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        this.context = context;
//        initView(attrs);
        this(context, attrs, 0);
    }

    public MyCardBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        typedArray(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.card_banner, this, true);
        viewWidth = context.getResources().getDisplayMetrics().widthPixels;
        cardView = view.findViewById(R.id.card_view);
        baseTransformer = new ScaleYTransformer();
        mLayoutManager = new MyLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        RelativeLayout pointContainerRl = new RelativeLayout(context);
        pointContainerRl.setPadding(DensityUtil.dp2px(12), DensityUtil.dp2px(12), DensityUtil.dp2px(12), DensityUtil.dp2px(12));
        LayoutParams pointContainerLp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // 处理圆点在顶部还是底部
        pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(pointContainerRl, pointContainerLp);
        LayoutParams indicatorLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        indicatorLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mPointRealContainerLl = new LinearLayout(context);
        mPointRealContainerLl.setOrientation(LinearLayout.HORIZONTAL);
        mPointRealContainerLl.setGravity(Gravity.CENTER_VERTICAL);
        pointContainerRl.addView(mPointRealContainerLl, indicatorLp);
    }

    private void typedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.card_banner);
        borderWidth = typedArray.getDimensionPixelSize(R.styleable.card_banner_banner_border_width, BannerUtils.dp2px(context, borderWidth));
        radius = typedArray.getDimensionPixelSize(R.styleable.card_banner_radius, BannerUtils.dp2px(context, radius));
        mainTitleTextColor = typedArray.getColor(R.styleable.card_banner_main_title_text_color, mainTitleTextColor);
        subtitleTitleTextColor = typedArray.getColor(R.styleable.card_banner_subtitle_title_text_color, subtitleTitleTextColor);
        dividerWidth = typedArray.getDimensionPixelSize(R.styleable.card_banner_divider_width, BannerUtils.dp2px(context, dividerWidth)) / 2;
        mainTitleTextSize = BannerUtils.px2sp(context, typedArray.getDimensionPixelSize(R.styleable.card_banner_main_title_text_size, BannerUtils.sp2px(context, mainTitleTextSize)));
        subtitleTitleTextSize = BannerUtils.px2sp(context, typedArray.getDimensionPixelSize(R.styleable.card_banner_subtitle_title_text_size, BannerUtils.sp2px(context, subtitleTitleTextSize)));
        typedArray.recycle();
    }


    public MyCardBanner setDatas(List<ImageData> datas) {
        this.datas = datas;
        this.dataCount = datas.size();
        return this;
    }

    public MyCardBanner setDataCount(int dataCount) {
        this.datas = null;
        this.dataCount = dataCount;
        return this;
    }

    public MyCardBanner setTransformer(BaseTransformer baseTransformer) {
        this.baseTransformer = baseTransformer;
        return this;
    }

    public void setOnItemClickListener(MyCardBanner.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void start() {
        setData();
        if (isPlay) {
            autoPlay();
        }
    }

    public MyCardBanner setPlay(boolean play) {
        isPlay = play;
        return this;
    }

    public MyCardBanner setBannerAdapter(BannerAdapter bannerAdapter) {
        this.bannerAdapter = bannerAdapter;
        return this;
    }

    private void setData() {
        cardView.setLayoutManager(mLayoutManager);
        cardView.setViewMode(baseTransformer);
//        mCircleRecyclerView.setNeedCenterForce(true);
//        mCircleRecyclerView.setNeedLoop(!mIsNotLoop);
        Log.d("CardBanner", "getWidth():" + getWidth());
        pagerSnapHelper.attachToRecyclerView(cardView);
        cardView.setOnCenterItemClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItem((int) v.getTag(com.xuezj.cardbanner.R.id.key_position));
            }
        });
        cardAdapter = new CardAdapter(context, viewWidth, borderWidth, dividerWidth);
        if (datas != null) {
            cardView.setDataCount(datas.size());
            cardAdapter.setDatas(datas);
        } else if (dataCount != 0) {
            cardView.setDataCount(dataCount);
            cardAdapter.setDataCount(dataCount);
            mViews = new ArrayList<>();
            for (int i = 0; i < dataCount; i++) {
                mViews.add(View.inflate(getContext(), R.layout.common_item_banner, null));
            }
            if (mPointRealContainerLl != null) {
                mPointRealContainerLl.removeAllViews();
                if (mViews.size() > 1) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(DensityUtil.dp2px(3), 0, DensityUtil.dp2px(3), 0);
                    ImageView imageView;
                    for (int i = 0; i < mViews.size(); i++) {
                        imageView = new ImageView(getContext());
                        imageView.setLayoutParams(lp);
                        imageView.setImageResource(R.drawable.banner_indicator);
                        mPointRealContainerLl.addView(imageView);
                    }
                }
            }
            if (bannerAdapter != null) {
                cardAdapter.setBannerAdapter(bannerAdapter);
            } else {
                throw new RuntimeException("[CardBanner] --> please set BannerAdapter");
            }
        } else {
            throw new RuntimeException("[CardBanner] --> please set Data or DataCount,but the DataCount More than 0");
        }
        cardAdapter.setTextSize(mainTitleTextSize, subtitleTitleTextSize);
        cardView.setAdapter(cardAdapter);
        cardView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        //滚动停止
                        int position = cardView.getCurrentItem() % dataCount;
                        if (mPointRealContainerLl != null) {
                            if (mViews != null && mViews.size() > 0 && position < mViews.size()) {
                                mPointRealContainerLl.setVisibility(View.VISIBLE);
                                for (int i = 0; i < mPointRealContainerLl.getChildCount(); i++) {
                                    mPointRealContainerLl.getChildAt(i).setEnabled(i == position);
                                    // 处理指示器选中和未选中状态图片尺寸不相等
                                    mPointRealContainerLl.getChildAt(i).requestLayout();
                                }
                            } else {
                                mPointRealContainerLl.setVisibility(View.GONE);
                            }
                        }
                        break;
                    default:
                }
            }
        });
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (dataCount > 1 && isPlay) {
                currentItem = cardView.getCurrentItem() + 1;
                cardView.smoothScrollToPosition(currentItem);
                handler.postDelayed(task, delayTime);
            }
        }
    };

    public void autoPlay() {
        if (isPlay) {
            handler.removeCallbacks(task);
            handler.postDelayed(task, delayTime);
        }
    }

    public void stopPlay() {
        if (isPlay) {
            handler.removeCallbacks(task);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.i(tag, ev.getAction() + "--" + isAutoPlay);
        if (isPlay) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE||action == MotionEvent.ACTION_DOWN) {
                autoPlay();
//            } else if (action == MotionEvent.ACTION_DOWN) {
//                stopPlay();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public interface OnItemClickListener {
        void onItem(int position);
    }
}
