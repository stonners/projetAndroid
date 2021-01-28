package fr.univ_lorraine.iutmetz.wmce.dmcd0.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.R;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.SessionManager;

public class ConnexionFragment extends Fragment {
SessionManager sessionManager;
public Button btnConnexion;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_connexion, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        textView.setText("Connexion !");
        sessionManager= new SessionManager(this.getContext());

        this.btnConnexion = root.findViewById(R.id.btnConnexion);
        this.btnConnexion.setOnClickListener(this::onConnexion);
        return root;
    }

    private void onConnexion(View view) {
        Log.e( "onConnexion: ","testttttttttttt" );
    }


}
