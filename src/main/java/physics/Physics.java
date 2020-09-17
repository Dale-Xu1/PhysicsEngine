package physics;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import physics.engine.Engine;
import physics.engine.World;
import physics.engine.body.Body;
import physics.engine.body.MovableBody;
import physics.engine.body.collision.Collision;
import physics.engine.body.shape.Circle;
import physics.engine.body.shape.Rectangle;
import physics.engine.math.Vector2;

public class Physics extends Parent
{

    public static final String TITLE = "Physics Engine";

    public static final int WIDTH = 720;
    public static final int HEIGHT = 420;


    private class Animation extends AnimationTimer
    {
        @Override
        public void handle(long now)
        {
            // Clear screen
            gc.clearRect(0, 0, WIDTH, HEIGHT);
            gc.save();

            render();
            gc.restore();
        }
    }

    private final GraphicsContext gc;

    // Create world
    private final World world = new World();
    private final Engine engine = new Engine(world);


    public Physics()
    {
        // Create canvas
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        createWorld();

        // Start animation
        Animation animation = new Animation();
        animation.start();
    }

    private void createWorld()
    {
        world.addBody(new Body(new Vector2(5, 2), 1, Circle.create(3)));
        world.addBody(new MovableBody(new Vector2(5, 2), Circle.create(2)));
        world.addBody(new Body(new Vector2(-1, 0), -1.3f, Rectangle.create(4, 2)));

        // Start engine
        engine.start();
    }


    private void render()
    {
        // Center origin
        gc.translate(WIDTH / 2f, HEIGHT / 2f);

        // Render bodies
        for (Body body : world.getBodies())
        {
            body.render(gc, world);
        }

        for (Collision collision : engine.collisions)
        {
            collision.render(gc, world);
        }
    }

}
