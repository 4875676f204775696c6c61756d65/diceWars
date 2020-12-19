package projet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.List;

public class Carte{
	private Territoire[][] listeTerritoire;
	
	public Carte() {
		 System.out.print("Saisir le nombre de lignes dans la matrice: ");
		    Scanner sc = new Scanner(System.in);
		    int m = sc.nextInt();
		    
		    System.out.print("Saisir le nombre de colonnes dans la matrice: ");
		    int n = sc.nextInt();
		    
		    sc.close();
		    
		    listeTerritoire = new Territoire[m][n];
		    
		    for (int i = 0; i < m; i++) {
			      for (int j = 0; j < n; j++) {
			    String x = String.valueOf(i);
			    String y = String.valueOf(j);
			    String id = x + "/" +y;
			    listeTerritoire[i][j] = new Territoire(id, String.valueOf((int)(Math.random() * ( 10 - 0 ))));
			      }
			    }
			   
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
		
	public Carte(String path) {
		 
		String csvFile = path;
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = ";";

	        try {

	            br = new BufferedReader(new FileReader(csvFile));
	                listeTerritoire = new Territoire[3][3];
	    		    for (int i = 0; i < 3; i++) {
	    			      for (int j = 0; j < 3; j++) {
	    			    	  line = br.readLine();
	    				      String[]country = line.split(cvsSplitBy);
	    			    listeTerritoire[i][j] = new Territoire(country[0], country[1]);
	    			      }
	    			    }
	            

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
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
	

	public void afficherMatrice() {
		System.out.println("\n-------------------[CARTE]---------------------------------------------------\n");
		 for (int i = 0; i < this.listeTerritoire.length; i++) {
		      for (int j = 0; j < this.listeTerritoire[0].length; j++) {
		        System.out.print(this.listeTerritoire[i][j] + "      ");
		      }
		      System.out.println("\n");
		    }
	}
}
