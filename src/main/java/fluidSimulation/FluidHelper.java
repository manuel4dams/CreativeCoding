package fluidSimulation;

import static fluidSimulation.Constants.SIZE;
import static processing.core.PApplet.constrain;
import static processing.core.PApplet.floor;

public class FluidHelper {

    public static void diffuse(int b, float[] x, float[] x0, float diff, float dt) {
        float a = dt * diff * (SIZE - 2) * (SIZE - 2);
        lin_solve(b, x, x0, a, 1 + 4 * a);
    }

    public static void lin_solve(int b, float[] x, float[] x0, float a, float c) {
        float cRecip = 1.0f / c;
        for (int k = 0; k < Constants.ITER; k++) {
            for (int j = 1; j < SIZE - 1; j++) {
                for (int i = 1; i < SIZE - 1; i++) {
                    x[IX(i, j)] =
                            (x0[IX(i, j)]
                                    + a * (x[IX(i + 1, j)]
                                    + x[IX(i - 1, j)]
                                    + x[IX(i, j + 1)]
                                    + x[IX(i, j - 1)]
                            )) * cRecip;
                }
            }

            set_bnd(b, x);
        }
    }

    public static void project(float[] velocX, float[] velocY, float[] p, float[] div) {
        for (int j = 1; j < SIZE - 1; j++) {
            for (int i = 1; i < SIZE - 1; i++) {
                div[IX(i, j)] = -0.5f * (
                        velocX[IX(i + 1, j)]
                                - velocX[IX(i - 1, j)]
                                + velocY[IX(i, j + 1)]
                                - velocY[IX(i, j - 1)]
                ) / SIZE;
                p[IX(i, j)] = 0;
            }
        }

        set_bnd(0, div);
        set_bnd(0, p);
        lin_solve(0, p, div, 1, 4);

        for (int j = 1; j < SIZE - 1; j++) {
            for (int i = 1; i < SIZE - 1; i++) {
                velocX[IX(i, j)] -= 0.5f * (p[IX(i + 1, j)]
                        - p[IX(i - 1, j)]) * SIZE;
                velocY[IX(i, j)] -= 0.5f * (p[IX(i, j + 1)]
                        - p[IX(i, j - 1)]) * SIZE;
            }
        }
        set_bnd(1, velocX);
        set_bnd(2, velocY);
    }

    public static void advect(int b, float[] d, float[] d0, float[] velocX, float[] velocY, float dt) {
        float i0, i1, j0, j1;

        float dtx = dt * (SIZE - 2);
        float dty = dt * (SIZE - 2);

        float s0, s1, t0, t1;
        float tmp1, tmp2, x, y;

        float Nfloat = SIZE;
        float ifloat, jfloat;
        int i, j;

        for (j = 1, jfloat = 1; j < SIZE - 1; j++, jfloat++) {
            for (i = 1, ifloat = 1; i < SIZE - 1; i++, ifloat++) {
                tmp1 = dtx * velocX[IX(i, j)];
                tmp2 = dty * velocY[IX(i, j)];
                x = ifloat - tmp1;
                y = jfloat - tmp2;

                if (x < 0.5f) x = 0.5f;
                if (x > Nfloat + 0.5f) x = Nfloat + 0.5f;
                i0 = floor(x);
                i1 = i0 + 1.0f;
                if (y < 0.5f) y = 0.5f;
                if (y > Nfloat + 0.5f) y = Nfloat + 0.5f;
                j0 = floor(y);
                j1 = j0 + 1.0f;

                s1 = x - i0;
                s0 = 1.0f - s1;
                t1 = y - j0;
                t0 = 1.0f - t1;

                int i0i = (int) (i0);
                int i1i = (int) (i1);
                int j0i = (int) (j0);
                int j1i = (int) (j1);

                // DOUBLE CHECK THIS!!!
                d[IX(i, j)] =
                        s0 * (t0 * d0[IX(i0i, j0i)] + t1 * d0[IX(i0i, j1i)]) +
                                s1 * (t0 * d0[IX(i1i, j0i)] + t1 * d0[IX(i1i, j1i)]);
            }
        }

        set_bnd(b, d);
    }

    public static void set_bnd(int b, float[] x) {
        for (int i = 1; i < SIZE - 1; i++) {
            x[IX(i, 0)] = b == 2 ? -x[IX(i, 1)] : x[IX(i, 1)];
            x[IX(i, SIZE - 1)] = b == 2 ? -x[IX(i, SIZE - 2)] : x[IX(i, SIZE - 2)];
        }
        for (int j = 1; j < SIZE - 1; j++) {
            x[IX(0, j)] = b == 1 ? -x[IX(1, j)] : x[IX(1, j)];
            x[IX(SIZE - 1, j)] = b == 1 ? -x[IX(SIZE - 2, j)] : x[IX(SIZE - 2, j)];
        }

        x[IX(0, 0)] = 0.5f * (x[IX(1, 0)] + x[IX(0, 1)]);
        x[IX(0, SIZE - 1)] = 0.5f * (x[IX(1, SIZE - 1)] + x[IX(0, SIZE - 2)]);
        x[IX(SIZE - 1, 0)] = 0.5f * (x[IX(SIZE - 2, 0)] + x[IX(SIZE - 1, 1)]);
        x[IX(SIZE - 1, SIZE - 1)] = 0.5f * (x[IX(SIZE - 2, SIZE - 1)] + x[IX(SIZE - 1, SIZE - 2)]);
    }

    public static int IX(int x, int y) {
        x = constrain(x, 0, SIZE - 1);
        y = constrain(y, 0, SIZE - 1);
        return x + (y * SIZE);
    }
}
