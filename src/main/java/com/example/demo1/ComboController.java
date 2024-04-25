package com.example.demo1;

import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class ComboController implements Initializable {

    @FXML private ComboBox<String> vehiculo;
    @FXML private ComboBox<String> clientes;

    public static class DataRetriever {
        public static List<String> retrieveData(String tablaElegida,Integer numColumna) {
            String url = "jdbc:mysql://localhost:3306/alquiler_vehiculos";
            String usuario = "root";
            String contrasenya = "admin";

            List<String> datosTabla = new ArrayList<>();

            try (Connection conexion = DriverManager.getConnection(url, usuario, contrasenya)) {
                String consulta = "SELECT * FROM " + tablaElegida + ";";
                Statement statement = conexion.createStatement();
                ResultSet resultSet = statement.executeQuery(consulta);

                while (resultSet.next()) {
                    // Supongamos que la tabla tiene una sola columna de tipo String
                    String valor = resultSet.getString(numColumna); // Obtener el valor de la primera columna
                    datosTabla.add(valor);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Ahora datosTabla contiene los datos de la tabla en una lista

            return datosTabla;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<String> data = DataRetriever.retrieveData("vehiculos",1);
        vehiculo.getItems().addAll(data);
        List<String> data2 = DataRetriever.retrieveData("clientes",2);
        clientes.getItems().addAll(data2);
    }


    public void guardar(ActionEvent actionEvent) {
    }
}
