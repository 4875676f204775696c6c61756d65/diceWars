package GameCore;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class IAMoyenne extends JoueurIA {

    public IAMoyenne(String _pseudo, Integer level) {
        super(_pseudo, level);
    }

    @Override
    public boolean peutJouer(Carte carte) {

        for (Territoire T : getTerrConquis()) {

            if (T.getForce() > 1) {

                for (Integer voisin : T.getVoisins()) {

                    boolean possible = false;
                    if (T.getForce() >= carte.getTerritoireWithId(voisin).getForce()) {
                        possible = true;
                    }
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
        List<Territoire> attaquantPotentiel = new LinkedList<>();
        for (Territoire terr : getTerrConquis()) {
            if (terr.getForce() > 1) {

                for (Integer voisin : terr.getVoisins()) {

                    boolean possible = false;
                    if (terr.getForce() >= actuelle.getTerritoireWithId(voisin).getForce()) {
                        possible = true;
                    }
                    for (Territoire P : getTerrConquis()) {

                        if (P.getId().equals(voisin)) {
                            possible = false;
                        }
                    }

                    if (possible) {
                        attaquantPotentiel.add(terr);
                    }

                }

            }
        }
        Collections.shuffle(attaquantPotentiel);
        Territoire territoireAttaquant = attaquantPotentiel.iterator().next();
        List<Integer> ciblePotentiel = new LinkedList<>();
        for (Integer att : territoireAttaquant.getVoisins()) {
            boolean atta = false;
            if (territoireAttaquant.getForce() >= actuelle.getTerritoireWithId(att).getForce()) {
                atta = true;
            }
            for (Territoire P : getTerrConquis()) {

                if (P.getId().equals(att)) {
                    atta = false;
                }
            }

            if (atta) {
                ciblePotentiel.add(att);
            }
        }
        Integer[] retour = new Integer[2];
        retour[0] = territoireAttaquant.getId();
        Collections.shuffle(ciblePotentiel);
        retour[1] = ciblePotentiel.iterator().next();
        return retour;
    }
}
