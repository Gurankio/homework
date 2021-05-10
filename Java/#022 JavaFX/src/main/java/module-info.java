module gurankio {

    requires java.desktop;
    requires javafx.fxml;
    requires javafx.controls;
    requires org.jfxtras.styles.jmetro;

    exports gurankio;
    exports gurankio.model;
    exports gurankio.controller;

    opens gurankio.model to javafx.base;
    opens gurankio.controller to javafx.fxml;
}