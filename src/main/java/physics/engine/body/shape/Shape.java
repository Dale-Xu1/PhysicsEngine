package physics.engine.body.shape;

import javafx.scene.canvas.GraphicsContext;
import physics.engine.World;

public abstract class Shape
{

    private final float inverseMass;
    private final float inverseInertia;

    private final float radius;


    public Shape(float mass, float inertia, float radius)
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

        this.radius = radius;
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

    public float getRadius()
    {
        return radius;
    }

}
