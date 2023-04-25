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
	private List<Enseignant> enseignants = new ArrayList<>();
	private List<Travail> travaux = new ArrayList<>();
	private List<Cour> cours = new ArrayList<>();
	
	public Module(String nom, String description, String couleurEDT) {
		this.nom = nom;
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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Enseignant> getEnseignants() {
		return enseignants;
	}

	public void setEnseignants(List<Enseignant> enseignants) {
		this.enseignants = enseignants;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Travail> getTravaux() {
		return travaux;
	}

	public void setTravaux(List<Travail> travaux) {
		this.travaux = travaux;
	}
	
	public void addTravail(Travail travail) {
		this.travaux.add(travail);
	}
	
	public void addCour(Cour cour) {
		this.cours.add(cour);
	}

	public List<Cour> getCours() {
		return cours;
	}

	public void setCours(List<Cour> cours) {
		this.cours = cours;
	}

	public String getCouleurEDT() {
		return couleurEDT;
	}

	public void setCouleurEDT(String couleurEDT) {
		this.couleurEDT = couleurEDT;
	}
	
}
