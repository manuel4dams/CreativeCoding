package fractal;

import processing.core.PApplet;
import processing.core.PVector;



public class Fractal extends PApplet {

    enum SetFractal{
        CIRCLES,
        TREE,
        OOPTREE
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
        fractals = new Fractals(this, SetFractal.OOPTREE);

        background(0);
        frameRate(30);

        fractals.init();
    }

    public void draw() {
        background(0);
        fractals.draw();
        resetMatrix();
        frameIndex++;
    }
}
