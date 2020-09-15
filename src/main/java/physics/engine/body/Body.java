package physics.engine.body;

import javafx.scene.canvas.GraphicsContext;
import physics.engine.World;
import physics.engine.body.shape.Shape;
import physics.engine.math.Vector2;

public class Body
{

    private Vector2 position;
    private float rotation;

    private final Shape shape;


    public Body(Vector2 position, float rotation, Shape shape)
    {
        this.position = position;
        this.rotation = rotation;
        this.shape = shape;
    }

    public Body(Vector2 position, Shape shape)
    {
        this(position, 0, shape);
    }


    public void integrate(float dt, Vector2 gravity)
    {
        // Integrate body
    }

    public void render(GraphicsContext gc, World world)
    {
        // Apply transformations
        gc.save();
        gc.translate(position.x, position.y);
        gc.rotate(rotation);

        // Render shape
        shape.render(gc, world);

        gc.restore();
    }


    public Vector2 getPosition()
    {
        return position;
    }

    public float getRotation()
    {
        return rotation;
    }

    public Shape getShape()
    {
        return shape;
    }

}
