/**
 * Classe abstraite pour factoriser les 3 attributs communs à Catégorie et Produit
 *
 * Doit implémenter Serializable pour que les catégories/produits
 * puissent être sauvegardés et passés en paramètre d'une activité à l'autre
 *
 */
package fr.univ_lorraine.iutmetz.wmce.dmcd0.modele;

import java.io.Serializable;

public abstract class ElementVente implements Serializable {

    private int id;
    private String titre;
    private String visuel;

    public enum Exceptions { TITRE, VISUEL;}


    protected ElementVente(String titre, String visuel) {

        this(-1, titre, visuel);
    }

    protected ElementVente(int id, String titre, String visuel) {

        this.setId(id);
        this.setTitre(titre);
        this.setVisuel(visuel);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {

        if (titre==null || titre.trim().length()==0) {
            throw new IllegalArgumentException(Exceptions.TITRE.toString());
        }
        this.titre = titre;
    }

    public String getVisuel() {
        return this.visuel;
    }

    public void setVisuel(String visuel) {

        if (visuel==null || visuel.trim().length()==0) {
            throw new IllegalArgumentException(Exceptions.VISUEL.toString());
        }
        this.visuel = visuel;
    }
}
