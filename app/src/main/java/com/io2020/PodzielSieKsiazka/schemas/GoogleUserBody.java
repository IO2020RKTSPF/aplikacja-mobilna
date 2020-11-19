package com.io2020.PodzielSieKsiazka.schemas;

public class GoogleUserBody {
    private String googleId;
    private String googleName;

    public GoogleUserBody(String googleId, String googleName) {
        this.googleId = googleId;
        this.googleName = googleName;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getGoogleName() {
        return googleName;
    }

    public void setGoogleName(String googleName) {
        this.googleName = googleName;
    }
}
