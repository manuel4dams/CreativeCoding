package bouncingBalls;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class BouncingBalls extends PApplet {

    private final ArrayList<Ball> balls = new ArrayList<>();

    public static void main(String... args) {
        PApplet.main("bouncingBalls.BouncingBalls");
    }

    public void settings() {
        size(1024, 1024);
    }

    public void setup() {
        background(255);
        strokeWeight(2);
        addBalls(60);
    }

    public void draw() {
        background(255);

        updateBalls();
        drawBalls();
        drawMouse();
    }

    private void addBalls(int numberOfBalls) {
        for (int i = 0; i < numberOfBalls; i++) {
            balls.add(new Ball(
                    new PVector(random(0, width), random(0, height)),
                    (height * width) * 0.00002f));
        }
    }

    private void updateBalls() {
        for (Ball ball : balls) {
            // left and top border is always at 0
            if (ball.getLocation().x > width || ball.getLocation().x < 0) {
                ball.setVelocityX(-ball.getVelocity().x);
            }
            if ((ball.getLocation().y > height) || (ball.getLocation().y < 0)) {
                ball.setVelocityY(-ball.getVelocity().y);
            }
            // get acceleration to move ball towards the target location
            PVector directionAcceleration = PVector.sub(new PVector(mouseX, mouseY), ball.getLocation());
            directionAcceleration.normalize().mult(0.5f);
            // if mouse pressed negate acceleration to move the balls away from the mouse position
            if (mousePressed) {
                directionAcceleration.mult(-1);
            }
            // calculate the acceleration to the ball velocity
            ball.setVelocity(ball.getVelocity().add(directionAcceleration).limit(ball.getMaximumSpeed()));
            // calculate new ball location
            ball.setLocation(ball.getLocation().add(ball.getVelocity()));
        }
    }

    private void drawBalls() {
        fill(0, 0, 0, 100);
        for (Ball ball : balls) {
            ellipse(
                    ball.getLocation().x,
                    ball.getLocation().y,
                    ball.getRadius(),
                    ball.getRadius()
            );
        }
    }

    private void drawMouse() {
        if (!mousePressed) {
            fill(0, 255, 0, 100);
        } else {
            fill(255, 0, 0, 100);
        }
        ellipse(mouseX, mouseY, (height * width) * 0.00003f, (height * width) * 0.00003f);
    }
}
