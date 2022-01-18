package com.Alvaro.objects;

import com.Alvaro.utils.Dialog;
import javafx.beans.property.SimpleDoubleProperty;

public class Water {
    private final SimpleDoubleProperty mass;
    private final SimpleDoubleProperty temp;

    public Water(double mass, double temp) {
        this.mass = new SimpleDoubleProperty(mass);
        this.temp = new SimpleDoubleProperty(temp);
    }

    public double getMass() {
        return mass.get();
    }

    public SimpleDoubleProperty massProperty() {
        return mass;
    }

    public double getTemp() {
        return temp.get();
    }

    public SimpleDoubleProperty tempProperty() {
        return temp;
    }

    public synchronized void setTemp(double temp) {
        if ((temp<=0) || (temp >=100))
            if (temp<=0)
                Dialog.showWarning("Error", "El agua no puede estar congelada", "No se puede mezclar agua con hielo");
            else
                Dialog.showWarning("Error", "El agua no puede estar hirviendo", "No se puede mezclar agua con vapor");
        else
            this.temp.set(temp);
    }
}
