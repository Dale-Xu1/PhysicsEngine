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

        if (Main.UP) position = position.add(new Vector2(0, 1).mult(dt));
        if (Main.DOWN) position = position.add(new Vector2(0, -1).mult(dt));
        if (Main.LEFT) position = position.add(new Vector2(-1, 0).mult(dt));
        if (Main.RIGHT) position = position.add(new Vector2(1, 0).mult(dt));
        if (Main.CW) rotation -= 0.5f * dt;
        if (Main.CCW) rotation += 0.5f * dt;
    }

}
