package fractal.tree;

import fractal.Fractal;
import fractal.FractalImplementation;

// @see https://www.youtube.com/watch?v=0jjeOYMjmDU
public class Tree implements FractalImplementation {

    private static final int MINIMUM_BRANCH_LENGTH = 2;

    private final Fractal fractal;
    private final float branchLength;

    public Tree(Fractal fractal, float branchLength) {
        this.fractal = fractal;
        this.branchLength = branchLength;
    }

    public void draw() {
        fractal.stroke(255);
        fractal.strokeWeight(0.5f);
        fractal.translate(fractal.width / 2f, fractal.height - 200f);
        draw(branchLength);
    }

    public void draw(float currentBranchLength) {
        float newBranchLength = 0.67f * currentBranchLength;
        float angle = fractal.PI * fractal.frameIndex * 0.001f;

        fractal.scale(1.2f);
        fractal.stroke(139, 69, 19);
        fractal.line(0, 0, 0, -currentBranchLength);
        fractal.translate(0, -currentBranchLength);

        if (currentBranchLength > MINIMUM_BRANCH_LENGTH) {
            fractal.pushMatrix();
            fractal.rotate(angle);
            draw(newBranchLength);
            fractal.popMatrix();

            fractal.pushMatrix();
            fractal.rotate(-angle);
            draw(newBranchLength);
            fractal.popMatrix();
        }else{
            fractal.noStroke();
            fractal.fill(34, 139, 34);
            fractal.ellipse(0,0,2,2);
        }
    }
}
