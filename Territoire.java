package GameCore;

import java.util.ArrayList;
import java.util.List;

public class Territoire {

    private final Integer id;
    private String idJoueur;
    private Integer force;
    private List<Integer> voisins;

    public Territoire(Integer numero, String _idJoueur) {
        id = numero;
        idJoueur = _idJoueur;
        force = 1;
        voisins = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public Integer getForce() {

        return force;

    }

    public List<Integer> getVoisins() {

        return new ArrayList<>(voisins);

    }

    public String getIdJoueur() {
        return idJoueur;
    }

    public void setVoisins(ArrayList<Integer> _voisins) {

        voisins = new ArrayList<>(_voisins);

    }

    public void setForce(Integer valeur) {

        force = valeur;

    }

    public void perdreForce() {

        force = 1;

    }

    @Override
    public String toString() {
        return "[id = " + id + " | Joueur = " + idJoueur + " | force = " + force + "]";
    }

    public void conquis(String id) {

        idJoueur = id;

    }
}
