/*
  Classe utilisée par la ListView pour afficher les items de catégories

 */
package fr.univ_lorraine.iutmetz.wmce.dmcd0;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.univ_lorraine.iutmetz.wmce.dmcd0.modele.Categorie;

public class CategoriesAdapter extends ArrayAdapter<Categorie> {



    private ArrayList<Categorie> listeCategories;
    private ArrayList<Bitmap> listeImagesCategories;

    public CategoriesAdapter(Context context, ArrayList<Categorie> liste, ArrayList<Bitmap> listeImages) {
        super(context, 0, liste);
        this.listeCategories = liste;
        this.listeImagesCategories = listeImages;
    }


    /**
     * Méthde chargée d'afficher une ligne(=un item) de la liste
     * @param position numéro de la ligne à afficher
     * @param convertView la vue d'affichage de la ligne  (permet de ne pas tout recréer si c'est inutile)
     * @param parent la vue parente
     * @return la vue d'affichage de la ligne
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_liste_categories, parent, false);
        }
            TextView tv = convertView.findViewById(R.id.ilc_titre);
            tv.setText(this.listeCategories.get(position).getTitre());

            ImageView img = convertView.findViewById(R.id.ilc_visuel);
            if (listeImagesCategories.get(position) != null) {
                img.setImageBitmap(this.listeImagesCategories.get(position));
            } else {
                int id = getContext().getResources().getIdentifier(
                    this.listeCategories.get(position).getVisuel(),
                    "drawable",
                    getContext().getPackageName()
                );
                img.setImageResource(id);
            }

        return convertView;
    }
}
