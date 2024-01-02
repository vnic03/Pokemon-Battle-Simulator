

module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;
    requires org.apache.httpcomponents.httpcore;

    opens org.example to javafx.fxml;
    exports org.example;
}