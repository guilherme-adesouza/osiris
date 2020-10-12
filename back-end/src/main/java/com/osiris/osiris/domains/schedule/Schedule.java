package com.osiris.osiris.domains.schedule;

import com.osiris.osiris.domains.device.Device;

import javax.persistence.*;


@Entity
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String cron;
    @Column
    private String description;
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "embedded_id")
    private Device device;
    @Column
    private Boolean active;

    public Schedule() {
        this.active = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}