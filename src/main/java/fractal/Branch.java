package fractal;

import processing.core.PApplet;
import processing.core.PVector;

public class Branch {
    private final Fractal fractal;

    private final PVector branchStart;
    private final PVector branchEnd;

    public boolean grown;

    public Branch(Fractal fractal, PVector branchStart, PVector branchEnd) {
        this.fractal = fractal;
        this.branchStart = branchStart;
        this.branchEnd = branchEnd;
    }
    // TODO do something with the angle?
    public Branch branchLeft() {
        return new Branch(fractal, branchEnd, PVector.add(branchEnd, getDirection().rotate(-PApplet.PI / 6).mult(0.67f)));
    }

    public Branch branchRight() {
        return new Branch(fractal, branchEnd, PVector.add(branchEnd, getDirection().rotate(PApplet.PI / 4).mult(0.67f)));
    }

    private PVector getDirection() {
        return PVector.sub(branchEnd, branchStart);
    }

    public void draw() {
        fractal.line(branchStart.x, branchStart.y, branchEnd.x, branchEnd.y);
    }

    public PVector getBranchEnd() {
        return branchEnd;
    }
}
