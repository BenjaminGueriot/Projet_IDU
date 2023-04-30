package fr.usmb.projetidu.Enseignement;

import java.util.ArrayList;
import java.util.List;

import fr.usmb.projetidu.Enseignement.Module.Module;

public class UE {

	/**
	 * 	Code de l'ue
	 */
	private String code;
	
	/**
	 * 	Nom de l'ue
	 */
	private String nom;
	
	/**
	 * 	Numéro du semestre de l'ue
	 */
	private int semester;
	
	/**
	 * 	Liste des modules de l'ue
	 */
	private List<Module> modules = new ArrayList<>();
	
	
	/**
	 * 	Constructeur de la classe
	 */
	public UE(String code, String nom, int semester) {
		this.code = code;
		this.nom = nom;
		this.semester = semester;
	}
	
	/**
	 * 	Méthode pour ajouter un ou plusieurs modules à la liste
	 */
	public void addModule(Module... modules) {
		for(Module module : modules) {
			this.modules.add(module);
		}
	}
	
	/**
	 * 	Accesseur renvoyant le semestre
	 */
	public Integer getSemester() {
		return this.semester;
	}

	/**
	 * 	Accesseur renvoyant le nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * 	Accesseur renvoyant la liste de modules
	 */
	public List<Module> getModules() {
		return this.modules;
	}
	
	/**
	 * 	Accesseur renvoyant le code
	 */
	public String getCode() {
		return this.code;
	}


	
	
}
