package org.group2.iatic.ihm.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.group2.iatic.ihm.MainApplication;
import org.group2.iatic.ihm.controllers.GuestsController;
import org.group2.iatic.ihm.models.PersonDataModel;
import org.group2.iatic.ihm.utils.Alerts;

import java.util.List;
import java.util.stream.Collectors;

public class GuestsView extends VBox {

    GuestsController guestsController;

    VBox pnItems;
    Label totalGuests;

    TextField searchPersons;

    public GuestsView(GuestsController guestsController) {
        this.guestsController = guestsController;
        setPrefHeight(600);
        setPrefWidth(830);
        setAlignment(javafx.geometry.Pos.CENTER);
        setSpacing(20.0);
        setPadding(new Insets(30.0, 30.0, 30.0, 30.0));

        // HBox1
        HBox spacing = new HBox();
        spacing.setSpacing(10);

        Label guestsLabel = new Label("Guests");
        guestsLabel.setTextFill(Color.web("#e7e5e5"));
        guestsLabel.setFont(new Font(24.0));

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        searchPersons = new TextField();
        searchPersons.setPrefHeight(27.0);
        searchPersons.setPrefWidth(183.0);
        searchPersons.setPromptText("search");
        searchPersons.setStyle("-fx-background-color: #02030A; -fx-border-color: #B7C3D7; -fx-border-radius: 2em;");

        spacing.getChildren().addAll(guestsLabel, region, searchPersons);

        // HBox2
        HBox hBox2 = new HBox();
        hBox2.setLayoutX(45.0);
        hBox2.setLayoutY(72.0);
        hBox2.setPrefHeight(92.0);
        hBox2.setPrefWidth(661.0);

        // VBox1 inside HBox2
        VBox vBox1 = new VBox();
        vBox1.setAlignment(javafx.geometry.Pos.CENTER);
        vBox1.setPrefHeight(200.0);

        totalGuests = new Label("0");
        totalGuests.setTextFill(Color.web("#2a73ff"));
        totalGuests.setFont(Font.font("System Bold", 26.0));

        Label totalGuestsLabel = new Label("Total Guests");
        totalGuestsLabel.setTextFill(Color.web("#e7e5e5"));

        vBox1.getChildren().addAll(totalGuests, totalGuestsLabel);

        // Region for spacing
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        // VBox2 inside HBox2
        VBox vBox2 = new VBox();
        vBox2.setAlignment(javafx.geometry.Pos.CENTER);
        vBox2.setPrefHeight(200.0);

        Button addButton = new Button("Add new guest");

        addButton.setOnMouseClicked(event -> handleAddGuest());

        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: #ffffff; -fx-font-size: 14px;");

        vBox2.getChildren().addAll(addButton);

        hBox2.getChildren().addAll(vBox1, region2, vBox2);

        // ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setLayoutX(14.0);
        scrollPane.setLayoutY(232.0);
        scrollPane.setPrefHeight(430);
        scrollPane.setPrefWidth(840);
        scrollPane.getStylesheets().add(MainApplication.class.getResource("style.css").toExternalForm());

        // VBox inside ScrollPane
        pnItems = new VBox();
        pnItems.setPrefHeight(430);
        pnItems.setPrefWidth(840);
        pnItems.setSpacing(5.0);
        pnItems.setStyle("-fx-background-color: #02030A;");
        scrollPane.setContent(pnItems);
        // Add nodes to VBox
        getChildren().addAll(spacing, hBox2, scrollPane);
        initListeners();
    }

    private void initListeners() {
        guestsController.guestUpdates.addListener((observableValue, aBoolean, t1) -> {
            updateContent();
        });
        updateContent();
        searchPersons.textProperty().addListener((observable, oldValue, newValue) -> {
            updateContent();
        });
    }

    void updateContent() {
        totalGuests.setText(String.valueOf(guestsController.personDataModels.size()));
        List<PersonDataModel> filteredPersonDataModels = guestsController.personDataModels.stream()
                .filter(person -> person.getName().toLowerCase().contains(searchPersons.getText().toLowerCase()))
                .collect(Collectors.toList());
        pnItems.getChildren().clear();
        pnItems.getChildren().add(getTableHeader());
        for (PersonDataModel personDataModel : filteredPersonDataModels) {
            pnItems.getChildren().add(getGuestElement(personDataModel));
        }
    }

    HBox getTableHeader() {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPrefHeight(42.0);
        hbox.setPrefWidth(840.0);
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

    HBox getGuestElement(PersonDataModel personDataModel) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPrefHeight(42.0);
        hbox.setPrefWidth(840.0);
        hbox.setSpacing(80.0);
        hbox.setStyle("-fx-background-color: #02030A;");

        Label labelId = new Label(String.valueOf(personDataModel.getId()));
        labelId.setPrefWidth(30);
        labelId.setTextFill(Color.web("#ffffff"));
        HBox.setMargin(labelId, new Insets(0, 0, 0, 50)); // Adjust the insets as needed

        Label labelName = new Label(personDataModel.getName());
        labelName.setPrefWidth(200);
        labelName.setTextFill(Color.web("#ffffff"));

        // Create a delete button
        Button deleteButton = new Button();
        ImageView deleteIcon = new ImageView(new Image(MainApplication.class.getResource("images/delete.png").toExternalForm())); // Replace "delete_icon.png" with the actual path to your delete button icon
        deleteIcon.setFitHeight(20); // Set the desired size for your delete button icon
        deleteIcon.setFitWidth(20);
        deleteButton.setGraphic(deleteIcon);
        deleteButton.setOnMouseClicked(mouseEvent -> {
            try {
                guestsController.onDeleteGuest(personDataModel);
            } catch (Exception e) {
                Alert alert = Alerts.error("Error", "Error deleting guest", e.getMessage());
                alert.showAndWait();
            }
        });
        hbox.getChildren().addAll(labelId, labelName, deleteButton);
        return hbox;
    }

    private void handleAddGuest() {
        System.out.println("Alert clicked");
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
            if (!result.isEmpty()) {
                try {
                    guestsController.onAddGuest(result);
                } catch (Exception e) {
                    Alert alert = Alerts.error("Error", "Error adding guest", e.getMessage());
                    alert.showAndWait();
                }
            }
        });
    }
}
