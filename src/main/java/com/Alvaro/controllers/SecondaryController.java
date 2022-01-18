package com.Alvaro.controllers;

import com.Alvaro.App;
import com.Alvaro.objects.Person;
import com.Alvaro.objects.Pool;
import com.Alvaro.utils.Tools;
import com.github.javafaker.Faker;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SecondaryController {

    @FXML
    private MenuItem mi_close;
    @FXML
    private Button btn_add_person;
    @FXML
    private TableView<Person> tableview_bath_plp;
    @FXML
    private TableView<Person> tableview_waiting_plp;
    @FXML
    private TableColumn<Person, String> tc_w8_name;
    @FXML
    private TableColumn<Person, String> tc_bath_name;
    private final Pool p = new Pool();
    private final SimpleObjectProperty<List<Person>> people = new SimpleObjectProperty<>(new ArrayList<>());

    @FXML
    protected void initialize() {
        people.addListener((observableValue, newVal, oldVal) -> {
            if (newVal.size() != oldVal.size()) {
                tableview_bath_plp.refresh();
                tableview_waiting_plp.refresh();
            }
            for (Person p : newVal) {
                if (p.isInPool())
                    tableview_bath_plp.getItems().add(p);
                else
                    tableview_waiting_plp.getItems().add(p);
            }
        });
        mi_close.setOnAction(actionEvent -> {
            App.closeScene((Stage) btn_add_person.getScene().getWindow());
        });
        btn_add_person.setOnAction(actionEvent -> {
            String name = new Faker().name().firstName();
            int weight = 40 + new Random().nextInt(120 - 40 + 1);
            Person pa = new Person(name, p, weight);
            people.get().add(pa);
            pa.start();
            new Thread(() -> {
                try {
                    pa.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    Notifications.create().title("¡Bañista fuera!").text(pa.getName1() + " ha salido de la piscina").showInformation();
                });
            }).start();
        });
        tableview_waiting_plp.setItems(FXCollections.observableList(p.onGrass));
        tableview_bath_plp.setItems(FXCollections.observableList(p.bathingPeople));
        setIcons();
        tc_w8_name.setCellValueFactory(eachPerson -> new SimpleStringProperty(eachPerson.getValue().getName1()));
        tc_bath_name.setCellValueFactory(eachPerson -> new SimpleStringProperty(eachPerson.getValue().getName1()));
        Platform.runLater(() -> btn_add_person.getScene().getWindow().setOnCloseRequest(windowEvent -> mi_close.fire()));
    }

    private void setIcons() {
        btn_add_person.setGraphic(Tools.getIcon("addPerson"));
        mi_close.setGraphic(Tools.getIcon("close"));
    }
}
