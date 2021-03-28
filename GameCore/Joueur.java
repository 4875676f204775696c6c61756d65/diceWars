package GameCore;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Joueur implements Serializable {

    @Serial
    private static final long serialVersionUID = 331L;
    private final String pseudo;
    private final List<Territoire> terrConquis;

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

    @Override
    public String toString() {
        return pseudo;
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

        System.out.println("Fin du tours de " + pseudo + ".");

    }

    public List<Territoire> getTerrConquis() {

        return terrConquis;

    }
}
