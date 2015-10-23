package com.cl.earosb.iipzs.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by earosb on 16-10-15.
 */
@Table(name = "ControlEstandar")
public class ControlEstandar extends Model {

    @Column(name = "remote_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public int remote_id;

    @Column(name = "fecha")
    public String fecha;

    @Column(name = "km_inicio")
    public int km_inicio;

    public ControlEstandar() {
        super();
    }

    public ControlEstandar(int remote_id, String fecha, int km_inicio) {
        super();
        this.remote_id = remote_id;
        this.fecha = fecha;
        this.km_inicio = km_inicio;
    }
}
