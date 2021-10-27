package quarterCircles;

import processing.core.PApplet;

public class QuarterCircles extends PApplet {

    public static void main(String... args) {
        PApplet.main("quarterCircles.QuarterCircles");
    }

    public void settings() {
        size(600, 600);
    }

    public void setup() {
        background(255);
        frameRate(100);
    }

    public void draw() {

    }

    public void mousePressed() {
        saveFrame("HelloWorld.png");
    }
}
