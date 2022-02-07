package fluidSimulation;

import java.util.ArrayList;

public class VelocityField {

    private final FluidSimulation fluidSimulation;

    private ArrayList<GridPixel> pixelGrid;

    public VelocityField(FluidSimulation fluidSimulation) {
        this.fluidSimulation = fluidSimulation;

        init();
    }

    private void init() {
        pixelGrid = new ArrayList<>();

        for (int i = 0; i < fluidSimulation.width * fluidSimulation.height; i++) {
            pixelGrid.add(new GridPixel(fluidSimulation.color(255, 0, 0, 255)));
        }
    }

    public void update() {

        for (GridPixel gridPixel : pixelGrid) {
            gridPixel.updateColor();
            gridPixel.addDensity(10f);
            gridPixel.addVelocity();
        }

        draw();
    }

    private void draw() {

        fluidSimulation.loadPixels();
        for (int i = 0; i < pixelGrid.size(); i++) {
            fluidSimulation.pixels[i] = pixelGrid.get(i).color;
        }
        fluidSimulation.updatePixels();
    }
}
