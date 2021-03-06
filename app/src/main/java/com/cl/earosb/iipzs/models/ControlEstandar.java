package com.cl.earosb.iipzs.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by earosb on 16-10-15.
 * Control de estandar es una Inspección
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

    @Column(name = "obs")
    public String obs;

    @Column(name = "sync")
    public boolean sync;

    public ControlEstandar() {
        super();
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

    public List<GeoVia> getGeo() {
        return getMany(GeoVia.class, "controlEstandar");
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
