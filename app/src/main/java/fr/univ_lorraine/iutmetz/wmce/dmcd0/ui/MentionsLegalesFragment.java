package fr.univ_lorraine.iutmetz.wmce.dmcd0.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.R;

public class MentionsLegalesFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.fragment_mentions_legales, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        textView.setText("Mentions Légales !");
        return root;
    }
}
