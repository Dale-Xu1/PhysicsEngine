package physics.engine.body.collision;

import physics.engine.body.Body;
import physics.engine.math.Vector2;

public class Collision
{

    private final float penetration;
    private final Vector2 normal;

    private final Vector2 start;
    private final Vector2 end;


    public Collision(float penetration, Vector2 normal, Vector2 start)
    {
        this(penetration, normal, start, start.add(normal.mult(penetration)));
    }

    private Collision(float penetration, Vector2 normal, Vector2 start, Vector2 end)
    {
        this.penetration = penetration;
        this.normal = normal;

        this.start = start;
        this.end = end;
    }


    public Collision getInverse()
    {
        return new Collision(penetration, normal.negate(), end, start);
    }


    public void resolveCollision(Body a, Body b, float rate)
    {
        // Get inverse masses
        float massA = a.getShape().getInverseMass();
        float massB = b.getShape().getInverseMass();

        // Don't resolve collision if both are static
        if (massA > 0 || massB > 0)
        {
            correctPositions(a, b, rate);
            applyImpulses();
        }
    }

    private void correctPositions(Body a, Body b, float rate)
    {
        float massA = a.getShape().getInverseMass();
        float massB = b.getShape().getInverseMass();

        // Calculate correction amount
        float correction = penetration / (massA + massB) * rate; // Multiply by rate to avoid errors

        // Distribute correction based on mass because of Newton's second law
        a.move(normal.mult(-correction * massA));
        b.move(normal.mult(correction * massB));
    }

    private void applyImpulses()
    {
        // TODO: Collision resolution
    }

}
