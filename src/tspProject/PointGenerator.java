package tspProject;

import java.util.Random;

/**
 * Created by Jolee on 2/28/2015.
 */
public class PointGenerator {

    private Vector max;
    static private Random random = new Random();

    public PointGenerator(Vector max) {
        this.max = max;
    }

    /**
     * @return A random number generator
     */
    protected Random getRandom() {
        return random;
    }

    protected Vector getVector() {
        return new Vector(random.nextDouble() * max.getX(),random.nextDouble() * max.getY());
    }
}
