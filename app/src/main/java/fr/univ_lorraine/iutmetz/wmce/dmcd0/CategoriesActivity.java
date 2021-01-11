/**
 * Affichage de la liste des catégories et du panier courant
 */
package fr.univ_lorraine.iutmetz.wmce.dmcd0;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.modele.Categorie;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.ActiviteEnAttenteImage;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.ImageFromURL;

public class CategoriesActivity extends AppCompatActivity
    implements Response.Listener<JSONArray>, Response.ErrorListener, AdapterView.OnItemClickListener, ActiviteEnAttenteImage {


    public static final int VC_VENTE = 0;
    public static final int VC_CATALOGUE = 1;

    private ArrayList<Categorie> listeCategories;
    private ArrayList<Bitmap> listeImagesCategories;
    private double panier;

    private TextView txtPanier;
    private RadioButton rbVente;
    private CategoriesAdapter adaptateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        // Cas 1 : l'app vient d'être lancée
        if (savedInstanceState == null) {
            this.listeCategories = new ArrayList<>(3);
            this.listeCategories.add(new Categorie(1, "Pulls", "pull1"));
            this.listeCategories.add(new Categorie(2, "Bonnets", "bonnet1"));
            this.listeCategories.add(new Categorie(3, "Casquettes", "casquette1"));

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

        this.txtPanier = this.findViewById(R.id.txt_panier);
        this.rbVente = this.findViewById(R.id.rb_vente);
        ListView lvCategories = this.findViewById(R.id.lv_liste);

        this.adaptateur = new CategoriesAdapter(
            this,
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

        Intent intent = new Intent(CategoriesActivity.this, VenteCatalogueActivity.class);
        intent.putExtra("id_categ", this.listeCategories.get(position).getId());
        intent.putExtra("panier", this.panier);
        if (this.rbVente.isChecked()) {
            intent.putExtra("role_activite", VC_VENTE);
            startActivityForResult(intent, VC_VENTE);
        } else {
            intent.putExtra("role_activite", VC_CATALOGUE);
            startActivityForResult(intent, VC_CATALOGUE);
        }

    }

    /**
     * Retour depuis l'activité VenteCatalogue
     *
     * @param requestCode le code d'envoi vers VenteCatalogueActivity
     * @param resultCode  le code renvoyé par VenteCatalogueActivity : retour normal ou annulation
     * @param intent      les paramètres envoyés par VenteCatalogueActivity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == VenteCatalogueActivity.RETOUR) {
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
            for (int i = 0; i < response.length(); i++) {
                JSONObject o = response.getJSONObject();
            }
        } catch (Exception e) {
            Log.e("Error", "" + e);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
        //quand volley sera fini
        //    Toast.makeText(this, R.string.ca_erreur_bdd,Toast.LENGTH_LONG).show();
    }
}
