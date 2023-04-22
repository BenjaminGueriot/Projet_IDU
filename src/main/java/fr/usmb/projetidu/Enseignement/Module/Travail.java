package fr.usmb.projetidu.Enseignement.Module;

import java.util.Date;

public class Travail {

	private String nom;
	private String sujet;
	private Date date;
	private Module module;
	
	public Travail(String nom, String sujet, Date date, Module module) {
		super();
		this.nom = nom;
		this.sujet = sujet;
		this.date = date;
		this.module = module;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
	
	
}
