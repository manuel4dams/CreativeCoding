package quarterCircles;

import processing.core.PApplet;

public class QuarterCircles extends PApplet {
    private final int WIDTH = 32;
    private final int HEIGHT = 32;

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
        for (int x = 0; x <= 1024; x = x + WIDTH) {
            for (int y = 0; y <= 1024; y = y + HEIGHT) {
                fillHalfCircles((int) random(0, 5), x, y);
            }
        }
    }

    void fillHalfCircles(int random, int x, int y) {
        stroke(255);
        fill((int) random(0, 256),
                (int) random(0, 256),
                (int) random(0, 256));
        switch (random) {
            case 0:
                drawHalfCircle(x, y, 0, HALF_PI);
                break;
            case 1:
                drawHalfCircle(x + WIDTH, y, HALF_PI, PI);
                break;
            case 2:
                drawHalfCircle(x + WIDTH, y + HEIGHT, PI, PI + HALF_PI);
                break;
            case 3:
                drawHalfCircle(x, y + HEIGHT, PI + HALF_PI, PI + PI);
                break;
            case 4:
                break;
            default:
                throw new IllegalArgumentException("Argument out of bounds");
        }
    }

    void drawHalfCircle(float x, float y, float start, float stop) {
        arc(x, y, WIDTH * 2, HEIGHT * 2, start, stop, PIE);
    }
}
