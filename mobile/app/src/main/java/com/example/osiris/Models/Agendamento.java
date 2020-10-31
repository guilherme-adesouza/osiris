package com.example.osiris.Models;

import java.io.Serializable;

public class Agendamento implements Serializable {

    private String id;
    private String cron;
    private String description;
    private String deviceId;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCron() {
        return cron;
    }
    public void setCron(String cron) {
        this.cron = cron;
    }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
}
