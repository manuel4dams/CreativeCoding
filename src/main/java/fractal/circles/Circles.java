package fractal.circles;

import fractal.Fractal;
import fractal.FractalImplementation;
import processing.core.PApplet;

public class Circles implements FractalImplementation {

    private final Fractal fractal;

    public Circles(Fractal fractal) {
        this.fractal = fractal;
    }

    public void draw() {
        fractal.ellipseMode(fractal.CENTER);
        fractal.noStroke();
        fractal.translate(fractal.width / 2f, fractal.height / 2f);

        draw((int) PApplet.map(fractal.mouseX, 0, fractal.height, 1, 8));
    }

    private void draw(int recursionDepth) {
        // only draw circles when reaching last recursion level
        if (recursionDepth <= 0) {
            fractal.ellipse(0, 0, fractal.width, fractal.height);
        } else {
            recursionDepth--;
            fractal.rotate(0.0005f * fractal.millis());
            fractal.scale(0.43f);

            for (int x = -1; x < 2; x += 2) {
                for (int y = -1; y < 2; y += 2) {
                    fractal.pushMatrix();
                    fractal.scale(x, y);
                    fractal.translate(fractal.width / 2f, fractal.height / 2f);
                    draw(recursionDepth);
                    fractal.popMatrix();
                }
            }
        }
    }
}
