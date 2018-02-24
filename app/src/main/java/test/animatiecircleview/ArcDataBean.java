package test.animatiecircleview;

/**
 * Created by Administrator on 2018/2/24.
 */

public class ArcDataBean {
    private float startAngle;
    private float endAngle;
    private float sweppAngle;
    private int color;

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
}
