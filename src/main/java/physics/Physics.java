package physics;

import javafx.animation.AnimationTimer;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

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


    public Physics()
    {
        // Create canvas
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();

        // Start animation
        Animation animation = new Animation();
        animation.start();
    }


    private void render()
    {
        gc.translate(WIDTH / 2f, HEIGHT / 2f);
        gc.fillRect(0, 0, 100, 100);
    }

}
