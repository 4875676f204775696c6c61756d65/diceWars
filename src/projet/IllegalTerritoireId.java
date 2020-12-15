package projet;

public class IllegalTerritoireId extends Exception{
	private String territoireId;
	
	public IllegalTerritoireId(String id){
		super(id);
		territoireId = id;
	}
	
	public String GetError() {
		return "Le territoire " + territoireId + " n'est pas possédé par ce joueur.";
	}
}