package fractal;

import fractal.barnsleyfern.BarnsleyFern;
import fractal.circles.Circles;
import fractal.mandelbrot.Mandelbrot;
import fractal.ltree.LSystem;
import fractal.oop.OOPTree;
import fractal.tree.Tree;

import java.util.HashMap;

import static processing.core.PApplet.println;
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
                            put('X', "F+[[X]-X]-F[-FX]+X");
                            put('F', "FF");
                        }},
                        "X",
                        8,
                        (character, depth) -> {
                            if (character == 'F') {
                                var length = -250 * (float) Math.pow(0.5, depth);
                                fractal.stroke(34, 139, 34);
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
                fractalImplementation = new Mandelbrot(fractal);
                break;
            case BARNSLEY_FERN:
                fractalImplementation = new BarnsleyFern(fractal);
                break;
            default:
                break;
        }
    }

    public void draw() {
        fractalImplementation.draw();
    }
}
