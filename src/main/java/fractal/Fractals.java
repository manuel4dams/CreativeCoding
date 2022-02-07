package fractal;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Fractals {

    private final Fractal fractal;
    private final Fractal.SetFractal setFractal;

    public ArrayList<Branch> objectOrientedTree = new ArrayList<>();
    public ArrayList<PVector> leaves = new ArrayList<>();

    public Fractals(Fractal fractal, Fractal.SetFractal setFractal) {
        this.fractal = fractal;
        this.setFractal = setFractal;
    }

    public void init() {
        if (setFractal == Fractal.SetFractal.OOPTREE) {
            objectOrientedTree.add(new Branch(
                    fractal,
                    new PVector(fractal.width / 2f, fractal.height),
                    new PVector(fractal.width / 2f, fractal.height - 300)
            ));
        }
    }

    public void draw() {
        switch (setFractal) {
            case CIRCLES:
                fractal.translate(fractal.width / 2f, fractal.height / 2f);
                drawCircles((int) PApplet.map(fractal.mouseX, 0, fractal.height, 1, 8));
                break;
            case TREE:
                fractal.translate(fractal.width / 2f, fractal.height - 200f);
                drawTree(100f);
                break;
            case OOPTREE:
                ObjectOrientedTree();
                break;
            default:
                break;
        }
    }

    private void drawCircles(int recursionDepth) {
        fractal.ellipseMode(fractal.CENTER);
        fractal.noStroke();

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
                    drawCircles(recursionDepth);
                    fractal.popMatrix();
                }
            }
        }
    }

    // @see https://www.youtube.com/watch?v=0jjeOYMjmDU
    private void drawTree(float branchLength) {
        float newBranchLength = 0.67f * branchLength;
        float angle = fractal.PI * fractal.frameIndex * 0.005f;
        int minimumBranchLength = 2;

        fractal.scale(1.2f);
        fractal.stroke(255);
        fractal.strokeWeight(0.5f);

        fractal.line(0, 0, 0, -branchLength);
        fractal.translate(0, -branchLength);

        if (branchLength > minimumBranchLength) {

            fractal.pushMatrix();
            fractal.rotate(angle);
            drawTree(newBranchLength);
            fractal.popMatrix();

            fractal.pushMatrix();
            fractal.rotate(-angle);
            drawTree(newBranchLength);
            fractal.popMatrix();
        }
    }

    // @see https://www.youtube.com/watch?v=fcdNSZ9IzJM
    private void ObjectOrientedTree() {
        int depth = 10;

        for (var i = objectOrientedTree.size() - 1; i >= 0 && fractal.frameIndex < depth; i--) {
            if (!objectOrientedTree.get(i).grown) {
                objectOrientedTree.add(objectOrientedTree.get(i).branchLeft());
                objectOrientedTree.add(objectOrientedTree.get(i).branchRight());
                objectOrientedTree.get(i).grown = true;
            }
        }
        if (fractal.frameIndex == depth) {
            for (Branch branch : objectOrientedTree) {
                if (!branch.grown) {
                    leaves.add(branch.getBranchEnd().copy());
                }
            }
        }

        // TODO move leaves or branches???
        for (Branch branch : objectOrientedTree) {
            fractal.stroke(139, 69, 19);
            branch.draw();
        }

        fractal.noStroke();
        for (PVector leave : leaves) {
            fractal.fill(34, 139, 34);
            fractal.ellipse(leave.x, leave.y, 6, 6);
        }
    }

    // @see https://www.youtube.com/watch?v=E1B4UoSQMFw
    private void LSystemTree() {
        //TODO
    }

    // @see https://www.youtube.com/watch?v=6z7GQewK-Ks
    private void Mandelbrot() {
        //TODO
    }

    // @see https://www.youtube.com/watch?v=JFugGF1URNo
    private void BarnsleyFern() {
        //TODO
    }
}
