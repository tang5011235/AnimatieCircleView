package test.animatiecircleview;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */

public class CircleAnimate extends Animation {
    private List<ArcDataBean> mArcDataBeans;
    private ArcDataBean mLastArcData;

    public CircleAnimate(List<ArcDataBean> arcDataBeans) {
        mArcDataBeans = arcDataBeans;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        if (interpolatedTime >= 0.0f && interpolatedTime <= 1.0f) {
            float angle = 360 * interpolatedTime;//即将画的角度(包含已经画过的角度)
            Log.d("circleANIMATTE=====", ": " + angle);

            if (mLastArcData != null && mLastArcData.isInRange(angle)) {//如果上一个为画完
                mAnimateHandle.onAnimateProcessing(angle, mLastArcData);
                return;
            }
            for (ArcDataBean arcDataBean : mArcDataBeans) {
                if (arcDataBean.isInRange(angle)) {
                    Log.d("circleANIMATTE=====", "applyTransformation: " + angle);
                    mLastArcData = arcDataBean;
                    mAnimateHandle.onAnimateProcessing(angle, arcDataBean);
                    return;
                }
            }
        }
    }

    private AnimateHandle mAnimateHandle;

    public void setAnimateHandle(AnimateHandle animateHandle) {
        mAnimateHandle = animateHandle;
    }

    public interface AnimateHandle {
        void onAnimateProcessing(float angle, ArcDataBean arcDataBean);
    }
}
