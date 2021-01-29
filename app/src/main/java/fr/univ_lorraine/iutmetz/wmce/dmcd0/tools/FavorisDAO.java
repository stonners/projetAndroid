package fr.univ_lorraine.iutmetz.wmce.dmcd0.tools;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class FavorisDAO {

    public static void findByClient(Fragment fragment, String idClient) {

        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~moirod1u/WS_PM/php/favoris/findByClient.php?idClient=" + idClient;

        // Request a string response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                (Response.Listener<JSONArray>) fragment,
                (Response.ErrorListener) fragment);

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }

    public static void updateFavStatus(Fragment fragment, String idClient, int idProduit, boolean favStatus) {

        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        JsonArrayRequest jsonRequest;

        if (favStatus == true) {

            String url = "https://devweb.iutmetz.univ-lorraine.fr/~moirod1u/WS_PM/php/favoris/createFavoris.php?idClient=" + idClient + "&idProduit=" + idProduit;

            // Request a string response from the provided URL.
            jsonRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                    (Response.Listener<JSONArray>) fragment,
                    (Response.ErrorListener) fragment);
        }
        else {

            String url = "https://devweb.iutmetz.univ-lorraine.fr/~moirod1u/WS_PM/php/favoris/deleteFavoris.php?idClient=" + idClient + "&idProduit=" + idProduit;

            // Request a string response from the provided URL.
            jsonRequest = new JsonArrayRequest(Request.Method.DELETE, url, null,
                    (Response.Listener<JSONArray>) fragment,
                    (Response.ErrorListener) fragment);
        }

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);

    }

}
