package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class CalculatorController {
    @FXML
    private TextField display;

    private double num1 = 0;
    private String operator = "";



    @FXML
    protected void pressOperator(ActionEvent event) {
        num1 = Double.valueOf(display.getText());
        operator = ((Button)event.getSource()).getText();
        display.setText("");
    }

    @FXML
    protected void calculate() {
        double num2 = Double.valueOf(display.getText());
        switch (operator) {
            case "+":
                display.setText(String.valueOf(num1 + num2));
                break;
            case "-":
                display.setText(String.valueOf(num1 - num2));
                break;
            case "*":
                display.setText(String.valueOf(num1 * num2));
                break;
            case "/":
                display.setText(String.valueOf(num1 / num2));
                break;
            // Add more cases for other operations
        }
    }
    @FXML
    protected void pressClear(ActionEvent event) {
        display.setText("");
    }

    protected void handleKeyPress(KeyEvent event) {
        String key = event.getText(); // Obtener el texto de la tecla pulsada

        // Verificar si el texto coincide con alguno de los operadores
        if (key.equals("/") || key.equals("*") || key.equals("+") || key.equals("-")) {
            pressOperator(key);
        }
    }

    @FXML
    protected void pressOperator(String operator) {
        num1 = Double.valueOf(display.getText());
        this.operator = operator;
        display.setText("");
    }



}