package fr.univ_lorraine.iutmetz.wmce.dmcd0;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.modele.Categorie;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.CategorieDAO;

public class SplashScreen extends AppCompatActivity implements Response.Listener<JSONArray>,Response.ErrorListener {
    private static final int SPLASH_TIME_OUT = 3000;
    private ArrayList listeCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        final int SPLASH_TIME_OUT = 3000;
//a denoté aquand le volleye st fini

        this.listeCategories = new ArrayList<>();
        new Handler().postDelayed(
            ()-> CategorieDAO.findAll(this),
            SPLASH_TIME_OUT);


        /*new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Log.e("run: ","runnnn" );
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);
    */}

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONArray response) {
        try {

            for (int i = 0; i  < response.length(); i++ ) {
                JSONObject categorie = response.getJSONObject(i);
                int id = categorie.getInt("id_categorie");
                String titre = categorie.getString("titre");
                String visuel = categorie.getString("visuel");
                this.listeCategories.add(new Categorie(id, titre, visuel));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("listeCategories", listeCategories);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
