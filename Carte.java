package GameCore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Carte {

    private final Territoire[][] listeTerritoire;

    public Carte(List<Joueur> joueurs) {
        System.out.print("Saisir le nombre de lignes dans la matrice: ");
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();

        System.out.print("Saisir le nombre de colonnes dans la matrice: ");
        int n = sc.nextInt();

        listeTerritoire = new Territoire[m][n];

        int id = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                Collections.shuffle(joueurs);
                Joueur maitre = joueurs.iterator().next();

                listeTerritoire[i][j] = new Territoire(id, maitre.getPseudo());
                id = id + 1;
            }
            id = id + 1;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                ArrayList<Integer> voisins = new ArrayList<>();

                if (i > 0) {
                    voisins.add(listeTerritoire[i - 1][j].getId());
                }

                if (i < m - 1) {
                    voisins.add(listeTerritoire[i + 1][j].getId());
                }

                if (j > 0) {
                    voisins.add(listeTerritoire[i][j - 1].getId());
                }

                if (j < n - 1) {
                    voisins.add(listeTerritoire[i][j + 1].getId());
                }

                listeTerritoire[i][j].setVoisins(voisins);

            }
        }

    }


    public void afficherMatrice() {
        System.out.println("\n-------------------[CARTE]---------------------------------------------------\n");
        for (Territoire[] territoires : this.listeTerritoire) {
            for (int j = 0; j < this.listeTerritoire[0].length; j++) {
                System.out.print(territoires[j] + "      ");
            }
            System.out.println("\n");
        }
    }

    public Territoire[][] getListeTerritoire() {

        return listeTerritoire;

    }
}
