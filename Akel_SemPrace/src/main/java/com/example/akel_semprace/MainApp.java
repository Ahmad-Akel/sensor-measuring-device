package com.example.akel_semprace;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       /*FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 570);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();*/
        Parent root= FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}