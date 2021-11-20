package scaleRotateQuads;

import processing.core.PApplet;

public class ScaleRotateQuads extends PApplet {

    public static void main(String... args) {
        PApplet.main("scaleRotateQuads.ScaleRotateQuads");
    }

    public void settings() {
        size(1024, 512);
    }

    public void setup() {
        rectMode(CENTER);
        noFill();
    }

    public void draw() {
        background(255);

        int maxIterations = 1000;
        int iteration = (int) map(mouseX, 0, width, 1, maxIterations);

        translate(width / 2f, height / 2f);
        for (int i = 0; i < iteration; i++) {
            rotate(radians(map(iteration, 3, maxIterations, 6, 20f * mouseY / height)));
            scale(map(mouseX, 0, width, 0.5f, 0.9f));
            rect(0, 0, width, height);
        }
    }
}
