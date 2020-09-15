package physics.engine;

import physics.engine.body.Body;
import physics.engine.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class World
{

    private static final float GRAVITY = -9.81f;
    private static final float SCALE = 10;

    private final Vector2 gravity;
    private final float scale;

    private final List<Body> bodies = new ArrayList<>();


    public World(Vector2 gravity, float scale)
    {
        this.gravity = gravity;
        this.scale = scale;
    }

    public World(Vector2 gravity)
    {
        this(gravity, SCALE);
    }

    public World(float scale)
    {
        this(new Vector2(0, GRAVITY), scale);
    }

    public World()
    {
        this(SCALE);
    }


    public Vector2 toScreen(Vector2 vector)
    {
        // Scale vector and invert y-axis
        Vector2 scaled = vector.mult(scale);
        return new Vector2(scaled.x, -scaled.y); // We want y to point up in this world
    }

    public float toScreen(float value)
    {
        return value * scale;
    }


    public Vector2 getGravity()
    {
        return gravity;
    }


    public void addBody(Body body)
    {
        bodies.add(body);
    }

    public List<Body> getBodies()
    {
        return bodies;
    }

}
