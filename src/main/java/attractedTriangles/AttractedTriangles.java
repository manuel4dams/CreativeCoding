package attractedTriangles;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class AttractedTriangles extends PApplet {
    private final ArrayList<Triangle> triangles = new ArrayList<>();

    public static void main(String... args) {
        PApplet.main("attractedTriangles.AttractedTriangles");
    }

    public void settings() {
        size(1024, 1024);
    }

    public void setup() {
        background(255);
        strokeWeight(2);
        AddTriangles(60);
    }

    public void draw() {
        background(255);
        updateTriangles();
        drawTriangles();
        drawMouse();
    }

    private void AddTriangles(int numberOfTriangles) {
        for (int i = 0; i < numberOfTriangles; i++) {
            triangles.add(new Triangle(
                    new PVector(
                            random(0, width),
                            random(0, height)),
                    random(0, 360)));
        }
    }

    private void updateTriangles() {
        for (Triangle triangle : triangles) {
            // left and top border is always at 0
            if (triangle.getPosition().x > width || triangle.getPosition().x < 0) {
                triangle.setVelocityX(-triangle.getVelocity().x);
            }
            if ((triangle.getPosition().y > height) || (triangle.getPosition().y < 0)) {
                triangle.setVelocityY(-triangle.getVelocity().y);
            }
            // get acceleration to move triangle towards the target location
            PVector directionAcceleration = PVector.sub(new PVector(mouseX, mouseY), triangle.getPosition());
            directionAcceleration.normalize().mult(0.5f);
            // if mouse pressed negate acceleration to move the triangles away from the mouse position
            if (mousePressed) {
                directionAcceleration.mult(-1);
            }
            // calculate the acceleration to the triangle velocity
            triangle.setVelocity(triangle.getVelocity().add(directionAcceleration).limit(triangle.getMaximumSpeed()));
            // calculate new triangle location
            triangle.setPosition(triangle.getPosition().add(triangle.getVelocity()));
            // set the rotation
            triangle.setAngle(atan2(triangle.getVelocity().y, triangle.getVelocity().x));
        }
    }

    private void drawTriangles() {
        fill(0, 0, 0, 100);
        for (Triangle triangle : triangles) {
            pushMatrix();
            translate(triangle.getPosition().x, triangle.getPosition().y);
            rotate(triangle.getAngle());
            triangle(0, -10, 40, 0, 0, 10);
            popMatrix();
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
