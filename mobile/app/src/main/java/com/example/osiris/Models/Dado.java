package com.example.osiris.Models;

import java.io.Serializable;

public class Dado implements Serializable {

    private String luminosity;
    private String humidity;
    private String deviceId;

    public String getLuminosity() {
        return luminosity;
    }
    public void setLuminosity(String luminosity) {
        this.luminosity = luminosity;
    }
    public String getHumidity() {return humidity; }
    public void setHumidity(String humidity) { this.humidity = humidity; }
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId;}

}
