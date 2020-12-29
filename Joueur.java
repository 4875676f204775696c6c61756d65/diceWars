package GameCore;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Joueur {

    private final String pseudo;
    private final ArrayList<Territoire> terrConquis;

    public Joueur(String _pseudo) {

        pseudo = _pseudo;
        terrConquis = new ArrayList<>();

    }

    public String getPseudo() {
        return pseudo;
    }

    public int getnbrTerritoire() {

        return terrConquis.size();

    }

    public void addTerritoire(final Territoire ajout) {

        terrConquis.add(ajout);

    }

    public void perdreTerritoire(Territoire territoire) {

        terrConquis.remove(territoire);

    }

    public Integer[] attaquerTerritoire() throws InputError {

        Scanner scanner = new Scanner(System.in);

        int idTerrSource, idTerrCible;

        try {
            idTerrSource = scanner.nextInt();
            idTerrCible = scanner.nextInt();
        } catch (InputMismatchException ex) {

            throw new InputError();

        }

        Integer[] retour = new Integer[2];

        retour[0] = idTerrSource;
        retour[1] = idTerrCible;

        return retour;

    }

    public void terminerTour() {

        System.out.println("Fin du tours joeuurs");

    }

    public ArrayList<Territoire> getTerrConquis() {

        return terrConquis;

    }
}
