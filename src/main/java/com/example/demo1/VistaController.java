package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class VistaController {

    @FXML
    private TextField nameInput;

    @FXML
    private TextField surnameInput;

    @FXML
    private TextField ageInput;

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

    @FXML
    public void addPersona() {
        String name = nameInput.getText();
        String surname = surnameInput.getText();
        String age = ageInput.getText();

        Persona persona1 = new Persona(name, surname, age);


        personTable.getItems().add(persona1);

        nameInput.clear();
        surnameInput.clear();
        ageInput.clear();
    }
    @FXML
    public void modificarPersona() {
        if (!nameInput.getText().isEmpty() || !surnameInput.getText().isEmpty() || !ageInput.getText().isEmpty()) {
            return;
        }

        Persona personaSeleccionada = personTable.getSelectionModel().getSelectedItem();
        if (personaSeleccionada != null) {
            nameInput.setText(personaSeleccionada.getName());
            surnameInput.setText(personaSeleccionada.getSurname());
            ageInput.setText(personaSeleccionada.getAge());

            personTable.getItems().remove(personaSeleccionada);
        }
    }

    @FXML
    public void eliminarPersona() {
        Persona personaSeleccionada = personTable.getSelectionModel().getSelectedItem();
        if (personaSeleccionada != null) {
            personTable.getItems().remove(personaSeleccionada);
        }
    }
}