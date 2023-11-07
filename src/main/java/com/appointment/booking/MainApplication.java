package com.appointment.booking;

import com.appointment.booking.dao.Database;
import com.appointment.booking.utils.Alerts;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        if (Database.isOK()) {
            Parent root = FXMLLoader.load(MainApplication.class.getResource("home.fxml"));
            stage.setScene(new Scene(root));
            //set stage borderless
            stage.initStyle(StageStyle.UNDECORATED);

            //drag it here
            root.setOnMousePressed(event -> {
//                x = event.getSceneX();
//                y = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {

//                stage.setX(event.getScreenX() - x);
//                stage.setY(event.getScreenY() - y);

            });
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