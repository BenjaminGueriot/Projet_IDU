package fr.usmb.projetidu.Personne;

public abstract class Personne {

	protected String nom;
	protected String prenom;
	protected String mail;
	
	public Personne(String nom, String prenom, String mail) {
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return this.prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMail() {
		return this.mail;
	}
	
}
