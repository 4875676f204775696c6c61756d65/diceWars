package GameCore;

import java.util.HashMap;
import java.util.LinkedList;

public class IADifficile extends JoueurIA {

    public IADifficile(String _pseudo, Integer level) {
        super(_pseudo, level);
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

                    if (possible == true) {

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
        double attaqueForte = nbrAttaque(carte);
        attaqueForte = nbTour - attaqueForte;
        if (attaqueForte == 0) {
            return "terminer";
        }
        if (attaqueForte < 0) {
            attaqueForte = 1;
        }
        if (MATH < (1 / attaqueForte)) {

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
        int score = -1;
        Territoire attaquant = null;
        Territoire defenseur = null;
        for (Territoire attaquantPotentiel : getTerrConquis()) {
            if (attaquantPotentiel.getForce() > 1) {
                int forceAtt = attaquantPotentiel.getForce();
                for (Integer def : attaquantPotentiel.getVoisins()) {
                    Territoire defenseurPotentiel = actuelle.getTerritoireWithId(def);
                    if (!attaquantPotentiel.getIdJoueur().equals(defenseurPotentiel.getIdJoueur())) {
                        int forceDef = defenseurPotentiel.getForce();
                        int taille = 0;
                        HashMap<Integer, Territoire> parcourus = new HashMap<>();
                        LinkedList<Territoire> suivants = new LinkedList<>();
                        for (Integer IDVoisin : attaquantPotentiel.getVoisins()) {
                            Territoire potentiel = actuelle.getTerritoireWithId(IDVoisin);
                            if (potentiel.getIdJoueur().equals(attaquantPotentiel.getIdJoueur())) {
                                suivants.addFirst(actuelle.getTerritoireWithId(IDVoisin));
                            }
                        }
                        while (suivants.size() != 0) {
                            Territoire suivant = suivants.pop();
                            if (!parcourus.containsKey(suivant.getId())) {
                                parcourus.put(suivant.getId(), suivant);
                                taille += 1;
                                for (Integer potentiel : suivant.getVoisins()) {
                                    Territoire possible = actuelle.getTerritoireWithId(potentiel);
                                    if (possible.getIdJoueur().equals(attaquantPotentiel.getIdJoueur())) {
                                        suivants.addFirst(possible);
                                    }
                                }
                            }
                        }
                        if (taille == 0) {
                            taille = 1;
                        }
                        int scorePotentiel = forceAtt - forceDef + taille;
                        if (forceAtt < forceDef - 1) {
                            scorePotentiel = 0;
                        }
                        if (score < scorePotentiel) {
                            score = scorePotentiel;
                            attaquant = attaquantPotentiel;
                            defenseur = defenseurPotentiel;
                        }
                    }

                }
            }
        }
        Integer[] retour = new Integer[2];
        retour[0] = attaquant.getId();
        retour[1] = defenseur.getId();
        return retour;

    }


    public int nbrAttaque(Carte carte) {
        int compteur = 0;
        for (Territoire attaquant : getTerrConquis()) {
            int forceAtt = attaquant.getForce();
            for (Integer def : attaquant.getVoisins()) {
                Territoire defenseur = carte.getTerritoireWithId(def);
                if (!attaquant.getIdJoueur().equals(defenseur.getIdJoueur())) {
                    int forceDef = defenseur.getForce();
                    HashMap<Integer, Territoire> parcourus = new HashMap<>();
                    LinkedList<Territoire> suivants = new LinkedList<>();
                    int taille = 0;
                    for (Integer IDVoisin : attaquant.getVoisins()) {
                        Territoire potentiel = carte.getTerritoireWithId(IDVoisin);
                        if (potentiel.getIdJoueur().equals(attaquant.getIdJoueur())) {
                            suivants.addFirst(carte.getTerritoireWithId(IDVoisin));
                        }
                    }
                    while (suivants.size() != 0) {
                        Territoire suivant = suivants.pop();
                        if (!parcourus.containsKey(suivant.getId())) {
                            parcourus.put(suivant.getId(), suivant);
                            taille += 1;
                            for (Integer potentiel : suivant.getVoisins()) {
                                Territoire possible = carte.getTerritoireWithId(potentiel);
                                if (possible.getIdJoueur().equals(attaquant.getIdJoueur())) {
                                    suivants.addFirst(possible);
                                }
                            }
                        }
                    }
                    if (taille == 0) {
                        taille = 1;
                    }
                    int score = forceAtt - forceDef + taille;
                    if (forceAtt < forceDef - 1) {
                        score = 0;
                    }
                    if (score > 10) {
                        compteur += 1;
                    }
                }
            }
        }
        return compteur;
    }

}
