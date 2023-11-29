package fluidSimulation;

import processing.core.PApplet;
import processing.core.PVector;

public class FluidSimulation extends PApplet {

    private float frameTime = 0;

    private Fluid fluid;

    public static void main(String... args) {
        PApplet.main("fluidSimulation.FluidSimulation");
    }

    public void settings() {
        size(Constants.SIZE * Constants.SCALE, Constants.SIZE * Constants.SCALE);
    }

    public void setup() {
        background(0);
        frameRate(30);

        fluid = new Fluid(this, 0.2f, 0, 0.0000001f);
    }

    public void draw() {
        int cx = (int) (0.5 * width / Constants.SCALE);
        int cy = (int) (0.5 * height / Constants.SCALE);

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                fluid.addDensity(cx + i, cy + j, random(50, 150));
            }
        }
        for (int i = 0; i < 2; i++) {
            PVector v = PVector.fromAngle(noise(frameTime) * TWO_PI * 2).mult(0.2f);
            fluid.addVelocity(cx, cy, v.x, v.y);

            frameTime += 0.01f;
        }

        fluid.step();
        fluid.renderD();
//        fluid.renderV();
//        fluid.fadeD();
    }

}
