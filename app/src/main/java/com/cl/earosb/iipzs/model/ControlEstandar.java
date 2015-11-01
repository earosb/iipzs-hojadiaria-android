package com.cl.earosb.iipzs.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by earosb on 16-10-15.
 */
@Table(name = "ControlEstandar")
public class ControlEstandar extends Model {

    @Column(name = "fecha")
    public String fecha;

    @Column(name = "km_inicio")
    public int km_inicio;

    public ControlEstandar() {
        super();
    }

    public ControlEstandar(String fecha, int km_inicio) {
        super();
        this.fecha = fecha;
        this.km_inicio = km_inicio;
    }
}
