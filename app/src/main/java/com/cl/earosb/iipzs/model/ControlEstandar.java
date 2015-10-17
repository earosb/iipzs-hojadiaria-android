package com.cl.earosb.iipzs.model;

/**
 * Created by earosb on 16-10-15.
 */
public class ControlEstandar {

    private int id;

    private String fecha;

    private int km_inicio;

    public ControlEstandar(int id, String fecha, int km_inicio) {
        this.id = id;
        this.fecha = fecha;
        this.km_inicio = km_inicio;
    }

    public int getKm_inicio() {
        return km_inicio;
    }

    public void setKm_inicio(int km_inicio) {
        this.km_inicio = km_inicio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
