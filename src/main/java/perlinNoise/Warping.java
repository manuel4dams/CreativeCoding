package perlinNoise;

import processing.core.PApplet;
import processing.core.PVector;

public class Warping {

    private final Void theVoid;

    public Warping(Void theVoid) {
        this.theVoid = theVoid;
    }

    // @see https://www.iquilezles.org/www/articles/warp/warp.htm
    public void pattern() {
        theVoid.loadPixels();
        var minS = 10f;
        var maxS = 0f;
        var data = new float[theVoid.width * theVoid.height];
        for (var y = 0; y < theVoid.height; y++) {
            for (var x = 0; x < theVoid.width; x++) {
                var index = (x + y * theVoid.width);

                var p = new PVector(x * 0.01f, y * 0.01f);
                var q = new PVector(
                        fbm(p),
                        fbm(PVector.add(p, new PVector(5.2f, 1.3f)))
                );
                var r = new PVector(
                        fbm(PVector.add(p, PVector.mult(q, 4.0f)).add(new PVector(1.7f, 9.2f))),
                        fbm(PVector.add(p, PVector.mult(q, 4.0f)).add(new PVector(8.3f, 2.8f)))
                );
                float s = fbm(PVector.add(p, PVector.mult(r, 4.0f)));

                minS = PApplet.min(minS, s);
                maxS = PApplet.max(maxS, s);

                data[index] = s;
            }
        }

        for (var y = 0; y < theVoid.height; y++) {
            for (var x = 0; x < theVoid.width; x++) {
                var index = (x + y * theVoid.width);
                var s = data[index];
                var r = PApplet.map(s, minS, maxS, 0f, 1f);
                var t = PApplet.round(r * 3f) / 3f;
                var color = palette(
                        t,
                        new PVector(0.5f, 0.5f, 0.5f),
                        new PVector(0.5f, 0.5f, 0.5f),
                        new PVector(2.0f, 1.0f, 0.0f),
                        new PVector(0.5f, 0.20f, 0.25f)
                );
                theVoid.pixels[index] = color;
            }
        }

        theVoid.updatePixels();
    }

    // TODO search better color values
    public float fbm(PVector pVector) {
        var result = 0f;
        var heightSum = 0f;

        var frequency = 1.0f;
        var amplitude = 1.0f;
        var layers = 4;
        for (var i = 0; i < layers; i++) {
            result += theVoid.noise(
                    pVector.x * frequency,
                    pVector.y * frequency
            ) * amplitude;
            heightSum += amplitude;

            frequency *= 2f;
            amplitude *= 0.5f;
        }

//        return min(max(0, result), 1);
        return result / heightSum;
    }

    // @see https://www.iquilezles.org/www/articles/palettes/palettes.htm
    public int palette(float t, PVector a, PVector b, PVector c, PVector d) {
        var angle = 6.28318f;

        var o1 = PVector.mult(c, t);
        var o2 = PVector.add(o1, d);
        var o3 = PVector.mult(o2, angle);
        var o4 = new PVector(PApplet.cos(o3.x), PApplet.cos(o3.y));
        var o5 = new PVector(b.x * o4.x, b.y * o4.y);
        var o6 = PVector.add(a, o5);

        return theVoid.color(o6.x * 255, o6.y * 255, o6.z * 255, 255);
    }
}
