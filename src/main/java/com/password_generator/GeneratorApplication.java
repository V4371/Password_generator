package com.password_generator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * 
 * Class of application, that extends javafx.application.Application class from JavaFX.
 * It is responsible for window control
 */
public class GeneratorApplication extends Application {
    /**
     * The function that is responsible for actions when the application is launched
     * @param stage The basis for creating a GUI in JavaFX.
     *             It is a container into which all other interface components are placed.
     * @throws IOException checked exceptions type that is required by fxmlLoader.load() method
     */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GeneratorApplication.class.getResource("generator-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 370, 240);
        stage.setTitle("Password generator");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }


}
