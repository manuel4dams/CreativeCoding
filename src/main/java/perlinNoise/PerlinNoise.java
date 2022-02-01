package perlinNoise;

import processing.core.PApplet;

public class PerlinNoise {

    private final Void theVoid;

    public PerlinNoise(Void theVoid) {
        this.theVoid = theVoid;
    }

    // @see https://www.youtube.com/watch?v=ikwNrFvnL3g&list=PLRqwX-V7Uu6bgPNQAdxQZpJuJCjeOr7VD&index=4
    public void drawPerlinNoiseUncolored() {
        theVoid.loadPixels();
        for (var i = 0; i < theVoid.width * theVoid.height; i++) {
            var x = i % theVoid.width;
            var y = i / theVoid.width;
            var r = theVoid.noise(x * 0.01f, y * 0.01f) * 255;
            theVoid.pixels[i] = theVoid.color(r, r, r, 255);
        }
        theVoid.updatePixels();
    }

    public void drawPerlinNoiseColored() {
        theVoid.loadPixels();
        for (var y = 0; y < theVoid.height; y++) {
            for (var x = 0; x < theVoid.width; x++) {
                var index = (x + y * theVoid.width);
                var r = PApplet.pow(theVoid.noise(x * 0.01f, y * 0.01f), 1) * 255;
                var g = PApplet.pow(theVoid.noise((x + theVoid.width) * 0.01f, (y + theVoid.height) * 0.005f), 1) * 255;
                var b = PApplet.pow(theVoid.noise(x * 0.01f, y * 0.01f), 1) * 255;
                theVoid.pixels[index] = theVoid.color(r, g, b, 255);
            }
        }
        theVoid.updatePixels();
    }
}
