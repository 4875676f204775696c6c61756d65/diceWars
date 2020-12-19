package projet;

public class Cartetest {

	  public static void main(String[] args) {
	   
		 Carte maCarte = new Carte("/Users/baudouin/Desktop/Territoire.csv");
		 maCarte.afficherMatrice();
		 maCarte.writeToCsvFile(";", "test.csv");
	  }
	}