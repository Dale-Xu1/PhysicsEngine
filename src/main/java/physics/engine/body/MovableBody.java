package physics.engine.body;

import physics.Main;
import physics.engine.body.shape.Shape;
import physics.engine.math.Vector2;

public class MovableBody extends Body
{

    public MovableBody(Vector2 position, float rotation, Shape shape)
    {
        super(position, rotation, shape);
    }

    public MovableBody(Vector2 position, Shape shape)
    {
        super(position, shape);
    }


    @Override
    public void integrate(float dt, Vector2 gravity)
    {
        super.integrate(dt, gravity);

        dt *= 8;

        if (Main.UP) translate(new Vector2(0, 1).mult(dt));
        if (Main.DOWN) translate(new Vector2(0, -1).mult(dt));
        if (Main.LEFT) translate(new Vector2(-1, 0).mult(dt));
        if (Main.RIGHT) translate(new Vector2(1, 0).mult(dt));
        if (Main.CW) rotate(-0.5f * dt);
        if (Main.CCW) rotate(0.5f * dt);
    }

}
