package com.cl.earosb.iipzs.models;

/**
 * Created by earosb on 10-11-15.
 */
public class User {

    private String username;

    private String email;

    private String token_api;

    public User(String username, String email, String token_api) {
        this.username = username;
        this.email = email;
        this.token_api = token_api;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken_api() {
        return token_api;
    }

    public void setToken_api(String token_api) {
        this.token_api = token_api;
    }
}
