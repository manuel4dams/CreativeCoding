package particleSystem;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;

public class ParticleSystem extends PApplet {

    private final ArrayList<Particle> particles = new ArrayList<>();
    private int defaultParticleCount = 200;
    private PImage particleImage;
    private PVector windForce = new PVector(0, 0);

    public static void main(String... args) {
        PApplet.main("particleSystem.ParticleSystem");
    }

    public void settings() {
        size(1024, 1024);
    }

    public void setup() {
        background(0);
        imageMode(CENTER);
        particleImage = loadImage("ParticleTexture.png");
        particleImage.resize(200, 200);
        while (defaultParticleCount > 0) {
            particles.add(new Particle());
            defaultParticleCount--;
        }
    }

    public void draw() {
        background(0);
        updateWind();
        updateParticles();
        drawWindIndicator();
        drawParticles();
    }

    private void updateWind() {
        if (frameCount % 60 == 0) {
            windForce = PVector.mult(PVector.random2D(), 1f);
        }
    }

    private void updateParticles() {
        for (Particle particle : particles) {
            if (!particle.isAlive())
                particle.reset(
                        new PVector(width / 2f, height / 1.1f),
                        new PVector(random(-5f, 5f), random(-20f, 0f)),
                        random(1, 100));

            particle.setVelocity(particle.getVelocity().add(new PVector(random(-0.1f, 0.1f), -0.1f).add(windForce)));
            particle.setLocation(particle.getLocation().add(particle.getVelocity()));
            particle.setLifetime(particle.getLifetime() - 3f);
        }
    }

    private void drawParticles() {
        for (Particle particle : particles) {
            tint(0.01f * millis() % 256, 0.02f * millis() % 256, 0.03f * millis() % 256, particle.getLifetime());
            image(particleImage, particle.getLocation().x, particle.getLocation().y);
        }
    }

    // copied from https://fbe-gitlab.hs-weingarten.de/scherzer/creative-coding/-/blob/master/Week-7/particleSystem/particleSystem.pde
    private void drawWindIndicator() {
        stroke(255);
        PVector start = new PVector(width / 2f, height / 2f);
        PVector end = PVector.add(start, PVector.mult(windForce, 100));
        line(start.x, start.y, end.x, end.y);
        PVector a = PVector.add(end, PVector.mult(windForce, 50));
        PVector b = PVector.add(end, windForce.copy().rotate(HALF_PI).mult(25));
        PVector c = PVector.add(end, windForce.copy().rotate(-HALF_PI).mult(25));
        triangle(a.x, a.y, b.x, b.y, c.x, c.y);
        noStroke();
    }
}
