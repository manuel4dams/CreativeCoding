package helloWorld;

import processing.core.PApplet;

public class HelloWorld extends PApplet {
    private float rnd;

    public static void main(String... args) {
        PApplet.main("helloWorld.HelloWorld");
    }

    public void settings() {
        size(900, 600);
    }

    public void setup() {
        surface.setResizable(true);
        background(83, 153, 245);
        frameRate(100);
    }

    public void draw() {
        rnd = random(10, 40);
        var color = color(255, random(0, 100), random(0, 100), 200);

        ellipseMode(RADIUS);
        fill(color);
        noStroke();
        ellipse(random(width), random(height), rnd, rnd);
    }

    public void mousePressed() {
        saveFrame("HelloWorld.png");
    }
}
