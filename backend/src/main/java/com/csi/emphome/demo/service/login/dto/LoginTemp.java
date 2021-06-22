package com.csi.emphome.demo.service.login.dto;

import java.io.Serializable;

public class LoginTemp implements Serializable {

    private String username;
    private String password;

    public LoginTemp() {
    }

    public LoginTemp( String username, String password) {

        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginTemp{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
