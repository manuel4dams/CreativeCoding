package kaleidoscopeSnowflakeChallenge;

import processing.core.PApplet;

public class KaleidoscopeSnowflakeChallenge extends PApplet {
    public static void main(String... args) {
        PApplet.main("kaleidoscopeSnowflakeChallenge.KaleidoscopeSnowflakeChallenge");
    }

    public void settings() {
        size(1024, 1024);
    }

    public void setup() {
        background(0);
        strokeWeight(width / 40f);
        stroke(255, 100);
    }

    public void draw() {
        if (!mousePressed) {
            return;
        }
        translate(width / 2f, height / 2f);
        for (float i = 0; i < 6; i++) {
            // Mirror to 6 corners
            rotate(radians(360 / 6f));
            drawLine();
            pushMatrix();
            // Mirror the line on vertical axis, to get 12 copies of the line
            scale(-1, 1);
            drawLine();
            popMatrix();
        }
    }

    void drawLine() {
        line(
                mouseX - (width / 2f),
                mouseY - (height / 2f),
                pmouseX - (width / 2f),
                pmouseY - (height / 2f)
        );
    }
}
