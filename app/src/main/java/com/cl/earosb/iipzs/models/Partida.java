package com.cl.earosb.iipzs.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;


/**
 * Created by earosb on 20-10-15.
 * <p/>
 * Partida es un trabajo
 */
@Table(name = "Partida")
public class Partida extends Model {

    @Column(name = "remote_id")
    public int remote_id;

    @Column(name = "nombre")
    public String nombre;

    @Column(name = "unidad")
    public String unidad;

    @Column(name = "ranking")
    public int ranking;

    public Partida() {
        super();
    }

    public static List<Partida> getAll(String orderBy) {
        return new Select()
                .from(Partida.class)
                .orderBy(orderBy)
                .execute();
    }

    @Override
    public String toString() {
        return "Partida{" +
                "remote_id=" + remote_id +
                ", nombre='" + nombre + '\'' +
                ", unidad='" + unidad + '\'' +
                ", ranking=" + ranking +
                '}';
    }
}
