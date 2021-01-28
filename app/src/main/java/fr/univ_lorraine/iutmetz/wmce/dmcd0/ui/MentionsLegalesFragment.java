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
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.R;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.MentionDAO;

public class MentionsLegalesFragment extends Fragment implements Response.Listener<String>, Response.ErrorListener {

    private TextView textView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mentions_legales, container, false);
        this.textView = root.findViewById(R.id.text_gallery);
        textView.setText("Mentions Légales !");


        MentionDAO.find(this);

        return root;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        try {


                String mentionLegale = response;

                String mentionLegale2=mentionLegale.replaceAll("\\. " ,  "\n");
                Log.e("onResponse: ", mentionLegale);
         //   System.out.println("ligne 1"+newLine+"ligne2);
                this.textView.setText(mentionLegale2);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

