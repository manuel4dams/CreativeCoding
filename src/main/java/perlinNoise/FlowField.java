package perlinNoise;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;

public class FlowField {

    private final Void theVoid;

    private final ArrayList<PVector> flowField = new ArrayList<>();

    // TODO other flowfield types, maybe https://www.youtube.com/watch?v=ZI1dmHv3MeM
    public FlowField(Void theVoid) {
        this.theVoid = theVoid;
    }

    public void update() {
        updateFlowField();
    }

    // @see https://www.youtube.com/watch?v=BjoM9oKOAKY&list=PLRqwX-V7Uu6bgPNQAdxQZpJuJCjeOr7VD&index=7
    public void updateFlowField() {
        flowField.clear();
        var limitWidth = Math.ceil(theVoid.width / Void.SCALE);
        var limit = limitWidth * Math.ceil(theVoid.height / Void.SCALE);
        var scaledWith = (int) limitWidth;
        for (var i = 0; i < limit; i++) {
            var x = i % scaledWith;
            var y = i / scaledWith;
            var noise = theVoid.noise(
                    x * 0.05f,
                    y * 0.05f,
                    theVoid.frameIndex * 0.005f
            );
            var angle =  PApplet.pow(noise, 1f) * PConstants.TWO_PI * 5f;
            flowField.add(PVector.fromAngle(angle).setMag(0.2f));

            if (Void.DEBUG) {
                theVoid.translate(x * Void.SCALE, y * Void.SCALE);
                theVoid.rotate(angle);
                theVoid.stroke(0);
                theVoid.noFill();
                theVoid.scale(0.25f);
                theVoid.triangle(0, -10, 40, 0, 0, 10);
                theVoid.resetMatrix();

                // Draw noise as square
                if (false) {
                    theVoid.translate(x * Void.SCALE, y * Void.SCALE);
                    theVoid.noStroke();
                    theVoid.fill(angle * 255, 255);
                    theVoid.rect(0, 0, Void.SCALE, Void.SCALE);
                    theVoid.resetMatrix();
                }
            }
        }
    }

    public PVector getVectorForPosition(PVector position) {
        var x = position.x;
        var y = position.y;
        if (x > theVoid.width)
            x = theVoid.width;
        else if (x < 0)
            x = 0f;

        if (y > theVoid.height)
            y = theVoid.height;
        else if (y < 0)
            y = 0f;

        var gridX = (int) (x / Void.SCALE);
        var gridY = (int) (y / Void.SCALE);
        var index = gridX + gridY * (int) Math.ceil(theVoid.height / Void.SCALE);

        // For fail safety also check if the index is out of bounds.
        if (index >= flowField.size()) {
           System.out.println("Out of index");
            return new PVector();
        }

        return flowField.get(index);
    }
}
