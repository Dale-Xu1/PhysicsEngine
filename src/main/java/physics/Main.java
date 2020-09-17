package physics;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application
{

    // TODO: Remove testing functionality
    public static boolean UP = false;
    public static boolean DOWN = false;
    public static boolean LEFT = false;
    public static boolean RIGHT = false;


    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage stage)
    {
        // Create stage with application in it
        Physics physics = new Physics();
        Scene scene = new Scene(physics, Physics.WIDTH, Physics.HEIGHT);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent e) ->
        {
            if (e.getCode() == KeyCode.W) UP = true;
            else if (e.getCode() == KeyCode.S) DOWN = true;
            else if (e.getCode() == KeyCode.A) LEFT = true;
            else if (e.getCode() == KeyCode.D) RIGHT = true;
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent e) ->
        {
            if (e.getCode() == KeyCode.W) UP = false;
            else if (e.getCode() == KeyCode.S) DOWN = false;
            else if (e.getCode() == KeyCode.A) LEFT = false;
            else if (e.getCode() == KeyCode.D) RIGHT = false;
        });

        stage.setTitle(Physics.TITLE);
        stage.setScene(scene);
        stage.show();
    }

}
