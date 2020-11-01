package com.example.osiris.Models;

public class Usuario {

    private String id;
    private String login;
    private String password;
    private String deviceList;


    public String getId() {  return id;  }
    public void setId(String id) {
        this.id = id;
    }
    public String getLogin() { return login;  }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return login;
    }
}
