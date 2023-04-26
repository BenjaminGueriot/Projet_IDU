package fr.usmb.projetidu.Enseignement.Module.Cour;

public enum CourEnum {

	CM("CM"), TD("TD"), TP("TP"), EXAM("EXAM"), SPECIAL("SPECIAL");
	
	private String nom;
	
	CourEnum(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
}
