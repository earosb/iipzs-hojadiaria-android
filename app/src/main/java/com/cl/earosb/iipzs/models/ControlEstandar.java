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

    @Column(name = "fecha")
    public String fecha;

    @Column(name = "km_inicio")
    public int km_inicio;

    @Column(name = "sync")
    public boolean sync;

    public ControlEstandar() {
        super();
    }

    public ControlEstandar(String fecha, int km_inicio, boolean sync) {
        super();
        this.fecha = fecha;
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
}
