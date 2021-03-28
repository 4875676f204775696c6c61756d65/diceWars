package GameCore;

import java.io.*;
import java.util.Objects;

// TODO :

// Ajouter serialization de Carte + Partie


public class Sauvegarde implements Serializable {

    @Serial
    private static final long serialVersionUID = 1350092881346723535L;

    private final Partie partie;

    public Sauvegarde(String path) {

        partie = null;

        charger(path);

    }

    public Sauvegarde(Partie partie) {
        this.partie = partie;
    }


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
            oos.writeObject(this);

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

        Sauvegarde m = null;

        try {
            assert ois != null;
            m = (Sauvegarde) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Partie getPartie() {

        return partie;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sauvegarde)) return false;
        Sauvegarde that = (Sauvegarde) o;
        return Objects.equals(partie, that.partie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partie);
    }
}

