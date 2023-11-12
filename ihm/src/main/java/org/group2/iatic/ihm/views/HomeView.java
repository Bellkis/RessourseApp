package org.group2.iatic.ihm.views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.group2.iatic.ihm.MainApplication;
import org.group2.iatic.ihm.actions.BookingActions;
import org.group2.iatic.ihm.actions.GuestsActions;
import org.group2.iatic.ihm.actions.RoomsActions;
import org.group2.iatic.ihm.actions.TimeSlotActions;
import org.group2.iatic.ihm.controllers.BookingsController;
import org.group2.iatic.ihm.controllers.GuestsController;
import org.group2.iatic.ihm.controllers.RoomsController;
import org.group2.iatic.ihm.controllers.TimeSlotController;

public class HomeView extends AnchorPane {

    GuestsActions guestsActions;
    RoomsActions roomsActions;
    BookingActions bookingActions;
    TimeSlotActions timeSlotActions;

    StackPane stackPane;
    Pane pnlGuests;

    Pane pnlBookings;

    Pane pnlRooms;

    Pane pnlTimeSlots;

    public HomeView(GuestsActions guestsActions, RoomsActions roomsActions, BookingActions bookingActions, TimeSlotActions timeSlotActions) {
        this.guestsActions = guestsActions;
        this.roomsActions = roomsActions;
        this.bookingActions = bookingActions;
        this.timeSlotActions = timeSlotActions;
        initiateElements();
    }

    private void initiateElements() {
        this.setPrefHeight(700.0);
        this.setPrefWidth(1100.0);
        this.setStyle("-fx-background-color: #123456;");

        // StackPane
        stackPane = new StackPane();
        stackPane.setLayoutX(258.0);
        stackPane.setPrefHeight(700.0);
        stackPane.setPrefWidth(844.0);
        stackPane.setStyle("-fx-background-color: #02030A;");

        // Panes inside StackPane
        pnlGuests = new Pane();
        pnlGuests.getChildren().add(new GuestsView(new GuestsController(guestsActions)));
        pnlGuests.setPrefHeight(554);
        pnlGuests.setPrefWidth(793);

        pnlRooms = new Pane();
        pnlRooms.getChildren().add(new RoomsView(new RoomsController(roomsActions)));
        pnlRooms.setPrefHeight(554);
        pnlRooms.setPrefWidth(793);

        pnlBookings = new Pane();
        pnlBookings.getChildren().add(new BookingsView(new BookingsController(bookingActions)));
        pnlBookings.setPrefHeight(554);
        pnlBookings.setPrefWidth(793);

        pnlTimeSlots = new Pane();
        pnlTimeSlots.getChildren().add(new TimeSlotsView(new TimeSlotController(timeSlotActions)));
        pnlTimeSlots.setPrefHeight(554);
        pnlTimeSlots.setPrefWidth(793);

        // Set effect for AnchorPane
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.valueOf("#1b1eeb"));
        setEffect(dropShadow);

        getChildren().addAll(getLeftDrawer(), stackPane);
        switchPane(pnlGuests);
    }

    VBox getLeftDrawer() {
        VBox vBox = new VBox();
        vBox.setAlignment(javafx.geometry.Pos.TOP_CENTER);
        vBox.setPrefHeight(576.0);
        vBox.setPrefWidth(256.0);
        vBox.setStyle("-fx-background-color: #05071F;");
        setBottomAnchor(vBox, 0.0);
        setTopAnchor(vBox, 0.0);

        // ImageView inside VBox
        javafx.scene.image.Image image = new javafx.scene.image.Image(MainApplication.class.getResource("images/booking.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(73.0);
        imageView.setFitWidth(67.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        VBox.setMargin(imageView, new Insets(50.0, 0.0, 10.0, 0.0));

        // VBox inside VBox
        VBox spacing = new VBox();
        spacing.setMinHeight(10);

        Button guestsButton = getDrawerButton("Guests", "user.png");

        guestsButton.setOnMouseClicked(mouseEvent -> {
            System.out.println("Clicked on guests");
            switchPane(pnlGuests);
        });
        Button roomsButton = getDrawerButton("Rooms", "room.png");

        roomsButton.setOnMouseClicked(mouseEvent -> {
            System.out.println("Clicked on rooms");
            switchPane(pnlRooms);
        });

        Button bookingsButton = getDrawerButton("Bookings", "booking.png");

        bookingsButton.setOnMouseClicked(mouseEvent -> {
            System.out.println("Clicked on bookings");
            switchPane(pnlBookings);
        });

        Button timeSlotsButton = getDrawerButton("Time Slots", "calendar.png");

        timeSlotsButton.setOnMouseClicked(mouseEvent -> {
            System.out.println("Clicked on bookings");
            switchPane(pnlTimeSlots);
        });

        vBox.getChildren()
                .addAll(imageView, spacing, guestsButton, roomsButton, bookingsButton, timeSlotsButton);
        return vBox;
    }

    Button getDrawerButton(String title, String icon) {
        Button button = new Button(title);
        button.setAlignment(javafx.geometry.Pos.BASELINE_LEFT);
        button.setGraphicTextGap(22.0);
        button.setMnemonicParsing(false);
        button.setPrefHeight(42.0);
        button.setPrefWidth(259.0);
        button.getStylesheets().add(MainApplication.class.getResource("style.css").toExternalForm());
        button.setTextFill(javafx.scene.paint.Color.valueOf("#e7e5e5"));

        ImageView imageView = new ImageView();
        imageView.setFitHeight(23.0);
        imageView.setFitWidth(27.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        Image image2 = new Image(MainApplication.class.getResource("images/" + icon).toExternalForm());
        imageView.setImage(image2);
        button.setGraphic(imageView);

        button.setPadding(new Insets(0.0, 20.0, 0, 30));
        return button;
    }

    private void switchPane(Pane newPane) {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(newPane);
        stackPane.requestLayout();
    }

}
