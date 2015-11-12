package com.cl.earosb.iipzs.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by earosb on 16-10-15.
 */
@Table(name = "ControlEstandar")
public class ControlEstandar extends Model {

    @Column(name = "causa")
    public String causa;

    @Column(name = "fecha")
    public String fecha;

    @Column(name = "fecha_title")
    public String fecha_title;

    @Column(name = "km_inicio")
    public int km_inicio;

    @Column(name = "sync")
    public boolean sync;

    public ControlEstandar() {
        super();
    }

    public ControlEstandar(String causa, String fecha, String fecha_title, int km_inicio, boolean sync) {
        this.causa = causa;
        this.fecha = fecha;
        this.fecha_title = fecha_title;
        this.km_inicio = km_inicio;
        this.sync = sync;
    }

    public static List<ControlEstandar> getAll(){
        return new Select()
                .from(ControlEstandar.class)
                .orderBy("Id DESC")
                .execute();
    }

    public List<Hectometro> getHectometros() {
        return getMany(Hectometro.class, "controlEstandar");
    }

    @Override
    public String toString() {
        return "ControlEstandar{" +
                "fecha='" + fecha + '\'' +
                ", km_inicio=" + km_inicio +
                ", sync=" + sync +
                '}';
    }
}
