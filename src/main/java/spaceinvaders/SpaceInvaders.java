package spaceinvaders;

import processing.core.PApplet;
import processing.core.PImage;

public class SpaceInvaders extends PApplet {

    private static final int INVADER_SIZE = 7;
    private static final int PIXEL_SIZE = 50;
    private static final int BORDER_SIZE = 50;

    public static void main(String... args) {
        PApplet.main("spaceinvaders.SpaceInvaders");
    }

    public void settings() {
        noSmooth();
        size(650, 650);
    }

    public void setup() {
        surface.setResizable(true);
        frameRate(1f);
    }

    public void draw() {
        background(242, 105, 105, 255);
        drawInvaders();
        noLoop();
    }

    public void mousePressed() {
        redraw();
    }

    private void drawInvaders() {
        var offsetX = (BORDER_SIZE - (width % (PIXEL_SIZE + BORDER_SIZE))) / 2;
        var offsetY = (BORDER_SIZE - (height % (PIXEL_SIZE + BORDER_SIZE))) / 2;

        for (var x = offsetX; x < width; x += PIXEL_SIZE + BORDER_SIZE) {
            for (var y = offsetY; y < height; y += PIXEL_SIZE + BORDER_SIZE) {
                var invader = createInvader(INVADER_SIZE);
                image(invader, x, y, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }

    private PImage createInvader(int size) {
        var colors = new int[]{
                color(0, 0),
                color(0, 0),
                color(0, 0),
                generateRandomColor(),
                generateRandomColor(),
                generateRandomColor()
        };
        var image = createImage(size, size, ARGB);

        for (var row = 0; row < size; row++) {
            for (var column = 0; column < ((float) size / 2); column++) {
                var color = colors[(int) random(colors.length - 1)];
                image.set(column, row, color);
                image.set(size - 1 - column, row, color);
            }
        }
        return image;
    }

    private int generateRandomColor() {
        return color(
                random(0, 255),
                random(0, 255),
                random(0, 255),
                255
        );
    }
}
