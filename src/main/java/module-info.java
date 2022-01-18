module PoolProyect {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.base;
    requires org.jfxtras.styles.jmetro;
    requires org.slf4j;
    requires java.sql;
    requires com.h2database;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires javafaker;
    requires org.controlsfx.controls;

    opens com.Alvaro.controllers to javafx.fxml, javafx.controls, javafx.graphics, javafx.media, javafx.base, org.controlsfx.controls;
    opens com.Alvaro.objects to org.hibernate.orm.core, org.hibernate.commons.annotations;
    exports com.Alvaro;
}