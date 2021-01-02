package GameCore;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import projet.Territoire;

public class Carte {

	private Territoire[][] listeTerritoire;

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


public void writeToCsvFile(String separator, String fileName){
    try (FileWriter writer = new FileWriter(fileName)){
    	writer.append("ID DES TERRES");
    	writer.append(separator);
        writer.append("ID DES JOUEURS");
        writer.append(System.lineSeparator());
        for (Territoire[] terres : listeTerritoire) {
        	for(Territoire terre : terres) {
    			writer.append(terre.getId());
                writer.append(separator);
                writer.append(terre.getIdJoueur());
                writer.append(System.lineSeparator());
        		
        	}
        }
        writer.flush();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
	
public Carte(String path) throws FileNotFoundException {
	 
	String csvFile = path;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            line = br.readLine();
		      String[]country = line.split(cvsSplitBy);
                listeTerritoire = new Territoire[Integer.parseInt(country[0])][Integer.parseInt(country[1])];
    		    for (int i = 0; i < Integer.parseInt(country[0]); i++) {
    			      for (int j = 0; j < Integer.parseInt(country[1]); j++) {
    			    	  line = br.readLine();
    				      country = line.split(cvsSplitBy);
    				      
    	
    			    listeTerritoire[i][j] = new Territoire(Integer.parseInt(country[0]), country[1]);
    				      
    				
    			      }
    			    }
            

        } catch (FileNotFoundException e) {
            System.out.print("fichier pas bon");
        } catch (IOException e) {
            e.printStackTrace();
        } 
       finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}
}


