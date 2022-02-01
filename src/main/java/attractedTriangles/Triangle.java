package attractedTriangles;

import processing.core.PVector;

public class Triangle {
    private PVector position;
    private float angle;

    private PVector velocity = new PVector(0, 0);
    private final float maximumSpeed;

    public Triangle(PVector position, float angle) {
        this.position = position;
        this.angle = angle;
        maximumSpeed = 10;
    }

    public PVector getPosition() {
        return position;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public PVector getVelocity() {
        return velocity;
    }

    public void setVelocity(PVector velocity) {
        this.velocity = velocity;
    }

    public void setVelocityX(float x) {
        this.velocity.x = x;
    }

    public void setVelocityY(float y) {
        this.velocity.y = y;
    }

    public float getMaximumSpeed() {
        return maximumSpeed;
    }
}
