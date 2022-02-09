package fractal.mandelbrot;

import fractal.Fractal;
import fractal.FractalImplementation;
import processing.core.PApplet;

// @see https://www.youtube.com/watch?v=6z7GQewK-Ks
// @see https://en.wikipedia.org/wiki/Mandelbrot_set
public class Mandelbrot implements FractalImplementation {
    private final Fractal fractal;

    public Mandelbrot(Fractal fractal) {
        this.fractal = fractal;
    }

    public void draw() {
        var maximalIterationDepth = 100;

        fractal.loadPixels();
        for (var x = 0; x < fractal.width; x++) {
            for (var y = 0; y < fractal.width; y++) {
                var index = x + y * fractal.width;

                //TODO zoom in ?
                var zoomValue = 1.5f;
                var a = PApplet.map(x, 0, fractal.width, -zoomValue, zoomValue);
                var b = PApplet.map(y, 0, fractal.height, -zoomValue, zoomValue);
                var originalA = a;
                var originalB = b;

                // calculation
                // @see https://youtu.be/6z7GQewK-Ks?t=457
                var n = 0;
                while (n < maximalIterationDepth) {
                    var calculatedA = a * a - b * b;
                    var nextB = 2 * a * b;

                    a = calculatedA + originalA;
                    b = nextB + originalB;

                    if (Math.abs(a + b) > 16) {
                        break;
                    }
                    n++;
                }

                // TODO apply nice colors!
                var colorValue = PApplet.map(n, 0, maximalIterationDepth, 0, 255);
                if (n == maximalIterationDepth) {
                    colorValue = 0;
                }

                var red = colorValue;
                var green = colorValue;
                var blue = colorValue;
                var alpha = 255;

                fractal.pixels[index] = fractal.color(red, green, blue, alpha);
            }
        }
        fractal.updatePixels();
    }
}
