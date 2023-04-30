package fr.usmb.projetidu.Enseignement.Module.Cour;

import java.util.Date;

import fr.usmb.projetidu.Enseignement.Module.Module;

public class CM extends Cour {

	/**
	 * 	Constructeur de la classe
	 */
	public CM(int num_seance, Date date, double heure_debut, double duree, Module module, CourEnum courenum) {
		super(num_seance, date, heure_debut, duree, module, courenum);
	}

}
