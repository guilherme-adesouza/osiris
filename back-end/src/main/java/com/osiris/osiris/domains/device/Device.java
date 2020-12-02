package com.osiris.osiris.domains.device;

import javax.persistence.*;

@Entity
@Table(name = "embedded")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "motor_status")
    private boolean motorStatus;

    public Device( int id ){
        this.id = id;
    }

    public Device(long id, String macAddress, String displayName, boolean motorStatus) {
        this.id = id;
        this.macAddress = macAddress;
        this.displayName = displayName;
        this.motorStatus = motorStatus;
    }

    public Device(){
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isMotorStatus() {
        return motorStatus;
    }

    public void setMotorStatus(boolean motorStatus) {
        this.motorStatus = motorStatus;
    }
}