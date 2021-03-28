package Graphical;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lanceur extends JDialog implements ActionListener {
    private Integer[] data;

    private final JComboBox<Integer> nbJoueur;
    private final JComboBox<Integer> nbIaFacile;
    private final JComboBox<Integer> nbIaMoyenne;
    private final JComboBox<Integer> nbIaDiffcile;

    private final JPanel nbJoueurPanel;
    private final JPanel nbIaFacilePanel;
    private final JPanel nbIaDifficilePanel;
    private final JPanel nbIaMoyennePanel;

    private final JPanel boutonPanel;

    private final JLabel nbJoueurLabel;
    private final JLabel nbIaFacileLabel;
    private final JLabel nbIaDifficilelabel;
    private final JLabel nbIaMoyenneLabel;

    private final JButton btnOk;
    private final JButton btnCancel;


    public Lanceur(Frame parent) {

        super(parent, "Définir les parametres de la partie.", true);

        data = new Integer[4];

        JPanel panel = new JPanel();

        Integer[] choixPossible = new Integer[11];
        for (int i = 0; i < 11; i++) {
            choixPossible[i] = i;
        }

        // Joueur

        nbJoueurPanel = new JPanel();
        nbJoueurPanel.setLayout(new FlowLayout());

        nbJoueurLabel = new JLabel("Choisissez le nombre de joueur(s) humain(s) :");
        nbJoueur = new JComboBox<>(choixPossible);

        nbJoueurPanel.add(nbJoueurLabel);
        nbJoueurPanel.add(nbJoueur);

        // Ia Facile

        nbIaFacilePanel = new JPanel();
        nbIaFacilePanel.setLayout(new FlowLayout());

        nbIaFacileLabel = new JLabel("Choisissez le nombre d'IA(s) facile(s) :");
        nbIaFacile = new JComboBox<>(choixPossible);

        nbIaFacilePanel.add(nbIaFacileLabel);
        nbIaFacilePanel.add(nbIaFacile);

        // Ia moyenne

        nbIaMoyennePanel = new JPanel();
        nbIaMoyennePanel.setLayout(new FlowLayout());

        nbIaMoyenneLabel = new JLabel("Choisissez le nombre d'IA(s) moyenne(s) :");
        nbIaMoyenne = new JComboBox<>(choixPossible);

        nbIaMoyennePanel.add(nbIaMoyenneLabel);
        nbIaMoyennePanel.add(nbIaMoyenne);

        // Ia difficile

        nbIaDifficilePanel = new JPanel();
        nbIaDifficilePanel.setLayout(new FlowLayout());

        nbIaDifficilelabel = new JLabel("Choisissez le nombre d'IA(s) difficile(s) :");
        nbIaDiffcile = new JComboBox<>(choixPossible);

        nbIaDifficilePanel.add(nbIaDifficilelabel);
        nbIaDifficilePanel.add(nbIaDiffcile);

        Point loc = parent.getLocation();
        setLocation(loc.x + 300, loc.y + 250);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        boutonPanel = new JPanel(new FlowLayout());

        btnCancel = new JButton("Annuler");
        btnOk = new JButton("Creer");

        boutonPanel.add(btnCancel);
        boutonPanel.add(btnOk);

        panel.add(nbJoueurPanel);
        panel.add(nbIaFacilePanel);
        panel.add(nbIaMoyennePanel);
        panel.add(nbIaDifficilePanel);
        panel.add(boutonPanel);


        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);

        getContentPane().add(panel);
        pack();
    }

    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source == btnOk) {

            data[0] = (Integer) nbJoueur.getSelectedItem();
            data[1] = (Integer) nbIaFacile.getSelectedItem();
            data[2] = (Integer) nbIaMoyenne.getSelectedItem();
            data[3] = (Integer) nbIaDiffcile.getSelectedItem();

            int total = data[0] + data[1] + data[2] + data[3];

            if (total < 10) {

                if (total < 2) {

                    JOptionPane.showMessageDialog(this, "Vous devez mettre au moins deux joueurs pour une partie. Vous avez selectionne " + total + " joueur.", "Attention !", JOptionPane.WARNING_MESSAGE);

                } else {

                    dispose();

                }

            } else {

                JOptionPane.showMessageDialog(this, "Vous ne pouvez pas avoir plus de 9 joueurs toutes catégories confondu. Vous avez selectionne " + total + " joueur(s).", "Attention !", JOptionPane.WARNING_MESSAGE);

            }

        } else {
            data = null;

            dispose();
        }
    }

    public Integer[] run() {
        this.setVisible(true);
        return data;
    }
}


