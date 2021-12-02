package polar;

import processing.core.PApplet;

// @see https://en.wikipedia.org/wiki/Rose_(mathematics)
public class Polar extends PApplet {

    private final float ROW_COUNT = 12;

    public static void main(String... args) {
        PApplet.main("polar.Polar");
    }

    public void settings() {
        size(1024, 1024);
    }

    public void setup() {
        background(255);
        noFill();
        noLoop();
    }

    public void draw() {
        background(255);
        for (float n = 1; n <= ROW_COUNT; n++) {
            for (float d = 1; d <= ROW_COUNT; d++) {
                pushMatrix();
                translate((n - 1f) * (width / ROW_COUNT), (d - 1f) * (height / ROW_COUNT));
                drawShape(floor(n), floor(d));
                popMatrix();
            }
        }
    }

    private void drawShape(float n, float d) {
        beginShape();
        translate((width / ROW_COUNT) / 2f, (height / ROW_COUNT) / 2f);
        for (float a = 0; a < TWO_PI * d; a += 0.1f) {
            float r = ((width / ROW_COUNT) / 3f) * cos((n / d) * a);
            float x = r * cos(a);
            float y = r * sin(a);
            vertex(x, y);
        }
        endShape(CLOSE);
    }
}
