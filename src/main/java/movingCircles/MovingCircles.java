package movingCircles;

import processing.core.PApplet;

public class MovingCircles extends PApplet {

    public static void main(String... args) {
        PApplet.main("movingCircles.MovingCircles");
    }

    public void settings() {
        size(1024, 1024);
    }

    public void setup() {
        background(255);
        frameRate(100);

    }

    public void draw() {
        background(255);
        noStroke();
        randomSeed(36421);
        for (int x = width / 64; x < width; x = x + width / 32) {
            for (int y = height / 64; y < height; y = y + height / 32) {
                float shiftedX = x + (random(-1, 1) * mouseX / 32);
                float shiftedY = y + (random(-1, 1) * mouseY / 32);

                //black circles
                fill(0);
                ellipse(shiftedX, shiftedY, width / 32f, height / 32f);
                //white circles
                fill(255);
                ellipse(x, y, width / 64f, height / 64f);
            }
        }
    }
}
