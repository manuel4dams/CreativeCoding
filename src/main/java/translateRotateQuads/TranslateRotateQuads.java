package translateRotateQuads;

import processing.core.PApplet;

public class TranslateRotateQuads extends PApplet {

    public static void main(String... args) {
        PApplet.main("translateRotateQuads.TranslateRotateQuads");
    }

    public void settings() {
        size(1024, 1024);
    }

    public void setup() {
        background(255);
        frameRate(100);
    }

    public void draw() {
        background(255);
        randomSeed(36421);
        translate((width - 32 * 12) / 2f, (height - 32 * 24) / 2f);
        float rotationFactor = sin((float) (0.0001 * millis()));
        float translationFactor = 0.0001f * millis();

        for (int x = 0; x < 12; x++) {
            for (int y = 0; y < 24; y++) {
                pushMatrix();
                translate(x * 32, y * 32);
                rotate(radians(y * rotationFactor * (random(-20, 20))));
                translate(x * (translationFactor * random(-1, 1)), y * (translationFactor * random(-1, 1)));
                rect(0, 0, 32, 32);
                popMatrix();

            }
        }
    }
}
