package fr.usmb.projetidu.Enseignement.Module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.usmb.projetidu.Enseignement.Module.Cour.Cour;
import fr.usmb.projetidu.Personne.Enseignant;

public class Module {
	
	/**
	 * 	Nom du module
	 */
	private String nom;
	
	/**
	 * 	Description du module
	 */
	private String description;
	
	/**
	 * 	Code couleur du module pour l'emploi du temps
	 */
	private String couleurEDT;
	
	/**
	 * 	Code du module
	 */
	private String code;
	
	/**
	 * 	Nombre d'heures du module
	 */
	private double nbHeures;
	
	/**
	 * 	Coefficient du module
	 */
	private double coeff;
	
	/**
	 * 	Type d'évaluation
	 */
	private String evaluation;
	
	/**
	 * 	Liste des enseignants du module
	 */
	private List<Enseignant> enseignants = new ArrayList<>();
	
	/**
	 * 	Liste des travaux du module
	 */
	private List<Travail> travaux = new ArrayList<>();
	
	/**
	 * 	Liste des cours du module
	 */
	private List<Cour> cours = new ArrayList<>();
	
	/**
	 * 	Constructeur de la classe
	 */
	public Module(String code, String nom, double nbHeures, double coeff, String evaluation, String description, String couleurEDT) {
		this.code = code;
		this.nom = nom;
		this.nbHeures = nbHeures;
		this.coeff = coeff;
		this.evaluation = evaluation;
		this.description = description;
		this.couleurEDT = couleurEDT;
	}
	
	/**
	 * 	Méthode permettant d'ajouter un enseignant à la liste des enseignants
	 */
	public void addEnseignant(Enseignant enseignant) {
		this.enseignants.add(enseignant);
	}
	
	/**
	 * 	Méthode permettant d'ajouter un ou plusieurs travaux à la liste des travaux
	 */
	public void addTravail(Travail... travaux) {
		for(Travail travail : travaux) {
			this.travaux.add(travail);
		}
	}
	
	/**
	 * 	Méthode permettant d'ajouter un cours à la liste des cours
	 */
	public void addCour(Cour cour) {
		this.cours.add(cour);
	}
	
	/**
	 * 	Méthode permettant de récupérer l'avancement d'un module
	 */
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
	
	/**
	 * 	Accesseur renvoyant le nombre d'heures
	 */
	public Double getNbHeures() {
		return this.nbHeures;
	}
	
	/**
	 * 	Accesseur renvoyant le coefficient
	 */
	public Double getCoeff() {
		return this.coeff;
	}
	
	/**
	 * 	Accesseur renvoyant le type d'évaluation sous forme de chaîne de caractères
	 */
	public String getEvaluation() {
		return this.evaluation;
	}

	/**
	 * 	Accesseur renvoyant le nom
	 */
	public String getNom() {
		return this.nom;
	}
	
	/**
	 * 	Accesseur renvoyant le code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * 	Accesseur renvoyant la liste d'enseignants
	 */
	public List<Enseignant> getEnseignants() {
		return this.enseignants;
	}

	/**
	 * 	Accesseur renvoyant la description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 	Accesseur renvoyant la liste des travaux
	 */
	public List<Travail> getTravaux() {
		return this.travaux;
	}

	/**
	 * 	Accesseur renvoyant la liste des cours
	 */
	public List<Cour> getCours() {
		return this.cours;
	}

	/**
	 * 	Accesseur renvoyant la code couleur
	 */
	public String getCouleurEDT() {
		return this.couleurEDT;
	}

}
