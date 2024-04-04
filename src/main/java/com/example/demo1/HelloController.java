package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Label rocaText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");}

    @FXML
    protected void onRocaButtonClick() {
        rocaText.setText("te falta calle");}

    @FXML
    protected void onResetButtonClick() {
        rocaText.setText("");}

}