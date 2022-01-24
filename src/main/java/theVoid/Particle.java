package theVoid;

import agents.MyMath;
import processing.core.PVector;

public class Particle {

    private static final float MAXIMUM_SPEED = 2f;
    private static final int MINIMUM_LIFETIME = 10;
    private static final int MAXIMUM_LIFETIME = 240;
    private static final float VELOCITY_VARIANCE = 0.1f;
    private static final float FOLLOW_EMITTER_Value = 0.2f;
    private static final boolean KEEP_ALIVE = false;
    private static final boolean DRAW_PARTICLE_IMAGE = true;
    // only for aging particles
    private static float ALPHA_MULTIPLIER = 9f;

    private final Void theVoid;

    private final PVector velocity = new PVector();
    private final PVector position;
    private final int color;
    private final Emitter emitter;

    private final int initialLifetime;
    private int remainingLifetime;

    private final int maximumAge;
    private int age;

    public Particle(Void theVoid, PVector position, int color) {
        this.theVoid = theVoid;
        this.position = position;
        this.color = color;
        this.emitter = null;

        remainingLifetime = (int) MyMath.random(MINIMUM_LIFETIME, MAXIMUM_LIFETIME);
        initialLifetime = remainingLifetime;

        maximumAge = (int) MyMath.random(MINIMUM_LIFETIME, MAXIMUM_LIFETIME);
        age = MINIMUM_LIFETIME;
    }

    public Particle(Void theVoid, PVector position, int color, Emitter emitter) {
        this.theVoid = theVoid;
        this.position = position;
        this.color = color;
        this.emitter = emitter;

        remainingLifetime = (int) MyMath.random(MINIMUM_LIFETIME, MAXIMUM_LIFETIME);
        initialLifetime = remainingLifetime;

        maximumAge = (int) MyMath.random(MINIMUM_LIFETIME, MAXIMUM_LIFETIME);
        age = MINIMUM_LIFETIME;
    }

    public void update() {
        keepOnScreen();

        velocity
                .add(
                        theVoid.random(-VELOCITY_VARIANCE, VELOCITY_VARIANCE),
                        theVoid.random(-VELOCITY_VARIANCE, VELOCITY_VARIANCE)
                )
                .add(followEmitter())
                .add(followFlowField());
        velocity.limit(MAXIMUM_SPEED);
        position.add(velocity);

        age++;
        remainingLifetime--;

        draw();
    }

    public boolean isAlive() {
        return (age <= maximumAge || KEEP_ALIVE);
    }

    private PVector followFlowField() {
        return theVoid.flowField.getVectorForPosition(position);
    }

    private PVector followEmitter() {
        if (emitter == null)
            return new PVector();
        return PVector.sub(emitter.position, position).normalize().mult(FOLLOW_EMITTER_Value);
    }

    private void keepOnScreen() {
        if (position.x >= theVoid.width) {
            position.x = 0;
        } else if (position.x < 0) {
            position.x = theVoid.width;
        }
        if (position.y >= theVoid.height) {
            position.y = 0;
        } else if (position.y < 0) {
            position.y = theVoid.height;
        }
    }

    private void draw() {
        if (Void.DEBUG) {
            theVoid.fill(0);
            theVoid.ellipse(position.x, position.y, 4f, 4f);
            return;
        }

        // Show when half lifetime is over
        // Fade out when young or old
        float visibilityFactor;
        if (KEEP_ALIVE) {
            visibilityFactor = (float) age;
            ALPHA_MULTIPLIER  = 0.05f;
        } else {
            visibilityFactor = (float) (initialLifetime - Math.abs(initialLifetime - remainingLifetime * 2)) / initialLifetime;
        }

        if (DRAW_PARTICLE_IMAGE) {
            theVoid.tint(color, ALPHA_MULTIPLIER * visibilityFactor);
            theVoid.image(theVoid.particleImage, position.x, position.y);
        } else {
            theVoid.stroke(color, ALPHA_MULTIPLIER * visibilityFactor);
            theVoid.strokeWeight(1f);
            theVoid.point(position.x, position.y);
        }
    }
}