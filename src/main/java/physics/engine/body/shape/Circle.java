package physics.engine.body.shape;

import javafx.scene.canvas.GraphicsContext;
import physics.engine.World;

public class Circle extends Shape
{

    private final float radius;


    public Circle(float radius, float mass, float inertia)
    {
        super(mass, inertia);
        this.radius = radius;
    }


    @Override
    public void render(GraphicsContext gc, World world)
    {
        float radius = world.toScreen(this.radius);

        // Draw circle
        gc.strokeOval(-radius, -radius, radius * 2, radius * 2);
        gc.strokeLine(0, 0, radius, 0); // Line to show rotation
    }


    public float getRadius()
    {
        return radius;
    }

}
