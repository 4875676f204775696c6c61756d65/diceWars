package GameCore;

import java.util.LinkedList;

public class CarteTest {

    public static void main(String[] args) {


        LinkedList<Joueur> joueurs = new LinkedList<>();

        for (int i = 0; i < 4; i++) {

            String pseudo = "Frankie" + i;

            joueurs.add(new Joueur(pseudo));
        }

        Carte carte = new Carte(joueurs);

        carte.afficherMatrice();

        for (Territoire T : carte.getTerritoires()[0]) {

            System.out.println(T.getVoisins());

        }

        System.out.println("Test baudouin");

        Carte maCarte = new Carte("cartes/Territoire.csv", joueurs);

        for (Territoire T : maCarte.getTerritoires()[0]) {

            System.out.println(T.getVoisins());

        }

        maCarte.afficherMatrice();
        maCarte.writeToCsvFile(";", "cartes/test.csv");

    }


}
