package bouncingBalls;

import processing.core.PVector;

public class Ball {

    private PVector location;
    private final float radius;

    private PVector velocity = new PVector(0, 0);
    private final float maximumSpeed;

    public Ball(PVector location, float radius) {
        this.location = location;
        this.radius = radius;

        maximumSpeed = 10;
    }

    public PVector getLocation() {
        return location;
    }

    public void setLocation(PVector location) {
        this.location = location;
    }

    public float getRadius() {
        return radius;
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
