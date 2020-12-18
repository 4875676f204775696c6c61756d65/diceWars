package projet;
import java.util.*;


public class Partie implements Iterable<Joueur>{
	private Integer nbrDeJoueur;
	private Carte maCarte;
	private List<Joueur> joueurs;
	
	
	public Partie(int _nbrDeJoueur) {
		nbrDeJoueur = _nbrDeJoueur;
		joueurs = new ArrayList<Joueur>();
		for(int i = 0; i < nbrDeJoueur; i++) {
			joueurs.add(new Joueur());
		}
		//maCarte = new Carte();
	}
	
	@Override
	public Iterator<Joueur> iterator() {
		return joueurs.iterator();
	}
	
	@Override
	public String toString() {
		return "Partie [nbrDeJoueur=" + nbrDeJoueur + ", maCarte=" + maCarte + ", joueurs=" + joueurs + "]";
	}
	
	public void lancerPartie() {
		while(joueurs.size() != 1) {
			Joueur joueurActuel = choisirJoueur();
			String selection = "";
			while(selection.compareTo("non") != 0) {
				System.out.println(joueurs);
				System.out.println(joueurActuel);
				//joueurActuel.attaquerTerritoire();
				//eliminerJoueur();
				//attribuerRenfortAleatoire(joueurActuel);
				System.out.println("Voulez vous attaquer à nouveau(non pour arreter)");
				Scanner sc = new Scanner(System.in);
				selection = sc.next();
				sc.close();
				joueurs.remove(joueurActuel);
			}
		}
		Victoire();
	}
	
	public void Victoire() {
		System.out.println("Felicitation à " + joueurs.iterator().next() + " pour sa victoire");
	}
	
	public void afficherPartie() {
		// matrice
	}
	
	public Joueur choisirJoueur() {
		Collections.shuffle(joueurs);
		return joueurs.iterator().next();
	}
	
	public ArrayList<Integer> lancerDes(Integer nbr){
		ArrayList<Integer> lance = new ArrayList<Integer>();
		for(int i = 0; i < nbr;i++) {
			lance.add(1 + (int)(Math.random()*6));
		}
		return lance;
	}
	
	public Integer score(ArrayList<Integer> des) {
		int somme = 0;
		for(int de : des) {
			somme += de;
		}
		return somme;
	}
	
	public void eliminerJoueur() {
		for(Joueur joueur : joueurs) {
			int nbr = joueur.nbrTerritoire();
			if(nbr == 0) {
				joueurs.remove(joueur);
			}
		}
	}
	
	public void attribuerRenfort(String idJoueur) {
		//????
	}
	
	public void attribuerRenfortAleatoire(Joueur joueurActuel) {
		int quantite = joueurActuel.nbrTerritoire();
		joueurActuel.ajouterRenfort(quantite);
	}
}
