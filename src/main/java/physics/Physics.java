package physics;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import physics.engine.Engine;
import physics.engine.World;
import physics.engine.body.Body;
import physics.engine.body.shape.Shape;
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
    private final World world = new World(); // Create world


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
        world.addBody(new Body(new Vector2(5, 2), (float) Math.PI / 3, Shape.createCircle(2)));

        // Create engine
        Engine engine = new Engine(world);
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
    }

}
