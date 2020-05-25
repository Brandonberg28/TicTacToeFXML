module org.brandonberg28 {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.brandonberg28 to javafx.fxml;
    exports org.brandonberg28;
}