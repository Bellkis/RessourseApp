module com.appointment.booking {
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.appointment.booking to javafx.fxml;
    exports com.appointment.booking;
    exports com.appointment.booking.controllers;
    exports com.appointment.booking.models;

}