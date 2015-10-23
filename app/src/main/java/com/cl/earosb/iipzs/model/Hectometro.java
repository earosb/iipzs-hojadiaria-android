package com.cl.earosb.iipzs.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by earosb on 20-10-15.
 */
@Table(name = "Hectometro")
public class Hectometro extends Model {

    @Column(name = "Km_inicio")
    public int km_inicio;

    @Column(name = "ControlEstandar")
    public ControlEstandar controlEstandar;

    public Hectometro() {
        super();
    }

    public Hectometro(int km_inicio, ControlEstandar controlEstandar) {
        super();
        this.km_inicio = km_inicio;
        this.controlEstandar = controlEstandar;
    }
}
