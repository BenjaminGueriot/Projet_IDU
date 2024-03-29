package fr.usmb.projetidu.Enseignement.Module.Cour;

import java.util.Date;

import fr.usmb.projetidu.Enseignement.Module.Module;

public interface CourFactory {
	
	/**
	 * 	Méthode permettant de créer un objet Cour
	 */
	Cour createCour(CourEnum cour_enum, int num_seance, Date date, double heure_debut, double duree, Module module);

}
