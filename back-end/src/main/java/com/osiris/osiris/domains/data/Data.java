package com.osiris.osiris.domains.data;

import com.osiris.osiris.domains.device.Device;

import javax.persistence.*;

@Entity
@Table(name = "data")
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "embedded_id")
    private Device device;

    private double luminosity;
    private double humidity;
    private double temperature;
    private double soil;
    @Column(name = "water_high")
    private boolean waterHigh;
    @Column(name = "water_low")
    private boolean waterLow;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public double getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(double luminosity) {
        this.luminosity = luminosity;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getSoil() {
        return soil;
    }

    public void setSoil(double soil) {
        this.soil = soil;
    }

    public boolean isWaterHigh() {
        return waterHigh;
    }

    public void setWaterHigh(boolean waterHigh) {
        this.waterHigh = waterHigh;
    }

    public boolean isWaterLow() {
        return waterLow;
    }

    public void setWaterLow(boolean waterLow) {
        this.waterLow = waterLow;
    }
}
