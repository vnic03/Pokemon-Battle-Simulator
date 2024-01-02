

module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires kotlin.stdlib;


    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.Gui;
    exports org.example.Pokemon;
}