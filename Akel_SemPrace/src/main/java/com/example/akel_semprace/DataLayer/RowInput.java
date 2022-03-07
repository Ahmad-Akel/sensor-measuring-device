package com.example.akel_semprace.DataLayer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Date;

public class RowInput implements Comparable<RowInput> {
   public final SimpleDoubleProperty  m3 = new SimpleDoubleProperty();

    private final ObjectProperty<Date> date = new SimpleObjectProperty<>(this, "date");
    public final SimpleIntegerProperty sensorId = new SimpleIntegerProperty();
    private int ID;
   public RowInput(){

   }
    public void setM(double m3) {
       this.m3.set(m3);

    }
    public final double getM3() {
        return this.m3.getValue();
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public final  Date getDate() {
        return date.getValue();
    }

    public void setSensorID(int sensorId) {
        this.sensorId.set(sensorId);
    }

    public final int getSensorId() {
        return sensorId.getValue();
    }

    @Override
    public String toString() {
        return "element Id = " +this.getID()+" Sensor Id: " + sensorId.getValue() + " Date: " + date.getValue() + " Measurment: " + m3.getValue();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public int compareTo(RowInput o) {
         return  compare(this.getM3(),o.getM3());
    }
    public  int compare (Double x, Double y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }
}
