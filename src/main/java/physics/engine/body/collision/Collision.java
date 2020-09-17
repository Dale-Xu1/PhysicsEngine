package physics.engine.body.collision;

import javafx.scene.canvas.GraphicsContext;
import physics.engine.World;
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


    public void render(GraphicsContext gc, World world)
    {
        Vector2 startScreen = world.toScreen(start);
        Vector2 endScreen = world.toScreen(end);

        gc.moveTo(startScreen.x, startScreen.y);
        gc.lineTo(endScreen.x, endScreen.y);
        gc.stroke();

        gc.fillOval(startScreen.x - 2, startScreen.y - 2, 4, 4);
    }

}
