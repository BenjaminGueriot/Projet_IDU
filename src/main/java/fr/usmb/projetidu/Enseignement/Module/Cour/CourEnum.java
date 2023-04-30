package fr.usmb.projetidu.Enseignement.Module.Cour;

public enum CourEnum {

	/**
	 * 	Valeurs de la classe enumération CourEnum
	 */
	CM("CM"), TD("TD"), TP("TP"), EXAM("EXAM"), SPECIAL("SPECIAL");
	
	/**
	 * 	Nom d'un type de cours
	 */
	private String nom;
	
	/**
	 * 	Constructeur de la classe
	 */
	CourEnum(String nom) {
		this.nom = nom;
	}

	/**
	 * 	Accesseur renvoyant le nom
	 */
	public String getNom() {
		return this.nom;
	}

}
