package shootThemUp;

import processing.core.PImage;

public class SpaceInvader {

    private float positionX;
    private float positionY;
    private float radius;
    private float speed;

    public PImage getInvaderImage() {
        return invaderImage;
    }

    private PImage invaderImage;

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public float getRadius() {
        return radius;
    }

    public SpaceInvader(float positionX, float positionY, float radius, float speed, PImage invaderImage) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;
        this.speed = speed;
        this.invaderImage = invaderImage;
    }

    public void update() {
        positionY = positionY + speed;
    }
}
