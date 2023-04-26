package fr.usmb.projetidu.Enseignement.Module.Cour;

import java.util.Date;

import fr.usmb.projetidu.Enseignement.Module.Module;

public abstract class Cour {

	protected int num_seance;
	protected Date date;
	protected double heure_debut;
	protected double duree;
	protected Module module;
	protected CourEnum type;
	
	public Cour(int num_seance, Date date, double heure_debut, double duree, Module module, CourEnum type) {
		super();
		this.num_seance = num_seance;
		this.date = date;
		this.heure_debut = heure_debut;
		this.duree = duree;
		this.module = module;
		this.type = type;
	}

	public int getNum_seance() {
		return num_seance;
	}

	public Date getDate() {
		return this.date;
	}

	public double getHeure_debut() {
		return this.heure_debut;
	}

	public double getDuree() {
		return this.duree;
	}

	public Module getModule() {
		return this.module;
	}

	public CourEnum getType() {
		return this.type;
	}

	
}
