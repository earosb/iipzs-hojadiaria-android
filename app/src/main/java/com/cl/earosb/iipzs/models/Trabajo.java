package com.cl.earosb.iipzs.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by earosb on 20-10-15.
 */
@Table(name = "Trabajo")
public class Trabajo extends Model {

    @Column(name = "Partida")
    public Partida partida;

    @Column(name = "Cantidad")
    public int cantidad;

    @Column(name = "Hectometro")
    public Hectometro hectometro;

    @Column(name = "Observaciones")
    public String observaciones;

    public Trabajo() {
        super();
    }

    public Trabajo(Partida partida, int cantidad, Hectometro hectometro, String observaciones) {
        super();
        this.partida = partida;
        this.cantidad = cantidad;
        this.hectometro = hectometro;
        this.observaciones = observaciones;
    }
}
