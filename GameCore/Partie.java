package GameCore;

import java.io.Serial;
import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.*;

public class Partie implements Serializable {

    @Serial
    private static final long serialVersionUID = -7077155813557735862L;
    private static int nbTour = 0;
    private final Carte carte;
    private final List<Joueur> joueurs;

    public Partie(int _nbJoueur, int _nbIAFacile, int _nbIAMoyenne, int _nbIADifficile, String cheminCarte) {

        joueurs = new LinkedList<>();

        for (int i = 0; i < _nbJoueur; i++) {

            String pseudo = "Player " + i;

            joueurs.add(new Joueur(pseudo));
        }

        for (int i = 0; i < _nbIAFacile; i++) {

            String pseudo = "IA Facile " + i;

            joueurs.add(new IAFacile(pseudo, 1));
        }

        for (int i = 0; i < _nbIAMoyenne; i++) {

            String pseudo = "IA Moyenne " + i;

            joueurs.add(new IAMoyenne(pseudo, 2));
        }

        for (int i = 0; i < _nbIADifficile; i++) {

            String pseudo = "IA Difficile " + i;

            joueurs.add(new IADifficile(pseudo, 3));
        }

        if (cheminCarte.equals("")) {
            carte = new Carte(joueurs);
        } else {

            carte = new Carte(cheminCarte, joueurs);

        }

        for (Territoire[] ligne : carte.getTerritoires()) {

            for (Territoire T : ligne) {

                for (Joueur J : joueurs) {

                    if (J.getPseudo().equals(T.getIdJoueur())) {

                        J.addTerritoire(T);

                    }

                }

            }

        }

        int min = carte.getNbTerritoires();

        for (Joueur J : joueurs) {

            if (J.getnbrTerritoire() < min) {

                min = J.getnbrTerritoire();

            }

        }

        for (Joueur J : joueurs) {

            distribuerRenfort(J, (min * 4) - J.getnbrTerritoire());

        }

    }

    public static int getNbTour() {

        return nbTour;

    }

    private static void setNbTour(int nouveau) {

        nbTour = nouveau;

    }

    public Joueur choisirJoueur() throws PartieTermine {

        if (joueurs.size() > 1) {

            Collections.shuffle(joueurs);
            return joueurs.iterator().next();

        } else {

            throw new PartieTermine();

        }

    }

    public String eliminerPerdant() throws PartieTermine {

        for (Joueur joueur : joueurs) {
            int nbr = joueur.getnbrTerritoire();
            if (nbr == 0) {

                System.out.println(joueurs);

                if (joueur instanceof JoueurIA) {

                    for (int i = 0; i < joueurs.size(); i++) {

                        if (joueurs.get(i) == joueur) {

                            joueurs.remove(i);

                        }

                    }

                } else {
                    joueurs.remove(joueur);
                }

                if (joueurs.size() == 1) {
                    throw new PartieTermine();
                }

                return joueur.getPseudo();
            }
        }

        return null;

    }

    public Territoire getTerritoireWithId(Integer id) {

        for (Territoire[] L : carte.getTerritoires()) {

            for (Territoire T : L) {

                if (T.getId().equals(id)) {

                    return T;

                }
            }

        }

        return null;

    }

    public ArrayList<String> actionIA(JoueurIA I) {

        int tour = 1;

        ArrayList<String> joueurElimine = new ArrayList<>();

        while (true) {

            String choix = I.choixAction(tour, carte);

            if (choix.equals("attaquer")) {

                Integer[] territoires = I.choixAttaque(carte);

                try {

                    Territoire t_attaquant = getTerritoireWithId(territoires[0]);

                    Territoire t_attaquer = getTerritoireWithId(territoires[1]);

                    int s_attaquant = score(lancerDes(t_attaquant.getForce()));

                    int s_attaquer = score(lancerDes(t_attaquer.getForce()));

                    if (s_attaquant <= s_attaquer) {

                        t_attaquant.perdreForce();

                    } else {

                        for (Joueur J : joueurs) {

                            if (J.getPseudo().equals(t_attaquer.getIdJoueur())) {

                                J.perdreTerritoire(t_attaquer);

                            }

                        }

                        I.addTerritoire(t_attaquer);

                        t_attaquer.conquis(I.getPseudo());

                        t_attaquer.setForce(t_attaquant.getForce() - 1);

                        t_attaquant.perdreForce();

                        String perdant = eliminerPerdant();

                        if (perdant != null) {

                            joueurElimine.add(perdant);

                        }

                    }
                } catch (PartieTermine e) {
                    System.out.println("Il semble qu'il n'y ai plus d'adversaire...");
                    return joueurElimine;
                }

            } else {

                I.terminerTour();

                if (I.getnbrTerritoire() > 0) {
                    int renfort = calculRenfort(I);

                    System.out.println("Renfort pour ce tour :" + renfort);

                    distribuerRenfort(I, renfort);
                }

                return joueurElimine;

            }

            tour = tour + 1;

        }

    }

    public ArrayList<Integer> lancerDes(Integer nbr) throws InvalidParameterException {

        if (nbr < 1) {

            throw new InvalidParameterException();

        } else {

            ArrayList<Integer> lance = new ArrayList<>();

            for (int i = 0; i < nbr; i++) {
                lance.add(1 + (int) (Math.random() * 6));
            }

            return lance;

        }

    }

    public Integer score(ArrayList<Integer> des) {
        int somme = 0;
        for (int de : des) {
            somme += de;
        }
        return somme;
    }

    public void distribuerRenfort(Joueur J, int nombre) {

        int lastCount = 0;

        do {

            Collections.shuffle(J.getTerrConquis());

            if (lastCount == nombre) {
                System.out.println("Impossible de vous donner plus de renfort. Tous vos territoires sont plein.");
                break;
            }

            lastCount = nombre;

            for (Territoire T : J.getTerrConquis()) {

                if (nombre > 0 && T.getForce() < 8) {

                    int renfort = (1 + (int) (Math.random() * nombre));

                    if (T.getForce() + renfort > 8) {

                        renfort = 8 - T.getForce();

                    }

                    nombre = nombre - renfort;

                    if (nombre < 0) {
                        T.setForce(T.getForce() + (renfort + nombre));
                    } else {
                        T.setForce(T.getForce() + renfort);
                    }

                } else {
                    break;
                }

            }

        } while (nombre > 0);

    }

    public int calculRenfort(Joueur J) {

        int renfort = 0;

        for (Territoire T : J.getTerrConquis()) {

            int compteur = 0;

            HashMap<Integer, Territoire> parcourus = new HashMap<>();
            LinkedList<Territoire> suivants = new LinkedList<>();

            for (Integer Tid : T.getVoisins()) {

                Territoire potentiel = getTerritoireWithId(Tid);

                if (potentiel.getIdJoueur().equals(J.getPseudo())) {

                    suivants.addFirst(getTerritoireWithId(Tid));

                }

            }

            while (suivants.size() != 0) {

                Territoire A = suivants.pop();

                if (!parcourus.containsKey(A.getId())) {

                    parcourus.put(A.getId(), A);
                    compteur = compteur + 1;
                    for (Integer Tid : A.getVoisins()) {

                        Territoire potentiel = getTerritoireWithId(Tid);

                        if (potentiel.getIdJoueur().equals(J.getPseudo())) {

                            suivants.addFirst(getTerritoireWithId(Tid));

                        }

                    }
                }

            }

            if (compteur >= renfort) {
                renfort = compteur;
            }

        }

        if (renfort == 0) {
            return 1;
        } else {
            return renfort;
        }

    }

    public List<Joueur> getJoueurs() {

        return joueurs;

    }

    public Carte getCarte() {

        return carte;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Partie)) return false;
        Partie partie = (Partie) o;
        return Objects.equals(carte, partie.carte) && Objects.equals(joueurs, partie.joueurs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carte, joueurs);
    }

    public boolean disposeDeForceSuffisante(Territoire territoireAttaquant) throws ForceInsuffisante {

        if (getTerritoireWithId(territoireAttaquant.getId()).getForce() > 1) {
            return true;
        } else {
            throw new ForceInsuffisante();
        }

    }

    public boolean estAccessible(Territoire source, Territoire cible) throws TerritoireNonAccessible {

        for (Integer Id : source.getVoisins()) {

            if (Id.equals(cible.getId())) {
                return true;
            }

        }

        throw new TerritoireNonAccessible();

    }

    public String attaqueJoueurHumain(Joueur attaquant, Integer iDattaquant, Integer iDattaquer) {

        Territoire t_attaquant = getTerritoireWithId(iDattaquant);

        Territoire t_attaquer = getTerritoireWithId(iDattaquer);

        int s_attaquant = score(lancerDes(t_attaquant.getForce()));

        int s_attaquer = score(lancerDes(t_attaquer.getForce()));

        if (s_attaquant <= s_attaquer) {

            t_attaquant.perdreForce();

            return "Echec de la mission...";

        } else {

            for (Joueur J : joueurs) {

                if (J.getPseudo().equals(t_attaquer.getIdJoueur())) {

                    J.perdreTerritoire(t_attaquer);

                }

            }

            attaquant.addTerritoire(t_attaquer);

            t_attaquer.conquis(attaquant.getPseudo());

            t_attaquer.setForce(t_attaquant.getForce() - 1);

            t_attaquant.perdreForce();

            return "Felicitations vous avez gagné un nouveau territoire.";

        }
    }

    public void terminerTour(Joueur J) {

        int renfort = calculRenfort(J);

        System.out.println("Renfort pour ce tour :" + renfort);

        distribuerRenfort(J, renfort);

    }
}
