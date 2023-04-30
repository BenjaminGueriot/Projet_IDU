package fr.usmb.projetidu.Enseignement.Module.Cour;

import java.util.Date;

import fr.usmb.projetidu.Enseignement.Module.Module;

public abstract class Cour {

	/**
	 * 	Numéro de la séance du cours
	 */
	protected int num_seance;
	
	/**
	 * 	Date du cours
	 */
	protected Date date;
	
	/**
	 * 	Heure de début du cours 
	 */
	protected double heure_debut;
	
	/**
	 * 	Durée du cours
	 */
	protected double duree;
	
	/**
	 * 	Module du cours
	 */
	protected Module module;
	
	/**
	 * 	Type du cours
	 */
	protected CourEnum type;
	
	/**
	 * 	Constructeur de la classe
	 */
	public Cour(int num_seance, Date date, double heure_debut, double duree, Module module, CourEnum type) {
		this.num_seance = num_seance;
		this.date = date;
		this.heure_debut = heure_debut;
		this.duree = duree;
		this.module = module;
		this.type = type;
	}

	/**
	 * 	Accesseur renvoyant le numéro du cours
	 */
	public int getNum_seance() {
		return this.num_seance;
	}

	/**
	 * 	Accesseur renvoyant la date du cours
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * 	Accesseur renvoyant l'heure de début du cours
	 */
	public double getHeure_debut() {
		return this.heure_debut;
	}

	/**
	 * 	Accesseur renvoyant la durée du cours
	 */
	public double getDuree() {
		return this.duree;
	}

	/**
	 * 	Accesseur renvoyant le module du cours
	 */
	public Module getModule() {
		return this.module;
	}

	/**
	 * 	Accesseur renvoyant le type du cours
	 */
	public CourEnum getType() {
		return this.type;
	}

	
}
