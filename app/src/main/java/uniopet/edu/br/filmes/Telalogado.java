package uniopet.edu.br.filmes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static com.android.volley.Request.Method.GET;

public class Telalogado extends Activity {

    private FirebaseAuth mAuth;
    private TextView nomeUsuariologado, titulofilme, idepisodio, diretorfilme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telalogado);

        mAuth = FirebaseAuth.getInstance();
        nomeUsuariologado = findViewById(R.id.nomeUsuariologado);
        titulofilme = findViewById(R.id.titulofilme);
        idepisodio = findViewById(R.id.idepisodio);
        diretorfilme = findViewById(R.id.diretorfilme);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://swapi.co/api/films/";

        nomeUsuariologado.setText("Bem vindo, "+mAuth.getCurrentUser().getEmail());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Filme filme = gson.fromJson(response,Filme.class);

                        for (Results items : filme.getResults()) {
                            titulofilme.setText(items.getTituloFilme());
                            idepisodio.setText(items.getIDFilme());
                            diretorfilme.setText(items.getDiretorFilme());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        queue.add(stringRequest);
    }

    public void logout(View view) {
        mAuth.signOut();
        telaprincipal();
    }
    public void telaprincipal(){
        Intent principal = new Intent(this, MainActivity.class);
        startActivity(principal);
    }
}
