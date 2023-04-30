package fr.usmb.projetidu.Enseignement.Module;

import java.util.Date;

public class Travail {

	/**
	 * 	Nom du travail
	 */
	private String nom;
	
	/**
	 * 	Sujet du travail
	 */
	private String sujet;
	
	/**
	 * 	Date de rendu du travail
	 */
	
	private Date date;
	/**
	 * 	Module du travail
	 */
	private Module module;
	
	/**
	 * 	Constructeur de la classe
	 */
	public Travail(String nom, String sujet, Date date, Module module) {
		this.nom = nom;
		this.sujet = sujet;
		this.date = date;
		this.module = module;
	}

	/**
	 * 	Accesseur renvoyant le nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * 	Accesseur renvoyant la date de rendu du travail
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * 	Accesseur renvoyant le sujet
	 */
	public String getSujet() {
		return this.sujet;
	}

	/**
	 * 	Accesseur renvoyant le module
	 */
	public Module getModule() {
		return this.module;
	}

}
