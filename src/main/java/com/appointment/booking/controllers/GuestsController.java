package com.appointment.booking.controllers;

import com.appointment.booking.MainApplication;
import com.appointment.booking.dao.PersonDAO;
import com.appointment.booking.models.Person;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GuestsController implements Initializable {

    @FXML
    public TextField searchPersons;

    @FXML
    public VBox pnItems;

    @FXML
    public Label totalGuests;

    List<Person> persons = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PersonDAO.getPersons().addListener((ListChangeListener<Person>) change -> {
            persons = new ArrayList<>(PersonDAO.getPersons());
            updateContent(persons);
        });
        persons = new ArrayList<>(PersonDAO.getPersons());
        updateContent(persons);
        searchPersons.textProperty().addListener((observable, oldValue, newValue) -> {
            updateContent(persons);
        });
    }

    void updateContent(List<Person> persons) {
        totalGuests.setText(String.valueOf(persons.size()));
        List<Person> filteredPersons = persons.stream()
                .filter(person -> person.getName().contains(searchPersons.getText()))
                .collect(Collectors.toList());
        pnItems.getChildren().clear();
        pnItems.getChildren().add(getTableHeader());
        for (Person person : filteredPersons) {
            pnItems.getChildren().add(getGuestElement(person));
        }
    }

    HBox getTableHeader() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPrefHeight(42.0);
        hbox.setPrefWidth(712.0);
        hbox.setSpacing(80.0);
        hbox.setStyle("-fx-background-color: #02030A;");

        Label labelId = new Label("Id");
        labelId.setTextFill(Color.web("#ffffff"));
        HBox.setMargin(labelId, new Insets(0, 0, 0, 50)); // Adjust the insets as needed

        Label labelName = new Label("Name");
        labelName.setTextFill(Color.web("#ffffff"));

        hbox.getChildren().addAll(labelId, labelName);
        return hbox;
    }

    HBox getGuestElement(Person person) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPrefHeight(42.0);
        hbox.setPrefWidth(712.0);
        hbox.setSpacing(80.0);
        hbox.setStyle("-fx-background-color: #02030A;");

        Label labelId = new Label(String.valueOf(person.getId()));
        labelId.setTextFill(Color.web("#ffffff"));
        HBox.setMargin(labelId, new Insets(0, 0, 0, 50)); // Adjust the insets as needed

        Label labelName = new Label(person.getName());
        labelName.setTextFill(Color.web("#ffffff"));

        // Create a delete button
        Button deleteButton = new Button();
        ImageView deleteIcon = new ImageView(new Image(MainApplication.class.getResource("images/delete.png").toExternalForm())); // Replace "delete_icon.png" with the actual path to your delete button icon
        deleteIcon.setFitHeight(20); // Set the desired size for your delete button icon
        deleteIcon.setFitWidth(20);
        deleteButton.setGraphic(deleteIcon);
        deleteButton.setOnMouseClicked(mouseEvent -> PersonDAO.delete(person.getId()));
        hbox.getChildren().addAll(labelId, labelName, deleteButton);
        return hbox;
    }


    public void handleAddGuest(ActionEvent actionEvent) {
        showAddGuesDialog();
    }

    private void showAddGuesDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add New Guest");

        ButtonType buttonTypeOk = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField nameField = new TextField();
        nameField.setPromptText("Guest Name");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeOk) {
                return nameField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            if (result != null && !result.isEmpty()) {
                PersonDAO.insertPerson(result);
            }
        });
    }
}
