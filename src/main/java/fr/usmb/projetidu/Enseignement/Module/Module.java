package fr.usmb.projetidu.Enseignement.Module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.usmb.projetidu.Enseignement.Module.Cour.Cour;
import fr.usmb.projetidu.Personne.Enseignant;

public class Module {
	
	private String nom;
	private String description;
	private String couleurEDT;
	private String code;
	private double nbHeures;
	private double coeff;
	private String evaluation;
	private List<Enseignant> enseignants = new ArrayList<>();
	private List<Travail> travaux = new ArrayList<>();
	private List<Cour> cours = new ArrayList<>();
	
	public Module(String code, String nom, double nbHeures, double coeff, String evaluation, String description, String couleurEDT) {
		this.code = code;
		this.nom = nom;
		this.nbHeures = nbHeures;
		this.coeff = coeff;
		this.evaluation = evaluation;
		this.description = description;
		this.couleurEDT = couleurEDT;
	}
	
	public void addEnseignant(Enseignant enseignant) {
		this.enseignants.add(enseignant);
	}
	
	public void addTravail(Travail... travaux) {
		for(Travail travail : travaux) {
			this.travaux.add(travail);
		}
	}
	
	public int getAvancement() {
		
		int total_cours = this.cours.size();
		int already_past = 0;
		
		for(Cour cour : this.cours) {
			
			if(cour.getDate().before(new Date())) {
				already_past++;
			}
			
		}
		
		if(total_cours == 0) return 0;
		
		return (100 * already_past) / total_cours;
		
	}
	
	public Double getNbHeures() {
		return this.nbHeures;
	}
	
	public Double getCoeff() {
		return this.coeff;
	}
	
	public String getEvaluation() {
		return this.evaluation;
	}

	public String getNom() {
		return this.nom;
	}
	
	public String getCode() {
		return this.code;
	}

	public List<Enseignant> getEnseignants() {
		return this.enseignants;
	}

	public String getDescription() {
		return this.description;
	}

	public List<Travail> getTravaux() {
		return this.travaux;
	}

	public void addTravail(Travail travail) {
		this.travaux.add(travail);
	}
	
	public void addCour(Cour cour) {
		this.cours.add(cour);
	}

	public List<Cour> getCours() {
		return this.cours;
	}

	public String getCouleurEDT() {
		return this.couleurEDT;
	}

	public void setCouleurEDT(String couleurEDT) {
		this.couleurEDT = couleurEDT;
	}
	
}
