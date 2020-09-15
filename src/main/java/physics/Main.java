package physics;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{

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

        stage.setTitle(Physics.TITLE);
        stage.setScene(scene);
        stage.show();
    }

}
