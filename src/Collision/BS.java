package Collision;

import java.util.Vector;


public class BS extends Shape {
    private Vector<Float> center;
    private float radius;

    public BS(Vector<Float> ballCenter, float ballRadius) {
        setCenter(ballCenter);
        setRadius(ballRadius);
    }

    public Vector<Float> getCenter() {
        return center;
    }

    private void setCenter(Vector<Float> center) {
        this.center = center;
    }

    public float getRadius() {
        return radius;
    }

    private void setRadius(float radius) {
        this.radius = radius;
    }
}
