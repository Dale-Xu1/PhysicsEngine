package physics.engine.body.shape;

import physics.engine.math.Vector2;

public class Rectangle
{

    public static Polygon create(float width, float height, float density)
    {
        // Calculate vertex positions
        float halfWidth = width / 2;
        float halfHeight = height / 2;

        Vector2[] vertices = new Vector2[]
            {
                new Vector2(-halfWidth, -halfHeight),
                new Vector2(halfWidth, -halfHeight),
                new Vector2(halfWidth, halfHeight),
                new Vector2(-halfWidth, halfHeight),
            };

        // Calculate mass and inertia
        float magSq = (width * width) + (height * height);

        float mass = (width * height) * density;
        float inertia = mass * magSq / 12;

        // Calculate radius for broad phase collision detection
        float radius = (float) Math.sqrt(magSq) / 2;

        return new Polygon(vertices, mass, inertia, radius);
    }

    public static Polygon create(float width, float height)
    {
        return create(width, height, 1);
    }

}
