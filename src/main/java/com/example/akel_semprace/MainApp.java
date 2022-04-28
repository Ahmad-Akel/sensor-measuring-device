package com.example.akel_semprace;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Image icon = new Image("C:\\Users\\ahmad.akel\\Desktop\\School\\BDATS\\Sensor-Measuring-Device\\src\\speedometer.png");
        Parent root= FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(icon);
        stage.setTitle("Sensor Measuring Device");
        scene.getStylesheets().add(MainApp.class.getResource("/style.css").toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}