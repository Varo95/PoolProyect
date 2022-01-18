package com.Alvaro.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class AboutController {

    private static final Logger logger = LoggerFactory.getLogger(AboutController.class);

    @FXML
    private Button github;

    @FXML
    protected void initialize() {
        github.setOnAction(actionEvent -> {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    URI uri;
                    try {
                        uri = new URI("https://github.com/Varo95/PoolProyect");
                        desktop.browse(uri);
                    } catch (URISyntaxException | IOException e) {
                        if (e.getClass().equals(URISyntaxException.class))
                            logger.error("La cadena introducida viola el código RFC2396" + e.getMessage());
                        else
                            logger.error("No se encontró el navegador por defecto " + e.getMessage());
                    }
                }
            }
        });
    }
}
