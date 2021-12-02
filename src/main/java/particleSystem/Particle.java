package particleSystem;

import processing.core.PVector;

public class Particle {

    private PVector location;
    private PVector velocity;

    private float lifetime = 0;

    public void reset(PVector location, PVector velocity, float lifetime){
        this.location = location;
        this.lifetime = lifetime;
        this.velocity = velocity;
    }

    public boolean isAlive() {
        return lifetime > 0;
    }

    public PVector getLocation() {
        return location;
    }

    public void setLocation(PVector location) {
        this.location = location;
    }

    public PVector getVelocity() {
        return velocity;
    }

    public void setVelocity(PVector velocity) {
        this.velocity = velocity;
    }

    public float getLifetime() {
        return lifetime;
    }

    public void setLifetime(float lifetime) {
        this.lifetime = lifetime;
    }
}
