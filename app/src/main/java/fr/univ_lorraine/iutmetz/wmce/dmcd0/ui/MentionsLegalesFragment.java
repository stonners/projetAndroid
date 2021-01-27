package fr.univ_lorraine.iutmetz.wmce.dmcd0.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.R;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.CategorieDAO;

public class MentionsLegalesFragment extends Fragment implements Response.Listener<JSONArray>,Response.ErrorListener{

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.fragment_mentions_legales, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        textView.setText("Mentions Légales !");


             CategorieDAO.findAll(this.getContext());

        return root;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONArray response) {

    }
}
