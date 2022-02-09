package fractal.barnsleyfern;

import fractal.Fractal;
import fractal.FractalImplementation;
import processing.core.PApplet;
import processing.core.PVector;

// @see https://www.youtube.com/watch?v=JFugGF1URNo
// @see https://en.wikipedia.org/wiki/Barnsley_fern
public class BarnsleyFern implements FractalImplementation {

    private static final int POINTS = 100000;

    private final Fractal fractal;
    private final PVector point = new PVector(0,0);


    public BarnsleyFern(Fractal fractal) {
        this.fractal = fractal;
        fractal.noLoop();
    }

    public void draw() {
        fractal.strokeWeight(1);
        fractal.stroke(0,255,0);

        for (int i = 0; i < POINTS; i++) {
            float probabilityFactor = fractal.random(1f);
            if (probabilityFactor < 0.01f) {
                calculateNextPoint(0f, 0f, 0f, 0.16f, 0f);
            } else if (probabilityFactor < 0.86f) {
                calculateNextPoint(0.85f, 0.04f, -0.04f, 0.85f, 1.60f);
            } else if (probabilityFactor < 0.93f) {
                calculateNextPoint(0.20f, -0.26f, 0.23f, 0.22f, 1.60f);
            } else {
                calculateNextPoint(-0.15f, 0.28f, 0.26f, 0.24f, 0.44f);
            }

            float px = PApplet.map(point.x, -8, 8, 0, fractal.width);
            float py = PApplet.map(point.y, 0, 12, fractal.height, 0);
            fractal.point(px, py);
        }
    }

    //  coefficient e is always 0 and therefore has no relevance
    private void calculateNextPoint(float a, float b, float c, float d, float f) {
        var x = a * point.x + b * point.y;
        var y = c * point.x + d * point.y + f;

        point.x = x;
        point.y = y;
    }
}
