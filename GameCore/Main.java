package GameCore;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        System.out.println("Bienvenue dans notre version V1 du jeu DiceWars");

        System.out.println("Combien de joueurs pour la partie (minimum 2) :");

        Scanner sc = new Scanner(System.in);

        int nbJoueur = 0;

        do {
            if (sc.hasNextInt()) {
                nbJoueur = sc.nextInt();
            } else {
                System.out.println("Veuillez entrer un chiffre :");
            }
        } while (nbJoueur < 0);

        //Partie partie = new Partie(nbJoueur);

        //partie.start();

        System.out.println("Projet de L3.");

    }
}
