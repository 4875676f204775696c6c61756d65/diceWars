package Graphical;


import GameCore.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class Controller implements Serializable {

    Model model;
    Vue vue;

    public Controller(String chemin) {

        charger(chemin);

    }

    public Controller(Vue v, Model m) {

        vue = v;
        model = m;

    }

    public void initController() {

        vue.getFinTourBouton().addActionListener(e -> finDeTour());
        vue.getFinPartieBouton().addActionListener(e -> Exit());

        vue.getBoutonSave().addActionListener(e -> choixSauvegarde());
        vue.getBoutonNouvellePartie().addActionListener(e -> genererNouvellePartie());

        TreeMap<JButton, Territoire> BoutonTerritoire = (TreeMap<JButton, Territoire>) vue.getCasesTerritoires();
        for (JButton bouton : BoutonTerritoire.keySet()) {
            bouton.addActionListener(e -> selectionCase(bouton));
        }

        if (model.getActuel() instanceof JoueurIA) {

            lancerJeuIA();

        }
    }

    public void genererNouvellePartie() {

        JFrame main = new JFrame("Dice Wars V2");

        JFrame old = vue.getFrame();

        old.setVisible(false);

        old.dispose();

        Lanceur test = new Lanceur(main);

        Integer[] result = test.run();

        if (result != null) {

            Model newModel = new Model(result[0], result[1], result[2], result[3]);

            Vue newVue = new Vue(newModel, main);

            this.model = newModel;
            this.vue = newVue;

            initController();

        } else {

            JOptionPane.showMessageDialog(main, "Remplissez bien les options...", "Attention", JOptionPane.WARNING_MESSAGE);
            System.exit(0);

        }

    }

    public void choixSauvegarde() {

        String result = (String) JOptionPane.showInputDialog(
                null,
                "Entrer le nom de la sauvegarde :",
                "Sauvegarder",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "SauvegardeAutomatique1"
        );
        if (result != null && result.length() > 0) {
            sauvegarder("sauvegarde/" + result + ".ser"); // Vulnérabilité potentielle

            JOptionPane.showMessageDialog(null, "Partie enregistrer.", "Sauvegarde réussi !", JOptionPane.INFORMATION_MESSAGE);

        }

    }

    public void lancerJeuIA() {

        JOptionPane.showMessageDialog(null, "C'est à l'IA de joué.", "IA", JOptionPane.INFORMATION_MESSAGE);

        ArrayList<String> perdants = model.getPartie().actionIA((JoueurIA) model.getActuel());

        if (perdants != null) {

            if (perdants.size() != 0) {

                for (String pseudo : perdants) {

                    JOptionPane.showMessageDialog(null, "Le joueur " + pseudo + "a été éliminé.", "Elimination", JOptionPane.INFORMATION_MESSAGE);

                }

            }

            if (model.getPartie().getJoueurs().size() == 1) {
                vue.actualiserAffichageGrille();
                JOptionPane.showMessageDialog(null, "La partie est terminé. Le joueur " + model.getPartie().getJoueurs().iterator().next() + " a gagné.", "Partie fini...", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "merci d'avoir joué", "Info", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);

            }

        }

        finDeTour();

    }

    public void AfficherBouton(JButton bouton) {
        System.out.println(bouton);
    }

    public void selectionCase(JButton b) {

        AfficherBouton(b);

        if (!(model.getActuel() instanceof JoueurIA)) {

            if (model.getSource() == null) {

                try {
                    if (getJoueurParId(getPseudoDepuisBouton(b.getActionCommand())) == model.getActuel()) {

                        try {

                            try {
                                if (model.getPartie().disposeDeForceSuffisante(getTerritoireParBouton(b))) {
                                    model.setSource(getTerritoireParBouton(b));
                                }
                            } catch (ForceInsuffisante forceInsuffisante) {
                                JOptionPane.showMessageDialog(null, "Il vous faut au moins deux de force pour attaquer", "C'est la ruine...", JOptionPane.INFORMATION_MESSAGE);
                            }

                        } catch (TerritoireNonExistant territoireNonExistant) {
                            JOptionPane.showMessageDialog(null, "Le territoire spécifié n'existe pas. Erreur du programme. Arret du programme.", "Erreur !", JOptionPane.ERROR_MESSAGE);
                        }

                    } else {

                        JOptionPane.showMessageDialog(null, "C'est mieux d'attaquer depuis chez soi...", "Attention !", JOptionPane.INFORMATION_MESSAGE);

                    }
                } catch (JoueurNonPresent joueurNonPresent) {
                    JOptionPane.showMessageDialog(null, "Le joueur spécifié n'existe pas. Erreur du programme. Arret du programme.", "Erreur !", JOptionPane.ERROR_MESSAGE);
                }

            } else {

                try {
                    if (!(getJoueurParId(getPseudoDepuisBouton(b.getActionCommand())) == model.getActuel())) {

                        try {
                            if (model.getPartie().estAccessible(model.getSource(), getTerritoireParBouton(b))) {
                                model.setCible(getTerritoireParBouton(b));

                                lancerAttaqueJoueur();
                            }
                        } catch (TerritoireNonAccessible territoireNonAccessible) {
                            JOptionPane.showMessageDialog(null, "Le territoire doit être à proximité direct pour être attaqué.", "Attention !", JOptionPane.INFORMATION_MESSAGE);
                        } catch (TerritoireNonExistant territoireNonExistant) {
                            JOptionPane.showMessageDialog(null, "Le territoire spécifié n'existe pas. Erreur du programme. Arret du programme.", "Erreur !", JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "C'est mieux d'attaquer les ennemis...", "Attention !", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (JoueurNonPresent joueurNonPresent) {
                    JOptionPane.showMessageDialog(null, "Le joueur spécifié n'existe pas. Erreur du programme. Arret du programme.", "Erreur !", JOptionPane.ERROR_MESSAGE);
                }

            }

        } else {

            JOptionPane.showMessageDialog(null, "C'est au tour de l'IA !", "Attendez !", JOptionPane.INFORMATION_MESSAGE);

        }

        System.out.println(model.getSource());
        System.out.println(model.getCible());

    }

    public void lancerAttaqueJoueur() {

        String resultat = model.getPartie().attaqueJoueurHumain(model.getActuel(), model.getSource().getId(), model.getCible().getId());

        JOptionPane.showMessageDialog(null, resultat, "Attaque !", JOptionPane.INFORMATION_MESSAGE);

        vue.actualiserAffichageGrille();

        try {
            model.getPartie().eliminerPerdant();
        } catch (PartieTermine partieTermine) {
            vue.actualiserAffichageGrille();
            JOptionPane.showMessageDialog(null, "La partie est terminé. Le joueur " + model.getPartie().getJoueurs().iterator().next() + " a gagné.", "Partie fini...", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "merci d'avoir joué", "Info", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        model.setSource(null);

        model.setCible(null);

    }

    public String getPseudoDepuisBouton(String infos) {

        return infos.substring(0, infos.indexOf(":") - 1);

    }

    public Territoire getTerritoireParBouton(JButton rechercher) throws TerritoireNonExistant {

        for (JButton b : vue.getCasesTerritoires().keySet()) {

            if (b == rechercher) {

                return vue.getCasesTerritoires().get(b);

            }

        }

        throw new TerritoireNonExistant();

    }

    public void finDeTour() {

        if (!(model.getActuel() instanceof JoueurIA)) {

            model.getPartie().terminerTour(model.getActuel());

        } else {

            JOptionPane.showMessageDialog(null, "Fin du tour IA", "Info", JOptionPane.INFORMATION_MESSAGE);

        }

        model.setSource(null);
        model.setCible(null);

        try {
            model.setActuel(model.getPartie().choisirJoueur());
            vue.setTitre(" Jeux discWars : " + model.getActuel());

            vue.actualiserAffichageGrille();

            if (model.getActuel() instanceof JoueurIA) {

                lancerJeuIA();

            }

        } catch (PartieTermine partieTermine) {
            vue.actualiserAffichageGrille();
            JOptionPane.showMessageDialog(null, "Le joueur " + model.getPartie().getJoueurs().iterator().next().getPseudo() + " a gagné la partie !", "Felicitations !", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null, "merci d'avoir joué", "Info", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        System.out.println("Fin de tour");

    }

    public void Exit() {

        JOptionPane.showMessageDialog(null, "merci d'avoir joué", "Info", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);

    }

    public Joueur getJoueurParId(String Id) throws JoueurNonPresent {

        for (Joueur J : model.getPartie().getJoueurs()) {

            if (J.getPseudo().equals(Id)) {

                return J;

            }

        }

        throw new JoueurNonPresent();

    }

    // Fonction pour la sauvegarde :

    public void sauvegarder(String path) {
        File fichier = new File(path);

        ObjectOutputStream oos = null;


        try {
            oos = new ObjectOutputStream(new FileOutputStream(fichier));
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {

            assert oos != null;
            oos.writeObject(this.model);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void charger(String path) {
        File fichier = new File(path);

        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(fichier));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Model m = null;

        try {
            assert ois != null;
            m = (Model) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame();

        this.model = m;

        this.vue = new Vue(model,frame);

        this.initController();

    }

}
