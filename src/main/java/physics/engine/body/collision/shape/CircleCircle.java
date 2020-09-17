package physics.engine.body.collision.shape;

import physics.engine.body.Body;
import physics.engine.body.collision.Collision;
import physics.engine.math.Vector2;

public class CircleCircle implements ShapeCollision
{

    @Override
    public Collision testCollision(Body a, Body b)
    {
        // Get radii
        float radiusA = a.getShape().getRadius();
        float radiusB = b.getShape().getRadius();

        float radius = radiusA + radiusB;

        // Get squared distance
        Vector2 direction = b.getPosition().sub(a.getPosition());
        float distanceSq = direction.magSq();

        // Test if distance is less than the sum of the two radii
        if (distanceSq >= radius * radius)
        {
            // No collision
            return null;
        }
        else if (distanceSq == 0)
        {
            // Circles are in the same position
            Vector2 normal = new Vector2((radiusA > radiusB) ? 1 : -1, 0);

            // Calculate start position
            Vector2 position = a.getPosition();
            Vector2 start = position.add(normal.mult(-radiusB));

            return new Collision(radius, normal, start);
        }
        else
        {
            // Calculate direction and magnitude of penetration
            float distance = (float) Math.sqrt(distanceSq);
            float penetration = radius - distance;

            Vector2 normal = direction.normalize();

            // Position on B's circumference since normal points towards B
            Vector2 offset = normal.mult(-radiusB);
            Vector2 start = b.getPosition().add(offset);

            return new Collision(penetration, normal, start);
        }
    }

}
