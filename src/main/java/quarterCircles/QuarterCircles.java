package quarterCircles;

import processing.core.PApplet;

public class QuarterCircles extends PApplet {

    public static void main(String... args) {
        PApplet.main("quarterCircles.QuarterCircles");
    }

    public void settings() {
        size(1024, 1024);
    }

    public void setup() {
        background(255);
        singleDraw();
    }

    public void singleDraw() {
        for (int x = 0; x <= width; x = x + width / 32) {
            for (int y = 0; y <= height; y = y + height / 32) {
                fillHalfCircles((int) random(0, 5), x, y);
            }
        }
    }

    void fillHalfCircles(int random, int x, int y) {
        stroke(255);

        colorMode(RGB, 255, 0, 127);

        fill((int) random(0, 256),
                (int) random(0, 256),
                (int) random(0, 256));
        switch (random) {
            case 0:
                drawHalfCircle(x, y, 0, HALF_PI);
                break;
            case 1:
                drawHalfCircle(x + width / 32f, y, HALF_PI, PI);
                break;
            case 2:
                drawHalfCircle(x + width / 32f, y + height / 32f, PI, PI + HALF_PI);
                break;
            case 3:
                drawHalfCircle(x, y + height / 32f, PI + HALF_PI, PI + PI);
                break;
            case 4:
                break;
            default:
                throw new IllegalArgumentException("Argument out of bounds");
        }
    }

    void drawHalfCircle(float x, float y, float start, float stop) {
        arc(x, y, width / 32f * 2, height / 32f * 2, start, stop, PIE);
    }
}
