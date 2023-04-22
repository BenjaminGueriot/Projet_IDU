package fr.usmb.projetidu.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import fr.usmb.projetidu.Main;
import fr.usmb.projetidu.Enseignement.Ecole;
import fr.usmb.projetidu.Enseignement.Filiere;
import fr.usmb.projetidu.Enseignement.Promo;
import fr.usmb.projetidu.Enseignement.UE;
import fr.usmb.projetidu.Enseignement.Module.Module;
import fr.usmb.projetidu.Enseignement.Module.Travail;
import fr.usmb.projetidu.Enseignement.Module.Cour.Cour;
import fr.usmb.projetidu.Enseignement.Module.Cour.CourEnum;
import fr.usmb.projetidu.Enseignement.Module.Cour.CourFactoryImpl;
import fr.usmb.projetidu.Personne.Eleve;
import fr.usmb.projetidu.Personne.Enseignant;

public class Initialize {
	
	private static HashMap<Integer, Travail> travaux = new HashMap<>();
	private static ArrayList<Eleve> eleves = new ArrayList<>();
	
	public static Travail getTravailFromId(int id) {
		
		return travaux.get(id);
		
	}
	
	@SuppressWarnings("unused")
	public static void resetObject(Eleve eleve) {
		
		for(UE ue : eleve.getPromo().getFiliere().getListe_ue()) {
			ue = null;
		}
		
		for(Module module : eleve.getAllModules()) {
			
			for(Cour cour : module.getCours()) {
				cour = null;
			}
			for(Travail travail : module.getTravaux()) {
				travail = null;
			}
			for(Enseignant enseignant : module.getEnseignants()) {
				enseignant = null;
			}
			module = null;
		}
		
		
		eleve.getPromo().getFiliere().setListe_ue(new ArrayList<UE>());
		
		Promo promo = eleve.getPromo();
		
		promo = null;
		
		eleve = null;
		
	}
	
	public static boolean checkConnexion(String login, String mot_de_passe) {
		String QUERY = "SELECT * FROM eleve WHERE login_eleve = '" + login + "';";
		
		String mdp = "";
		
		try {
			ResultSet rs = Main.database.querySQL(QUERY);
			
				while(rs.next()){
					
					mdp = rs.getString(5);
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		String pass_hash = generatePassword(mot_de_passe);
		
		return pass_hash.equals(mdp) ? true : false;
		
	}
	
	public static String generatePassword(String mdp) {
		
		 String passwordToHash = mdp;
		 String generatedPassword = null;
		
		 try 
		    {
		      MessageDigest md = MessageDigest.getInstance("MD5");

		      md.update(passwordToHash.getBytes());

		      byte[] bytes = md.digest();

		      StringBuilder sb = new StringBuilder();
		      for (int i = 0; i < bytes.length; i++) {
		        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		      }

		      generatedPassword = sb.toString();
		    } catch (NoSuchAlgorithmException e) {
		      e.printStackTrace();
		    }
		    return generatedPassword;
		 }
		
		
	
	public static Eleve InitializeEleve(String login) {
		
		String QUERY = "SELECT * FROM eleve WHERE login_eleve = '" + login + "';";
		
		int id_eleve = -1;
		String nom = "";
		String prenom = "";
		Date date_arrive = null;
		int promo_id = -1;
		
		
		try {
			ResultSet rs = Main.database.querySQL(QUERY);
			
				while(rs.next()){
					
					id_eleve = rs.getInt(1);
					nom = rs.getString(2);
					prenom = rs.getString(3);
					date_arrive = rs.getDate(6);
					promo_id = rs.getInt(7);
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		ModuleColor.generateColors();
		
		Promo promo = InitializePromo(id_eleve, promo_id);
		
		Eleve eleve = new Eleve(nom, prenom, promo, login, date_arrive);
		
		eleves.add(eleve);
		promo.addEleve(eleve);
		
		InitializeNote(eleve, id_eleve);
		InitializeEleveInSamePromo(id_eleve, eleve, promo.getId());
		
		return eleve;
		
	}
	
	public static void InitializeEleveInSamePromo(int id_eleve, Eleve eleve, int id_promo) {
		
		String QUERY = "SELECT * FROM eleve WHERE id_promo = '" + id_promo + "';";
		
		HashMap<Integer, Object[]> eleves_list = new HashMap<>();
		
		try {
			ResultSet rs = Main.database.querySQL(QUERY);
			
				while(rs.next()){
					
					if(rs.getInt(1) != id_eleve) {
						
						
						eleves_list.put(rs.getInt(1), new Object[] {rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(6), rs.getInt(7)});
					}
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id : eleves_list.keySet()) {

			Object[] values = eleves_list.get(id);
			
			String nom = (String) values[0];
			String prenom = (String) values[1];
			String login = (String) values[2];
			Date date_arrivee = (Date) values[3];
			int id_new_eleve_promo = (int) values[4];
			
			
			if(id_new_eleve_promo == id_promo) {
				Eleve new_eleve = new Eleve(nom, prenom, eleve.getPromo(), login, date_arrivee);
				eleves.add(eleve);
				eleve.getPromo().addEleve(new_eleve);
				
				InitializeNote(new_eleve, id);
			}
		}
		
	}
	
	private static Promo InitializePromo(int id_eleve, int id_promo) {
		
		String QUERY = "SELECT * FROM promo WHERE id_promo = " + id_promo + ";";
		
		Date annee = null;
		int filiere_id = -1;
		int ecole_id = -1;
		
		try {
			ResultSet rs = Main.database.querySQL(QUERY);
			
				while(rs.next()){
					annee = rs.getDate(2);
					filiere_id = rs.getInt(3);
					ecole_id = rs.getInt(4);
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		
		Filiere filiere = InitializeFiliere(filiere_id);
		InitializeEcole(ecole_id);
		
		Promo promo = new Promo(filiere, annee, id_promo);
		
		Ecole.getInstance().addPromo(promo);
		
		return promo;
		
	}
	
	private static Filiere InitializeFiliere(int id_filiere) {
		
		String QUERY = "SELECT * FROM filiere WHERE id_filiere = " + id_filiere + ";";
		
		String nom = null;
		String description = null;
		
		try {
			ResultSet rs = Main.database.querySQL(QUERY);
			
				while(rs.next()){
					nom = rs.getString(2);
					description = rs.getString(3);
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		
		Filiere filiere = null;
		
		switch (nom.toUpperCase()) {
			case "IDU3":
				filiere = Filiere.IDU3;
				InitializeUE_Filiere(filiere, id_filiere);
				break;
			case "IDU4":
				filiere = Filiere.IDU4;
				InitializeUE_Filiere(filiere, id_filiere);
				break;
			case "IDU5":
				filiere = Filiere.IDU5;
				InitializeUE_Filiere(filiere, id_filiere);
				break;
			case "SNI3":
				filiere = Filiere.SNI3;
				InitializeUE_Filiere(filiere, id_filiere);
				break;
			case "SNI4":
				filiere = Filiere.SNI4;
				InitializeUE_Filiere(filiere, id_filiere);
				break;
			case "SNI5":
				filiere = Filiere.SNI5;
				InitializeUE_Filiere(filiere, id_filiere);
				break;
			case "MM3":
				filiere = Filiere.MM3;
				InitializeUE_Filiere(filiere, id_filiere);
				break;
			case "MM4":
				filiere = Filiere.MM4;
				InitializeUE_Filiere(filiere, id_filiere);
				break;
			case "MM5":
				filiere = Filiere.MM5;
				InitializeUE_Filiere(filiere, id_filiere);
				break;
			case "PEIP1":
				filiere = Filiere.PEIP1;
				InitializeUE_Filiere(filiere, id_filiere);
				break;
			case "PEIP2":
				filiere = Filiere.PEIP2;
				InitializeUE_Filiere(filiere, id_filiere);
				break;
			case "DIPLOME":
				filiere = Filiere.DIPLOME;
				InitializeUE_Filiere(filiere, id_filiere);
				break;
		}
		
		filiere.setDescription(description);
		filiere.setNom(nom);
		
		return filiere;
		
	}
	
	private static void InitializeUE_Filiere(Filiere filiere, int id_filiere) {
		
		String QUERY = "SELECT * FROM ue_filiere WHERE id_filiere = " + id_filiere + ";";
		
		List<Integer> ues_id = new ArrayList<>();
		
		try {
			ResultSet rs = Main.database.querySQL(QUERY);
			
				while(rs.next()){
					ues_id.add(rs.getInt(2));
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id : ues_id) {
			InitializeUE(filiere, id);
		}
		
		
	}
	
	private static void InitializeEcole(int id_ecole) {
		
		String QUERY = "SELECT * FROM ecole WHERE id_ecole = " + id_ecole + ";";
		
		String nom = null;
		
		try {
			ResultSet rs = Main.database.querySQL(QUERY);
			
				while(rs.next()){
					nom = rs.getString(2);
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		Ecole ecole = Ecole.getInstance();
		ecole.setNom(nom);
		
	}
	
	private static void InitializeUE(Filiere filiere, int id_ue) {
		
		String QUERY = "SELECT * FROM ue WHERE id_ue = " + id_ue + ";";
		
		HashMap<Integer, String> ue_list = new HashMap<>();
		
		try {
			ResultSet rs = Main.database.querySQL(QUERY);
			
				while(rs.next()){
					
					ue_list.put(rs.getInt(1), rs.getString(2));
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id : ue_list.keySet()){
			UE ue = new UE(ue_list.get(id));
			filiere.addUe(ue);
			InitializeModule(ue, id);
		}
		
		
	}
	
	private static void InitializeModule(UE ue, int id_ue) {
		
		String QUERY = "SELECT * FROM module WHERE id_ue = " + id_ue + ";";
		
		HashMap<Integer, String[]> module_list = new HashMap<>();
		
		try {
			ResultSet rs = Main.database.querySQL(QUERY);
			
				while(rs.next()){
					
					module_list.put(rs.getInt(1), new String[] {rs.getString(2), rs.getString(3), rs.getString(4)});
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id : module_list.keySet()){
			
			String[] values = module_list.get(id);
			
			String nom = values[0];
			String description = values[1];
			
			
			String color = "";
			
			for(String c : ModuleColor.colors.keySet()) {
				
				if(!ModuleColor.colors.get(c)) {
					
					color = c;
					ModuleColor.colors.put(c, true);
					break;
					
				}
				
			}
			
			Module module = new Module(nom, description, color);
			
			ue.addModule(module);
			
			InitializeCour(module, id);
			InitializeEnseigne(id, module);
			InitializeTravail(id, module);
		}
		
	}
	
	private static void InitializeCour(Module module, int id_module) {
		
		String QUERY = "SELECT * FROM cours WHERE id_module = " + id_module + ";";
		
		HashMap<Integer, Object[]> cours_list = new HashMap<>();
		
		try {
			ResultSet rs = Main.database.querySQL(QUERY);
			
				while(rs.next()){
					
					cours_list.put(rs.getInt(1), new Object[] {rs.getInt(2), rs.getDate(3), rs.getDouble(4), rs.getDouble(5), rs.getString(6)});
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id : cours_list.keySet()){
			
			Object[] values = cours_list.get(id);
			
			int num_seance = (int) values[0];
			Date date = (Date) values[1];
			double heure_debut = (double) values[2];
			double duree = (double) values[3];
			String type = (String) values[4];
			
			CourFactoryImpl factory = new CourFactoryImpl();
			
			switch (type.toLowerCase()) {
			case "cm":
				module.addCour(factory.createCour(CourEnum.CM, num_seance, date, heure_debut, duree, module));
				break;
			case "td":
				module.addCour(factory.createCour(CourEnum.TD, num_seance, date, heure_debut, duree, module));
				break;
			case "tp":
				module.addCour(factory.createCour(CourEnum.TP, num_seance, date, heure_debut, duree, module));
				break;
			}
		}
		
	}
	
	private static void InitializeEnseigne(int id_module, Module module) {
		
		String QUERY = "SELECT id_enseignant FROM enseigne WHERE id_module = " + id_module + ";";
		
		List<Integer> liste_ids = new ArrayList<>();
		
		try {
			ResultSet rs = Main.database.querySQL(QUERY);
			
				while(rs.next()){
					
					liste_ids.add(rs.getInt(1));
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id : liste_ids){
			
			InitializeEnseignant(id, module);
			
			
		}
		
	}

	private static void InitializeEnseignant(int id_enseignant, Module module) {
		
		
		String QUERY = "SELECT * FROM enseignant WHERE id_enseignant = " + id_enseignant + ";";
		
		HashMap<Integer, Object[]> enseignant_list = new HashMap<>();
		
		try {
			ResultSet rs = Main.database.querySQL(QUERY);
			
				while(rs.next()){
					
					enseignant_list.put(rs.getInt(1), new Object[] {rs.getString(2), rs.getString(3), rs.getDate(4)});
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id : enseignant_list.keySet()){
			
			Object[] values = enseignant_list.get(id);
			
			String nom = (String) values[0];
			String prenom = (String) values[1];
			Date date_arrivee = (Date) values[2];
			
			Enseignant enseignant = new Enseignant(nom, prenom, date_arrivee);
			module.addEnseignant(enseignant);
			enseignant.addModule(module);
			
		}
		
		
	}
	
	private static void InitializeTravail(int id_module, Module module) {
		
		
		String QUERY = "SELECT * FROM travail WHERE id_module = " + id_module + ";";
		
		HashMap<Integer, Object[]> travaux_list = new HashMap<>();
		
		try {
			ResultSet rs = Main.database.querySQL(QUERY);
			
				while(rs.next()){
					
					travaux_list.put(rs.getInt(1), new Object[] {rs.getString(2), rs.getString(3), rs.getDate(4)});
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id : travaux_list.keySet()){
			
			Object[] values = travaux_list.get(id);
			
			String nom = (String) values[0];
			String sujet = (String) values[1];
			Date date_rendu = (Date) values[2];
			
			Travail travail = new Travail(nom, sujet, date_rendu, module);
			
			travaux.put(id, travail);
			module.addTravail(travail);
			
		}
		
		
	}

	private static void InitializeNote(Eleve eleve, int id_eleve) {
		
		String QUERY = "SELECT * FROM note WHERE id_eleve = " + id_eleve + ";";
		
		HashMap<Integer, double[]> travaux_list = new HashMap<>();
		
		try {
			ResultSet rs = Main.database.querySQL(QUERY);
			
				while(rs.next()){
					travaux_list.put(rs.getInt(2), new double[] {rs.getDouble(3), rs.getDouble(4)});
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		HashMap<Travail, double[]> res = new HashMap<>();
		
		for(int id : travaux_list.keySet()){
			
			double[] values = travaux_list.get(id);
			
			
			Travail travail = getTravailFromId(id);
			
			res.put(travail, values);
			
		}
		
		List<UE> ues = eleve.getPromo().getFiliere().getListe_ue();
		List<Module> modules = new ArrayList<>();
		
		for(UE ue : ues) {
			
			
			modules.addAll(ue.getModules());
			
			eleve.addUE(ue);
		}
		
		
		for(Module module : modules) {
			
			for(Travail travail : res.keySet()) {
				
				if(module.getTravaux().contains(travail)) {
					
					
					eleve.addNote(module, travail, res.get(travail)[0], res.get(travail)[1]);
					
				}
			}
			
			
		}
			
		
	}
	
	
	

}
