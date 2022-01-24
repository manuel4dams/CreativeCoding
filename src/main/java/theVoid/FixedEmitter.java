package theVoid;

import processing.core.PVector;

import java.util.ArrayList;

public class FixedEmitter {

    public enum Position {
        TOP,
        BOTTOM,
        RIGHT,
        LEFT,
        MID
    }

    // emit interval in second
    private static final int EMIT_INTERVAL = 3;
    private static final int PARTICLE_QUANTITY_MINIMUM = 10;
    private static final int PARTICLE_QUANTITY_MAXIMUM = 100;
    private final int color;
    private static final boolean DRAW_EMITTER = false;

    private final Position position;
    private static final int HEIGHT_OFFSET = 10;
    private static final int WIDTH_OFFSET = 10;
    private static final int MID_OFFSET = 10;

    private final Void theVoid;

    private final ArrayList<Particle> particles = new ArrayList<>();

    public FixedEmitter(Void theVoid, Position position, int color) {
        this.theVoid = theVoid;
        this.position = position;
        this.color = color;
    }

    public void update() {
        if (theVoid.frameIndex % EMIT_INTERVAL == 0)
            emit();

        if (Void.DEBUG)
            debugDraw();

        updateParticles();

        if (Void.DEBUG)
            debugDraw();

        if (DRAW_EMITTER)
            draw();
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
            particles.add(new Particle(theVoid, getParticlePosition(), color));
        }
    }

    private PVector getParticlePosition() {
        PVector particlePosition = new PVector();

        switch (position) {
            case TOP:
                particlePosition.x = theVoid.random(0, theVoid.width);
                particlePosition.y = theVoid.random(0, HEIGHT_OFFSET);
                break;
            case BOTTOM:
                particlePosition.x = theVoid.random(0, theVoid.width);
                particlePosition.y = theVoid.random(theVoid.height - HEIGHT_OFFSET, theVoid.height);
                break;
            case RIGHT:
                particlePosition.x = theVoid.random(theVoid.width - WIDTH_OFFSET, theVoid.width);
                particlePosition.y = theVoid.random(0, theVoid.height);
                break;
            case LEFT:
                particlePosition.x = theVoid.random(0, WIDTH_OFFSET);
                particlePosition.y = theVoid.random(0, theVoid.height);
                break;
            case MID:
                particlePosition.x = theVoid.random(theVoid.width / 2f - WIDTH_OFFSET, theVoid.width / 2f + WIDTH_OFFSET);
                particlePosition.y = theVoid.random(theVoid.height / 2f - HEIGHT_OFFSET, theVoid.height / 2f + HEIGHT_OFFSET);
                break;
            default:
                return null;
        }
        return particlePosition;
    }

    private void draw() {
        theVoid.noStroke();
        switch (position) {
            case TOP:
                theVoid.fill(color, 8f);
                theVoid.rect(0, 0, theVoid.width, HEIGHT_OFFSET);
                break;
            case BOTTOM:
                theVoid.fill(color, 8f);
                theVoid.rect(0, theVoid.height - HEIGHT_OFFSET, theVoid.width, HEIGHT_OFFSET);
                break;
            case RIGHT:
                theVoid.fill(color, 8f);
                theVoid.rect(theVoid.width - WIDTH_OFFSET, 0, WIDTH_OFFSET, theVoid.height);
                break;
            case LEFT:
                theVoid.fill(color, 8f);
                theVoid.rect(0, 0, WIDTH_OFFSET, theVoid.height);
                break;
            case MID:
                theVoid.fill(color, 8f);
                for (int i = 0; i <= MID_OFFSET; i++) {
                    theVoid.ellipse(theVoid.width / 2f,
                            theVoid.height / 2f,
                            i * 2f,
                            i * 2f);
                }
                break;
            default:
                break;
        }
    }

    private void debugDraw() {
        theVoid.noStroke();
        switch (position) {
            case TOP:
                theVoid.fill(0);
                theVoid.rect(0, 0, theVoid.width, HEIGHT_OFFSET);
                break;
            case BOTTOM:
                theVoid.fill(0);
                theVoid.rect(0, theVoid.height - HEIGHT_OFFSET, theVoid.width, HEIGHT_OFFSET);
                break;
            case RIGHT:
                theVoid.fill(0);
                theVoid.rect(theVoid.width - WIDTH_OFFSET, 0, WIDTH_OFFSET, theVoid.height);
                break;
            case LEFT:
                theVoid.fill(0);
                theVoid.rect(0, 0, WIDTH_OFFSET, theVoid.height);
                break;
            case MID:
                theVoid.fill(0);
                theVoid.rect(theVoid.width / 2f - MID_OFFSET / 2f,
                        theVoid.height / 2f - MID_OFFSET / 2f,
                        MID_OFFSET,
                        MID_OFFSET
                );
                break;
            default:
                break;
        }
    }
}
