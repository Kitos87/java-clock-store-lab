package com.example.visualjavafxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import org.controlsfx.control.spreadsheet.Grid;
import org.example.*;

import java.io.IOException;

public class HelloController implements IOserver {
    @FXML
    public CheckBox isSmartWatch;
    Clock_store c = BModel.build();

    public HelloController(){
        c.sub(this);

    }
    @FXML
    public TextField name;
    @FXML
    public TextField price;
    @FXML
    public GridPane allClock;
    @FXML
    private Label labelMostExpensiveClock;
    @FXML
    private Label labelMostCommonBrand;
    @FXML
    private TextField hourInput;
    @FXML
    private TextField minuteInput;
    @FXML
    private TextField secondInput;

    private boolean initialized = false;


    @FXML
    public void addClock(ActionEvent actionEvent) {
        //c.addClock(new Watch(name.getText(),Double.parseDouble(price.getText())));
        String clockName = name.getText();
        double clockPrice = Double.parseDouble(price.getText());
        if (isSmartWatch.isSelected()) {
            Smart_Watch smartWatch = new Smart_Watch(0,0, 0, 0, clockName, clockPrice);
            c.addClock(smartWatch);
        } else {
            Watch watch = new Watch(0,0, 0, clockName, clockPrice);
            c.addClock(watch);
        }
        if (!initialized) {
            initialized = true;
        }
    }

    @Override
    public void event(Clock_store m) {
        allClock.getChildren().clear();
        for (Clock_Interface p : m) {
            try {
                ClockController pc = new ClockController();
                FXMLLoader fxmlLoader = new FXMLLoader(ClockController.class.getResource("clock-elem.fxml"));
                fxmlLoader.setController(pc);
                Parent root = fxmlLoader.load();
                pc.setP(p);
                root.setUserData(pc);
                root.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.SECONDARY) {
                        pc.createContextMenu(event);
                    }
                });
                allClock.addColumn(0, root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void showMostExpensiveClock(ActionEvent actionEvent) {
        Clock_Interface mostExpensiveClock = c.getMostExpensiveClock();
        if (mostExpensiveClock != null) {
            labelMostExpensiveClock.setText("Самые дорогие часы: " +
                    mostExpensiveClock.Get_Name() + " - " +
                    mostExpensiveClock.Get_Price());
        } else {
            labelMostExpensiveClock.setText("Самые дорогие часы: Нет данных");
        }
    }

    public void showMostCommonBrand(ActionEvent actionEvent) {
        String mostCommonBrand = c.getMostCommonBrand();
        if (mostCommonBrand != null) {
            labelMostCommonBrand.setText("Самый популярный бренд: " + mostCommonBrand);
        } else {
            labelMostCommonBrand.setText("Самый популярный бренд: Нет данных");
        }
    }


    @FXML
    private void setTimeForAllWatches() {
        String hourStr = hourInput.getText();
        String minuteStr = minuteInput.getText();
        String secondStr = secondInput.getText();
        if (!isTimeValid(hourStr, minuteStr, secondStr)) {
            showAlert("Ошибка", "Введено некорректное время. Пожалуйста, введите время в формате ЧЧ:ММ:СС (00-23:00-59:00-59).");
            return;
        }
        int hours = Integer.parseInt(hourStr);
        int minutes = Integer.parseInt(minuteStr);
        int seconds = Integer.parseInt(secondStr);
        for (Clock_Interface clock : c.getClocks()) {
            try {
                clock.set(Enum_1.HOUR, hours);
                clock.set(Enum_1.MINUTE, minutes);
                if (clock instanceof Smart_Watch) {
                    ((Smart_Watch) clock).set(Enum_1.SECOND, seconds);
                }
                c.updateClock(clock);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Node node : allClock.getChildren()) {
            if (node.getUserData() instanceof ClockController) {
                ClockController pc = (ClockController) node.getUserData();
                pc.updateClockDisplay(pc.p);
            }
        }
    }

    private boolean isTimeValid(String hourStr, String minuteStr, String secondStr) {
        try {
            int hours = Integer.parseInt(hourStr);
            int minutes = Integer.parseInt(minuteStr);
            int seconds = Integer.parseInt(secondStr);
            if (hours < 0 || hours > 23) return false;
            if (minutes < 0 || minutes > 59) return false;
            if (seconds < 0 || seconds > 59) return false;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
