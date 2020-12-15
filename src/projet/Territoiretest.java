package projet;

public class Territoiretest {

    public static void main(String[] args) {
	Joueur monjoueur = new Joueur();
	
	Territoire monterrterr = new Territoire("1",monjoueur.getID());
	Territoire monterrterr2 = new Territoire("2",monjoueur.getID());
	Territoire monterrterr3 = new Territoire("3",monjoueur.getID());
	monjoueur.gagnerTerritoire(monterrterr);
	monjoueur.gagnerTerritoire(monterrterr2);
	monjoueur.gagnerTerritoire(monterrterr3);
	
	try {
		monjoueur.perdreTerritoire(monterrterr2.getId());
		System.out.println(monjoueur);
	} catch (IllegalTerritoireId e) {
		System.out.println(e.GetError());
	}
	try {
		monjoueur.perdreTerritoire("8");
	} catch (IllegalTerritoireId e) {
		System.out.println(e.GetError());
	}
	
			
    }
}
