package com.Alvaro.controllers;

import com.Alvaro.App;
import com.Alvaro.objects.Tap;
import com.Alvaro.utils.Dialog;
import com.Alvaro.utils.Tools;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private Button btn_increase_cold_mass;
    @FXML
    private Label lb_cold_mass;
    @FXML
    private Button btn_decrease_cold_mass;
    @FXML
    private Button btn_increase_hot_mass;
    @FXML
    private Label lb_hot_mass;
    @FXML
    private Button btn_decrease_hot_mass;
    @FXML
    private MenuItem mi_pool;
    @FXML
    private MenuItem mi_about;
    @FXML
    private Button btn_play_stop;
    @FXML
    private Label lb_temp_final;
    @FXML
    private Label lb_cold_temp;
    @FXML
    private Label lb_hot_temp;
    @FXML
    private Button btn_increase_cold_temp;
    @FXML
    private Button btn_increase_hot_temp;
    @FXML
    private Button btn_decrease_cold_temp;
    @FXML
    private Button btn_decrease_hot_temp;

    private Tap t;

    @FXML
    protected void initialize() {
        mi_pool.setOnAction(event -> App.loadScene(new Stage(), "secondary", "PoolManager", true, false));
        mi_about.setOnAction(event -> App.loadScene(new Stage(), "about", "About this App", true, false));
        t = new Tap();
        createLabelBindings();
        setActionButtons();
        setIcons();
        Platform.runLater(() -> btn_play_stop.getScene().getWindow().setOnCloseRequest(windowEvent -> {
            if (!t.isInterrupted()) t.interrupt();
        }));
    }

    private void setIcons() {
        mi_pool.setGraphic(Tools.getIcon("pool"));
        mi_about.setGraphic(Tools.getIcon("info"));
        btn_increase_cold_mass.setGraphic(Tools.getIcon("add"));
        btn_increase_hot_mass.setGraphic(Tools.getIcon("add"));
        btn_increase_hot_temp.setGraphic(Tools.getIcon("add"));
        btn_increase_cold_temp.setGraphic(Tools.getIcon("add"));
        btn_decrease_cold_mass.setGraphic(Tools.getIcon("remove"));
        btn_decrease_hot_mass.setGraphic(Tools.getIcon("remove"));
        btn_decrease_cold_temp.setGraphic(Tools.getIcon("remove"));
        btn_decrease_hot_temp.setGraphic(Tools.getIcon("remove"));
        btn_play_stop.setGraphic(Tools.getIcon("play"));
        btn_play_stop.setStyle("-fx-background-color: rgb(132,227,70);");
        btn_play_stop.setText("Play");
    }

    private void setActionButtons() {
        btn_increase_cold_temp.setOnAction(event -> {
            t.getCold().setTemp(t.getCold().getTemp() + 0.5D);
        });
        btn_increase_hot_temp.setOnAction(event -> {
            t.getHot().setTemp(t.getHot().getTemp() + 0.5D);
        });
        btn_decrease_cold_temp.setOnAction(event -> {
            t.getCold().setTemp(t.getCold().getTemp() - 0.5D);
        });
        btn_decrease_hot_temp.setOnAction(event -> {
            t.getHot().setTemp(t.getHot().getTemp() - 0.5D);
        });
        btn_increase_hot_mass.setOnAction(event -> {
            t.setHot_mass(t.getHot_mass() + 0.5D);
        });
        btn_increase_cold_mass.setOnAction(event -> {
            t.setCold_mass(t.getCold_mass() + 0.5D);
        });
        btn_decrease_hot_mass.setOnAction(event -> {
            t.setHot_mass(t.getHot_mass() - 0.5D);
        });
        btn_decrease_cold_mass.setOnAction(event -> {
            t.setCold_mass(t.getCold_mass() - 0.5D);
        });
        btn_play_stop.setOnAction(event -> {
            if (btn_play_stop.getText().equals("Play")) {
                btn_play_stop.setGraphic(Tools.getIcon("pause"));
                btn_play_stop.setStyle("-fx-background-color: rgb(241,65,65);");
                btn_play_stop.setText("Stop");
                t.start();
            } else if (btn_play_stop.getText().equals("Stop")) {
                btn_play_stop.setGraphic(Tools.getIcon("play"));
                btn_play_stop.setStyle("-fx-background-color: rgb(132,227,70);");
                btn_play_stop.setText("Play");
                t.interrupt();
                t = new Tap();
                createLabelBindings();
            }
        });
    }

    private void createLabelBindings() {
        lb_hot_temp.textProperty().bind(Bindings.createStringBinding(() -> t.getHot().getTemp() + " ºC", t.getHot().tempProperty()));
        lb_cold_temp.textProperty().bind(Bindings.createStringBinding(() -> t.getCold().getTemp() + " ºC", t.getCold().tempProperty()));
        lb_hot_mass.textProperty().bind(Bindings.createStringBinding(() -> t.getHot_mass() + " Gramos", t.hot_massProperty()));
        lb_cold_mass.textProperty().bind(Bindings.createStringBinding(() -> t.getCold_mass() + " Gramos", t.cold_massProperty()));
        t.final_tempProperty().addListener((observableValue, oldValue, newValue) -> Platform.runLater(() -> {
            if (newValue != null && (newValue + "").contains(".")) {
                if (Double.parseDouble((newValue.doubleValue() + "").split("\\.")[1]) != 0) {
                    lb_temp_final.setText("≈" + Math.round(newValue.doubleValue()) + " ºC");
                } else {
                    lb_temp_final.setText(newValue + " ºC");
                }
            }
        }));
    }

}
