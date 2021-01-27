package fr.univ_lorraine.iutmetz.wmce.dmcd0.tools;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class FavorisDAO {

    public static void findByClient(Context activite, int idClient) {

        RequestQueue queue = Volley.newRequestQueue(activite);
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~moirod/WS_PM/php/favoris/findByClient.php?idClient=" + idClient;

        // Request a string response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                (Response.Listener<JSONArray>) activite,
                (Response.ErrorListener) activite);

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }

}
