package fr.usmb.projetidu.Personne;

import java.util.ArrayList;
import java.util.List;

import fr.usmb.projetidu.Enseignement.Module.Module;

public class Enseignant extends Personne {

	/**
	 * Liste des modules d'un enseignant
	 */
	private List<Module> modules = new ArrayList<>();
	
	/**
	 * Constructeur de la classe
	 */
	public Enseignant(String nom, String prenom, String mail) {
		super(nom, prenom, mail);
	}
	
	/**
	 * Méthode pour ajouter un ou plusieurs modules à la liste
	 */
	public void addModule(Module... modules) {
		for(Module module : modules) {
			this.modules.add(module);
		}
	}
	
	

}
