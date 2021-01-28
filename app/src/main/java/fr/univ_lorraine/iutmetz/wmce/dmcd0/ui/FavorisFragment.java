package fr.univ_lorraine.iutmetz.wmce.dmcd0.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.R;

public class FavorisFragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_panier, container, false);
        TextView textView = root.findViewById(R.id.text_gallery);
        textView.setText("Panier !");
        return root;
}
}
