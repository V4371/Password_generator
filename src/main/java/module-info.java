module com.password_generator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires org.apache.logging.log4j;

    opens com.password_generator to javafx.fxml;
    exports com.password_generator;
}