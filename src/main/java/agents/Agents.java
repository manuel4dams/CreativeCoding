package agents;

import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

public class Agents extends PApplet {
    private static final boolean DEBUG = true;

    private final ArrayList<ArrayList<Agent>> swarms = new ArrayList<>();

    public static void main(String... args) {
        PApplet.main("agents.Agents");
    }

    public void settings() {
        size(1000, 1000);
    }

    public void setup() {
        frameRate(60);
        background(0);
        ellipseMode(CENTER);
    }

    int frameIndex;
    public void draw() {
        background(0);
        updateAgents();
        drawAgents();

        if (frameIndex % 2 == 0)
            saveFrame("TMP/tmp_" + frameIndex + ".png");
        frameIndex++;
    }

    public void mouseClicked() {
        addSwarm(new PVector(mouseX, mouseY));
    }

    private void addSwarm(PVector centerPoint) {
        ArrayList<Agent> swarm = new ArrayList<>();
        Color swarmColor = new Color(
                1f - pow(random(0f, 1f), 1f),
                1f - pow(random(0f, 1f), 2f),
                1f - pow(random(0f, 1f), 3f),
                1f
        );
        for (int agentCount = (int) random(1, 100); agentCount > 0; agentCount--) {
            swarm.add(new Agent(
                    width,
                    height,
                    new PVector(
                            random(centerPoint.x - 20f, centerPoint.x + 20f),
                            random(centerPoint.y - 20f, centerPoint.y + 20f)),
                    new Color(
                            max(0f, min(1f, (swarmColor.getRed() / 255f) * random(0.8f, 1.2f))),
                            max(0f, min(1f, (swarmColor.getGreen() / 255f) * random(0.8f, 1.2f))),
                            max(0f, min(1f, (swarmColor.getBlue() / 255f) * random(0.8f, 1.2f)))
                    ),
                    random(0, 100)
            ));
        }
        swarms.add(swarm);
    }

    private void updateAgents() {
        ArrayList<ArrayList<Agent>> swarmsCopy = new ArrayList<>(swarms);
        for (ArrayList<Agent> swarm : swarmsCopy) {
            if (swarm.size() > 0) {
                ArrayList<Agent> swarmCopy = new ArrayList<>(swarm);
                for (Agent agent : swarmCopy) {
                    if (!agent.isAlive()) {
                        swarm.remove(agent);
                    }
                }
                for (Agent agent : swarm) {
                    agent.update(swarm);
                }
                continue;
            }
            swarms.remove(swarm);
        }
    }

    private void drawAgents() {
        for (ArrayList<Agent> swarm : swarms) {
            for (Agent agent : swarm) {
                pushMatrix();
                translate(agent.getPosition().x, agent.getPosition().y);
                rotate(agent.getRotation());

                fill(
                        min(255, agent.getColor().getRed() * noise(agent.getAge())),
                        min(255, agent.getColor().getGreen() * noise(agent.getAge() + 0.01f)),
                        min(255, agent.getColor().getBlue() * noise(agent.getAge() + 0.02f)),
                        min(255, agent.getColor().getAlpha() * (1 - agent.getAge() / 100f))
                );
                noStroke();

                beginShape();
                var xOffset = 0;
                for (float a = 0; a < TWO_PI; a += 0.1f) {
                    var offset = map(noise(xOffset, agent.getAge() * 1.1f), 0, 1, -6, 6);
                    var radius = agent.getRadius() * (agent.getAge() * 0.2f) + offset;
                    vertex(
                            radius * cos(a),
                            radius * sin(a));
                    xOffset += 1.1f;
                }
                endShape(CLOSE);

                if (DEBUG){
                    stroke(255);
                    line(0,0,10,0);
                }
                    popMatrix();
            }
        }
    }
}
