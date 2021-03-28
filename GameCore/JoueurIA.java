package GameCore;

import java.util.Objects;

public abstract class JoueurIA extends Joueur {

    Integer niveau;

    public JoueurIA(String _pseudo, Integer level) {
        super(_pseudo);
        niveau = level;
    }

    /**
     * public boolean peutJouer(){
     * <p>
     * for (Territoire T : getTerrConquis()){
     * <p>
     * if (T.getForce() > 1){
     * <p>
     * for (Integer voisin : T.getVoisins()){
     * <p>
     * boolean possible = true;
     * <p>
     * for (Territoire P : getTerrConquis()){
     * <p>
     * if (P.getId().equals(voisin)) {
     * possible = false;
     * break;
     * }
     * <p>
     * }
     * <p>
     * if (possible){
     * <p>
     * return true;
     * <p>
     * }
     * <p>
     * }
     * <p>
     * }
     * <p>
     * }
     * <p>
     * return false;
     * <p>
     * }
     */

    public abstract boolean peutJouer(Carte carte);

    public abstract String choixAction(double nbTour, Carte carte);

    public abstract Integer[] choixAttaque(Carte actuelle);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JoueurIA)) return false;
        JoueurIA joueurIA = (JoueurIA) o;
        return Objects.equals(niveau, joueurIA.niveau);
    }

    @Override
    public int hashCode() {
        return Objects.hash(niveau);
    }
}
