package agents;

public class MyMath {
    public static float random(float min, float max) {
        return (float) Math.random() * (max - min) + min;
    }
}
