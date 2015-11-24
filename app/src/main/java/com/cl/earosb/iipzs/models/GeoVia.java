package com.cl.earosb.iipzs.models;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Geometria de la vía
 * Medición de trocha y peralte
 * Created by earosb on 24-11-15.
 */
@Table(name = "GeoVia")
public class GeoVia extends Model {

    @Column(name = "km")
    public int km;

    @Column(name = "trocha")
    public float trocha;

    @Column(name = "peralte")
    public float peralte;

    @Column(name = "controlEstandar", onDelete = Column.ForeignKeyAction.CASCADE)
    public ControlEstandar controlEstandar;

    public GeoVia() {
        super();
    }
}
