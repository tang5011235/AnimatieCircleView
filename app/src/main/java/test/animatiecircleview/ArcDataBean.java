package test.animatiecircleview;

/**
 * Created by Administrator on 2018/2/24.
 */

public class ArcDataBean {
    private float startAngle;
    private float endAngle;
    private float sweppAngle;
    private int color;
    private float restOfAngle;
    private boolean isDrawed;
    private float drawedAngle;

    public float getDrawedAngle() {
        return drawedAngle;
    }

    public float getRestOfAngle() {
        return restOfAngle;
    }

    public boolean isDrawed() {
        return isDrawed;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(float endAngle) {
        this.endAngle = endAngle;
    }

    public float getSweppAngle() {
        return endAngle - startAngle;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    /**
     * 判断该角度在这个范围里面
     */
    public boolean isInRange(float startAngle) {

        if (this.startAngle < startAngle) {
            isDrawed = true;
            drawedAngle = startAngle - this.startAngle;
        }

        if (this.startAngle <= startAngle && startAngle <= endAngle) {
            restOfAngle = endAngle - startAngle;
            return true;
        }
        return false;
    }
}
