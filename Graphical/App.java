package Graphical;


import javax.swing.*;
import java.io.File;

public class App {
    public static void main(String[] args) {

        JFrame frame = new JFrame();

        String[] proposition = {"Charger partie", "Creer partie"};

        int choix = JOptionPane.showOptionDialog(frame,
                "Souhaitez vous charger une ancienne partie ou en creer une nouvelle :",
                "DiceWars V2", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null,
                proposition,
                proposition[1]);

        if (choix == 0) {

            String nom = JOptionPane.showInputDialog("Nom de la sauvegarde ?");

            String chemin = "sauvegarde/" + nom + ".ser";

            File f = new File(chemin);
            if(f.isFile())
            {
                Controller controller = new Controller(chemin);

                controller.initController();

            }else{

                JOptionPane.showMessageDialog(frame, "Aucune sauvegarde de ce nom. Verifier l'orthographe.", "Fin du programme", JOptionPane.WARNING_MESSAGE);
                System.exit(0);

            }


        } else if (choix == 1) {

            Lanceur test = new Lanceur(frame);

            Integer[] result = test.run();

            if (result != null) {

                Model model = new Model(result[0], result[1], result[2], result[3]);

                Vue vue = new Vue(model, frame);

                Controller controller = new Controller(vue, model);

                controller.initController();

            } else {

                JOptionPane.showMessageDialog(frame, "Remplissez bien les options...", "Attention", JOptionPane.WARNING_MESSAGE);
                System.exit(0);

            }

        } else {

            JOptionPane.showMessageDialog(frame, "A bientôt", "Fin du programme", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);

        }
    }
}
