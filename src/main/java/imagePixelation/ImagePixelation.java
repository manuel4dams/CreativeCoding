package imagePixelation;

import processing.core.PApplet;
import processing.core.PImage;

//TODO Post good looking images in the moodle forum to show others what is possible.
public class ImagePixelation extends PApplet {

    private PImage image;
    private float circleSize = 30;

    public static void main(String... args) {

        PApplet.main("imagePixelation.ImagePixelation");
    }

    public void settings() {
        size(900, 900);
        image = loadImage("Monkey.png");
    }

    public void setup() {
        image.resize(width, height);
        ellipseMode(CENTER);
        noStroke();

        background(135, 135, 135, 255);
        pixelate();
    }

    private void pixelate() {

        for (int x = 0; x < width; x += circleSize) {
            for (int y = 0; y < height; y += circleSize) {
                fill(image.get(x, y));
                ellipse(x, y, circleSize, circleSize);
            }
            if (circleSize > 1) {
                circleSize *= 0.976;
            }
        }
    }
}
