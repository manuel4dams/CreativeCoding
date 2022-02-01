package helloworld;

import processing.core.PApplet;

public class HelloWorld extends PApplet {

    public static void main(String... args) {
        PApplet.main("helloworld.HelloWorld");
    }

    public void settings() {
        size(1024, 1024);
    }

    public void setup() {
        background(83, 153, 245);
        frameRate(100);
    }

    public void draw() {
        var randomSize = random(0, 40);
        var color = color(255, random(0, 100), random(0, 100), 200);

        ellipseMode(RADIUS);
        fill(color);
        noStroke();
        ellipse(random(width), random(height), randomSize, randomSize);
    }

    public void mousePressed() {
        saveFrame("HelloWorld.png");
    }
}
