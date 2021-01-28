package fr.univ_lorraine.iutmetz.wmce.dmcd0.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.R;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.SessionManager;

public class BoutiqueFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_boutique, container, false);

        BottomNavigationView bnv = root.findViewById(R.id.bnv_boutique);
        NavHostFragment navHostFragment=(NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment_boutique);
        NavigationUI.setupWithNavController(bnv,navHostFragment.getNavController());

        return root;
    }
}
