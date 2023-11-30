package com.example.visualjavafxapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import org.example.*;
import org.example.Clock_Interface;

import java.io.IOException;
import java.util.Optional;

public class ClockController {
    Clock_store c = BModel.build();

    Clock_Interface p;

    @FXML
    public Label name;
    @FXML
    public Label price;
    @FXML
    public Label time;

    public void setP(Clock_Interface p){
        this.p = p;
        name.setText(p.Get_Name());
        price.setText(Double.toString(p.Get_Price()));
        updateClockDisplay(p);
    }
    public void removeClock(ActionEvent actionEvent) {
        c.removeClock(p);
    }

    public void createContextMenu(MouseEvent event) {
        ContextMenu contextMenu = new ContextMenu();

        TextField hourInput = new TextField();
        hourInput.setPromptText("Часы");
        TextField minuteInput = new TextField();
        minuteInput.setPromptText("Минуты");
        TextField secondInput = new TextField();
        secondInput.setPromptText("Секунды");

        MenuItem setTimeItem = new MenuItem("Установить время");
        setTimeItem.setOnAction(e -> setTime(
                hourInput.getText(),
                minuteInput.getText(),
                secondInput.getText()
        ));

        contextMenu.getItems().add(new CustomMenuItem(hourInput, false));
        contextMenu.getItems().add(new CustomMenuItem(minuteInput, false));
        contextMenu.getItems().add(new CustomMenuItem(secondInput, false));
        contextMenu.getItems().add(setTimeItem);

        contextMenu.show((Node)event.getSource(), event.getScreenX(), event.getScreenY());
    }

    public void setTime(String hourStr, String minuteStr, String secondStr) {
        if (!isTimeValid(hourStr, minuteStr, secondStr)) {
            showAlert("Ошибка", "Введено некорректное время. Пожалуйста, введите время в формате ЧЧ:ММ:СС (00-23:00-59:00-59).");
            return;
        }
        try {
            int hours = Integer.parseInt(hourStr);
            int minutes = Integer.parseInt(minuteStr);
            int seconds = Integer.parseInt(secondStr);
            p.set(Enum_1.HOUR, hours);
            p.set(Enum_1.MINUTE, minutes);
            if (p instanceof Smart_Watch) {
                ((Smart_Watch) p).set(Enum_1.SECOND, seconds);
            }
            updateClockDisplay(p);
            c.updateClock(p);
        } catch (NumberFormatException ex) {
            System.out.println("Неверный формат времени!");
        } catch (Exception e) {
            throw new RuntimeException(e);
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

    public void updateClockDisplay(Clock_Interface clock) {
        if (time != null) {
            if (clock instanceof Smart_Watch) {
                Smart_Watch smartWatch = (Smart_Watch) clock;
                time.setText(String.format("%02d:%02d:%02d", smartWatch.getHour(), smartWatch.getMinute(), smartWatch.getSecond()));
            } else if (clock instanceof Watch) {
                Watch watch = (Watch) clock;
                time.setText(String.format("%02d:%02d", watch.getHour(), watch.getMinute()));
            }
        }
    }
}
