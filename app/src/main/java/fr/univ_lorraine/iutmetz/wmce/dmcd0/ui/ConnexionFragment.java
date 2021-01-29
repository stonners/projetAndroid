package fr.univ_lorraine.iutmetz.wmce.dmcd0.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.univ_lorraine.iutmetz.wmce.dmcd0.MainActivity;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.R;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.SessionManager;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.ConnexionDAO;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.ProduitDAO;

public class ConnexionFragment extends Fragment implements
        Response.Listener<String>,
        Response.ErrorListener{
SessionManager sessionManager;
public Button btnConnexion;
public EditText textPassword;
public View view;
public EditText textIdentifiant;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_connexion, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        textView.setText("Connexion !");
        sessionManager= new SessionManager(this.getContext());
        this.view=view;
        this.btnConnexion = root.findViewById(R.id.btnConnexion);
        this.btnConnexion.setOnClickListener(this::onConnexion);
        this.textPassword = root.findViewById(R.id.password);
        this.textIdentifiant =  root.findViewById(R.id.identifiant);
        return root;
    }

    private void onConnexion(View view) {
        this.view=view;
        new Handler().postDelayed(
                ()-> ConnexionDAO.Connexion(this, this.textIdentifiant.getText().toString(), this.textPassword.getText().toString()),
                1000);

    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
    }

    @Override
    public void onResponse(String response) {
        try {
            String id=response;

            if (id.compareTo("\"-1\"")!=0) {
                SessionManager session=new SessionManager(this.getContext());
                id = id.replaceAll("\"" ,  "");
                session.createSession(id);
                Log.e("teeeeet","ok ");
                Bundle bundle = new Bundle();
                Navigation.findNavController(this.view).navigate(R.id.nav_boutique,bundle);
                /*Intent intent = new Intent(this.getContext(), MainActivity.class);
                startActivity(intent);*/
            }

        } catch (Exception e) {
            Log.e("Error", "" + e);
        }
    }
}
