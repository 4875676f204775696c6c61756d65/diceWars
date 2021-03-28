package GameCore;

import java.util.ArrayList;
import java.util.Collections;

public class IAFacile extends JoueurIA {


    public IAFacile(String _pseudo, Integer level) {
        super(_pseudo, level);
    }

    @Override
    public String choixAction(double nbTour, Carte carte) {

        double MATH = Math.random();

        if (MATH < (0.5 + (1 / nbTour))) {

            if (peutJouer(carte)) {

                System.out.println(getPseudo() + " souhaite attaquer.");
                return "attaquer";

            }
        }

        System.out.println(getPseudo() + " s'arrete de jouer.");
        return "terminer";

    }

    @Override
    public Integer[] choixAttaque(Carte actuelle) {

        ArrayList<Integer> ciblePotentiel = new ArrayList<>();

        Territoire TerritoireAttaquant;

        do {

            ArrayList<Territoire> attaquantPotentiel = new ArrayList<>();

            for (Territoire T : getTerrConquis()) {

                if (T.getForce() > 1) {

                    attaquantPotentiel.add(T);

                }

            }

            Collections.shuffle(attaquantPotentiel);

            TerritoireAttaquant = attaquantPotentiel.iterator().next();

            for (Integer C : TerritoireAttaquant.getVoisins()) {

                boolean validation = false;

                for (Territoire T : getTerrConquis()) {

                    if (T.getId().equals(C)) {
                        validation = true;
                        break;
                    }

                }

                if (!validation) {

                    ciblePotentiel.add(C);

                }

            }

        } while (ciblePotentiel.size() == 0);

        Collections.shuffle(ciblePotentiel);

        Integer idAttaquer = ciblePotentiel.iterator().next();

        Integer[] retour = new Integer[2];

        retour[0] = TerritoireAttaquant.getId();
        retour[1] = idAttaquer;

        return retour;

    }

    @Override
    public boolean peutJouer(Carte carte) {

        for (Territoire T : getTerrConquis()) {

            if (T.getForce() > 1) {

                for (Integer voisin : T.getVoisins()) {

                    boolean possible = true;

                    for (Territoire P : getTerrConquis()) {

                        if (P.getId().equals(voisin)) {
                            possible = false;
                        }

                    }

                    if (possible) {

                        return true;

                    }

                }

            }

        }

        return false;

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
