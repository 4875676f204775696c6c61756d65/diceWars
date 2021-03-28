package Graphical;

import GameCore.Territoire;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.*;

public class Vue implements Serializable {

    @Serial
    private static final long serialVersionUID = -707719478505577382L;

    private final JFrame frame;
    private JPanel casesTerritoiresPanel = null;
    private final JPanel actionsPanel;

    private final List<Color> couleursDisponibles;
    private Map<JButton, Territoire> casesTerritoires;

    private final Map<String, Color> joueurs;

    private final JLabel titre;
    private final GroupLayout ensemble;
    private JButton finTourBouton;
    private JButton finPartieBouton;
    private final JButton boutonNouvellePartie;
    private final JButton boutonSave;

    private final JLabel image;
    private final ImageIcon iconPhoto;
    private final JLabel imageEfrei;
    private final ImageIcon iconPhotoEfrei;
    private JPanel Footerpanel;
    private final JLabel Footer;

    public Vue(Model model, JFrame frame) {
        casesTerritoires = new TreeMap<>(new ComparatorCasesTerritoires());

        couleursDisponibles = new ArrayList<>();
        couleursDisponibles.add(Color.BLUE);
        couleursDisponibles.add(Color.RED);
        couleursDisponibles.add(Color.GREEN);
        couleursDisponibles.add(Color.CYAN);
        couleursDisponibles.add(Color.MAGENTA);
        couleursDisponibles.add(Color.PINK);
        couleursDisponibles.add(Color.ORANGE);
        couleursDisponibles.add(Color.YELLOW);
        couleursDisponibles.add(Color.DARK_GRAY);
        couleursDisponibles.add(Color.LIGHT_GRAY);

        joueurs = new HashMap<>();

        for (int i = 0; i < model.getPartie().getCarte().getTerritoires().length; i++) {
            for (int j = 0; j < model.getPartie().getCarte().getTerritoires()[0].length; j++) {

                JButton bouton1 = new JButton();
                bouton1.setText(model.getPartie().getCarte().getTerritoires()[i][j].getIdJoueur() + " : " + model.getPartie().getCarte().getTerritoires()[i][j].getForce() + " [" + model.getPartie().getCarte().getTerritoires()[i][j].getId() + "]");

                if (joueurs.containsKey(model.getPartie().getCarte().getTerritoires()[i][j].getIdJoueur())) {
                    bouton1.setForeground(joueurs.get(model.getPartie().getCarte().getTerritoires()[i][j].getIdJoueur()));
                } else {

                    joueurs.put(model.getPartie().getCarte().getTerritoires()[i][j].getIdJoueur(), couleursDisponibles.get(0));
                    bouton1.setForeground(joueurs.get(model.getPartie().getCarte().getTerritoires()[i][j].getIdJoueur()));
                    couleursDisponibles.remove(0);
                }
                casesTerritoires.put(bouton1, model.getPartie().getCarte().getTerritoires()[i][j]);
            }
        }

        this.frame = frame;
        this.frame.getContentPane().setLayout(new BorderLayout());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

        //casesTerritoiresPanel = new JPanel();
        //casesTerritoiresPanel.setLayout(new GridLayout(model.getPartie().getCarte().getTerritoires().length, model.getPartie().getCarte().getTerritoires()[0].length));

        try {
            casesTerritoiresPanel = new JPanelBackground("images/territoire.jpg");
            casesTerritoiresPanel.setLayout(new GridLayout(model.getPartie().getCarte().getTerritoires().length,model.getPartie().getCarte().getTerritoires()[0].length));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        actionsPanel = new JPanel();
        actionsPanel.setLayout(new FlowLayout());

        // Create UI elements
        titre = new JLabel(" Joueur actuel : " + model.getActuel());

        Footer = new JLabel("     :     Cree par Baudouin de Lavigne, Hugo Guillaume, Chourouk Jdidi, et Loic Lecomte.");
        imageEfrei = new JLabel();
        iconPhotoEfrei = new ImageIcon("images/Logo-Efrei.png");
        imageEfrei.setIcon(iconPhotoEfrei);
        image = new JLabel();
        iconPhoto = new ImageIcon("images/dice_wars_3.png");
        image.setIcon(iconPhoto);

        // Creation bouton :

        boutonSave = new JButton();
        boutonSave.setText("Sauvegarder");

        boutonNouvellePartie = new JButton();
        boutonNouvellePartie.setText("Nouvelle partie");

        finTourBouton = new JButton();
        finTourBouton.setText("Fin de tour");

        finPartieBouton = new JButton();
        finPartieBouton.setText("Arreter le jeu");

        for (JButton e : casesTerritoires.keySet()) {
            casesTerritoiresPanel.add(e);
        }

        actionsPanel.add(finTourBouton);
        actionsPanel.add(finPartieBouton);
        actionsPanel.add(image);
        actionsPanel.add(boutonNouvellePartie);
        actionsPanel.add(boutonSave);

        Footerpanel = new JPanel();

        Footerpanel.setLayout(new FlowLayout());

        Footerpanel.add(imageEfrei);
        Footerpanel.add(Footer);

        // Add UI element to frame
        ensemble = new GroupLayout(frame.getContentPane());
        ensemble.setAutoCreateGaps(true);
        ensemble.setAutoCreateContainerGaps(true);

        JPanel panelTitre = new JPanel();

        panelTitre.setLayout(new FlowLayout());

        panelTitre.add(titre);

        ensemble.setHorizontalGroup(ensemble.createSequentialGroup()
                .addGroup(ensemble.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(panelTitre).addComponent(casesTerritoiresPanel).addComponent(actionsPanel).addComponent(Footerpanel)));


        ensemble.setVerticalGroup(ensemble.createSequentialGroup()
                .addGroup(ensemble.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(panelTitre))
                .addGroup(ensemble.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(casesTerritoiresPanel))
                .addGroup(ensemble.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(actionsPanel))
                .addGroup(ensemble.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(Footerpanel)));


        frame.getContentPane().setLayout(ensemble);

        frame.pack();

    }

    public Map<JButton, Territoire> getCasesTerritoires() {
        return casesTerritoires;
    }

    public void setCasesTerritoires(Map<JButton, Territoire> casesTerritoires) {
        this.casesTerritoires = casesTerritoires;
    }

    public JLabel getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre.setText(titre);
    }

    public JButton getFinTourBouton() {
        return finTourBouton;
    }

    public void setFinTourBouton(JButton finTourBouton) {
        this.finTourBouton = finTourBouton;
    }

    public JButton getFinPartieBouton() {
        return finPartieBouton;
    }

    public void setFinPartieBouton(JButton finPartieBouton) {
        this.finPartieBouton = finPartieBouton;
    }

    public void actualiserAffichageGrille() {

        for (JButton b : casesTerritoires.keySet()) {

            b.setText(casesTerritoires.get(b).getIdJoueur() + " : " + casesTerritoires.get(b).getForce() + " [" + casesTerritoires.get(b).getId() + "]");
            b.setForeground(joueurs.get(casesTerritoires.get(b).getIdJoueur()));

        }

    }

    public JFrame getFrame() {
        return frame;
    }

    public JButton getBoutonNouvellePartie() {
        return boutonNouvellePartie;
    }

    public JButton getBoutonSave() {
        return boutonSave;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vue)) return false;
        Vue vue = (Vue) o;
        return Objects.equals(frame, vue.frame) && Objects.equals(casesTerritoiresPanel, vue.casesTerritoiresPanel) && Objects.equals(actionsPanel, vue.actionsPanel) && Objects.equals(couleursDisponibles, vue.couleursDisponibles) && Objects.equals(casesTerritoires, vue.casesTerritoires) && Objects.equals(titre, vue.titre) && Objects.equals(ensemble, vue.ensemble) && Objects.equals(finTourBouton, vue.finTourBouton) && Objects.equals(finPartieBouton, vue.finPartieBouton);
    }

    @Override
    public int hashCode() {
        return Objects.hash(frame, casesTerritoiresPanel, actionsPanel, couleursDisponibles, casesTerritoires, titre, ensemble, finTourBouton, finPartieBouton);
    }
}
