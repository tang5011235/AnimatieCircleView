package test.animatiecircleview;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.util.List;

/**
 * Created by Administrator on 2018/2/24.
 */

public class CircleAnimate extends Animation {
    private List<ArcDataBean> mArcDataBeans;

    public CircleAnimate(List<ArcDataBean> arcDataBeans) {
        mArcDataBeans = arcDataBeans;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        if (interpolatedTime >= 0.0f && interpolatedTime <= 1.0f) {
            float angle = 360 * interpolatedTime;//即将画的角度(包含已经画过的角度)
            for (ArcDataBean arcDataBean : mArcDataBeans) {
                if (arcDataBean.isInRange(angle)) {
                    mAnimateHandle.onAnimateProcessing(angle, arcDataBean);
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
