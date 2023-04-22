package fr.usmb.projetidu.Enseignement;

import java.util.ArrayList;
import java.util.List;

public class Ecole {

	private String nom;
	private static Ecole ecole;
	private List<Promo> promos = new ArrayList<>();
	
	public Ecole() {
	}
	
	public static Ecole getInstance() {
		if (ecole == null) {
			ecole = new Ecole();
		}
		return ecole;
	}
	
	public String getNom() {
		return this.nom;
	}

	public List<Promo> getPromos() {
		return this.promos;
	}
	
	public void addPromo(Promo promo) {
		this.promos.add(promo);
	}

	public void setPromos(List<Promo> promos) {
		this.promos = promos;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
}
