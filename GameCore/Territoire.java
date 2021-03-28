package GameCore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Territoire implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Integer id;
    private String idJoueur;
    private Integer force;
    // On peut faire peter l'archi du projet
    private List<Integer> voisins;

    // Constructeur lors de la creation de la carte
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

    public void setForce(Integer valeur) {

        force = valeur;

    }

    public List<Integer> getVoisins() {

        return new ArrayList<>(voisins);

    }

    public void setVoisins(ArrayList<Integer> _voisins) {

        voisins = new ArrayList<>(_voisins);

    }

    public String getIdJoueur() {
        return idJoueur;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Territoire)) return false;
        Territoire that = (Territoire) o;
        return Objects.equals(id, that.id) && Objects.equals(idJoueur, that.idJoueur) && Objects.equals(force, that.force) && Objects.equals(voisins, that.voisins);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idJoueur, force, voisins);
    }
}
