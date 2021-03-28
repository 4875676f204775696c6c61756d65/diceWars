package GameCore;

import java.util.LinkedList;

public class SauvegardeTest {

    public static void main(String[] args) {


        LinkedList<Joueur> joueurs = new LinkedList<>();

        Territoire terr1 = new Territoire(1, "Baud");
        Territoire terr2 = new Territoire(2, "Baud");
        Territoire terr3 = new Territoire(3, "Baud");

        Joueur monjoueur1 = new Joueur("Baudouin");
        Joueur monjoueur2 = new Joueur("Hugo");
        Joueur monjoueur3 = new Joueur("Loïc");
        Joueur monjoueur4 = new Joueur("Chourouk");

        monjoueur1.addTerritoire(terr1);
        monjoueur1.addTerritoire(terr2);
        monjoueur1.addTerritoire(terr3);

        joueurs.add(monjoueur1);
        joueurs.add(monjoueur2);
        joueurs.add(monjoueur3);
        joueurs.add(monjoueur4);


        //Sauvegarde maSauv = new Sauvegarde(joueurs);

        //maSauv.sauvegarder();

        //maSauv.charger();

    }
}


