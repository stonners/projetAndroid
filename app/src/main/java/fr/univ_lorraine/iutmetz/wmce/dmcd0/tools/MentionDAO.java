package fr.univ_lorraine.iutmetz.wmce.dmcd0.tools;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import androidx.fragment.app.Fragment;

public class MentionDAO {

    public static void find(Fragment fragment) {

        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~moirod1u/WS_PM/php/mentions/findMention.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(url,
            (Response.Listener<String>) fragment,
            (Response.ErrorListener) fragment);

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}
