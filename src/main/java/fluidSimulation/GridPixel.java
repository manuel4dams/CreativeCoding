package fluidSimulation;

import processing.core.PVector;

public class GridPixel {

    private static final float DIFFUSION_VALUE = 1f;
    private static final float VISCOSITY_VALUE = 1f;

    public PVector velocity;
    public int density;
    public int color;

    public GridPixel(int color) {
        velocity = new PVector();
        density = 0;
        this.color = color;
    }

    public void addVelocity() {

    }

    public void addDensity(float amount) {
        density += amount;
    }

    public void updateColor() {

    }
}
