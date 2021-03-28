package GameCore;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Carte implements Serializable {

    @Serial
    private static final long serialVersionUID = -7564644082397529583L;

    private Territoire[][] territoires;

    public Carte(List<Joueur> joueurs) {

        int m = joueurs.size() * 2;
        int n = joueurs.size();

        territoires = new Territoire[m][n];

        int id = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (id < joueurs.size()) {

                    territoires[i][j] = new Territoire(id, joueurs.get(id).getPseudo());

                } else {

                    Collections.shuffle(joueurs);
                    Joueur maitre = joueurs.iterator().next();

                    territoires[i][j] = new Territoire(id, maitre.getPseudo());

                }

                id = id + 1;

            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                ArrayList<Integer> voisins = new ArrayList<>();

                if (i > 0) {
                    voisins.add(territoires[i - 1][j].getId());
                }

                if (i < m - 1) {
                    voisins.add(territoires[i + 1][j].getId());
                }

                if (j > 0) {
                    voisins.add(territoires[i][j - 1].getId());
                }

                if (j < n - 1) {
                    voisins.add(territoires[i][j + 1].getId());
                }

                territoires[i][j].setVoisins(voisins);

            }
        }

    }

    public Carte(String path, List<Joueur> joueurs) {

        BufferedReader br = null;
        String line;
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(path));
            line = br.readLine();
            String[] country = line.split(cvsSplitBy);
            territoires = new Territoire[Integer.parseInt(country[0])][Integer.parseInt(country[1])];
            for (int i = 0; i < territoires.length; i++) {
                for (int j = 0; j < territoires[0].length; j++) {
                    line = br.readLine();
                    country = line.split(cvsSplitBy);
                    System.out.print(Integer.parseInt(country[0]));
                    Collections.shuffle(joueurs);
                    Joueur maitre = joueurs.iterator().next();
                    territoires[i][j] = new Territoire(Integer.parseInt(country[0]), maitre.getPseudo());

                }
            }

            for (int i = 0; i < territoires.length; i++) {
                for (int j = 0; j < territoires[0].length; j++) {

                    ArrayList<Integer> voisins = new ArrayList<>();

                    if (i > 0) {
                        voisins.add(territoires[i - 1][j].getId());
                    }

                    if (i < territoires.length - 1) {
                        voisins.add(territoires[i + 1][j].getId());
                    }

                    if (j > 0) {
                        voisins.add(territoires[i][j - 1].getId());
                    }

                    if (j < territoires[0].length - 1) {
                        voisins.add(territoires[i][j + 1].getId());
                    }

                    territoires[i][j].setVoisins(voisins);

                }
            }

        } catch (FileNotFoundException e) {
            System.out.print("Fichier non existant.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void writeToCsvFile(String separator, String fileName) {

        int X = territoires.length;
        int Y = territoires[0].length;

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append(Integer.toString(X));
            writer.append(separator);
            writer.append(Integer.toString(Y));
            writer.append(System.lineSeparator());
            for (Territoire[] terres : territoires) {
                for (Territoire terre : terres) {
                    writer.append(Integer.toString(terre.getId()));
                    writer.append(System.lineSeparator());
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void afficherMatrice() {
        System.out.println("\n-------------------[CARTE]---------------------------------------------------\n");
        for (Territoire[] territoiresLigne : territoires) {
            for (int j = 0; j < territoires[0].length; j++) {
                System.out.print(territoiresLigne[j] + "      ");
            }
            System.out.println("\n");
        }
    }

    public Territoire[][] getTerritoires() {

        return territoires;

    }

    public int getNbTerritoires() {

        return territoires.length * territoires[0].length;

    }

    public Territoire getTerritoireWithId(Integer id) {

        for (Territoire[] L : territoires) {

            for (Territoire T : L) {

                if (T.getId().equals(id)) {

                    return T;

                }
            }

        }

        return null;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Carte)) return false;
        Carte carte = (Carte) o;
        return Arrays.equals(territoires, carte.territoires);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(territoires);
    }
}

