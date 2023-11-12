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
import org.group2.iatic.ihm.controllers.BookingsController;
import org.group2.iatic.ihm.models.BookingDataModel;
import org.group2.iatic.ihm.models.PersonDataModel;
import org.group2.iatic.ihm.models.RoomDataModel;
import org.group2.iatic.ihm.models.TimeSlotDataModel;
import org.group2.iatic.ihm.utils.Alerts;
import org.group2.iatic.ihm.utils.Generators;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class BookingsView extends VBox {
    BookingsController bookingsController;

    VBox pnItems;
    Label totalBookings;

    TextField searchPersons;

    public BookingsView(BookingsController bookingsController) {
        this.bookingsController = bookingsController;
        setPrefHeight(600);
        setPrefWidth(830);
        setAlignment(javafx.geometry.Pos.CENTER);
        setSpacing(20.0);
        setPadding(new Insets(30.0, 30.0, 30.0, 30.0));

        // HBox1
        HBox spacing = new HBox();
        spacing.setSpacing(10);

        Label bookingsLabel = new Label("Bookings");
        bookingsLabel.setTextFill(Color.web("#e7e5e5"));
        bookingsLabel.setFont(new Font(24.0));

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        searchPersons = new TextField();
        searchPersons.setPrefHeight(27.0);
        searchPersons.setPrefWidth(183.0);
        searchPersons.setPromptText("search");
        searchPersons.setStyle("-fx-background-color: #02030A; -fx-border-color: #B7C3D7; -fx-border-radius: 2em;");

        spacing.getChildren().addAll(bookingsLabel, region, searchPersons);

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

        totalBookings = new Label("0");
        totalBookings.setTextFill(Color.web("#2a73ff"));
        totalBookings.setFont(Font.font("System Bold", 26.0));

        Label totalBookingsLabel = new Label("Total Booking");
        totalBookingsLabel.setTextFill(Color.web("#e7e5e5"));

        vBox1.getChildren().addAll(totalBookings, totalBookingsLabel);

        // Region for spacing
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        // VBox2 inside HBox2
        VBox vBox2 = new VBox();
        vBox2.setAlignment(javafx.geometry.Pos.CENTER);
        vBox2.setPrefHeight(200.0);

        Button addButton = new Button("Add new booking");

        addButton.setOnMouseClicked(event -> handleAddBooking());

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
        bookingsController.bookingsUpdates.addListener((observableValue, aBoolean, t1) -> {
            updateContent();
        });
        updateContent();
        searchPersons.textProperty().addListener((observable, oldValue, newValue) -> {
            updateContent();
        });
    }

    void updateContent() {
        totalBookings.setText(String.valueOf(bookingsController.bookingDataModels.size()));
        List<BookingDataModel> filteredBookingDataModels = bookingsController.bookingDataModels.stream()
                .filter(bookingDataModel -> bookingDataModel.getPerson().getName().toLowerCase().contains(searchPersons.getText().toLowerCase()))
                .collect(Collectors.toList());
        pnItems.getChildren().clear();
        pnItems.getChildren().add(getTableHeader());
        for (BookingDataModel bookingDataModel : filteredBookingDataModels) {
            pnItems.getChildren().add(getBookingElement(bookingDataModel));
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
        labelId.setPrefWidth(30);
        labelId.setTextFill(Color.web("#ffffff"));
        HBox.setMargin(labelId, new Insets(0, 0, 0, 50)); // Adjust the insets as needed

        Label labelName = new Label("Person");
        labelName.setPrefWidth(80);
        labelName.setTextFill(Color.web("#ffffff"));

        Label roomName = new Label("Room");
        roomName.setPrefWidth(80);
        roomName.setTextFill(Color.web("#ffffff"));

        Label timeSlot = new Label("Time Slot");
        timeSlot.setPrefWidth(130);
        timeSlot.setTextFill(Color.web("#ffffff"));

        hbox.getChildren().addAll(labelId, labelName, roomName, timeSlot);
        return hbox;
    }

    HBox getBookingElement(BookingDataModel bookingDataModel) {
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPrefHeight(42.0);
        hbox.setPrefWidth(840.0);
        hbox.setSpacing(80.0);
        hbox.setStyle("-fx-background-color: #02030A;");

        Label labelId = new Label(String.valueOf(bookingDataModel.getId()));
        labelId.setPrefWidth(30);
        labelId.setTextFill(Color.web("#ffffff"));
        HBox.setMargin(labelId, new Insets(0, 0, 0, 50)); // Adjust the insets as needed

        Label labelName = new Label(bookingDataModel.getPerson().getName());
        labelName.setPrefWidth(80);
        labelName.setTextFill(Color.web("#ffffff"));

        Label roomName = new Label(bookingDataModel.getRoom().getName());
        roomName.setPrefWidth(80);
        roomName.setTextFill(Color.web("#ffffff"));

        Label timeSlot = new Label(bookingDataModel.getTimeSlot().toString());
        timeSlot.setPrefWidth(130);
        timeSlot.setTextFill(Color.web("#ffffff"));


        // Create a delete button
        Button deleteButton = new Button();
        ImageView deleteIcon = new ImageView(new Image(MainApplication.class.getResource("images/delete.png").toExternalForm())); // Replace "delete_icon.png" with the actual path to your delete button icon
        deleteIcon.setFitHeight(20); // Set the desired size for your delete button icon
        deleteIcon.setFitWidth(20);
        deleteButton.setGraphic(deleteIcon);
        deleteButton.setOnMouseClicked(mouseEvent -> {
            try {
                bookingsController.onDeleteBooking(bookingDataModel);
            } catch (Exception e) {
                Alert alert = Alerts.error("Error", "Error deleting guest", e.getMessage());
                alert.showAndWait();
            }
        });
        hbox.getChildren().addAll(labelId, labelName, roomName, timeSlot, deleteButton);
        return hbox;
    }

    private void handleAddBooking() {
        System.out.println("Alert clicked");
        showAddGuesDialog();
    }

    private void showAddGuesDialog() {
        Dialog<BookingDataModel> dialog = new Dialog<>();
        dialog.setTitle("Add New Booking");

        ButtonType buttonTypeOk = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        // Person Selector
        ComboBox<PersonDataModel> personComboBox = new ComboBox<>();
        // Assuming you have a list of persons, replace 'getFakePersons()' with your actual list
        personComboBox.getItems().addAll(bookingsController.personDataModels);
        personComboBox.setPromptText("Select Person");

        // Room Selector
        ComboBox<RoomDataModel> roomComboBox = new ComboBox<>();
        // Assuming you have a list of rooms, replace 'getFakeRooms()' with your actual list
        roomComboBox.getItems().addAll(bookingsController.roomDataModels);
        roomComboBox.setPromptText("Select Room");

        // Date-Time Pickers
        LocalDate now = LocalDate.now();
        DatePicker startTimePicker = new DatePicker(now);
        DatePicker endTimePicker = new DatePicker(now);

        grid.add(new Label("Person:"), 0, 0);
        grid.add(personComboBox, 1, 0);
        grid.add(new Label("Room:"), 0, 1);
        grid.add(roomComboBox, 1, 1);
        grid.add(new Label("Start Time:"), 0, 2);
        grid.add(startTimePicker, 1, 2);
        grid.add(new Label("End Time:"), 0, 3);
        grid.add(endTimePicker, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeOk) {
                TimeSlotDataModel timeSlotDataModel = new TimeSlotDataModel(Generators.generateRandomIntBasedOnTime(), startTimePicker.getValue().atTime(LocalTime.MIDNIGHT), endTimePicker.getValue().atTime(LocalTime.MIDNIGHT));
                return new BookingDataModel(Generators.generateRandomIntBasedOnTime(), personComboBox.getValue(), roomComboBox.getValue(), timeSlotDataModel);
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            if (result.getPerson() != null && result.getRoom() != null && result.getTimeSlot() != null) {
                try {
                    bookingsController.onAddBooking(result);
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error adding booking: " + e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                }
            }
        });
    }
}
