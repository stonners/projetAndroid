/**
 * Catégories de produits vendus (pulls, gants, bonnets, etc.)
 *
 */
package fr.univ_lorraine.iutmetz.wmce.dmcd0.modele;

public class Categorie extends ElementVente {

    public Categorie(String titre, String visuel) {
        this(-1, titre, visuel);
    }

    public Categorie(int id, String titre, String visuel) {

        super(id, titre, visuel);
    }

    @Override
    public String toString() {

        return this.getTitre();
    }
}
