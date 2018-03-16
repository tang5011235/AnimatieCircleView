package test.animatiecircleview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.gcssloop.view.CustomView;
import com.gcssloop.view.utils.CanvasAidUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */

public class circleView extends CustomView implements CircleAnimate.AnimateHandle {
    private List<ArcDataBean> mArcDataBeans = new ArrayList<>();
    private List<SimpleInfos> mSimpleInfos = new ArrayList<>();
    private float startAngle = -90;
    private float radius = 500;
    private float strokeWidth = 50;

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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘画坐标系
        canvas.save();
        canvas.translate(mViewWidth / 2, mViewHeight / 2);
        RectF rectF = new RectF(-radius, -radius, radius, radius);
        CanvasAidUtils.set2DAxisLength(500, 700);
        CanvasAidUtils.draw2DCoordinateSpace(canvas);

        if (mCacheArcBeans.size() > 0) {//绘画缓存圆形
            drawCacheArc(canvas, rectF);
        }
        drawCircle(canvas);
    }

    private void drawCacheArc(Canvas canvas, RectF rectF) {
        for (ArcDataBean cacheArcBean : mCacheArcBeans) {
            if (cacheArcBean.isDrawed()) {
                canvas.drawArc(rectF, );
            }
        }
    }


    private void drawCircle(Canvas canvas) {
        canvas.translate(-radius / 2, -radius / 2);
        mDeafultPaint.setStrokeWidth(strokeWidth);
        canvas.drawPoint(0, 0, mDeafultPaint);
        if (mArcDataBeans.size() > 0) {
            for (ArcDataBean arcDataBean : mArcDataBeans) {
                RectF rectF = new RectF(0, 0, radius, radius);
                mDeafultPaint.setColor(arcDataBean.getColor());
                canvas.drawArc(rectF, arcDataBean.getStartAngle(), arcDataBean.getSweppAngle(), true, mDeafultPaint);
            }
        }
        canvas.restore();
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
            arcDataBean.setEndAngle(Math.abs(arcDataBean.getStartAngle()) + sweepAngle);
            startAngle += arcDataBean.getEndAngle();
            arcDataBean.setColor(simpleInfo.getColor());

            mArcDataBeans.add(arcDataBean);
        }

        buildAnimate(mArcDataBeans);
    }

    private void buildAnimate(List<ArcDataBean> arcDataBeans) {
        CircleAnimate circleAnimate = new CircleAnimate(arcDataBeans);
        circleAnimate.setAnimateHandle(this);
        circleAnimate.start();
    }

    private List<ArcDataBean> mCacheArcBeans;//已经绘画过的圆弧

    /**
     * 1.将即将绘画的圆弧对象起来
     *
     * @param angle
     * @param arcDataBean
     */
    @Override
    public void onAnimateProcessing(float angle, ArcDataBean arcDataBean) {
        mCacheArcBeans.add(arcDataBean);
        invalidate();
    }
}
