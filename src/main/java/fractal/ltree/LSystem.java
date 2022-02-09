package fractal.ltree;

import fractal.Fractal;
import fractal.FractalImplementation;
import processing.core.PApplet;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @see <a href="https://www.youtube.com/watch?v=E1B4UoSQMFw">CodingTrain</a>
 * @see <a href="https://en.wikipedia.org/wiki/L-system#L-system_structure">L-system</a>
 */
public class LSystem implements FractalImplementation {

    private static final boolean DEBUG = false;

    private final Fractal fractal;
    private final Map<Character, String> rules;
    private final String axiom;
    private final BiConsumer<Character, Integer> drawCall;

    private String sentence;
    private int depth;

    public LSystem(Fractal fractal, Map<Character, String> rules, String axiom, int depth, BiConsumer<Character, Integer> drawCall) {
        this.fractal = fractal;
        this.rules = rules;
        this.axiom = axiom;
        this.drawCall = drawCall;
        generate(depth);
    }

    private void generate(int depth) {
        this.depth = depth;
        sentence = axiom;

        for (int n = 0; n < depth; n++) {
            var result = new StringBuilder();
            for (int i = 0; i < sentence.length(); i++) {
                var key = sentence.charAt(i);
                var value = rules.get(key);
                if (value == null)
                    value = String.valueOf(key);
                result.append(value);
            }
            sentence = result.toString();
        }

        if (DEBUG)
            printSentence();
    }

    public void draw() {
        var d = -1;
        fractal.translate(fractal.width / 2f, fractal.height);
        fractal.stroke(255, 100);

        for (var j = 0; j < sentence.length(); j++) {
            var character = sentence.charAt(j);
            drawCall.accept(character, depth);
        }
    }

    private void printSentence() {
        System.out.println(sentence);
    }
}
