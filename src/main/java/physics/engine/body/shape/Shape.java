package physics.engine.body.shape;

import javafx.scene.canvas.GraphicsContext;
import physics.engine.World;

public abstract class Shape
{

    public static Circle createCircle(float radius, float density)
    {
        // Calculate mass and inertia based on density
        float mass = (float) Math.PI * (radius * radius) * density;
        float inertia = mass * (radius * radius) / 12;

        return new Circle(radius, mass, inertia);
    }

    public static Circle createCircle(float radius)
    {
        return createCircle(radius, 1);
    }


    private final float inverseMass;
    private final float inverseInertia;


    public Shape(float mass, float inertia)
    {
        if (mass > 0)
        {
            // Mass and inertia are inverted because equations usually divide by mass or inertia
            inverseMass = 1 / mass;
            inverseInertia = 1 / inertia;
        }
        else
        {
            // 0 inverse mass represents infinite mass
            inverseMass = 0;
            inverseInertia = 0;
        }
    }


    public abstract void render(GraphicsContext gc, World world);


    public float getInverseMass()
    {
        return inverseMass;
    }

    public float getInverseInertia()
    {
        return inverseInertia;
    }

}
