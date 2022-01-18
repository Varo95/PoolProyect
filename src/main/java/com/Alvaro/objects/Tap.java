package com.Alvaro.objects;

import com.Alvaro.utils.Dialog;
import com.Alvaro.utils.Tools;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.MediaPlayer;

public class Tap extends Thread {
    private final Water hot;
    private final Water cold;
    private final SimpleDoubleProperty final_temp;

    public Tap() {
        this.final_temp = new SimpleDoubleProperty(0.0D);
        this.hot = new Water(1D, 60D);
        this.cold = new Water(1D, 10D);
    }

    public double getHot_mass() {
        return hot.getMass();
    }

    public SimpleDoubleProperty hot_massProperty() {
        return hot.massProperty();
    }

    public double getCold_mass() {
        return cold.getMass();
    }

    public SimpleDoubleProperty cold_massProperty() {
        return cold.massProperty();
    }

    public SimpleDoubleProperty final_tempProperty() {
        return final_temp;
    }

    public synchronized void setHot_mass(double hot_mass) {
        if (hot_mass > 0)
            this.hot.massProperty().set(hot_mass);
        else
            Dialog.showWarning("Error", "La masa no puede tener valores negativos", "No existe la masa negativa");
    }

    public synchronized void setCold_mass(double cold_mass) {
        if (cold_mass > 0)
            this.cold.massProperty().set(cold_mass);
        else
            Dialog.showWarning("Error", "La masa no puede tener valores negativos", "No existe la masa negativa");
    }

    public Water getHot() {
        return this.hot;
    }

    public Water getCold() {
        return this.cold;
    }

    //Fórmula=> Tf = ((m1*ti1)+(m2*ti2))/(m1+m2)
    @Override
    public void run() {
        MediaPlayer mp = Tools.getSound();
        mp.play();
        mp.setCycleCount(MediaPlayer.INDEFINITE);
        while (!this.isInterrupted()) {
            final_temp.set(((hot.massProperty().get() * hot.getTemp()) + (cold.massProperty().get() * cold.getTemp())) / (hot.massProperty().get() + cold.massProperty().get()));
        }
        mp.stop();
    }
}
