package theVoid;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Void extends PApplet {

    public static final boolean DEBUG = false;
    public static final float SCALE = 10;

    public List<Emitter> emitters = new ArrayList<>();
    public List<FixedEmitter> fixedEmitters = new ArrayList<>();
    public FlowField flowField;

    public PImage particleImage;

    public int frameIndex;

    public static void main(String... args) {
        PApplet.main("theVoid.Void");
    }

    public void settings() {
        size(1024, 1024);
    }

    public void setup() {
        particleImage = loadImage("ParticleTexture.png");
        particleImage.resize(10, 10);

        flowField = new FlowField(this);

//        emitters.add(new Emitter(this, new PVector(random(0, width), random(0, height)), color(0, 255, 255)));
//        emitters.add(new Emitter(this, new PVector(random(0, width), random(0, height)), color(255, 0, 0)));
        fixedEmitters.add(new FixedEmitter(this, FixedEmitter.Position.LEFT, color(0, 255, 255)));
        fixedEmitters.add(new FixedEmitter(this, FixedEmitter.Position.TOP, color(255, 0, 0)));
        fixedEmitters.add(new FixedEmitter(this, FixedEmitter.Position.MID, color(255, 255, 255)));

        emitters.add(new Emitter(this, new PVector(random(0, width), random(0, height)), color(0, 0, 0)));
        emitters.add(new Emitter(this, new PVector(random(0, width), random(0, height)), color(0, 0, 0)));
        emitters.add(new Emitter(this, new PVector(random(0, width), random(0, height)), color(0, 0, 0)));

        frameRate(60);
        pixelDensity(1);
        background(0);
    }

    public void draw() {
        if (DEBUG)
            background(255);
        flowField.update();
        emitters.forEach(Emitter::update);
        fixedEmitters.forEach(FixedEmitter::update);
        frameIndex++;
    }



    // @see https://www.youtube.com/watch?v=ikwNrFvnL3g&list=PLRqwX-V7Uu6bgPNQAdxQZpJuJCjeOr7VD&index=4
    public void drawPerlinNoiseUncolored() {
        loadPixels();
        for (var i = 0; i < width * height; i++) {
            var x = i % width;
            var y = i / width;
            var r = noise(x * 0.01f, y * 0.01f) * 255;
            pixels[i] = color(r, r, r, 255);
        }
        updatePixels();
    }

    public void drawPerlinNoiseColored() {
        loadPixels();
        for (var y = 0; y < height; y++) {
            for (var x = 0; x < width; x++) {
                var index = (x + y * width);
                var r = pow(noise(x * 0.01f, y * 0.01f), 1) * 255;
                var g = pow(noise((x + width) * 0.01f, (y + height) * 0.005f), 1) * 255;
                var b = pow(noise(x * 0.01f, y * 0.01f), 1) * 255;
                pixels[index] = color(r, g, b, 255);
            }
        }
        updatePixels();
    }
}