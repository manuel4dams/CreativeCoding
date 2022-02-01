package agents;

import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

public class Agent {

    private static final float ALIGN_VALUE = 0.3f;
    private static final float ALIGN_RADIUS = 150f;
    private static final float COHESION_VALUE = 0.001f;
    private static final float COHESION_RADIUS = 200f;
    private static final float SEPARATION_VALUE = 10f;
    private static final float SEPARATION_RADIUS = 200f;
    private static final float maximumSpeed = 5f;

    private final float borderWidth;
    private final float borderHeight;

    private PVector position;
    private float rotation;
    private float radius;

    private Color color;

    private PVector velocity = new PVector();
    private PVector acceleration = new PVector();

    private float age;
    private float lifetime;

    public Agent(float width, float height, PVector position, Color color, float age) {
        borderWidth = width;
        borderHeight = height;
        this.color = color;
        this.position = position;
        radius = 1f;
        this.age = age;
        lifetime = 100f;
    }

    public void update(ArrayList<Agent> agents) {
        acceleration
                .add(randomVelocity())
                .add(keepOnScreen())
                .add(align(agents).mult(ALIGN_VALUE))
                .add(cohere(agents).mult(COHESION_VALUE))
                .add(separate(agents).mult(SEPARATION_VALUE))
        ;
        velocity.add(acceleration);
        velocity.limit(maximumSpeed);
        undoEdgeCollision();
        position.add(velocity);
        rotation = (float) Math.atan2(velocity.y, velocity.x);
        acceleration.mult(0);
        age = age + 0.1f;
    }

    private PVector randomVelocity() {
        return new PVector(
                MyMath.random(-1f, 1f),
                MyMath.random(-1f, 1f)
        ).normalize().mult(1.5f);
    }

    private PVector keepOnScreen() {
        PVector v = new PVector();
        if (position.x > borderWidth - 100)
            v.x = -1;
        if (position.x < 100)
            v.x = 1;
        if (position.y > borderHeight - 100)
            v.y = -1;
        if (position.y < 100)
            v.y = 1;
        return v.mult(0.5f);
    }

    private PVector align(ArrayList<Agent> agents) {
        PVector alignment = new PVector();
        var count = 0;
        for (Agent other : agents) {
            if (this == other)
                continue;
            if (position.dist(other.position) > ALIGN_RADIUS)
                continue;
            count++;
            alignment.add(other.velocity);
        }

        if (count > 0)
            return alignment.div(count);
        return new PVector();
    }

    private PVector cohere(ArrayList<Agent> agents) {
        PVector sum = new PVector();
        var count = 0;
        for (Agent other : agents) {
            if (this == other)
                continue;
            if (this.position.dist(other.position) > COHESION_RADIUS)
                continue;
            count++;
            sum.add(other.position);
        }

        if (count > 0)
            return sum.div(count).sub(this.position);
        return new PVector();
    }

    private PVector separate(ArrayList<Agent> agents) {
//        var minDistance = Float.MAX_VALUE;
//        Agent minAgent = null;
//        for (Agent other : agents) {
//            if (this == other)
//                continue;
//            var distance = position.dist(other.position);
//            if (distance < minDistance) {
//                minDistance = distance;
//                minAgent = other;
//            }
//        }
//
//        if (minAgent == null)
//            return new PVector();
//
//        var offset = PVector.sub(position, minAgent.position);
//
//        var offsetLength = offset.mag();
//        if (offsetLength < SEPARATION_RADIUS) {
//            return offset.mult(SEPARATION_RADIUS - offsetLength);
//        }
//        return new PVector();
        // from Daniel https://fbe-gitlab.hs-weingarten.de/scherzer/creative-coding/-/blob/master/8-agents/agents/Agent.pde
        PVector sum = new PVector(0, 0);
        int count = 0;
        for (Agent agent : agents) {
            PVector distance = PVector.sub(position, agent.position);
            float distanceSquared = distance.magSq();
            if ((0 < distanceSquared) && (distanceSquared < SEPARATION_RADIUS)) {
                distance.div(distanceSquared); // importance fall-off
                sum.add(distance);
                ++count;
            }
        }
        if (count > 0)
            return sum;
        return new PVector();
    }

    private void undoEdgeCollision() {
        if (position.x > borderWidth) {
            position.x = borderWidth - radius;
        } else if (position.x < 0) {
            position.x = radius;
        }
        if (position.y > borderHeight) {
            position.y = borderHeight - radius;
        } else if (position.y < 0) {
            position.y = radius;
        }
    }

    public boolean isAlive() {
        return age <= lifetime;
    }

    public float getAge() {
        return age;
    }

    public PVector getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public Color getColor() {
        return color;
    }

    public float getRadius() {
        return radius;
    }
}
