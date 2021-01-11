/**
 * Produits vendus dans la boutique
 *
 */
package fr.univ_lorraine.iutmetz.wmce.dmcd0.modele;

public class Produit extends ElementVente {

    private String description;
    private double tarif;
    private int idCategorie;

    public enum Exceptions { TARIF, DESCRIPTION;}


    public Produit(String titre, String visuel, String description, double tarif, int idCategorie) {

        this(-1, titre, visuel, description, tarif, idCategorie);
    }

    public Produit(int id, String titre, String visuel, String description, double tarif, int idCategorie) {

        super(id, titre, visuel);
        this.setDescription(description);
        this.setTarif(tarif);
        this.setIdCategorie(idCategorie);
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {

        if (description==null || description.trim().length()==0) {
            throw new IllegalArgumentException(Exceptions.DESCRIPTION.toString());
        }
        this.description = description;
    }

    public double getTarif() {
        return this.tarif;
    }

    public void setTarif(double tarif) {

        if (tarif <= 0) {
            throw new IllegalArgumentException(Exceptions.TARIF.toString());
        }

        this.tarif = tarif;
    }

    public int getIdCategorie() {
        return this.idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }
}
