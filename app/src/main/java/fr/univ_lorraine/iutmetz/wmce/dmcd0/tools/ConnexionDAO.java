package fr.univ_lorraine.iutmetz.wmce.dmcd0.tools;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class ConnexionDAO {
    public static void Connexion(Fragment fragment, String identifiant, String mdp) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~moirod1u/WS_PM/php/clients/connexion.php?username=%27"+identifiant+"%27&password=" + mdp;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest( url,
                (Response.Listener<String>) fragment,
                (Response.ErrorListener) fragment);

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
