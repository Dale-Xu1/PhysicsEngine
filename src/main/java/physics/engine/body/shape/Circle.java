package physics.engine.body.shape;

import javafx.scene.canvas.GraphicsContext;
import physics.engine.World;

public class Circle extends Shape
{

    public static Circle create(float radius, float density)
    {
        // Calculate mass and inertia based on density
        float mass = (float) Math.PI * (radius * radius) * density;
        float inertia = mass * (radius * radius) / 2;

        return new Circle(radius, mass, inertia);
    }

    public static Circle create(float radius)
    {
        return create(radius, 1);
    }


    public Circle(float radius, float mass, float inertia)
    {
        super(mass, inertia, radius);
    }


    @Override
    public void setRotation(float angle) { } // A circle is the same in all orientations

    @Override
    public void render(GraphicsContext gc, World world)
    {
        float screenRadius = world.toScreen(getRadius());
        float screenDiameter = screenRadius * 2;

        // Draw circle
        gc.strokeOval(-screenRadius, -screenRadius, screenDiameter, screenDiameter);
        gc.strokeLine(0, 0, screenRadius, 0); // Line to show rotation
    }

}
