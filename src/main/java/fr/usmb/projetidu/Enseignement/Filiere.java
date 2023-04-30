package fr.usmb.projetidu.Enseignement;

import java.util.ArrayList;
import java.util.List;

public enum Filiere {

	/**
	 * 	Valeurs de la classe enumération filière
	 */
	IDU3("IDU3", "Informatique données usage 3ème année", new ArrayList<UE>()), SNI3("SNI3", "Systèmes numériques & Instrumentation 3ème année", new ArrayList<UE>()), PEIP1("PEIP1", "Parcours école ingénieurs Polytech 1ère année", new ArrayList<UE>()), MM3("MM3", "Mécanique & Matériaux 3ème année", new ArrayList<UE>()),
	IDU4("IDU4", "Informatique données usage 4ème année", new ArrayList<UE>()), SNI4("SNI4", "Systèmes numériques & Instrumentation 4ème année", new ArrayList<UE>()), PEIP2("PEIP2", "Parcours école ingénieurs Polytech 2ème année", new ArrayList<UE>()), MM4("MM4", "Mécanique & Matériaux 4ème année", new ArrayList<UE>()),
	IDU5("IDU5", "Informatique données usage 5ème année", new ArrayList<UE>()), SNI5("SNI5", "Systèmes numériques & Instrumentation 5ème année", new ArrayList<UE>()), MM5("MM5", "Mécanique & Matériaux 5ème année", new ArrayList<UE>()), DIPLOME("Diplomes", "Bravo à vous, vous êtes diplomés", new ArrayList<UE>());
	
	/**
	 * 	Nom de la filière
	 */
	private String nom;
	
	/**
	 * 	Description de la filière
	 */
	private String description;
	
	/**
	 * 	Liste des unités d'enseignements de la filière
	 */
	private List<UE> liste_ue = new ArrayList<>();
	
	/**
	 * 	Constructeur de la classe
	 */
	Filiere(String nom, String description, ArrayList<UE> liste_ue) {
		this.nom = nom;
		this.description = description;
		this.liste_ue = liste_ue;
	}
	
	/**
	 * 	Méthode pour ajouter un ou plusieurs UE
	 */
	public void addUe(UE... ues) {
		for(UE ue : ues) {
			this.liste_ue.add(ue);
		}
	}

	/**
	 * 	Accesseur renvoyant le nom de filière
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * 	Mutateur permettant de modifier le nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}


	/**
	 * 	Accesseur renvoyant la description de la filière
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 	Mutateur permettant de modifier la description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 	Accesseur renvoyant la liste des unités d'enseignements
	 */
	public List<UE> getListe_ue() {
		return liste_ue;
	}

	/**
	 * 	Mutateur permttant de modifier la liste d'unités d'enseignements
	 */
	public void setListe_ue(List<UE> liste_ue) {
		this.liste_ue = liste_ue;
	}
	
}
