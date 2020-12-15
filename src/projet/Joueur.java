package projet;
import java.util.*;

public final class Joueur{
	 private String ID;
	 private ArrayList<Territoire> terrConquis;
			
	 public Joueur(){
		 ID = UUID.randomUUID().toString();
		 terrConquis = new ArrayList<Territoire>();
	 }
	 
	 public String getID() {
		return ID;
	}

	public ArrayList<Territoire> getTerrConquis() {
		return new ArrayList<Territoire>(terrConquis);
	}

	public void gagnerTerritoire(Territoire monTerritoire) {
		terrConquis.add(monTerritoire);
	}
	
	public void perdreTerritoire(String IdTerritoire) throws IllegalTerritoireId{
		for(var Territoire : terrConquis) {
			if(Territoire.getId() == IdTerritoire) {
				terrConquis.remove(Territoire);
				return;
			}
		}
		throw new IllegalTerritoireId(IdTerritoire);
	}
	
	public void attaquerTerritoire() {
		Scanner scanner = new Scanner(System.in);
		Integer idTerr;
		idTerr = scanner.nextInt();
		scanner.close();
		// on modifie le territoire qu'on attaque
	}
	
	@Override
	public String toString() {
		return "Joueur [ID=" + ID + ", terrConquis=" + terrConquis + "]";
	}

	public void terminerTour() {
		// on termine le tour	
	}
}

