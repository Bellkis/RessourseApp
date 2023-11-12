module org.group2.iatic.ihm {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens org.group2.iatic.ihm to javafx.fxml;
    exports org.group2.iatic.ihm;

    exports org.group2.iatic.ihm.models;
    exports org.group2.iatic.ihm.actions;
    exports org.group2.iatic.ihm.utils;
}