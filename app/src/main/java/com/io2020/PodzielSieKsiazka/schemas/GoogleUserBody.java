package com.io2020.PodzielSieKsiazka.schemas;

public class GoogleUserBody {
    private String loginId;
    private String name;

    public GoogleUserBody(String loginId, String googleName) {
        this.loginId = loginId;
        this.name = googleName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getGoogleName() {
        return name;
    }

    public void setGoogleName(String googleName) {
        this.name = googleName;
    }
}
