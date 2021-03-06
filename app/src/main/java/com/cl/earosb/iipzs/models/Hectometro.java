package com.cl.earosb.iipzs.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by earosb on 20-10-15.
 *
 * Nombre del obj apunta a una "Vuelta del hectómetro"
 */
@Table(name = "Hectometro")
public class Hectometro extends Model {

    @Column(name = "km_inicio")
    public int km_inicio;

    @Column(name = "controlEstandar", onDelete = Column.ForeignKeyAction.CASCADE)
    public ControlEstandar controlEstandar;

    public Hectometro() {
        super();
    }

    public Hectometro(int km_inicio, ControlEstandar controlEstandar) {
        super();
        this.km_inicio = km_inicio;
        this.controlEstandar = controlEstandar;
    }

    public List<Trabajo> getTrabajos() {
        return getMany(Trabajo.class, "hectometro");
    }

    @Override
    public String toString() {
        return "Hectometro{" +
                "km_inicio=" + km_inicio +
                ", " + controlEstandar.toString() +
                '}';
    }
}
