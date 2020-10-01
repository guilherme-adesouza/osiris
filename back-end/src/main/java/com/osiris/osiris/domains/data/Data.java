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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
