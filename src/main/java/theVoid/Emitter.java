package theVoid;

import agents.MyMath;
import processing.core.PVector;

import java.util.ArrayList;

public class Emitter {
    // emit interval in second
    private static final int EMIT_INTERVAL = 3;
    private static final int PARTICLE_QUANTITY_MINIMUM = 10;
    private static final int PARTICLE_QUANTITY_MAXIMUM = 100;
    private static final float MAXIMUM_SPEED = 3f;
    private static final float RADIUS = 20f;
    private static final float VELOCITY_VARIANCE = 1f;
    // 0 for no jump
    private static final float JUMP_INTERVAL = 120f;
    private static final boolean DRAW_EMITTER = false;

    private final Void theVoid;

    private final PVector velocity = new PVector();
    public final PVector position;
    private final int color;
    private final ArrayList<Particle> particles = new ArrayList<>();

    public Emitter(Void theVoid, PVector position, int color) {
        this.theVoid = theVoid;
        this.position = position;
        this.color = color;
    }

    public void update() {
        if (JUMP_INTERVAL > 0 && theVoid.frameIndex % JUMP_INTERVAL == 0) {
            position.x = theVoid.random(0, theVoid.width - RADIUS);
            position.y = theVoid.random(0, theVoid.height - RADIUS);
        }

        velocity
                .add(followFlowFieldInverse())
                .add(keepOnScreen())
                .add(
                        theVoid.random(-VELOCITY_VARIANCE, VELOCITY_VARIANCE),
                        theVoid.random(-VELOCITY_VARIANCE, VELOCITY_VARIANCE)
                )
                .limit(MAXIMUM_SPEED);

        position.add(velocity);

        undoEdgeCollision();

        if (theVoid.frameIndex % EMIT_INTERVAL == 0)
            emit();

        if (Void.DEBUG)
            debugDraw();

        if (DRAW_EMITTER)
            draw();

        updateParticles();
    }

    private void updateParticles() {
        ArrayList<Particle> particlesCopy = new ArrayList<>(particles);
        for (var particle : particlesCopy) {
            particle.update();
            if (!particle.isAlive())
                particles.remove(particle);
        }
    }

    private void emit() {
        var quantity = (int) theVoid.random(PARTICLE_QUANTITY_MINIMUM, PARTICLE_QUANTITY_MAXIMUM);

        for (int i = 0; i < quantity; i++) {
            var position = this.position.copy();
            position.x = position.x + MyMath.random(-RADIUS, RADIUS);
            position.y = position.y + MyMath.random(-RADIUS, RADIUS);
            particles.add(new Particle(theVoid, position, color, this));
        }
    }

    private PVector followFlowFieldInverse() {
        return theVoid.flowField.getVectorForPosition(position).mult(-1f);
    }

    private PVector keepOnScreen() {
        var v = new PVector();
        if (position.x > theVoid.width - 100)
            v.x = -1;
        if (position.x < 100)
            v.x = 1;
        if (position.y > theVoid.height - 100)
            v.y = -1;
        if (position.y < 100)
            v.y = 1;
        return v.mult(0.5f);
    }

    private void undoEdgeCollision() {
        if (position.x > theVoid.width)
            position.x = theVoid.width - RADIUS;
        else if (position.x < 0)
            position.x = RADIUS;

        if (position.y > theVoid.height)
            position.y = theVoid.height - RADIUS;
        else if (position.y < 0)
            position.y = RADIUS;
    }

    private void draw() {
        for (int i = 0; i <= RADIUS; i++) {
            theVoid.noStroke();
            theVoid.fill(color, 8f);
            theVoid.ellipse(position.x,
                    position.y,
                    i * 2f,
                    i * 2f);
        }
    }

    private void debugDraw() {
        theVoid.fill(0);
        theVoid.ellipse(position.x,
                position.y,
                RADIUS * 2f,
                RADIUS * 2f);
    }
}