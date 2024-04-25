package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComboApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ComboApplication.class.getResource("combobox.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 650);
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}