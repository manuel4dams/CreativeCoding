package circlePacking;

public class Circle {
    private final float positionX;
    private final float positionY;
    private float radius;

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Circle(float positionX, float positionY, float radius) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;
    }

}
