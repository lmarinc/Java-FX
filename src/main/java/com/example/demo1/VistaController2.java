package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class VistaController2 {

//    @FXML
//    private TextField nameInput;
//
//    @FXML
//    private TextField surnameInput;
//
//    @FXML
//    private TextField ageInput;

    @FXML
    private TableView<Persona> personTable;

    @FXML
    private TableColumn<Persona, String> nameColumn;

    @FXML
    private TableColumn<Persona, String> surnameColumn;

    @FXML
    private TableColumn<Persona, String> ageColumn;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void showAddPersonaDialog() throws IOException {
        Stage stage = new Stage();
        GridPane grid = new GridPane();

        TextField nameInput = new TextField();
        TextField surnameInput = new TextField();
        TextField ageInput = new TextField();

        Button saveButton = new Button("Guardar");
        saveButton.setOnAction(event -> {
            String name = nameInput.getText();
            String surname = surnameInput.getText();
            String age = ageInput.getText();

            Persona persona = new Persona(name, surname, age);

            if (personTable.getItems().contains(persona)) {
                showError("Error", "La persona ya existe en la tabla.");
            } else {
                personTable.getItems().add(persona);
                showAlert("Información", "Se ha añadido correctamente.");

                nameInput.clear();
                surnameInput.clear();
                ageInput.clear();

                stage.close();
            }
        });

        Button exitButton = new Button("Salir");
        exitButton.setOnAction(event -> stage.close());

        grid.add(new Label("Nombre"), 0, 0);
        grid.add(nameInput, 1, 0);
        grid.add(new Label("Apellidos"), 0, 1);
        grid.add(surnameInput, 1, 1);
        grid.add(new Label("Edad"), 0, 2);
        grid.add(ageInput, 1, 2);
        grid.add(saveButton, 0, 3);
        grid.add(exitButton, 1, 3);

        stage.setScene(new Scene(grid));
        stage.show();
    }

//    @FXML
//    public void addPersona() {
//        String name = nameInput.getText();
//        String surname = surnameInput.getText();
//        String age = ageInput.getText();
//
//        Persona persona1 = new Persona(name, surname, age);
//
//        if (personTable.getItems().contains(persona1)) {
//            showAlert("Error", "La persona ya existe en la tabla.");
//        } else {
//            personTable.getItems().add(persona1);
//            showAlert("Información", "Se ha añadido correctamente.");
//
//            nameInput.clear();
//            surnameInput.clear();
//            ageInput.clear();
//        }
//    }


//    @FXML
//    public void modificarPersona() {
//        if (!nameInput.getText().isEmpty() || !surnameInput.getText().isEmpty() || !ageInput.getText().isEmpty()) {
//            return;
//        }
//
//        Persona personaSeleccionada = personTable.getSelectionModel().getSelectedItem();
//        if (personaSeleccionada != null) {
//            nameInput.setText(personaSeleccionada.getName());
//            surnameInput.setText(personaSeleccionada.getSurname());
//            ageInput.setText(personaSeleccionada.getAge());
//
//            personTable.getItems().remove(personaSeleccionada);
//        }
//    }

//    @FXML
//    public void eliminarPersona() {
//        Persona personaSeleccionada = personTable.getSelectionModel().getSelectedItem();
//        if (personaSeleccionada != null) {
//            personTable.getItems().remove(personaSeleccionada);
//        }
//    }


}