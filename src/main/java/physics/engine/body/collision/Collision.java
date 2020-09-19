package physics.engine.body.collision;

import physics.engine.body.Body;
import physics.engine.body.shape.Shape;
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
            applyImpulses(a, b);
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


    private void applyImpulses(Body a, Body b)
    {
        // Get vectors pointing to contact
        Vector2 contact = getContact(a, b);

        Vector2 contactA = contact.sub(a.getPosition());
        Vector2 contactB = contact.sub(b.getPosition());

        // Calculate relative velocity
        Vector2 velocityA = a.getVelocity().add(contactA.cross(a.getAngularVelocity()));
        Vector2 velocityB = b.getVelocity().add(contactB.cross(b.getAngularVelocity()));

        Vector2 relativeVelocity = velocityB.sub(velocityA);

        float normalVelocity = relativeVelocity.dot(normal);
        if (normalVelocity > 0) return; // Test if bodies are moving apart


        // Apply normal impulse
        applyImpulse(a, b, contactA, contactB, normal, normalVelocity);

        // Calculate tangent
        Vector2 tangent = relativeVelocity.add(normal.mult(-relativeVelocity.dot(normal))).normalize();
        float friction = Math.min(a.getFriction(), b.getFriction());

        float tangentVelocity = relativeVelocity.dot(tangent) * friction;

        // Apply tangent impulse
        applyImpulse(a, b, contactA, contactB, tangent, tangentVelocity);
    }

    private void applyImpulse(Body a, Body b, Vector2 contactA, Vector2 contactB, Vector2 normal, float velocity)
    {
        Shape shapeA = a.getShape();
        Shape shapeB = b.getShape();

        float restitution = Math.min(a.getRestitution(), b.getRestitution());
        float normalVelocity = -(1 + restitution) * velocity;

        float normalA = contactA.cross(normal);
        float normalB = contactB.cross(normal);

        // Calculate impulse
        float inverseMass = shapeA.getInverseMass() + shapeB.getInverseMass();
        float inertiaA = (normalA * normalA) * shapeA.getInverseInertia();
        float inertiaB = (normalB * normalB) * shapeB.getInverseInertia();

        float impulseScale = normalVelocity / (inverseMass + inertiaA + inertiaB);
        Vector2 impulse = normal.mult(impulseScale);

        // Apply impulses
        a.applyImpulse(impulse.negate(), contactA);
        b.applyImpulse(impulse, contactB);
    }

    private Vector2 getContact(Body a, Body b)
    {
        float massA = a.getShape().getInverseMass();
        float massB = b.getShape().getInverseMass();

        float massSum = massA + massB;

        // Calculate point in between start and end based on masses
        Vector2 contactB = start.mult(massB / massSum);
        Vector2 contactA = end.mult(massA / massSum);

        return contactB.add(contactA);
    }

}
