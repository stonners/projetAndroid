package fr.univ_lorraine.iutmetz.wmce.dmcd0.tools;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class CategorieDAO {

    public static void findAll(Context activite) {

        RequestQueue queue = Volley.newRequestQueue(activite);
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~laroche5/WS_PM/php/categories/findall.php";

        // Request a string response from the provided URL.
        JsonArrayRequest jsonrequest = new JsonArrayRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONArray>) activite,
                (Response.ErrorListener) activite);



        // Add the request to the RequestQueue.
        queue.add(jsonrequest);
    }
}
