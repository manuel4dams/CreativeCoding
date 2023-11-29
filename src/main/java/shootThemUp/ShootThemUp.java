package shootThemUp;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class ShootThemUp extends PApplet {

    static ArrayList<SpaceInvader> spaceInvaders = new ArrayList<>();
    private static final int INVADER_SIZE = 7;
    static int COUNTER;
    static int LAST_ENEMY_CREATED;
    static boolean GAME_OVER;

    public static void main(String... args) {
        PApplet.main("shootThemUp.ShootThemUp");
    }

    public void settings() {
        size(1024, 1024);
        noSmooth();
    }

    public void setup() {
        frameRate(60);
        background(0);
    }

    int frameIndex;

    public void draw() {
        if (!GAME_OVER) {
            background(0);
            // 200f defines creation interval
            if (millis() - 600f > LAST_ENEMY_CREATED) {
                AddInvader();
                LAST_ENEMY_CREATED = millis();
            }
            updateInvaders();
            drawInvaders();
            drawReticle();
            updateDrawCounter();
        } else {
            spaceInvaders.clear();
            COUNTER = 0;
            GAME_OVER = false;
        }

        if (frameIndex % 10 == 0)
            saveFrame("TMP/tmp_" + frameIndex + ".png");
        frameIndex++;
    }

    private void AddInvader() {
        SpaceInvader spaceInvader = new SpaceInvader(
                random(0, width),
                random(0f, -20f),
                20f,
                random(1f, 5f),
                createInvader());
        spaceInvaders.add(spaceInvader);
    }

    private void updateInvaders() {
        for (SpaceInvader spaceinvader : spaceInvaders) {
            spaceinvader.update();
            if (spaceinvader.getPositionY() >= height) {
                GAME_OVER = true;
            }
        }
    }

    private void drawInvaders() {
        for (SpaceInvader spaceinvader : spaceInvaders) {
            image(spaceinvader.getInvaderImage(), spaceinvader.getPositionX() - 20f, spaceinvader.getPositionY() - 20f, 40, 40);
        }
    }

    private void drawReticle() {
        stroke(255);
        strokeWeight(2);
        line(mouseX, mouseY - 20, mouseX, mouseY + 20);
        line(mouseX - 20, mouseY, mouseX + 20, mouseY);
    }

    public void mousePressed() {
        CheckMouseHit();
    }

    private void CheckMouseHit() {
        ArrayList<SpaceInvader> spaceInvadersCopy = new ArrayList<>(spaceInvaders);
        for (SpaceInvader spaceInvader : spaceInvadersCopy) {
            float distance = dist(
                    mouseX,
                    mouseY,
                    spaceInvader.getPositionX(),
                    spaceInvader.getPositionY());
            println(distance);
            // 4f is the mouse hit circle in the center of the reticle
            if ((distance < spaceInvader.getRadius() + 4f)) {
                spaceInvaders.remove(spaceInvader);
                COUNTER++;
            }
        }
    }

    private void updateDrawCounter() {
        textSize(50);
        fill(255);
        text(COUNTER, 20, 60);
    }

    private PImage createInvader() {
        var colors = new int[]{
                color(0, 0),
                color(0, 0),
                color(0, 0),
                generateRandomColor(),
                generateRandomColor(),
                generateRandomColor()
        };
        var image = createImage(INVADER_SIZE, INVADER_SIZE, ARGB);

        for (var row = 0; row < INVADER_SIZE; row++) {
            for (var column = 0; column < ((float) INVADER_SIZE / 2); column++) {
                var color = colors[(int) random(colors.length - 1)];
                image.set(column, row, color);
                image.set(INVADER_SIZE - 1 - column, row, color);
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
