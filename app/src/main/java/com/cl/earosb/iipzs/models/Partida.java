package com.cl.earosb.iipzs.models;

import android.os.AsyncTask;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

    public static Partida findByRemoteId(int remote_id) {
        return Partida.load(Partida.class, remote_id);
    }

    public static List<Partida> getAll() {
        return new Select()
                .from(Partida.class)
                .orderBy("ranking DESC")
                .execute();
    }

}
