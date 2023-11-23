module com.example.visualjavafxapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.visualjavafxapp to javafx.fxml;
    exports com.example.visualjavafxapp;
}