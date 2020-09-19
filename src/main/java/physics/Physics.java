package physics;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import physics.engine.Engine;
import physics.engine.World;
import physics.engine.body.Body;
import physics.engine.body.shape.Circle;
import physics.engine.body.shape.Polygon;
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
        world.addBody(new Body(new Vector2(0, -15), 0.1f, Rectangle.createStatic(50, 1)));
        world.addBody(new Body(new Vector2(-25, 0), Rectangle.createStatic(1, 35)));

//        world.addBody(new Body(new Vector2(-1, 0), Polygon.create(new Vector2[] { new Vector2(2.5f, 1.5f),  new Vector2(-1.5f, 1.5f), new Vector2(-1.5f, -2.5f) })));
//        world.addBody(new Body(new Vector2(-1, 0), Polygon.create(new Vector2[] { new Vector2(2.5f, 1.5f),  new Vector2(-1.5f, 1.5f), new Vector2(-1.5f, -2.5f) })));
//        world.addBody(new Body(new Vector2(-1, 0), Polygon.create(new Vector2[] { new Vector2(2.5f, 1.5f),  new Vector2(-1.5f, 1.5f), new Vector2(-1.5f, -2.5f) })));

        // Start engine
        engine.start();
    }


    private void render()
    {
        if (Math.random() < 0.1)
        {
            float angle = (float) (Math.random() * Math.PI * 2);

            if (Math.random() < 0.5)
            {
                world.addBody(new Body(new Vector2(-2, 10), angle, Circle.create((float) Math.random() + 0.7f)));
            }
            else
            {
                world.addBody(new Body(new Vector2(-2, 10), angle, Rectangle.create((float) Math.random() * 3 + 1, (float) Math.random() * 2 + 1)));
            }
        }

        // Center origin
        gc.translate(WIDTH / 2f, HEIGHT / 2f);

        // Render bodies
        for (Body body : world.getBodies())
        {
            body.render(gc, world);
        }
    }

}
