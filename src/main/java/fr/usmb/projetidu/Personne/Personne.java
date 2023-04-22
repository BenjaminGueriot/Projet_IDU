package fr.usmb.projetidu.Personne;

import java.util.Date;

public abstract class Personne {

	protected String nom;
	protected String prenom;
	protected Date date_arrivee;
	
	public Personne(String nom, String prenom, Date date_arrivee) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.date_arrivee = date_arrivee;
	}
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public Date getDate_arrivee() {
		return date_arrivee;
	}

	public void setDate_arrivee(Date date_arrivee) {
		this.date_arrivee = date_arrivee;
	}
	
	
}
