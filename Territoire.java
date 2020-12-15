package projet;
import java.util.*;

public class Territoire{
	private String id;
	private String idJoueur;
	
	public String getId() {
		return id;
	}

	public String getIdJoueur() {
		return idJoueur;
	}

	public Territoire(String numero,String _idJoueur){
		id = numero;
		idJoueur = _idJoueur;
	}

	@Override
	public String toString() {
		return "Territoire [id = " + id + ", idJoueur = " + idJoueur + "]";
	}
	
}