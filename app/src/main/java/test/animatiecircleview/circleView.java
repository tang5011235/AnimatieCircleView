package test.animatiecircleview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

import com.gcssloop.view.CustomView;
import com.gcssloop.view.utils.CanvasAidUtils;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/2/24.
 */

public class circleView extends CustomView implements CircleAnimate.AnimateHandle {
    private List<ArcDataBean> mArcDataBeans = new ArrayList<>();
    private List<SimpleInfos> mSimpleInfos = new ArrayList<>();
    private float startAngle = 0;
    private float radius = 500;
    private float strokeWidth = 50;
    private ArcDataBean mCurrentArcData;
    private boolean isUserCenter;

    public circleView(Context context) {
        super(context);
        init(context);
    }

    public circleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public circleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        CanvasAidUtils.setDrawAid(true);
        mDeafultPaint.setAntiAlias(true);
        mDeafultPaint.setStrokeWidth(100);
        mDeafultPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘画坐标系
        Log.d(TAG, "init: "+ this.isHardwareAccelerated());
        canvas.save();
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        RectF rectF = new RectF(-radius, -radius, radius, radius);
        CanvasAidUtils.set2DAxisLength(500, 700);
        CanvasAidUtils.draw2DCoordinateSpace(canvas);

        if (mCacheArcBeans.size() > 0) {//绘画缓存圆形
            drawCacheArc(canvas, rectF);
        }
        drawCircle(canvas,rectF);
    }

    private void drawCacheArc(Canvas canvas, RectF rectF) {
        for (ArcDataBean cacheArcBean : mCacheArcBeans) {
            if (cacheArcBean.isDrawed()) {
                mDeafultPaint.setColor(cacheArcBean.getColor());
                canvas.drawArc(rectF,
                        cacheArcBean.getStartAngle(),
                        cacheArcBean.getDrawedAngle(),
                        isUserCenter,
                        mDeafultPaint);
            }
        }
    }


    private void drawCircle(Canvas canvas, RectF rectF) {
        if (mCurrentArcData != null) {
            mDeafultPaint.setColor(mCurrentArcData.getColor());
            canvas.drawArc(rectF, mCurrentArcData.getStartAngle(), mCurrentArcData.getDrawedAngle(), isUserCenter, mDeafultPaint);
            mCacheArcBeans.add(mCurrentArcData);
        }
    }

    public void setSimpleInfos(List<SimpleInfos> simpleInfos) {
        mSimpleInfos = simpleInfos;

        float totalValue = 0;
        for (SimpleInfos simpleInfo : mSimpleInfos) {
            totalValue += simpleInfo.getValue();
        }

        //计算每部分占的角度
        float startAngle = this.startAngle;
        for (SimpleInfos simpleInfo : mSimpleInfos) {
            ArcDataBean arcDataBean = new ArcDataBean();
            float sweepAngle = simpleInfo.getValue() / totalValue * 360f;
            arcDataBean.setStartAngle(startAngle);
            arcDataBean.setEndAngle(Math.abs(arcDataBean.getStartAngle()) + sweepAngle > 359.1 ? 360 :Math.abs(arcDataBean.getStartAngle()) + sweepAngle);
            startAngle = arcDataBean.getEndAngle();
            arcDataBean.setColor(simpleInfo.getColor());

            mArcDataBeans.add(arcDataBean);
        }
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        buildAnimate(mArcDataBeans);
                    }
                }
        ,1000);

    }

    private void buildAnimate(List<ArcDataBean> arcDataBeans) {
        CircleAnimate circleAnimate = new CircleAnimate(arcDataBeans);
        circleAnimate.setDuration(2000);
        circleAnimate.setAnimateHandle(this);
        circleAnimate.setInterpolator(new AccelerateInterpolator());
        circleAnimate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        this.startAnimation(circleAnimate);
    }

    private List<ArcDataBean> mCacheArcBeans = new ArrayList<>();//已经绘画过的圆弧

    /**
     * 1.将即将绘画的圆弧对象起来
     *
     * @param angle
     * @param arcDataBean
     */
    @Override
    public void onAnimateProcessing(float angle, ArcDataBean arcDataBean) {
        mCurrentArcData = arcDataBean;
        invalidate();
    }
}
