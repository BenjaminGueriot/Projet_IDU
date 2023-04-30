package fr.usmb.projetidu.Enseignement;

import java.util.ArrayList;
import java.util.List;

import fr.usmb.projetidu.Enseignement.Module.Module;
import fr.usmb.projetidu.Enseignement.Module.Travail;
import fr.usmb.projetidu.Personne.Eleve;

public class Promo {
	
	/**
	 * 	Identifiant de la promo
	 */
	private int id;
	
	/**
	 * 	Filière de la promo
	 */
	private Filiere filiere;
	
	/**
	 * 	Date de fin de cycle de la promo (date de promotion)
	 */
	private int date;
	
	/**
	 * 	Liste des élèves de la promo
	 */
	private List<Eleve> eleves = new ArrayList<>();
	
	/**
	 * 	Constructeur de la classe
	 */
	public Promo(Filiere filiere, int date, int id) {
		super();
		this.filiere = filiere;
		this.date = date;
		this.id = id;
	}
	
	/**
	 * 	Accesseur renvoyant la filière
	 */
	public Filiere getFiliere() {
		return this.filiere;
	}

	/**
	 * 	Accesseur renvoyant la date de promotion
	 * Renvoit l'année de la date promotion sous forme d'entier
	 */
	public Integer getDatePromotion() {
		return this.date;
	}

	/**
	 * 	Accesseur renvoyant le nom de filière
	 */
	public String toString() {
		return this.filiere.getNom();
	}
	
	/**
	 * 	Méthode permettant d'ajouter un élève à la liste d'élèves
	 */
	public void addEleve(Eleve eleve) {
		this.eleves.add(eleve);
	}

	/**
	 * 	Accesseur renvoyant l'identifiant de la promo
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * 	Accesseur renvoyant la liste d'élèves
	 */
	public List<Eleve> getEleves() {
		return eleves;
	}

	/**
	 * 	Méthode retournant la moyenne d'une promo sur un module
	 */
	public Double getPromoMeanOfModule(Module module){
		
		double sum = 0;
		double count = 0;
		
		for(Eleve eleve : eleves) {
			
			sum += eleve.getMeanOfModule(module);
			count++;
			
		}
		double mean = sum / count;
		
		return Math.round(mean*100.0)/100.0;
		
	}
	
	/**
	 * 	Méthode retournant la moyenne d'une promo sur un travail
	 */
	public Double getPromoMeanOfTravail(Travail travail){
		
		double sum = 0;
		double count = 0;
		
		for(Eleve eleve : eleves) {
			
			if(eleve.getNoteOfTravail(travail) != null) {
				sum += (double) eleve.getNoteOfTravail(travail);
				count++;
			}
			
			
		}
		double mean = sum / count;
		
		return Math.round(mean*100.0)/100.0;
		
	}
	
	/**
	 * 	Méthode retournant la moyenne d'une promo sur un UE
	 */
	public Double getPromoMeanOfUE(UE ue){
		
		double sum = 0;
		double count = 0;
		
		for(Eleve eleve : eleves) {
			
			sum += eleve.getMeanOfUe(ue);
			count++;
			
		}
		
		double mean = sum / count;
		
		return Math.round(mean*100.0)/100.0;
		
	}
	
	/**
	 * 	Méthode retournant la moyenne générale d'une promo
	 */
	public Double getGlobalMean(){
		
		double sum = 0;
		double count = 0;
		
		for(Eleve eleve : eleves) {
			
			sum += eleve.getGlobalMean();
			count++;
			
		}
		double mean = sum / count;
		
		return Math.round(mean*100.0)/100.0;
		
	}
	
	
	
	

}
