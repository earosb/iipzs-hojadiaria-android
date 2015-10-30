package com.cl.earosb.iipzs.model;

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

    private static final String TAG = "iipzs_Partida";

    @Column(name = "remote_id")
    public int remote_id;

    @Column(name = "nombre")
    public String nombre;

    @Column(name = "unidad")
    public String unidad;

    @Column(name = "cont")
    public int cont;

    public Partida() {
        super();
    }

    public static Partida find(int remote_id) {
        return Partida.load(Partida.class, remote_id);
    }

    public static List<Partida> getAll() {
        return new Select()
                .from(Partida.class)
                .orderBy("cont ASC")
                .execute();
    }

    public void actualizar() {
        new DownloadTask().execute("http://icilicafalpzs.cl/api/v1/trabajos");
    }

    private class DownloadTask extends AsyncTask<String, Long, String> {
        protected String doInBackground(String... urls) {
            try {
                return HttpRequest.get(urls[0]).accept("application/json").body();
            } catch (HttpRequest.HttpRequestException exception) {
                return null;
            }
        }

        protected void onPostExecute(String response) {
            // Log.d(TAG, response);
            Log.d(TAG, prettyfyJSON(response));
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Partida>>(){}.getType();
            List<Partida> partidas = gson.fromJson(response, type);

            ActiveAndroid.beginTransaction();
            try {
                int length = partidas.size();
                for (int i = 0; i < length; i++) {
                    Partida p = new Partida();
                    p.remote_id = partidas.get(i).remote_id;
                    p.nombre = partidas.get(i).nombre;
                    p.unidad = partidas.get(i).unidad;
                    p.cont = 0;
                    p.save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }
        }

        private String prettyfyJSON(String json) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(json);
            return gson.toJson(element);
        }
    }
}
