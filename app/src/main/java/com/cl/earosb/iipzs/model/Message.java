package com.cl.earosb.iipzs.model;

/**
 * Objeto para manejar mesajes desde la API
 * Created by earosb on 30-10-15.
 */
public class Message {

    private boolean error;

    private String msg;

    public Message(boolean error, String msg) {
        this.error = error;
        this.msg = msg;
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
