package com.cl.earosb.iipzs.models;

/**
 * Created by earosb on 12-11-15.
 */
public class AuxObject {

    private String causa;

    private int trabajo_id;

    private int km_inicio;

    private int km_termino;

    private int cantidad;

    private String obs;

    public AuxObject(String causa, int trabajo_id, int km_inicio, int km_termino, int cantidad, String obs) {

        this.causa = causa;
        this.trabajo_id = trabajo_id;
        this.km_inicio = km_inicio;
        this.km_termino = km_termino;
        this.cantidad = cantidad;
        this.obs = obs;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public int getTrabajo_id() {
        return trabajo_id;
    }

    public void setTrabajo_id(int trabajo_id) {
        this.trabajo_id = trabajo_id;
    }

    public int getKm_inicio() {
        return km_inicio;
    }

    public void setKm_inicio(int km_inicio) {
        this.km_inicio = km_inicio;
    }

    public int getKm_termino() {
        return km_termino;
    }

    public void setKm_termino(int km_termino) {
        this.km_termino = km_termino;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}
