package spaceInvader;

import processing.core.PApplet;

public class SpaceInvader extends PApplet {
    public int[] colors;
    public final float GRID_SIZE = 7;

    public static void main(String... args) {
        PApplet.main("spaceInvader.SpaceInvader");
    }

    public void settings() {
        size(128, 128);
    }

    public void setup() {
        frameRate(1f);
    }

    public void draw() {
        colors = new int[]{
                color(0),
                color(0),
                color(0),
                generateRandomColor(),
                generateRandomColor(),
                generateRandomColor()
        };

        noStroke();
        for (var row = 0; row < GRID_SIZE; row++) {
            for (var column = 0; column < (GRID_SIZE / 2); column++) {
                fill(colors[(int) random(colors.length - 1)]);

                rect(
                        ceil(column * (width / GRID_SIZE)),
                        ceil(row * (height / GRID_SIZE)),
                        ceil(width / GRID_SIZE),
                        ceil(height / GRID_SIZE)
                );
                rect(
                        ceil((GRID_SIZE - 1 - column) * (width / GRID_SIZE)),
                        ceil(row * (height / GRID_SIZE)),
                        ceil(width / GRID_SIZE),
                        ceil(height / GRID_SIZE)
                );
            }
        }
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
