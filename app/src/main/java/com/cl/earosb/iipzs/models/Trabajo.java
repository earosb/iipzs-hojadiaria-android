package com.cl.earosb.iipzs.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by earosb on 20-10-15.
 */
@Table(name = "Trabajo")
public class Trabajo extends Model {

    @Column(name = "partida")
    public Partida partida;

    @Column(name = "cantidad")
    public int cantidad;

    @Column(name = "hectometro", onDelete = Column.ForeignKeyAction.CASCADE)
    public Hectometro hectometro;

    @Column(name = "observaciones")
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

    @Override
    public String toString() {
        return "Trabajo{" + partida.toString() +
                ", cantidad=" + cantidad +
                ", " + hectometro.toString() +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
