package com.example.demo1;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;


public class ControlPersonasController {

//    @FXML
//    private TextField nameInput;
//
//    @FXML
//    private TextField surnameInput;
//
//    @FXML
//    private TextField ageInput;

    @FXML
    private TextField filterInput;

    @FXML
    private TableView<Persona> personTable;

    @FXML
    private TableColumn<Persona, String> nameColumn;

    @FXML
    private TableColumn<Persona, String> surnameColumn;

    @FXML
    private TableColumn<Persona, String> ageColumn;
    private ObservableList<Persona> allData;

    @FXML
    public void initialize() {
        // Initialize allData with the items from the table
        allData = FXCollections.observableArrayList();
        // Load data into allData
        loadData();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        filterInput.textProperty().addListener((observable, oldValue, newValue) -> {
            filterTable(newValue);
        });

        // Set the table items to allData
        personTable.setItems(allData);
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
    private void loadData() {
        // Load data into allData
        // This is just an example, replace it with your actual data loading code
        allData.add(new Persona("John", "Doe", "30"));
        allData.add(new Persona("Jane", "Doe", "25"));
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

            if (allData.contains(persona)) {
                showError("Error", "La persona ya existe en la tabla.");
            } else {
                allData.add(persona); // Add the new persona to allData
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

    @FXML
    public void showEditPersonaDialog() throws IOException {
        Persona selectedPersona = personTable.getSelectionModel().getSelectedItem();

        if (selectedPersona == null) {
            showError("Error", "No hay ninguna persona seleccionada.");
            return;
        }

        Stage stage = new Stage();
        GridPane grid = new GridPane();

        TextField nameInput = new TextField(selectedPersona.getName());
        TextField surnameInput = new TextField(selectedPersona.getSurname());
        TextField ageInput = new TextField(selectedPersona.getAge());

        Button saveButton = new Button("Guardar");
        saveButton.setOnAction(event -> {
            String name = nameInput.getText();
            String surname = surnameInput.getText();
            String age = ageInput.getText();

            Persona persona = new Persona(name, surname, age);

            if (!persona.equals(selectedPersona) && allData.contains(persona)) {
                showError("Error", "La persona ya existe en la tabla.");
            } else {
                int selectedIndex = allData.indexOf(selectedPersona);
                allData.set(selectedIndex, persona);
                showAlert("Información", "Se ha modificado correctamente.");

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
    private void filterTable(String filter) {
        // Use allData to create the filtered list
        FilteredList<Persona> filteredData = new FilteredList<>(allData, p -> true);

        filteredData.setPredicate(persona -> {
            if (filter == null || filter.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = filter.toLowerCase();

            if (persona.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }

            return false;
        });

        SortedList<Persona> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(personTable.comparatorProperty());

        personTable.setItems(sortedData);
    }

    public void eliminarPersona(ActionEvent actionEvent) {
        Persona selectedPersona = personTable.getSelectionModel().getSelectedItem();

        if (selectedPersona == null) {
            showError("Error", "No hay ninguna persona seleccionada.");
            return;
        }

        allData.remove(selectedPersona);
        personTable.getItems().remove(selectedPersona);
        showAlert("Información", "Se ha eliminado correctamente.");
    }
}