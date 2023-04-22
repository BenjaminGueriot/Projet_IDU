package fr.usmb.projetidu.Enseignement.Module.Cour;

import java.util.Date;

import fr.usmb.projetidu.Enseignement.Module.Module;

public abstract class Cour {

	protected int num_seance;
	protected Date date;
	protected double heure_debut;
	protected double duree;
	protected Module module;
	protected CourEnum courenum;
	
	public Cour(int num_seance, Date date, double heure_debut, double duree, Module module, CourEnum courenum) {
		super();
		this.num_seance = num_seance;
		this.date = date;
		this.heure_debut = heure_debut;
		this.duree = duree;
		this.module = module;
		this.courenum = courenum;
	}

	public int getNum_seance() {
		return num_seance;
	}

	public void setNum_seance(int num_seance) {
		this.num_seance = num_seance;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getHeure_debut() {
		return heure_debut;
	}

	public void setHeure_debut(int heure_debut) {
		this.heure_debut = heure_debut;
	}

	public double getDuree() {
		return duree;
	}

	public void setDuree(double duree) {
		this.duree = duree;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public CourEnum getCourenum() {
		return courenum;
	}

	public void setCourenum(CourEnum courenum) {
		this.courenum = courenum;
	}
	
}
