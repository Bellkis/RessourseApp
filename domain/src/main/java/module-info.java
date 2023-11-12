module org.group2.iatic.domain {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.group2.iatic.domain to javafx.fxml;
    exports org.group2.iatic.domain;
    exports org.group2.iatic.domain.booking.entities;
}