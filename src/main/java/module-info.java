module com.example.visualjavafxapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.example.visualjavafxapp to javafx.fxml;
    exports com.example.visualjavafxapp;
}