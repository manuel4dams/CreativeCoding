package helloWorld;

import processing.core.PApplet;

public class HelloWorld extends PApplet {

    public void settings() {
        size(600, 600);
    }

    public void draw() {
        background(0);
        ellipse(mouseX, mouseY, 20, 20);
    }

    public static void main(String... args) {
        PApplet.main("helloWorld.HelloWorld");
    }
}
