package fr.univ_lorraine.iutmetz.wmce.dmcd0.tools;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class PanierDAO {
    public static void createdPanier(Fragment fragment, int idProduit, int quantite ,String idCli) {

        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url;
        int idClient = Integer.parseInt(idCli);

            url = "https://devweb.iutmetz.univ-lorraine.fr/~moirod1u/WS_PM/php/panier/createInPanier.php?idClient=1&idProduit="+idProduit+"&idTaille=3&quantite="+quantite;


        // Request a string response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.PATCH, url, null,
                (Response.Listener<JSONArray>) fragment,
                (Response.ErrorListener) fragment);

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }
}
