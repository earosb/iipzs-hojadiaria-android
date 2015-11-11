package com.cl.earosb.iipzs.models;

/**
 * Objeto para manejar mesajes desde la API
 * Created by earosb on 30-10-15.
 */
public class Message {

    private boolean error;

    private String msg;

    private User user;

    public Message(boolean error, String msg, User user) {
        this.error = error;
        this.msg = msg;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
