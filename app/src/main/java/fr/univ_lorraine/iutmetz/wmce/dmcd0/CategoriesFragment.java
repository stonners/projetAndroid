/**
 * Affichage de la liste des catégories et du panier courant
 */
package fr.univ_lorraine.iutmetz.wmce.dmcd0;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.modele.Categorie;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.modele.Produit;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.ActiviteEnAttenteImage;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.ImageFromURL;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.ProduitDAO;

public class CategoriesFragment extends Fragment
        implements Response.Listener<JSONArray>,
        Response.ErrorListener,
        AdapterView.OnItemClickListener,
        ActiviteEnAttenteImage {


    public static final int VC_VENTE = 0;
    public static final int VC_CATALOGUE = 1;

    private ArrayList<Categorie> listeCategories;
    private ArrayList<Bitmap> listeImagesCategories;
    private double panier;
    private View root;
    private TextView txtPanier;
    private CategoriesAdapter adaptateur;
    private View view;
    private ArrayList<Produit> listeProduit;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.root = inflater.inflate(R.layout.fragment_categories, container, false);



        // Cas 1 : l'app vient d'être lancée
        if (savedInstanceState == null) {

            Bundle bundleObject = this.getActivity().getIntent().getExtras();
            this.listeCategories = (ArrayList<Categorie>) bundleObject.getSerializable("listeCategories");

         /*   this.listeCategories = new ArrayList<>(3);
            this.listeCategories.add(new Categorie(1, "Pulls", "pull1"));
            this.listeCategories.add(new Categorie(2, "Bonnets", "bonnet1"));
            this.listeCategories.add(new Categorie(3, "Casquettes", "casquette1"));
*/
            this.panier = 0;
        } else {
            // cas 2 : on a pivoté (portrait / paysage) le téléphone
            this.listeCategories = (ArrayList<Categorie>) savedInstanceState.getSerializable("modele");
            this.panier = savedInstanceState.getDouble("panier");
        }
        this.listeImagesCategories = new ArrayList<>();
        for (int i = 0; i < this.listeCategories.size(); i++) {
            this.listeImagesCategories.add(null);
            ImageFromURL chargement = new ImageFromURL(this);
            chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~laroche5/WS_PM/" + this.listeCategories.get(i).getVisuel() + ".jpg",
                    String.valueOf(i));

        }

        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("modele", this.listeCategories);
        outState.putDouble("panier", this.panier);
    }

    @Override
    public void onStart() {

        super.onStart();

        this.txtPanier = this.root.findViewById(R.id.txt_panier);
        ListView lvCategories = this.root.findViewById(R.id.lv_liste);


        this.adaptateur = new CategoriesAdapter(
                this.getContext(),
                this.listeCategories,
                this.listeImagesCategories);

        lvCategories.setAdapter(adaptateur);

        lvCategories.setOnItemClickListener(this);

        this.updatePanier();
    }


    /**
     * clic sur un item de liste : lancement de l'activité VenteCatalgue
     * en spécifiant le requestCode : vc_vente ou vc_catalogue
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id_categ",this.listeCategories.get(position).getId());

        Navigation.findNavController(view).navigate(R.id.action_nav_boutique_to_venteCatalogueFragment,bundle);
        this.view=view;
        new Handler().postDelayed(
                ()-> ProduitDAO.findByCategories(this, this.listeCategories.get(position).getId())
                , 3000);



    }


    /**
     * Retour depuis l'activité VenteCatalogue
     *
     * @param requestCode le code d'envoi vers VenteCatalogueActivity
     * @param resultCode  le code renvoyé par VenteCatalogueActivity : retour normal ou annulation
     * @param intent      les paramètres envoyés par VenteCatalogueActivity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == VenteCatalogueFragment.RETOUR) {
            if (requestCode == VC_VENTE) {
                this.panier = intent.getDoubleExtra("panier", 0);
                this.updatePanier();
            } else if (requestCode == VC_CATALOGUE) {
                // ici, rien à faire si on revient du mode catalogue
            }
        } // on ne fait rien en cas d'annulation
    }

    /**
     * En début d'app ou au retour de VenteCatalogueActivity :
     * affichage du total du panier
     */
    private void updatePanier() {

        this.txtPanier.setText(String.format(getString(R.string.ac_txt_panier), this.panier));
    }

    public void receptionneImage(Object[] resultats) {
        if (resultats[0] != null) {
            int idx = Integer.parseInt((resultats[1].toString()));
            Bitmap img = (Bitmap) resultats[0];
            this.listeImagesCategories.set(idx, img);
            this.adaptateur.notifyDataSetChanged();
        }
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            listeProduit=new ArrayList<>();
            for (int i = 0; i  < response.length(); i++ ) {
                JSONObject produit = response.getJSONObject(i);
                int id = produit.getInt("id_produit");
                String titre = produit.getString("titre");
                String description = produit.getString("description");
                double tarif = produit.getDouble("tarif");
                String visuel= produit.getString("visuel");
                int id_categorie= produit.getInt("id_categorie");
                this.listeProduit.add(new Produit(id, titre, visuel,description,tarif,id_categorie));
            }

        } catch (Exception e) {
            Log.e("Error", "" + e);
        }


        Bundle bundle = new Bundle();
        bundle.putSerializable("listeProduit", listeProduit);


        Navigation.findNavController(this.view).navigate(R.id.action_nav_boutique_to_venteCatalogueFragment,bundle);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");

        //    Toast.makeText(this, R.string.ca_erreur_bdd,Toast.LENGTH_LONG).show();
    }
}
