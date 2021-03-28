package Graphical;

import GameCore.Joueur;
import GameCore.Partie;
import GameCore.PartieTermine;
import GameCore.Territoire;

import java.io.Serial;
import java.io.Serializable;

public class Model implements Serializable {

    @Serial
    private static final long serialVersionUID = 788839478505577382L;

    private final Partie partie;
    private Joueur actuel;
    private Territoire source;
    private Territoire cible;

    public Model(int nbJoueur, int nbIaFacile, int nbIaMoyenne, int nbIaDifficile) {

        partie = new Partie(nbJoueur, nbIaFacile, nbIaMoyenne, nbIaDifficile, "");

        try {
            actuel = partie.choisirJoueur();
        } catch (PartieTermine partieTermine) {
            System.out.println("La partie est terminé.");
        }

        source = null;

        cible = null;

    }

    public Partie getPartie() {
        return partie;
    }

    public Joueur getActuel() {
        return actuel;
    }

    public void setActuel(Joueur actuel) {
        this.actuel = actuel;
    }

    public Territoire getSource() {
        return source;
    }

    public void setSource(Territoire source) {
        this.source = source;
    }

    public Territoire getCible() {
        return cible;
    }

    public void setCible(Territoire cible) {
        this.cible = cible;
    }
}
