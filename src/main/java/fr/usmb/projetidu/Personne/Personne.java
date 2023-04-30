package fr.usmb.projetidu.Personne;

public abstract class Personne {

	/**
	 * Nom d'une personne
	 */
	protected String nom;
	
	/**
	 * Prenom d'une personne
	 */
	protected String prenom;
	
	/**
	 * Mail d'une personne
	 */
	protected String mail;
	
	/**
	 * Constructeur de la classe
	 */
	public Personne(String nom, String prenom, String mail) {
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
	}
	
	/**
	 * 	Accesseur renvoyant le nom
	 */
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * 	Accesseur renvoyant le prenom
	 */
	public String getPrenom() {
		return this.prenom;
	}

	/**
	 * 	Accesseur renvoyant le mail
	 */
	public String getMail() {
		return this.mail;
	}
	
}
