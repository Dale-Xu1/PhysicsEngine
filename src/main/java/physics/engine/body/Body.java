package physics.engine.body;

import javafx.scene.canvas.GraphicsContext;
import physics.engine.World;
import physics.engine.body.shape.Shape;
import physics.engine.math.Vector2;

public class Body
{

    private static final float ROTATION = 0;
    private static final float FRICTION = 0.8f;
    private static final float RESTITUTION = 0.2f;


    private Vector2 position;
    private Vector2 velocity = new Vector2(0, 0);
    private Vector2 acceleration = new Vector2(0, 0);

    private float rotation;
    private float angularVelocity = 0;
    private float angularAcceleration = 0;

    private final float friction;
    private final float restitution;

    private final Shape shape;


    public Body(Vector2 position, float rotation, float friction, float restitution, Shape shape)
    {
        this.position = position;
        this.rotation = rotation;
        this.friction = friction;
        this.restitution = restitution;
        this.shape = shape;
    }

    public Body(Vector2 position, float rotation, Shape shape)
    {
        this(position, rotation, FRICTION, RESTITUTION, shape);
    }

    public Body(Vector2 position, Shape shape)
    {
        this(position, ROTATION, shape);
    }


    public void applyForce(Vector2 force)
    {
        acceleration = acceleration.add(force);
    }

    public void applyImpulse(Vector2 impulse, Vector2 contact)
    {
        velocity = velocity.add(impulse.mult(shape.getInverseMass()));
        angularVelocity += contact.cross(impulse) * shape.getInverseInertia();
    }

    public void integrate(float dt, Vector2 gravity)
    {
        float inverseMass = shape.getInverseMass();
        float inverseInertia = shape.getInverseInertia();

        // Calculate acceleration
        acceleration = acceleration.mult(inverseMass);
        acceleration = (inverseMass > 0) ? acceleration.add(gravity) : acceleration; // Add gravity if not static

        // Integrate position
        velocity = velocity.add(acceleration.mult(dt));
        move(velocity.mult(dt));

        // Integrate rotation
        angularVelocity += angularAcceleration * inverseInertia * dt;
        rotate(angularVelocity * dt);

        // Clear acceleration
        acceleration = new Vector2(0, 0);
        angularAcceleration = 0;
    }


    public void move(Vector2 vector)
    {
        position = position.add(vector);
    }

    public void rotate(float angle)
    {
        rotation += angle;
    }


    public boolean boundsIntersect(Body body)
    {
        // Test if bounding circles intersect
        float distanceSq = position.sub(body.position).magSq();
        float radius = shape.getRadius() + body.shape.getRadius();

        return (distanceSq < radius * radius);
    }


    public void render(GraphicsContext gc, World world)
    {
        // Convert to screen coordinates
        Vector2 screenPosition = world.toScreen(position);
        float screenRotation = -rotation * 180 / (float) Math.PI;

        // Apply transformations
        gc.save();
        gc.translate(screenPosition.x, screenPosition.y);
        gc.rotate(screenRotation);

        // Render shape
        shape.render(gc, world);
        gc.restore();
    }


    public Vector2 getPosition()
    {
        return position;
    }

    public Vector2 getVelocity()
    {
        return velocity;
    }

    public float getRotation()
    {
        return rotation;
    }

    public float getAngularVelocity()
    {
        return angularVelocity;
    }


    public float getFriction()
    {
        return friction;
    }

    public float getRestitution()
    {
        return restitution;
    }


    public Shape getShape()
    {
        return shape;
    }

}
