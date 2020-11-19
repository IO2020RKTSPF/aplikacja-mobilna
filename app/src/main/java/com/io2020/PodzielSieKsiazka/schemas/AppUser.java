package com.io2020.PodzielSieKsiazka.schemas;

import java.io.Serializable;

public class AppUser implements Serializable {
    private int id;
    private String imgUrl;
    private String name;
    private String email;

    public AppUser(int id, String imgUrl, String name, String email) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}