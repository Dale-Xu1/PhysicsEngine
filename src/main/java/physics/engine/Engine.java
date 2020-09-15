package physics.engine;

import javafx.animation.AnimationTimer;
import physics.engine.body.Body;
import physics.engine.math.Vector2;

public class Engine
{

    private static final float DELTA = 0.02f;

    private class Timer extends AnimationTimer
    {
        private long previousTime = System.currentTimeMillis();
        private long accumulatedTime = 0;

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
    private Timer timer;

    private final float delta; // Used to make sure movements occur in unit rates
    private final int delay;


    public Engine(World world, float delta)
    {
        this.world = world;

        this.delta = delta;
        this.delay = (int) (delta * 1000);
    }

    public Engine(World world)
    {
        this(world, DELTA);
    }


    public void start()
    {
        // Create and start timer
        timer = new Timer();
        timer.start();
    }

    public void stop()
    {
        timer.stop();
    }


    private void update()
    {
        // Integrate bodies
        Vector2 gravity = world.getGravity();

        for (Body body : world.getBodies())
        {
            body.integrate(delta, gravity);
        }
    }

}
