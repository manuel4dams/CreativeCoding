package circlePacking;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class CirclePacking extends PApplet {

    static boolean ALLOW_GROWTH = true;
    private PImage image;
    ArrayList<Circle> circles = new ArrayList<>();

    public static void main(String... args) {
        PApplet.main("circlePacking.CirclePacking");
    }

    public void settings() {
        size(1024, 1024);
    }

    public void setup() {
        fillRandomCircleArrayList();
        image = loadImage("Bridge.jpg");
//        fillImageCircleArrayList();
    }

    private void fillRandomCircleArrayList() {
        for (var i = 0; i < 200; i++) {
            Circle candidate = new Circle(random(width), random(height), random(1f, width / 40f));
            if (isCandidateOverlapping(candidate)) {
                i--;
            } else {
                circles.add(candidate);
            }
        }
    }

    private void fillImageCircleArrayList() {
        ALLOW_GROWTH = false;
        for (int x = 0; x < image.width; x++) {
            for (int y = 0; y < image.height; y++) {
                if (brightness(image.get(x, y)) > 80) {
                    Circle candidate = new Circle(x, y, random(1f, width / 150f));
                    if (!isCandidateOverlapping(candidate)) {
                        circles.add(candidate);
                    }
                }
            }
        }

    }

    public void draw() {
        background(0);
        fill(255);
        noStroke();
        for (Circle circle : circles) {
            ellipse(
                    circle.getPositionX(),
                    circle.getPositionY(),
                    circle.getRadius() * 2,
                    circle.getRadius() * 2);

            if (!ALLOW_GROWTH) {
                continue;
            }
            circle.setRadius(circle.getRadius() + 0.1f);
            if (isCandidateOverlapping(circle)) {
                circle.setRadius(circle.getRadius() - 0.1f);
            }
        }
    }

    private boolean isCandidateOverlapping(Circle candidate) {
        for (Circle circle : circles) {
            float distance = dist(
                    circle.getPositionX(),
                    circle.getPositionY(),
                    candidate.getPositionX(),
                    candidate.getPositionY());
            if (circle == candidate)
                continue;
            if ((distance < circle.getRadius() + candidate.getRadius())) {
                return true;
            }
        }
        return false;
    }
}
