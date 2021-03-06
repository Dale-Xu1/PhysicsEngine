package physics.engine;

import javafx.animation.AnimationTimer;
import physics.engine.body.Body;
import physics.engine.body.collision.Collision;
import physics.engine.body.collision.CollisionDetector;
import physics.engine.math.Vector2;

import java.util.List;

public class Engine
{

    private static final float DELTA = 0.02f;
    private static final int ITERATIONS = 15;
    private static final float RATE = 0.8f;


    private class Timer extends AnimationTimer
    {
        private long previousTime;
        private long accumulatedTime = 0;

        @Override
        public void start()
        {
            super.start();
            previousTime = System.currentTimeMillis();
        }

        @Override
        public void handle(long now)
        {
            // Accumulate lagged time
            long currentTime = System.currentTimeMillis();

            accumulatedTime += currentTime - previousTime;
            previousTime = currentTime;

            // Update bodies
            while (accumulatedTime > delay)
            {
                update();
                accumulatedTime -= delay;
            }
        }
    }

    private final World world;
    private final Timer timer = new Timer();

    private final CollisionDetector detector = new CollisionDetector();

    private final float delta; // Used to make sure movements occur in unit rates
    private final int delay;

    private final int iterations;
    private final float rate;


    public Engine(World world, float delta, int iterations, float rate)
    {
        this.world = world;

        this.delta = delta;
        this.delay = (int) (delta * 1000);

        this.iterations = iterations;
        this.rate = rate;
    }

    public Engine(World world, float delta)
    {
        this(world, delta, ITERATIONS, RATE);
    }

    public Engine(World world)
    {
        this(world, DELTA);
    }


    public void start()
    {
        timer.start();
    }

    public void stop()
    {
        timer.stop();
    }


    private void update()
    {
        for (int i = 0; i < iterations; i++)
        {
            collisions();
        }

        integrate();
    }


    private void collisions()
    {
        // Test collisions for every combination of bodies
        List<Body> bodies = world.getBodies();

        for (int i = 0; i < bodies.size(); i++)
        {
            for (int j = i + 1; j < bodies.size(); j++) // Starts at i+1 to avoid repeats
            {
                Body a = bodies.get(i);
                Body b = bodies.get(j);

                resolveCollision(a, b);
            }
        }
    }

    private void resolveCollision(Body a, Body b)
    {
        // Broad phase
        if (a.boundsIntersect(b))
        {
            // Narrow phase
            Collision collision = detector.testCollision(a, b);

            if (collision != null)
            {
                // Resolve collision
                collision.resolveCollision(a, b, rate);
            }
        }
    }


    private void integrate()
    {
        // Integrate bodies
        Vector2 gravity = world.getGravity();

        for (Body body : world.getBodies())
        {
            body.integrate(delta, gravity);
        }
    }

}
