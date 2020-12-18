package projet;

import java.util.ArrayList;

public class Partie_test {

	public static void main(String[] args) {
		Joueur monJoueur = new Joueur();
		System.out.println(monJoueur);
		Partie maPartie = new Partie(4);
		System.out.println(maPartie);
		maPartie.Victoire();
		System.out.println(maPartie.choisirJoueur());
		ArrayList<Integer> des = new ArrayList<Integer>();
		des = maPartie.lancerDes(6);
		System.out.println(des);
		System.out.println(maPartie.score(des));
		maPartie.lancerPartie();
	}

}
