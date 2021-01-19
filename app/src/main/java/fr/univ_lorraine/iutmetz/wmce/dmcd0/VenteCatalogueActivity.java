/*
  Affichage et achat d'une catégorie de produits*

 */
package fr.univ_lorraine.iutmetz.wmce.dmcd0;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.modele.Produit;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.ActiviteEnAttenteImage;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.AnnulerAlerte;
import fr.univ_lorraine.iutmetz.wmce.dmcd0.tools.ImageFromURL;

public class VenteCatalogueActivity extends AppCompatActivity
    implements DialogInterface.OnClickListener, ActiviteEnAttenteImage {


    public static final int RETOUR = 0;
    public static final int ANNULER = 1;


    private ArrayList<Produit> modele;
    private ArrayList<Bitmap> listeImagesProduit;
    private double panier;

    private int noProduitCourant;
    private int roleActivite;

    private TextView nomProduit;
    private TextView tarifProduit;
    private TextView descriptionProduit;
    private ImageView imgProduit;
    private ImageView imgProduitZoom;
    private Button bPrecedent;
    private Button bSuivant;
    private CategoriesAdapter adaptateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventecatalogue);

        // cas 1 : appel depuis CategoriesActivity
        if (savedInstanceState == null) {
            // récupération des paramètes envoyés par CategoriesActivity
            int idCateg = this.getIntent().getIntExtra("id_categ", 1);
            this.panier = this.getIntent().getDoubleExtra("panier", 0);
            this.roleActivite = this.getIntent().getIntExtra("role_activite", CategoriesFragment.VC_CATALOGUE);

            // Initialisation des données à afficher
            this.modele = new ArrayList<>();
            this.noProduitCourant = 0;
            if (idCateg == 1) {
                Produit p0 = new Produit("A Noël c'est moi qui tient les rennes", "pull0", "Un pull qui ravira les petits et les grands. Tricoté par d'authentiques grand-mères Australiennes, en laine 100% coton naturel issue d'une filière agriculture durable certifiée ISO-2560.", 52, 1);
                Produit p1 = new Produit("Sonic te kiffe", "pull1", "Inspiré par la saga Séga (c'est plus fort que toi !), un pull 100% gamer qui te permettra de faire baver d'envie tes petits camarades de jeu.", 41.5, 1);
                Produit p2 = new Produit("Mon renne a les boules", "pull2", "Un grand classique revisité, à la fois renne et sapin de Noël, c'est toi le plus beau quand tu es décoré par ce pull !", 44, 1);
                Produit p3 = new Produit("Le père Noël a une gastro", "pull3", "Ah, le chic à la Française. Parce que les stars aussi vont sur le pot de temps en temps...", 35.2, 1);
                Produit p4 = new Produit("Une guirlande de pères Noël", "pull4", "Ils sont tous en rang, ils te regardent, à toi d'être bien sage pour mériter ce pull de fête !", 38, 1);
                this.modele.add(p0);
                this.modele.add(p1);
                this.modele.add(p2);
                this.modele.add(p3);
                this.modele.add(p4);
            } else if (idCateg == 2) {
                Produit b0 = new Produit("La chaleur des rennes", "bonnet0", "Classique mais efficace, un bonnet dont l'élégance n'est pas à souligner, il vous grattera comme il faut !", 15, 2);
                Produit b1 = new Produit("Noël Lorientais", "bonnet1", "Idéal pour tous les fans du FC Lorient, mais pas que ! ", 22, 2);
                Produit b2 = new Produit("Beau comme un Elfe", "bonnet2", "THE bonnet à oreilles, couleurs indémodables pour cette création inspirée de l'univers de Tolkien.", 17, 2);
                Produit b3 = new Produit("Traineau de rennes", "bonnet3", "Un grand classique, ce magnifique bonnet vous sierra en toutes circonstances, et s'adaptera à toutes vos tenues hivernales.", 15, 2);
                Produit b4 = new Produit("Real Santa", "bonnet4", "En direct de NYC, avec bois de rennes télescopiques", 20, 2);
                this.modele.add(b0);
                this.modele.add(b1);
                this.modele.add(b2);
                this.modele.add(b3);
                this.modele.add(b4);
            } else if (idCateg == 3) {
                Produit c0 = new Produit("Les trois amis", "casquette0", "Un trio féérique prendra soin de votre calvitie naissante, vous en serez ravi !", 30, 3);
                Produit c1 = new Produit("Dall", "casquette1", "Joyeux Noël avec nos petits lutins dansants", 35, 3);
                Produit c2 = new Produit("Magie de Noël", "casquette2", "Quoi de plus beau qu'un bonhomme de neige avec les enfants quand les premiers flocons tombent du ciel ?", 26, 2);
                Produit c3 = new Produit("Santa Hangover", "casquette3", "Le Père Noël est bien fatigué sur cette magnifique casquette, mais n'est-ce pas notre lot à tous une fois les fêtes passées ?", 30, 2);
                this.modele.add(c0);
                this.modele.add(c1);
                this.modele.add(c2);
                this.modele.add(c3);
            }
        } else {
            // cas 2 : gestion du pivot du téléphone portrait / paysage
            this.noProduitCourant = savedInstanceState.getInt("noProduit");
            this.modele = (ArrayList<Produit>) savedInstanceState.getSerializable("liste");
            this.panier = savedInstanceState.getDouble("panier");
            this.roleActivite = savedInstanceState.getInt("role_activite");
        }
        this.listeImagesProduit = new ArrayList<>();
        for (int i = 0; i < this.modele.size(); i++) {
            this.listeImagesProduit.add(null);
            ImageFromURL chargement = new ImageFromURL(this);
            chargement.execute("https://devweb.iutmetz.univ-lorraine.fr/~laroche5/WS_PM/" + this.modele.get(i).getVisuel() + ".jpg",
                String.valueOf(i));

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("noProduit", this.noProduitCourant);
        outState.putSerializable("liste", this.modele);
        outState.putDouble("panier", this.panier);
        outState.putInt("role_activite", this.roleActivite);
    }

    @Override
    public void onStart() {
        super.onStart();

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.bPrecedent = this.findViewById(R.id.btn_precedent);
        this.bSuivant = this.findViewById(R.id.btn_suivant);
        this.nomProduit = this.findViewById(R.id.txt_nomproduit);
        this.descriptionProduit = this.findViewById(R.id.txt_descriptionproduit);
        this.tarifProduit = this.findViewById(R.id.txt_tarifproduit);
        this.imgProduit = this.findViewById(R.id.img_produit);
        this.imgProduitZoom = this.findViewById(R.id.img_produit_zoom);

        this.changeProduit();
        this.gereVisibiliteNavigation();


        // Si l'activité est utilisée en catalogue, pas d'affichage des boutons "panier" et "annuler"
        ImageView ibPanier = this.findViewById(R.id.ib_panier);
        Button btnAnnuler = this.findViewById(R.id.btn_annuler);
        if (this.roleActivite == CategoriesFragment.VC_VENTE) {
            ibPanier.setVisibility(View.VISIBLE);
            btnAnnuler.setVisibility(View.VISIBLE);
        } else {
            ibPanier.setVisibility(View.GONE);
            btnAnnuler.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.onClickRetour();
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

        ImageView img = findViewById(R.id.img_produit);
        if (listeImagesProduit != null) {
                img.setImageBitmap(this.listeImagesProduit.get(noProduitCourant));

            } else {
            int id = this.getResources().getIdentifier(
                this.modele.get(noProduitCourant).getVisuel(),
                "drawable",
                this.getPackageName());
            this.imgProduit.setImageResource(id);

        }
        this.nomProduit.setText(this.modele.get(noProduitCourant).getTitre());
        this.descriptionProduit.setText(this.modele.get(noProduitCourant).getDescription());
        this.tarifProduit.setText(
            String.format(
                getString(R.string.vc_txt_tarifproduit),
                this.modele.get(noProduitCourant).getTarif()
            )
        );
    }

    /**
     * clic sur l'image d'ajout d'un produit au panier
     *
     * @param v la vue cliquée (l'ImageButton panier)
     */
    public void onClickAjouterPanier(View v) {

        Toast.makeText(this, String.format(getString(R.string.vc_ajout_panier), this.noProduitCourant), Toast.LENGTH_LONG).show();
        this.panier += this.modele.get(this.noProduitCourant).getTarif();
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

    /**
     * clic sur le bouton back du téléphone (celui du bas)
     */
    @Override
    public void onBackPressed() {
        this.onClickRetour();
    }

    /**
     * Gestion du retour vers l'activité CategoriesActivity
     * Permet de factoriser le code pour les deux méthodes précédentes
     */
    public void onClickRetour() {

        Intent intent = new Intent();
        intent.putExtra("panier", this.panier);

        this.setResult(RETOUR, intent);
        this.finish();
    }

    /**
     * clic sur le bouton annuler : demande de confirmation
     *
     * @param v la vue cliquée (le bouton annuler)
     */
    public void onClickAnnuler(View v) {

        AnnulerAlerte alerte = new AnnulerAlerte();
        alerte.show(getSupportFragmentManager(), "suppression");
    }

    /**
     * Callback de l'AlertDialog AnnulerAlerte
     *
     * @param dialog la boîte de dialogue sur laquelle on a cliqué sur un bouton
     * @param which  le bouton cliqué
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (which == DialogInterface.BUTTON_POSITIVE) {
            this.setResult(ANNULER);
            this.finish();
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


}
