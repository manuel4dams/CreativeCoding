package fractal;

import processing.core.PApplet;

public class Fractal extends PApplet {

    enum FractalType {
        CIRCLES,
        TREE,
        OOP_TREE,
        L_SYSTEM_TREE,
        MANDELBROT,
        BARNSLEY_FERN
    }

    private Fractals fractals;

    public int frameIndex;

    public static void main(String... args) {
        PApplet.main("fractal.Fractal");
    }

    public void settings() {
        size(1024, 1024);
    }

    public void setup() {
        frameRate(30);
        fractals = new Fractals(this, FractalType.BARNSLEY_FERN);
    }

    public void draw() {
        background(0);
        fractals.draw();
        resetMatrix();
        frameIndex++;
    }
}
