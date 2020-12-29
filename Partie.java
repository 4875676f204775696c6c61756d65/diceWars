package GameCore;

import java.security.InvalidParameterException;
import java.util.*;

public class Partie {

    private final Carte carte;
    private final LinkedList<Joueur> joueurs;

    public Partie(int _nbJoueur) {

        joueurs = new LinkedList<>();

        for (int i = 0; i < _nbJoueur; i++) {

            String pseudo = choisirPseudo();

            joueurs.add(new Joueur(pseudo));
        }

        carte = new Carte(joueurs);

        for (Territoire[] ligne : carte.getListeTerritoire()) {

            for (Territoire T : ligne) {

                for (Joueur J : joueurs) {

                    if (J.getPseudo().equals(T.getIdJoueur())) {

                        J.addTerritoire(T);

                    }

                }

            }

        }

        for (Joueur J : joueurs) {

            distribuerRenfort(J, _nbJoueur * 3);

        }

    }

    public String choisirPseudo() {

        String pseudo;

        System.out.print("Choisssisez un pseudo : ");

        while (true) {

            Scanner sc = new Scanner(System.in);

            if (sc.hasNext()) {
                pseudo = sc.next();

                boolean valid = true;

                for (Joueur J : joueurs) {

                    if (J.getPseudo().equalsIgnoreCase(pseudo)) {
                        valid = false;
                        break;
                    }

                }

                if (valid) {
                    return pseudo;
                } else {
                    System.out.println("Ce pseudo est deja pris choisissez en un autre.");
                }

            } else {
                System.out.println("Veuillez entrer un pseudo.");
            }

        }

    }

    public Joueur choisirJoueur() throws PartieTermine {

        if (joueurs.size() > 1) {

            Collections.shuffle(joueurs);
            return joueurs.iterator().next();

        } else {

            throw new PartieTermine();

        }

    }

    public void eliminerPerdant() {
        for (Joueur joueur : joueurs) {
            int nbr = joueur.getnbrTerritoire();
            if (nbr == 0) {
                joueurs.remove(joueur);
                System.out.println("Le joueur suivant a été éliminié : " + joueur);
            }
        }
    }

    public Boolean validerAttaque(Joueur attaquant, Integer idTerritoireAttaquant, Integer idTerritoireAtttaquer) {

        for (Territoire T : attaquant.getTerrConquis()) {

            if (T.getId().equals(idTerritoireAttaquant)) {

                if (T.getForce() > 1) {

                    for (Integer V : T.getVoisins()) {

                        if (V.equals(idTerritoireAtttaquer)) {

                            for (Territoire I : attaquant.getTerrConquis()) {

                                return !I.getId().equals(idTerritoireAtttaquer);

                            }

                        }

                    }
                }

            }

        }

        return false;

    }

    public Territoire getTerritoireWithId(Integer id) {

        for (Territoire[] L : carte.getListeTerritoire()) {

            for (Territoire T : L) {

                if (T.getId().equals(id)) {

                    return T;

                }
            }

        }

        return null;

    }


    private void action(Joueur j) {

        while (true) {

            System.out.println("Que souhaitez vous faire ? (attaquer,terminer)");

            Scanner sc = new Scanner(System.in);

            String choix;

            if (sc.hasNext()) {
                choix = sc.next();

                if (choix.equalsIgnoreCase("attaquer")) {

                    System.out.println("Quels territoire souhaiter vous etendre sur quel territoire ? (t_attaque t_cible)");

                    Integer[] territoires = null;

                    try {
                        territoires = j.attaquerTerritoire();
                    } catch (InputError e) {
                        System.out.println("Mauvaise entrer. Entrer deux numero separe par un espace.");
                    }

                    if (territoires != null) {

                        if (validerAttaque(j, territoires[0], territoires[1])) {

                            Territoire t_attaquant = getTerritoireWithId(territoires[0]);

                            Territoire t_attaquer = getTerritoireWithId(territoires[1]);

                            int s_attaquant = score(lancerDes(t_attaquant.getForce()));

                            int s_attaquer = score(lancerDes(t_attaquer.getForce()));

                            System.out.println("Attaquant : " + s_attaquant);

                            System.out.println("Cible : " + s_attaquer);

                            if (s_attaquant <= s_attaquer) {

                                t_attaquant.perdreForce();

                                System.out.println("Oups... echec de la mission...");

                            } else {

                                for (Joueur J : joueurs) {

                                    if (J.getPseudo().equals(t_attaquer.getIdJoueur())) {

                                        J.perdreTerritoire(t_attaquer);

                                    }

                                }

                                j.addTerritoire(t_attaquer);

                                t_attaquer.conquis(j.getPseudo());

                                t_attaquer.setForce(t_attaquant.getForce() - 1);

                                t_attaquant.perdreForce();

                                System.out.println("Victoire ! vous avec conquis un nouveau territoire.");

                            }

                        } else {
                            System.out.println("Le territoire attaquant doit vous "); // mettre des excetpions.
                        }

                    }

                } else if (choix.equalsIgnoreCase("terminer")) {

                    j.terminerTour();

                    int renfort = calculRenfort(j);

                    System.out.println("Renfort pour ce tour :" + renfort);

                    distribuerRenfort(j, renfort);

                    System.out.println("Fin du tours.");

                    return;

                } else {
                    System.out.println("Tapez attaquer ou terminer.");
                }

            } else {
                System.out.println("Tapez attaquer ou terminer.");
            }

            carte.afficherMatrice();

            for (Joueur J : joueurs) {
                System.out.println(J.getTerrConquis());
            }

        }

    }

    public void start() {

        System.out.println("Debut du jeu");

        carte.afficherMatrice();

        while (joueurs.size() > 1) {

            try {
                Joueur j = choisirJoueur();

                System.out.println("C'est au tour de " + j.getPseudo());

                action(j);

                eliminerPerdant();

            } catch (PartieTermine partieTermine) {
                System.out.println("La partie est malheureusement deja fini...");
            }

            carte.afficherMatrice();

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

        Collections.shuffle(J.getTerrConquis());

        for (Territoire T : J.getTerrConquis()) {

            int renfort = (1 + (int) (Math.random() * nombre));

            nombre = nombre - renfort;

            if (nombre < 0) {
                T.setForce(renfort - nombre);
            } else {
                T.setForce(T.getForce() + renfort);
            }

        }

    }

    public int calculRenfort(Joueur J) {

        int renfort = 0;

        for (Territoire T : J.getTerrConquis()) {

            int compteur = 0;

            HashMap<Integer, Territoire> parcourus = new HashMap<>();
            LinkedList<Territoire> suivants = new LinkedList<>();

            for (Integer Tid : T.getVoisins()) {

                suivants.addFirst(getTerritoireWithId(Tid));

            }

            while (suivants.size() != 0) {

                Territoire A = suivants.pop();

                if (!parcourus.containsKey(A.getId())) {

                    parcourus.put(A.getId(), A);
                    compteur = compteur + 1;
                    for (Integer Tid : A.getVoisins()) {

                        suivants.addFirst(getTerritoireWithId(Tid));

                    }
                }

            }

            if (compteur >= renfort) {
                renfort = compteur;
            }

        }

        return renfort;

    }

}
