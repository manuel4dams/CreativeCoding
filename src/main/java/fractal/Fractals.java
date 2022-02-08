package fractal;

import fractal.circles.Circles;
import fractal.ltree.LSystem;
import fractal.oop.OOPTree;
import fractal.tree.Tree;

import java.util.HashMap;

import static processing.core.PApplet.radians;

public class Fractals {

    private final Fractal fractal;

    private FractalImplementation fractalImplementation;

    public Fractals(Fractal fractal, Fractal.FractalType fractalType) {
        this.fractal = fractal;
        init(fractalType);
    }

    private void init(Fractal.FractalType fractalType) {
        switch (fractalType) {
            case CIRCLES:
                fractalImplementation = new Circles(fractal);
                break;
            case TREE:
                fractalImplementation = new Tree(fractal, 100f);
                break;
            case OOP_TREE:
                fractalImplementation = new OOPTree(fractal, 10);
                break;
            case L_SYSTEM_TREE:
                fractalImplementation = new LSystem(
                        fractal,
                        new HashMap<>() {{
                            put('F', "FF+[+F-F-F]-[-F+F+F]");
                        }},
                        "F",
                        5,
                        (character, depth) -> {
                            if (character == 'F') {
                                var length = -100f * (float) Math.pow(0.5, depth);
                                fractal.line(0, 0, 0, length);
                                fractal.translate(0, length);
                            } else if (character == '+') {
                                fractal.rotate(radians(25));
                            } else if (character == '-') {
                                fractal.rotate(-radians(25));
                            } else if (character == '[') {
                                fractal.pushMatrix();
                            } else if (character == ']') {
                                fractal.popMatrix();
                            }
                        });
                break;
            case MANDELBROT:
                // @see https://www.youtube.com/watch?v=6z7GQewK-Ks
                break;
            case BARNSLEY_FERN:
                // @see https://www.youtube.com/watch?v=JFugGF1URNo
                break;
            default:
                break;
        }
    }

    public void draw() {
        fractalImplementation.draw();
    }
}
