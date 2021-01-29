package fr.univ_lorraine.iutmetz.wmce.dmcd0;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import fr.univ_lorraine.iutmetz.wmce.dmcd0.modele.Produit;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.ActiviteEnAttenteImage;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.AnnulerAlerte;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.CategorieDAO;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.FavorisDAO;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.SessionManager;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.PanierDAO;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.ProduitDAO;


public class VenteCatalogueFragment extends Fragment//AppCompatActivity

        implements ActiviteEnAttenteImage ,
        Response.Listener<JSONArray>,
        Response.ErrorListener{




    public static final int RETOUR = 0;
    public static final int ANNULER = 1;

    private String idClient;
    private ArrayList<Produit> modele;
    private ArrayList<Bitmap> listeImagesProduit;
    private ArrayList<Integer> listeProduitsFavoris;
    private double panier;

    private View root;

    private int noProduitCourant;
    private int roleActivite;

    private TextView nomProduit;
    private TextView tarifProduit;
    private TextView descriptionProduit;
    private ImageView imgProduit;
    private ImageView imgProduitZoom;
    private ImageView btnPanier;
    private Button bPrecedent;
    private Button bSuivant;
    private CategoriesAdapter adaptateur;
    private ToggleButton favButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.root = inflater.inflate(R.layout.fragment_ventecatalogue, container, false);
        this.modele = (ArrayList<Produit>) this.getArguments().getSerializable("listeProduit");
        Log.e("prod", this.modele + " ");
        // cas 1 : appel depuis CategoriesActivity
        if (savedInstanceState == null) {
            // récupération des paramètes envoyés par CategoriesActivity
            this.panier = this.getActivity().getIntent().getDoubleExtra("panier", 0);
            this.roleActivite = this.getActivity().getIntent().getIntExtra("role_activite", CategoriesFragment.VC_CATALOGUE);

            // Initialisation des données à afficher
            this.noProduitCourant = 0;

        } else {
            // cas 2 : gestion du pivot du téléphone portrait / paysage
            this.noProduitCourant = savedInstanceState.getInt("noProduit");
            this.modele = (ArrayList<Produit>) savedInstanceState.getSerializable("liste");
            this.panier = savedInstanceState.getDouble("panier");
            this.roleActivite = savedInstanceState.getInt("role_activite");
        }
        this.listeImagesProduit = new ArrayList<>();
        /*for (int i = 0; i < this.modele.size(); i++) {
            this.listeImagesProduit.add(null);
            ImageFromURL chargement = new ImageFromURL(this);
            chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~laroche5/WS_PM/" + this.modele.get(i).getVisuel() + ".jpg",
                String.valueOf(i));
        }*/
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("noProduit", this.noProduitCourant);
        outState.putSerializable("liste", this.modele);
        outState.putDouble("panier", this.panier);
        outState.putInt("role_activite", this.roleActivite);
    }

    @Override
    public void onStart() {
        super.onStart();

        // this.root.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.bPrecedent = this.root.findViewById(R.id.btn_precedent);
        this.bSuivant = this.root.findViewById(R.id.btn_suivant);
        this.nomProduit = this.root.findViewById(R.id.txt_nomproduit);
        this.descriptionProduit = this.root.findViewById(R.id.txt_descriptionproduit);
        this.tarifProduit = this.root.findViewById(R.id.txt_tarifproduit);
        this.imgProduit = this.root.findViewById(R.id.img_produit);
        this.imgProduitZoom = this.root.findViewById(R.id.img_produit_zoom);
        this.btnPanier = this.root.findViewById(R.id.ib_panier);
        this.favButton = this.root.findViewById(R.id.button_favorite);

        this.bPrecedent.setOnClickListener(this::onClickPrecedent);
        this.bSuivant.setOnClickListener(this::onClickSuivant);
        this.imgProduit.setOnClickListener(this::onClickProduit);
        this.imgProduitZoom.setOnClickListener(this::onClickGrandProduit);
        this.btnPanier.setOnClickListener(this::onClickAjouterPanier);
        this.favButton.setOnClickListener(this::onClickFav);

        SessionManager sessionManager = new SessionManager(getActivity());
        Log.e("test session",sessionManager.getIdClient());
        idClient = sessionManager.getIdClient();

        FavorisDAO.findByClient(this, idClient);

        // delay le chargement
        new Handler().postDelayed(
                this::changeProduit
                , 500);

        // this.changeProduit();
        this.gereVisibiliteNavigation();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            //     this.onClickRetour();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * (Dés)activation des boutons précédent/suivant
     */
    private void gereVisibiliteNavigation() {

        this.bSuivant.setEnabled(this.noProduitCourant < this.modele.size() - 1);
        this.bPrecedent.setEnabled(this.noProduitCourant > 0);
    }

    /**
     * clic sur le bouton "produit suivant >"
     *
     * @param v la vue cliquée (le bouton suivant)
     */
    public void onClickSuivant(View v) {

        if (this.noProduitCourant < this.modele.size() - 1) {
            this.noProduitCourant++;
            this.changeProduit();
        }

        this.gereVisibiliteNavigation();
    }

    /**
     * clic sur le bouton "produit précédent >"
     *
     * @param v la vue cliquée (le bouton précédent)
     */
    public void onClickPrecedent(View v) {

        if (this.noProduitCourant > 0) {
            this.noProduitCourant--;
            this.changeProduit();
        }

        this.gereVisibiliteNavigation();
    }

    /**
     * Au lancement de l'activité, ou apèrs navigation précédent, suivant :
     * affichage du produit courant
     */
    private void changeProduit() {

        // TODO: FavorisDAO

        ImageView img = root.findViewById(R.id.img_produit);
        /*if (listeImagesProduit != null) {
                img.setImageBitmap(this.listeImagesProduit.get(noProduitCourant));
            } else {*/
        int id = this.getResources().getIdentifier(
                this.modele.get(noProduitCourant).getVisuel(),
                "drawable",
                this.getActivity().getPackageName());
        this.imgProduit.setImageResource(id);

        //     }
        this.nomProduit.setText(this.modele.get(noProduitCourant).getTitre());
        this.descriptionProduit.setText(this.modele.get(noProduitCourant).getDescription());
        this.tarifProduit.setText(
                String.format(
                        getString(R.string.vc_txt_tarifproduit),
                        this.modele.get(noProduitCourant).getTarif()
                )
        );
        // Check le toggleButton si le produit est en favoris
        if (listeProduitsFavoris.contains(this.modele.get(noProduitCourant).getId())) {
            favButton.setChecked(true);
        }
        else {
            favButton.setChecked(false);
        }
    }

    /**
     * clic sur l'image d'ajout d'un produit au panier
     *
     * @param v la vue cliquée (l'ImageButton panier)
     */
    public void onClickAjouterPanier(View v) {
        Toast.makeText(this.getActivity(), String.format(getString(R.string.vc_ajout_panier), this.noProduitCourant), Toast.LENGTH_LONG).show();
        this.panier += this.modele.get(this.noProduitCourant).getTarif();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getContext());
        alertDialog.setTitle("Quantité");

        // Setting Dialog Message
        alertDialog.setMessage("combien en voulez_vous?");
        final EditText input = new EditText(this.getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        String id_client=this.idClient;
        int id_produit=this.modele.get(this.noProduitCourant).getId();
        Fragment frag=this;
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new Handler().postDelayed(
                                ()-> PanierDAO.createdPanier(frag,id_produit,Integer.parseInt(input.getText().toString()),id_client),
                                1000);

                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }


    /**
     * clic sur l'image du produit : affichage de la version zoomée du produit
     *
     * @param v la vue cliquée (l'ImageView d'affichage du produit)
     */
    public void onClickProduit(View v) {

        this.bSuivant.setVisibility(View.GONE);
        this.bPrecedent.setVisibility(View.GONE);
        ImageView source = (ImageView) v;
        this.imgProduitZoom.setImageDrawable(source.getDrawable());
        this.imgProduitZoom.setVisibility(View.VISIBLE);

        this.imgProduitZoom.bringToFront();
        this.imgProduitZoom.invalidate();
    }

    /**
     * clic sur l'image zoomée : retour à l'affichage normal, la version zoomée du produit est cachée
     *
     * @param v la vue cliquée (l'ImageView d'affichage du produit zoomé)
     */
    public void onClickGrandProduit(View v) {

        this.bSuivant.setVisibility(View.VISIBLE);
        this.bPrecedent.setVisibility(View.VISIBLE);
        this.imgProduitZoom.setVisibility(View.GONE);
    }

    public void onClickFav(View v) {
        String text;
        if (favButton.isChecked()) {
            FavorisDAO.updateFavStatus(this, idClient, this.modele.get(noProduitCourant).getId(), true);
            text = "Produit ajouté aux favoris";
        }
        else {
            FavorisDAO.updateFavStatus(this, idClient, this.modele.get(noProduitCourant).getId(), false);
            text = "Produit retiré des favoris";
        }
        Toast.makeText(this.getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * clic sur le bouton back du téléphone (celui du bas)
     */
    //  @Override
    public void onBackPressed() {
        //       this.getContext().onClickRetour();
    }

    /**
     * Gestion du retour vers l'activité CategoriesActivity
     * Permet de factoriser le code pour les deux méthodes précédentes
     */
 /*   public void onClickRetour() {
        Intent intent = new Intent();
        intent.putExtra("panier", this.panier);
        this.setResult(RETOUR, intent);
        this.finish();
    }*/

    /**
     * clic sur le bouton annuler : demande de confirmation
     *
     * @param v la vue cliquée (le bouton annuler)
     */
    public void onClickAnnuler(View v) {

        AnnulerAlerte alerte = new AnnulerAlerte();
        alerte.show(getActivity().getSupportFragmentManager(), "suppression");
    }

    /**
     * Callback de l'AlertDialog AnnulerAlerte
     *
     * @param dialog la boîte de dialogue sur laquelle on a cliqué sur un bouton
     * @param which  le bouton cliqué
     */
    //@Override
    public void onClick(DialogInterface dialog, int which) {

        if (which == DialogInterface.BUTTON_POSITIVE) {
            this.getActivity().setResult(ANNULER);
            this.getActivity().finish();
        } // sinon, ne rien faire : on reste sur l'activité VenteCatalogue
    }

    @Override
    public void receptionneImage(Object[] resultats) {
        if (resultats[0] != null) {
            int idx = Integer.parseInt((resultats[1].toString()));
            Bitmap img = (Bitmap) resultats[0];
            this.listeImagesProduit.set(idx, img);
            this.changeProduit();
            // this.adaptateur.notifyDataSetChanged();
        }
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            listeProduitsFavoris = new ArrayList<>();
            for (int i = 0; i < response.length(); i++) {
                JSONObject produit = response.getJSONObject(i);
                int idProduitFav = produit.getInt("id_produit");
                this.listeProduitsFavoris.add(idProduitFav);
            }

        } catch (Exception e) {
            Log.e("Error", "" + e);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");

        //    Toast.makeText(this, R.string.ca_erreur_bdd,Toast.LENGTH_LONG).show();
    }

}



















