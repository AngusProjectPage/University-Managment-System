module com.example.cs308fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.example.cs308fx to javafx.fxml;
    exports com.example.cs308fx;
    exports com.example.cs308fx.controllers;
    opens com.example.cs308fx.controllers to javafx.fxml;
}