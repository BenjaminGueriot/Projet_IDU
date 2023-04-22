package fr.usmb.projetidu.Personne;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.usmb.projetidu.Enseignement.Module.Module;

public class Enseignant extends Personne {

	private List<Module> modules = new ArrayList<>();
	
	public Enseignant(String nom, String prenom, Date date_arrivee) {
		super(nom, prenom, date_arrivee);
	}
	
	public void addModule(Module... modules) {
		for(Module module : modules) {
			this.modules.add(module);
		}
	}
	
	

}