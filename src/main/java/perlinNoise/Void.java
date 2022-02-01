package perlinNoise;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Void extends PApplet {

    public static final boolean PERLIN_NOISE = false;
    public static final boolean WARPING = true;
    public static final boolean FLOW_FIELD = false;

    public static final boolean DEBUG = false;

    public static final float SCALE = 20;

    public PerlinNoise perlinNoise;
    public Warping warping;
    public FlowField flowField;
    public List<Emitter> emitters = new ArrayList<>();
    public List<FixedEmitter> fixedEmitters = new ArrayList<>();

    public PImage particleImage;

    public int frameIndex;

    public static void main(String... args) {
        PApplet.main("perlinNoise.Void");
    }

    public void settings() {
        size(1200, 1200);

        if (WARPING || PERLIN_NOISE)
            noLoop();
    }

    public void setup() {

        particleImage = loadImage("ParticleTexture.png");
        particleImage.resize(5, 5);

        if (PERLIN_NOISE)
            perlinNoise = new PerlinNoise(this);

        if (WARPING)
            warping = new Warping(this);

        if (FLOW_FIELD) {
            flowField = new FlowField(this);

            fixedEmitters.add(new FixedEmitter(this, FixedEmitter.Position.MID, color(0, 0, 255)));

            fixedEmitters.add(new FixedEmitter(this, FixedEmitter.Position.TOP, color(0, 255, 255)));
            fixedEmitters.add(new FixedEmitter(this, FixedEmitter.Position.LEFT, color(0, 255, 255)));

            emitters.add(new Emitter(this, new PVector(random(0, width), random(0, height)), color(0, 255, 0)));
            emitters.add(new Emitter(this, new PVector(random(0, width), random(0, height)), color(0, 255, 0)));
            emitters.add(new Emitter(this, new PVector(random(0, width), random(0, height)), color(0, 255, 0)));
        }

        frameRate(30);
        pixelDensity(1);
        background(0);
    }

    public void draw() {
        if (DEBUG)
            background(255);

        if (PERLIN_NOISE)
            perlinNoise.drawPerlinNoiseColored();

        if (WARPING)
            warping.pattern();

        if (FLOW_FIELD) {
            flowField.update();
            emitters.forEach(Emitter::update);
            fixedEmitters.forEach(FixedEmitter::update);
        }

//        if (frameIndex % 1 == 0)
//            clearPixels();

        frameIndex++;
    }

    // TODO fade out with sobol filter?
    public void clearPixels() {
        fill(0, 10f);
        rect(0, 0, width, height);
        int c = get(width / 2, height / 2);
        println("r:" + red(c) + ",g:" + green(c) + ",b:" + blue(c));
        loadPixels();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int index = x + y * width;
                var pixelColor = get(x, y);
                set(x, y, color(get(x, y), 0.1f));
            }
        }
        updatePixels();
    }
}