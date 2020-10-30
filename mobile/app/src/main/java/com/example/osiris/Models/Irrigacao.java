package com.example.osiris.Models;

import java.io.Serializable;

public class Irrigacao implements Serializable {

    private String cron;
    private String description;

    public String getCron() {
        return cron;
    }
    public void setCron(String cron) {
        this.cron = cron;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) { this.description = description;
    }
}
