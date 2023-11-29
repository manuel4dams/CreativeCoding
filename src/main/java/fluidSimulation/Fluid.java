package fluidSimulation;

import processing.core.PApplet;

import static fluidSimulation.FluidHelper.*;
import static processing.core.PApplet.abs;
import static processing.core.PConstants.HSB;

public class Fluid {

    private final FluidSimulation fluidSimulation;

    int size;
    float dt;
    float diffusion;
    float viscosity;

    float[] s;
    float[] density;

    float[] Vx;
    float[] Vy;

    float[] Vx0;
    float[] Vy0;

    public Fluid(FluidSimulation fluidSimulation, float dt, float diffusion, float viscosity) {
        this.fluidSimulation = fluidSimulation;

        this.size = Constants.SIZE;
        this.dt = dt;
        this.diffusion = diffusion;
        this.viscosity = viscosity;

        this.s = new float[Constants.SIZE * Constants.SIZE];
        this.density = new float[Constants.SIZE * Constants.SIZE];

        this.Vx = new float[Constants.SIZE * Constants.SIZE];
        this.Vy = new float[Constants.SIZE * Constants.SIZE];

        this.Vx0 = new float[Constants.SIZE * Constants.SIZE];
        this.Vy0 = new float[Constants.SIZE * Constants.SIZE];
    }

    public void step() {
        float visc = this.viscosity;
        float diff = this.diffusion;
        float dt = this.dt;
        float[] Vx = this.Vx;
        float[] Vy = this.Vy;
        float[] Vx0 = this.Vx0;
        float[] Vy0 = this.Vy0;
        float[] s = this.s;
        float[] density = this.density;

        diffuse(1, Vx0, Vx, visc, dt);
        diffuse(2, Vy0, Vy, visc, dt);

        project(Vx0, Vy0, Vx, Vy);

        advect(1, Vx, Vx0, Vx0, Vy0, dt);
        advect(2, Vy, Vy0, Vx0, Vy0, dt);

        project(Vx, Vy, Vx0, Vy0);

        diffuse(0, s, density, diff, dt);
        advect(0, density, s, Vx, Vy, dt);
    }

    public void addDensity(int x, int y, float amount) {
        int index = IX(x, y);
        this.density[index] += amount;
    }

    public void addVelocity(int x, int y, float amountX, float amountY) {
        int index = IX(x, y);
        this.Vx[index] += amountX;
        this.Vy[index] += amountY;
    }

    public void renderD() {
        fluidSimulation.colorMode(HSB, 255);
        fluidSimulation.noStroke();

        for (int i = 0; i < Constants.SIZE; i++) {
            for (int j = 0; j < Constants.SIZE; j++) {
                float x = i * Constants.SCALE;
                float y = j * Constants.SCALE;
                float d = this.density[IX(i, j)];
                fluidSimulation.fill((d + 50) % 255, 200, d);
                fluidSimulation.rect(x, y, Constants.SCALE, Constants.SCALE);
            }
        }
    }

    public void renderV() {
        fluidSimulation.stroke(255);

        for (int i = 0; i < Constants.SIZE; i++) {
            for (int j = 0; j < Constants.SIZE; j++) {
                float x = i * Constants.SCALE;
                float y = j * Constants.SCALE;
                float vx = this.Vx[IX(i, j)];
                float vy = this.Vy[IX(i, j)];

                if (!(abs(vx) < 0.1 && abs(vy) <= 0.1)) {
                    fluidSimulation.line(x, y, x + vx * Constants.SCALE, y + vy * Constants.SCALE);
                }
            }
        }
    }

    public void fadeD() {
        for (int i = 0; i < this.density.length; i++) {
            float d = density[i];
            density[i] = PApplet.constrain(d - 0.02f, 0f, 255f);
        }
    }
}
