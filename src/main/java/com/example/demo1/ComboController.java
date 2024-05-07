package com.example.demo1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
import javafx.scene.control.cell.PropertyValueFactory;

public class ComboController {
    @FXML
    private ComboBox<String> vehiculosInput;
    @FXML
    private ComboBox<String> clientesInput;
    @FXML
    private TextField nifInput;
    @FXML
    private TextField direccionInput;
    @FXML
    private TextField poblacionInput;
    @FXML
    private TextField descripcionInput;
    @FXML
    private TextField marcaInput;
    @FXML
    private TextField kilometrosInput;
    @FXML
    private TextField precioInput;
    @FXML
    private DatePicker fechaAlquilerInput;
    @FXML
    private DatePicker fechaEntregaInput;
    @FXML
    private TextField totalServicioInput;

    public void initialize() {
        try {
            // Establecer la conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/alquiler_vehiculos", "root", "admin");

            // Crear una declaración SQL
            Statement stmt = conn.createStatement();

            // Ejecutar la consulta SQL para vehiculos
            ResultSet rsVehiculos = stmt.executeQuery("SELECT matricula FROM vehiculos");

            // Crear una lista Observable para almacenar los nombres de los vehículos
            ObservableList<String> vehiculos = FXCollections.observableArrayList();

            // Iterar a través del ResultSet y añadir cada nombre a la lista
            while (rsVehiculos.next()) {
                vehiculos.add(rsVehiculos.getString("matricula"));
            }

            // Establecer la lista como los elementos del ComboBox
            vehiculosInput.setItems(vehiculos);

            // Ejecutar la consulta SQL para clientes
            ResultSet rsClientes = stmt.executeQuery("SELECT NyA FROM clientes");

            // Crear una lista Observable para almacenar los nombres de los clientes
            ObservableList<String> clientes = FXCollections.observableArrayList();

            // Iterar a través del ResultSet y añadir cada nombre a la lista
            while (rsClientes.next()) {
                clientes.add(rsClientes.getString("NyA"));
            }

            // Establecer la lista como los elementos del ComboBox
            clientesInput.setItems(clientes);
            fechaAlquilerInput.valueProperty().addListener((observable, oldValue, newValue) -> actualizarTotal());
            fechaEntregaInput.valueProperty().addListener((observable, oldValue, newValue) -> actualizarTotal());
            precioInput.textProperty().addListener((observable, oldValue, newValue) -> actualizarTotal());

            // Cerrar la conexión
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        clientesInput.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            try {
                // Establecer la conexión con la base de datos
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/alquiler_vehiculos", "root", "admin");

                // Crear una declaración SQL
                Statement stmt = conn.createStatement();

                // Ejecutar la consulta SQL para el cliente seleccionado
                ResultSet rsCliente = stmt.executeQuery("SELECT NIF, Direcion, Poblacion FROM clientes WHERE NyA = '" + newValue + "'");

                // Si el ResultSet tiene un resultado, establecer los valores de los TextField
                if (rsCliente.next()) {
                    nifInput.setText(rsCliente.getString("NIF"));
                    direccionInput.setText(rsCliente.getString("Direcion"));
                    poblacionInput.setText(rsCliente.getString("Poblacion"));
                }

                // Cerrar la conexión
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        vehiculosInput.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            try {
                // Establecer la conexión con la base de datos
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/alquiler_vehiculos", "root", "admin");

                // Crear una declaración SQL
                Statement stmt = conn.createStatement();

                // Ejecutar la consulta SQL para el vehículo seleccionado
                ResultSet rsVehiculo = stmt.executeQuery("SELECT descripcion, marca, kilometros, precio FROM vehiculos WHERE matricula = '" + newValue + "'");

                // Si el ResultSet tiene un resultado, establecer los valores de los TextField
                if (rsVehiculo.next()) {
                    descripcionInput.setText(rsVehiculo.getString("descripcion"));
                    marcaInput.setText(rsVehiculo.getString("marca"));
                    kilometrosInput.setText(rsVehiculo.getString("kilometros"));
                    precioInput.setText(rsVehiculo.getString("precio"));
                }

                // Cerrar la conexión
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
    public void guardar(ActionEvent event) {
        String vehiculoSeleccionado = vehiculosInput.getValue();
        String clienteSeleccionado = clientesInput.getValue();
        System.out.println("Vehículo seleccionado: " + vehiculoSeleccionado);
        System.out.println("Cliente seleccionado: " + clienteSeleccionado);

        // Obtén las fechas de alquiler y entrega
        LocalDate fechaAlquiler = fechaAlquilerInput.getValue();
        LocalDate fechaEntrega = fechaEntregaInput.getValue();

        // Calcula el total
        long dias = ChronoUnit.DAYS.between(fechaAlquiler, fechaEntrega);
        int precio = Integer.parseInt(precioInput.getText());
        int total = (int) dias * precio;

        // Muestra el total en totalServicioInput
        totalServicioInput.setText(String.valueOf(total));

        try {
            // Establecer la conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/alquiler_vehiculos", "root", "admin");

            // Crear una declaración SQL
            Statement stmt = conn.createStatement();

            // Obtén el total desde totalServicioInput
            int totalDesdeInput = Integer.parseInt(totalServicioInput.getText());

            // Ejecutar la consulta SQL para insertar el nuevo servicio
            String sql = "INSERT INTO servicios (matricula_vehiculo, nif_cliente, fecha_alquiler, fecha_entrega, total) VALUES ('"
                    + vehiculoSeleccionado + "', '"
                    + nifInput.getText() + "', '"
                    + fechaAlquiler + "', '"
                    + fechaEntrega + "', "
                    + totalDesdeInput + ")";
            stmt.executeUpdate(sql);

            // Cerrar la conexión
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void actualizarTotal() {
        // Obtén las fechas de alquiler y entrega
        LocalDate fechaAlquiler = fechaAlquilerInput.getValue();
        LocalDate fechaEntrega = fechaEntregaInput.getValue();

        // Verifica que las fechas y el precio no sean nulos
        if (fechaAlquiler != null && fechaEntrega != null && !precioInput.getText().isEmpty()) {
            // Calcula el total
            long dias = ChronoUnit.DAYS.between(fechaAlquiler, fechaEntrega);
            int precio = Integer.parseInt(precioInput.getText());
            int total = (int) dias * precio;

            // Muestra el total en totalServicioInput
            totalServicioInput.setText(String.valueOf(total));
        }



    }
    @FXML
    public void mostrarTabla() {
        try {
            // Crear un nuevo Stage y GridPane
            Stage stage = new Stage();
            GridPane grid = new GridPane();
            stage.setMinWidth(800); // Set the width as per your requirement
            stage.setMinHeight(600); // Set the height as per your requirement

            // Crear una tabla y establecer sus columnas
            TableView<Servicio> serviceTable = new TableView<>();
            serviceTable.setMinWidth(750); // Set the width as per your requirement
            serviceTable.setMinHeight(550); // Set the height as per your requirement
            TableColumn<Servicio, String> matriculaColumn = new TableColumn<>("Matricula Vehiculo");
            TableColumn<Servicio, String> nifColumn = new TableColumn<>("NIF Cliente");
            TableColumn<Servicio, LocalDate> fechaAlquilerColumn = new TableColumn<>("Fecha Alquiler");
            TableColumn<Servicio, LocalDate> fechaEntregaColumn = new TableColumn<>("Fecha Entrega");
            TableColumn<Servicio, Integer> totalColumn = new TableColumn<>("Total");

            // Establecer las celdas de las columnas
            matriculaColumn.setCellValueFactory(new PropertyValueFactory<>("matriculaVehiculo"));
            nifColumn.setCellValueFactory(new PropertyValueFactory<>("nifCliente"));
            fechaAlquilerColumn.setCellValueFactory(new PropertyValueFactory<>("fechaAlquiler"));
            fechaEntregaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaEntrega"));
            totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

            // Agregar las columnas a la tabla
            serviceTable.getColumns().addAll(matriculaColumn, nifColumn, fechaAlquilerColumn, fechaEntregaColumn, totalColumn);

            // Establecer la conexión con la base de datos
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/alquiler_vehiculos", "root", "admin");

            // Crear una declaración SQL
            Statement stmt = conn.createStatement();

            // Ejecutar la consulta SQL para obtener todos los servicios
            ResultSet rsServicios = stmt.executeQuery("SELECT * FROM servicios");

            // Crear una lista Observable para almacenar los servicios
            ObservableList<Servicio> servicios = FXCollections.observableArrayList();

            // Iterar a través del ResultSet y añadir cada servicio a la lista
            while (rsServicios.next()) {
                Servicio servicio = new Servicio();
                servicio.setMatriculaVehiculo(rsServicios.getString("matricula_vehiculo"));
                servicio.setNifCliente(rsServicios.getString("nif_cliente"));
                servicio.setFechaAlquiler(rsServicios.getDate("fecha_alquiler").toLocalDate());
                servicio.setFechaEntrega(rsServicios.getDate("fecha_entrega").toLocalDate());
                servicio.setTotal(rsServicios.getInt("total"));
                servicios.add(servicio);
            }

            // Establecer la lista como los elementos de la TableView
            serviceTable.setItems(servicios);

            // Crear un botón de salida
            Button exitButton = new Button("Salir");
            exitButton.setOnAction(e -> stage.close());

            // Agregar la tabla y el botón de salida al GridPane
            grid.add(serviceTable, 0, 0);
            grid.add(exitButton, 0, 1);

            // Establecer la escena y mostrar el Stage
            stage.setScene(new Scene(grid));
            stage.show();

            // Cerrar la conexión
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
