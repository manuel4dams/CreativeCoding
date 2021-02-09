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

        background(0);
        pixelate();
        saveFrame("ImagePixelation.png");
    }

    private void pixelate() {
        var overdrawingOffset = 10;
        for (int x = -overdrawingOffset; x <= width + overdrawingOffset; x += circleSize) {
            for (int y = -overdrawingOffset; y <= height + overdrawingOffset; y += circleSize) {
                var imageClampedX = max(0, min(x, image.width - 1));
                var imageClampedY = max(0, min(y, image.height - 1));
                fill(image.get(imageClampedX, imageClampedY));
                ellipse(x,
                        y,
                        circleSize,
                        circleSize);
            }
            circleSize *= 0.976;
            circleSize = max(circleSize, 1);
        }
    }
}
