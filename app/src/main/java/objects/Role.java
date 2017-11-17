package objects;

/**
 * Created by kevin on 17.11.2017.
 */

public class Role {


    private int id;
    private String nom;
    private String color;
    private String poids;
    private String taille;
    private String text;


    public Oiseau(int id, String nom, String color, String poids, String taille, String text) {
        this.id = id;
        this.nom = nom;
        this.color = color;
        this.poids = poids;
        this.taille = taille;
        this.text = text;
    }

    public Oiseau(String nom, String color, String poids, String taille, String text) {
        this.nom = nom;
        this.color = color;
        this.poids = poids;
        this.taille = taille;
        this.text = text;
    }


}
