package com.osiris.osiris.domains.user;

import com.osiris.osiris.domains.device.Device;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String login;
    @Column(columnDefinition = "text")
    private String password;
    @Column(name = "mobile_id")
    private String mobileId;
    @ManyToMany(targetEntity = Device.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_has_embedded",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "embedded_id"))
    private List<Device> deviceList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileId() {
        return mobileId;
    }

    public void setMobileId(String mobileId) {
        this.mobileId = mobileId;
    }

    public List<Device> getDeviceList() {
        if (deviceList == null) {
            deviceList = new ArrayList<>();
        }
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}