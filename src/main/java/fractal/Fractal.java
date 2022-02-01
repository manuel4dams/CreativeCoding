package fractal;

import processing.core.PApplet;

public class Fractal extends PApplet {

    public static void main(String... args) {
        PApplet.main("fractal.Fractal");
    }

    public void settings() {
        size(1024, 1024);
    }

    public void setup() {
        background(0);
        ellipseMode(CENTER);
        noStroke();
    }

    public void draw() {
        background(0);

        translate(width / 2f, height / 2f);

        int recursionDepth = (int) map(mouseX, 0, height, 1, 8);
        drawCircles(recursionDepth);
    }

    private void drawCircles(int recursionDepth) {
        // only draw circles when reaching last recursion level
        if (recursionDepth <= 0) {
            ellipse(0, 0, width, height);
        } else {
            recursionDepth--;
            rotate(0.0005f * millis());
            scale(0.43f);

            for (int x = -1; x < 2; x += 2) {
                for (int y = -1; y < 2; y += 2) {
                    pushMatrix();
                    scale(x, y);
                    translate(width / 2f, height / 2f);
                    drawCircles(recursionDepth);
                    popMatrix();
                }
            }
        }
    }
}
