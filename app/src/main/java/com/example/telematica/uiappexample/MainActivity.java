package com.example.telematica.uiappexample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.telematica.uiappexample.connection.HttpServerConnection;
import com.example.telematica.uiappexample.models.Libro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

            @Override
            protected void onPreExecute(){

            }

            @Override
            protected String doInBackground(Void... params) {
                String resultado = new HttpServerConnection().connectToServer("http://www.mocky.io/v2/56990dc51200009e47e25b44", 15000);
                return resultado;
            }

            @Override
            protected void onPostExecute(String result) {
                if(result != null){
                    //tem.out.println(result);

                    // specify an adapter (see also next example)
                    mAdapter = new UIAdapter(getLista(result));
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        };

        task.execute();

        realm = Realm.getDefaultInstance();
        // ... Do something ...
        //File outFile = this.getDatabasePath("default.realm");
        //String outFileName = outFile.getPath();
        //System.out.println(outFile);
        savedata();
    }

    private List<Libro> getLista(String result){
        List<Libro> listaLibros = new ArrayList<Libro>();
        try {
            JSONArray lista = new JSONArray(result);

            int size = lista.length();
            for(int i = 0; i < size; i++){
                Libro libro = new Libro();
                JSONObject objeto = lista.getJSONObject(i);

                libro.setId(objeto.getInt("id"));
                libro.setNombre(objeto.getString("nombre"));
                libro.setEditorial(objeto.getString("editorial"));
                libro.setGenero(objeto.getString("genero"));
                libro.setAutor(objeto.getInt("autor"));

                listaLibros.add(libro);

                realm.beginTransaction();
                Libro places = realm.createObject(Libro.class); // Create a new object
                places.setNombre(libro.getNombre());
                places.setEditorial(libro.getEditorial());
                places.setAutor(libro.getAutor());
                realm.commitTransaction();
            }
            return listaLibros;
        } catch (JSONException e) {
            e.printStackTrace();
            return listaLibros;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();
    }

    private void savedata(){

        RealmResults<Libro> results = realm.where(Libro.class)
                //.equalTo("imagen", "soccer")
                //.or()
                //.equalTo("imagen", "tenis")   FILTRAJE
                .findAll();

        for (int i = 0; i < results.size(); i++) {
            Libro u = results.get(i);

            System.out.println(u.getNombre());

        }
    }


}
