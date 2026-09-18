package com.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

public class CalculatorApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlLocation = Objects.requireNonNull(
                getClass().getResource("/calculator.fxml"),
                "No se encontró el archivo calculator.fxml"
        );
        URL stylesheetLocation = Objects.requireNonNull(
                getClass().getResource("/calculator.css"),
                "No se encontró el archivo calculator.css"
        );
        URL iconLocation = Objects.requireNonNull(
                getClass().getResource("/icons/app-icon.png"),
                "No se encontró el ícono de la aplicación"
        );
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();
        primaryStage.setTitle("Calculadora JavaFX");
        primaryStage.getIcons().add(new Image(iconLocation.toExternalForm()));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(stylesheetLocation.toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
