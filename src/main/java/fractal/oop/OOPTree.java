package fractal.oop;

import fractal.Fractal;
import fractal.FractalImplementation;
import processing.core.PVector;

import java.util.ArrayList;

// @see https://www.youtube.com/watch?v=fcdNSZ9IzJM
public class OOPTree implements FractalImplementation {
    private final Fractal fractal;
    private final int depth;

    private final ArrayList<Branch> objectOrientedTree = new ArrayList<>();
    private final ArrayList<PVector> leaves = new ArrayList<>();

    public OOPTree(Fractal fractal, int depth) {
        this.fractal = fractal;
        this.depth = depth;
        initializeRootBranch();
    }

    private void initializeRootBranch() {
        objectOrientedTree.add(new Branch(
                fractal,
                new PVector(fractal.width / 2f, fractal.height),
                new PVector(fractal.width / 2f, fractal.height - 300)
        ));
    }

    private void generateTree() {
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
    }

    public void draw() {
        generateTree();
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
}
