package com.wenlin.roundbanner.diamond;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.wenlin.roundbanner.R;
import com.wenlin.roundbanner.uitl.Checker;

/**
 * @author 文琳
 * @time 2020/5/7 11:53
 * @desc 仿山海镜花过场动画
 */
public class DiamondCardView extends SurfaceView {
    //32张图，按照顺序排列好，不重复的原始图片有8张。
    private int[] redIds = {R.mipmap.card_4,
            R.mipmap.card_1, R.mipmap.card_7, R.mipmap.card_1,
            R.mipmap.card_3, R.mipmap.card_8, R.mipmap.card_4, R.mipmap.card_6, R.mipmap.card_4,
            R.mipmap.card_1, R.mipmap.card_7, R.mipmap.card_2, R.mipmap.card_7, R.mipmap.card_3, R.mipmap.card_5, R.mipmap.card_1,
            R.mipmap.card_4, R.mipmap.card_6, R.mipmap.card_1, R.mipmap.card_6, R.mipmap.card_2, R.mipmap.card_8, R.mipmap.card_4,
            R.mipmap.card_1, R.mipmap.card_5, R.mipmap.card_4, R.mipmap.card_5, R.mipmap.card_1,
            R.mipmap.card_4, R.mipmap.card_8, R.mipmap.card_3,
            R.mipmap.card_1};
    private int viewWidth, viewHeight;
    private float space;//圆心处同一水平线的左右相邻菱形的内接圆圆心间距，就是图片的宽度
    private static final int SHOW_ANIM = 1;//显示动画
    private static final int HIDE_ANIM = 2;//隐藏动画
    private int nowAnim = SHOW_ANIM;//当前动画
    private Paint paint;
    private Diamond[] diamonds = new Diamond[32];
    private boolean runAnim = false;
    private SurfaceHolder surfaceHolder;
    private boolean baseDataInitAlready;
    private static final int ANIM_MAX = 100;
    private static final int ALPHA_MAX = 255;
    private static final int CHANGE_PICS = 6;
    private int currentIndex = 0;
    private final int ANIM_CHANGE = 2;
    private final int ALPHA_CHANGE = 5;

    public DiamondCardView(Context context) {
        this(context, null, 0, 0);
    }

    public DiamondCardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public DiamondCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DiamondCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        surfaceHolder = getHolder();
        setZOrderOnTop(true);
        setKeepScreenOn(true);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);//背景透明
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
    }

    private Runnable animRunnable = () -> {
        try {
            while (runAnim) {
                Thread.sleep(2);
                flush();
                draw();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    private void flush() {
        if (nowAnim == SHOW_ANIM) {//逐行显示出来
            Diamond firstDiamond = diamonds[0];
            if (firstDiamond.getAnimPercent() < ANIM_MAX) {
                firstDiamond.addAnimPercent();
                firstDiamond.addAlpha();
            }
            Diamond currentDiamond = diamonds[currentIndex];
            if (currentIndex > 0 && currentDiamond.getAnimPercent() < ANIM_MAX) {
                currentDiamond.addAnimPercent();
                currentDiamond.addAlpha();
            } else {
                currentIndex = currentIndex + 1;
                return;
            }
            for (int i = 0; i < CHANGE_PICS; i++) {
                if (currentIndex + i < diamonds.length && diamonds[currentIndex].getAnimPercent() > ANIM_MAX / CHANGE_PICS) {
                    Diamond d = diamonds[currentIndex + i];
                    d.addAnimPercent();
                    d.addAlpha();
                }
            }
            //当最后一个菱形显示完全时，切换模式，让其逐个消失
            if (diamonds[diamonds.length - 1].getAnimPercent() >= (ANIM_MAX - ANIM_CHANGE)) {
                nowAnim = HIDE_ANIM;
                LogUtils.e("flush->HIDE_ANIM");
            }
        } else {
            Diamond lastDiamond = diamonds[diamonds.length - 1];
            if (lastDiamond.getAnimPercent() > 0) {
                lastDiamond.subAnimPercent();
                lastDiamond.subAlpha();
            }
            Diamond currentDiamond = diamonds[currentIndex];
            if (currentIndex < diamonds.length - 1 && currentDiamond.getAnimPercent() > 0) {
                currentDiamond.subAnimPercent();
                currentDiamond.subAlpha();
            } else {
                currentIndex = currentIndex - 1;
                return;
            }
            for (int i = 0; i < CHANGE_PICS; i++) {
                if (currentIndex - i > 0 && diamonds[currentIndex].getAnimPercent() > ANIM_MAX / CHANGE_PICS) {
                    Diamond d = diamonds[currentIndex - i];
                    d.subAnimPercent();
                    d.subAlpha();
                }
            }
            //当最后一个菱形显示完全时，切换模式，让其逐个消失
            if (diamonds[0].getAnimPercent() <= ANIM_CHANGE) {
                nowAnim = SHOW_ANIM;
                LogUtils.e("flush->SHOW_ANIM");
            }
        }
    }

    private void draw() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas == null) return;
        for (int i = 0; i < diamonds.length; i++) {
            diamonds[i].drawDiamond(canvas);
        }
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    public boolean startOpenAnim() {
        if (runAnim || !baseDataInitAlready) {
            return false;
        }
        runAnim = true;
        new Thread(animRunnable).start();
        setVisibility(View.VISIBLE);
        return true;
    }

    public boolean startCloseAnim() {
        nowAnim = HIDE_ANIM;
        return startOpenAnim();
    }

    public void stopAnim() {
        runAnim = false;
        nowAnim = SHOW_ANIM;
//        setVisibility(View.GONE);//todo
        resetDiamonds();
    }

    private void initDiamonds() {
        float firstCenterX = -space / 3;
        float firstCenterY = viewHeight;
        Point[] points = DiamondUtil.getDiamondsCenterPoints(firstCenterX, firstCenterY, space);
        for (int i = 0; i < diamonds.length; i++) {
            diamonds[i] = new Diamond(i, points[i], redIds[i], 0, 0);
        }
        LogUtils.e("initDiamonds=" + diamonds.length);
    }

    private void resetDiamonds() {
        if (diamonds == null || diamonds.length == 0) return;
        for (Diamond diamond : diamonds) {
            diamond.setAnimPercent(0);
            diamond.setAlpha(0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight = getMeasuredHeight();
        viewWidth = getMeasuredWidth();
//        float radio = 448 / 774;
//        if (viewWidth / viewHeight < radio) {
//            viewHeight = (int) (viewWidth / radio);
//        }
        if (!baseDataInitAlready && viewWidth != 0 && viewHeight != 0) {
            space = (float) viewWidth / 14 * 6;
            initDiamonds();
            baseDataInitAlready = true;
        }
    }

    private class Diamond {
        private int position;
        private Point centerPoint;
        private int resId;
        private int animPercent;
        private int alpha;

        private Bitmap bitmap;

        public void drawDiamond(Canvas canvas) {
            if (Checker.isNull(canvas)) return;
//            if (nowAnim == HIDE_ANIM && animPercent <= 0 || nowAnim == HIDE_ANIM && alpha >= ALPHA_MAX)//|| nowAnim == HIDE_ANIM && animPercent == ANIM_MAX
//                return;
//            if (nowAnim == SHOW_ANIM && animPercent >= ANIM_MAX || nowAnim == SHOW_ANIM && alpha <= 0)// || nowAnim == SHOW_ANIM && animPercent == 0
//                return;
            paint.setAlpha(alpha);
            if (bitmap == null) {
                bitmap = BitmapFactory.decodeResource(getResources(), resId);
            }
            int left, right;
            if (nowAnim == SHOW_ANIM) {
                left = (int) (centerPoint.getX() - (space / 2) * (Math.min(ANIM_MAX, animPercent + 75)) / ANIM_MAX);
                right = (int) (centerPoint.getX() + (space / 2) * (Math.min(ANIM_MAX, animPercent + 75)) / ANIM_MAX);
            } else {
                left = (int) (centerPoint.getX() - (space / 2) * (Math.max(ANIM_MAX - 25, animPercent)) / ANIM_MAX);
                right = (int) (centerPoint.getX() + (space / 2) * (Math.max(ANIM_MAX - 25, animPercent)) / ANIM_MAX);
            }
            int top = (int) (centerPoint.getY() - space / 2);
            int bottom = (int) (centerPoint.getY() + space / 2);
            Rect rect = new Rect(left, top, right, bottom);
            canvas.drawBitmap(bitmap, null, rect, paint);
//            canvas.drawBitmap(bitmap, centerPoint.getX() - space / 2, centerPoint.getY() - space / 2, paint);
        }

        public Diamond(int position, Point centerPoint, int resId, int animPercent, int alpha) {
            this.position = position;
            this.centerPoint = centerPoint;
            this.resId = resId;
            this.animPercent = animPercent;
            this.alpha = alpha;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void addAnimPercent() {
            if (animPercent == ANIM_MAX) return;
            animPercent += ANIM_CHANGE;
            animPercent = Math.min(animPercent, ANIM_MAX);
        }

        public void subAnimPercent() {
            if (animPercent == 0) return;
            animPercent -= ANIM_CHANGE;
            animPercent = Math.max(animPercent, 0);
        }

        public void addAlpha() {
            if (alpha == ALPHA_MAX) {
                return;
            }
            alpha += ALPHA_CHANGE;
            alpha = Math.min(alpha, ALPHA_MAX);
        }

        public void subAlpha() {
            if (alpha == 0) {
                return;
            }
            alpha -= ALPHA_CHANGE;
            alpha = Math.max(alpha, 0);
        }

        public int getAlpha() {
            return alpha;
        }

        public void setAlpha(int alpha) {
            this.alpha = alpha;
        }

        public Point getCenterPoint() {
            return centerPoint;
        }

        public void setCenterPoint(Point centerPoint) {
            this.centerPoint = centerPoint;
        }

        public int getResId() {
            return resId;
        }

        public void setResId(int resId) {
            this.resId = resId;
        }

        public int getAnimPercent() {
            return animPercent;
        }

        public void setAnimPercent(int animPercent) {
            this.animPercent = animPercent;
        }
    }
}
