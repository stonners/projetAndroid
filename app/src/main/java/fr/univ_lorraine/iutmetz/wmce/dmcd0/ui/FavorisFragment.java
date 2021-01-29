package fr.univ_lorraine.iutmetz.wmce.dmcd0.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.R;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.modele.Produit;


public class FavorisFragment extends Fragment implements Response.Listener<JSONArray>, Response.ErrorListener {
    private ArrayList<Produit> listeProduitFavoris;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favoris, container, false);
        TextView textView = root.findViewById(R.id.text_gallery);
        textView.setText("Favoris !");
        return root;
    }

    public void onResponse(JSONArray response) {
        try {
            listeProduitFavoris = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                JSONObject produit = response.getJSONObject(i);
                Log.e("onResponse: ", produit + " ");
                int id = produit.getInt("id_produit");
                String titre = produit.getString("titre");
                String description = produit.getString("description");
                double tarif = produit.getDouble("tarif");
                String visuel = produit.getString("visuel");
                int id_categorie = produit.getInt("id_categorie");
                this.listeProduitFavoris.add(new Produit(id, titre, visuel, description, tarif, id_categorie));
            }

        } catch (Exception e) {
            Log.e("Error", "" + e);
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
    }
}
