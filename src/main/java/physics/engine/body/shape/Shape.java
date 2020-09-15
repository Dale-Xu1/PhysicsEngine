package physics.engine.body.shape;

import javafx.scene.canvas.GraphicsContext;
import physics.engine.World;

public abstract class Shape
{

    public abstract void render(GraphicsContext gc, World world);

}
