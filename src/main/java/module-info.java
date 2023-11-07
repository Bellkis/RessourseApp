module com.appointment.booking {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.appointment.booking to javafx.fxml;
    exports com.appointment.booking;
}