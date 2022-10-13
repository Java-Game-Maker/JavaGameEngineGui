module com.example.javagameenginegui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.javagameenginegui to javafx.fxml;
    exports com.example.javagameenginegui;
}