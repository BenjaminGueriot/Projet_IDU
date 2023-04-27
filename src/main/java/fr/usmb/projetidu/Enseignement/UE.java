package fr.usmb.projetidu.Enseignement;

import java.util.ArrayList;
import java.util.List;

import fr.usmb.projetidu.Enseignement.Module.Module;

public class UE {

	private String code;
	private String nom;
	private List<Module> modules = new ArrayList<>();
	private int semester;
	
	public UE(String code, String nom, int semester) {
		this.code = code;
		this.nom = nom;
		this.semester = semester;
	}
	
	public void addModule(Module... modules) {
		for(Module module : modules) {
			this.modules.add(module);
		}
	}
	
	public Integer getSemester() {
		return this.semester;
	}

	public String getNom() {
		return this.nom;
	}

	public List<Module> getModules() {
		return this.modules;
	}
	
	public String getCode() {
		return this.code;
	}


	
	
}
