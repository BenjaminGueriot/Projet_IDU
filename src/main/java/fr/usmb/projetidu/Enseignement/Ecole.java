package fr.usmb.projetidu.Enseignement;

import java.util.ArrayList;
import java.util.List;

public class Ecole {

	/**
	 * 	Nom de l'école
	 */
	private String nom;
	
	/**
	 * 	Objet unique Ecole
	 */
	private static Ecole ecole;
	
	/**
	 * 	Liste des promos
	 */
	private List<Promo> promos = new ArrayList<>();
	
	/**
	 * 	Méthode renvoyant l'instance unique de l'ecole
	 */
	public static Ecole getInstance() {
		if (ecole == null) {
			ecole = new Ecole();
		}
		return ecole;
	}
	
	/**
	 * 	Accesseur renvoyant le nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * 	Accesseur renvoyant la liste des promos
	 */
	public List<Promo> getPromos() {
		return this.promos;
	}
	
	/**
	 * 	Méthode permettant d'ajouter une promo
	 */
	public void addPromo(Promo promo) {
		this.promos.add(promo);
	}
	
	/**
	 * 	Mutateur permettant de modifier un nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	

}
