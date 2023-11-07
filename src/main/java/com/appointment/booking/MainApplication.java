package com.appointment.booking;

import com.appointment.booking.dao.Database;
import com.appointment.booking.utils.Alerts;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        if (Database.isOK()) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } else {
            Alerts.error(
                    "Database error",
                    "Could not load database",
                    "Error loading SQLite database. See log. Quitting..."
            ).showAndWait();
            Platform.exit();
        }

    }

    public static void main(String[] args) {
        launch();
    }

}