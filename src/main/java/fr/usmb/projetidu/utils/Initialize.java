package fr.usmb.projetidu.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import fr.usmb.projetidu.handler.MySQL;

public class Initialize {
	
	/*
	 * Dictionnaire avec comme clé l'id et comme valeur un objet travail
	 */
	private static HashMap<Integer, Travail> travaux = new HashMap<>();
	
	/*
	 * Liste des élèves initialisés
	 */
	private static ArrayList<Eleve> eleves = new ArrayList<>();
	
	/**
	 * Récupération de l'objet de la base de données
	 */
	private static MySQL database = Main.getDatabase();
	
	/**
	 * Récupération d'un objet travail grâce à son id
	 */
	public static Travail getTravailFromId(int id) {
		
		return travaux.get(id);
		
	}
	
	/**
	 * Méthode permettant de réinitialiser les objets liés à un élève
	 * Méthode utilisée lors de la deconnexion ou lors de l'utilisation d'un des boutons pour actualiser les informations
	 */
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
	
	/**
	 * Méthode permettant de créer un objet Eleve avec les informations de la base de données
	 */
	public static Eleve InitializeEleve(String login) {
		
		String QUERY = "SELECT * FROM eleve WHERE login = '" + login + "';";
		int id_eleve = -1;
		String nom = "";
		String prenom = "";
		String date_naissance = "";
		String mail = "";
		int polypoints = -1;
		String ine = "";
		
		int id_promo = -1;
		
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					
					id_eleve = rs.getInt(1);
					nom = rs.getString(2);
					prenom = rs.getString(3);
					date_naissance = rs.getString(4);
					mail = rs.getString(5);
					polypoints = rs.getInt(6);
					ine = rs.getString(7);
					id_promo = rs.getInt(9);
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		ModuleColor.generateColors();
		
		Promo promo = InitializePromo(id_eleve, id_promo);
		
		
		Eleve eleve = new Eleve(id_eleve, nom, prenom, promo, mail, date_naissance, polypoints, ine, login);
		
		eleves.add(eleve);
		promo.addEleve(eleve);
		
		InitializeNote(eleve, id_eleve);
		InitializeEleveInSamePromo(id_eleve, eleve, promo.getId());
		
		return eleve;
		
	}
	
	/**
	 * Méthode permettant de créer des objet Eleve pour ceux dans la même promo que l'utilisateur avec les informations de la base de données
	 * Cette méthode permet notamment de récupérer les moyennes des autres élèves présents dans la bdd
	 */
	public static void InitializeEleveInSamePromo(int id_eleve, Eleve eleve, int id_promo) {
		
		String QUERY = "SELECT * FROM eleve WHERE id_promo = '" + id_promo + "';";
		HashMap<Integer, Object[]> eleves_list = new HashMap<>();
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					
					if(rs.getInt(1) != id_eleve) {
						
						
						eleves_list.put(rs.getInt(1), new Object[] {rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getString(8), rs.getInt(9)});
					}
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id : eleves_list.keySet()) {

			Object[] values = eleves_list.get(id);
			
			String nom = (String) values[0];
			String prenom = (String) values[1];
			String date_naissance = (String) values[2];
			String mail = (String) values[3];
			int polypoints = (int) values[4];
			String ine = (String) values[5];
			String loginNewEleve = (String) values[6];
			int idNewElevePromo = (int) values[7];
			
			
			if(idNewElevePromo == id_promo) {
				Eleve new_eleve = new Eleve(idNewElevePromo, nom, prenom, eleve.getPromo(), mail, date_naissance, polypoints, ine, loginNewEleve);
				eleves.add(eleve);
				eleve.getPromo().addEleve(new_eleve);
				
				InitializeNote(new_eleve, id);
			}
		} 
		
	}
	
	/**
	 * Méthode permettant de créer un objet Promo avec les informations de la base de données
	 */
	private static Promo InitializePromo(int id_eleve, int id_promo) {
		
		String QUERY = "SELECT * FROM promo WHERE id_promo = " + id_promo + ";";
		int annee = -1;
		int filiere_id = -1;
		int ecole_id = -1;
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					annee = Integer.parseInt(rs.getString(2));
					filiere_id = rs.getInt(3);
					ecole_id = rs.getInt(4);
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		
		Filiere filiere = InitializeFiliere(id_eleve, filiere_id);
		InitializeEcole(ecole_id);
		
		Promo promo = new Promo(filiere, annee, id_promo);
		
		Ecole.getInstance().addPromo(promo);
		
		return promo;
		
	}
	
	/**
	 * Méthode permettant de créer un objet Filiere avec les informations de la base de données
	 */
	private static Filiere InitializeFiliere(int id_eleve, int id_filiere) {
		
		String QUERY = "SELECT * FROM filiere WHERE id_filiere = " + id_filiere + ";";
		
		String nom = "";
		String description = "";
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					nom = rs.getString(2);
					description = rs.getString(3);
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		Filiere filiere = null;
		for (Filiere fil : Filiere.values()) { 
			
			if(fil.getNom().equalsIgnoreCase(nom)) {
				
				filiere = fil;
				
			}
			
		}
		
		InitializeUE_Filiere(id_eleve, filiere, id_filiere);
		
		filiere.setDescription(description);
		filiere.setNom(nom);
		
		return filiere;
		
	}
	
	/**
	 * Méthode permettant d'appeler la méthode pour créer les objets UE en fonction d'une filière
	 */
	private static void InitializeUE_Filiere(int id_eleve, Filiere filiere, int id_filiere) {
		
		String QUERY = "SELECT * FROM ue_filiere WHERE id_filiere = " + id_filiere + ";";
		
		List<Integer> ues_id = new ArrayList<>();
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					ues_id.add(rs.getInt(2));
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id : ues_id) {
			InitializeUE(id_eleve, filiere, id);
		}
		
		
	}
	
	/**
	 * Méthode permettant de créer un objet Ecole avec les informations de la base de données
	 */
	private static void InitializeEcole(int id_ecole) {
		
		String QUERY = "SELECT * FROM ecole WHERE id_ecole = " + id_ecole + ";";
		
		String nom = null;
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					nom = rs.getString(2);
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		Ecole ecole = Ecole.getInstance();
		ecole.setNom(nom);
		
	}
	
	/**
	 * Méthode permettant de créer un objet UE avec les informations de la base de données
	 */
	private static void InitializeUE(int id_eleve, Filiere filiere, int id_ue) {
		
		String QUERY = "SELECT * FROM ue WHERE id_ue = " + id_ue + ";";
		
		HashMap<Integer, String[]> ue_list = new HashMap<>();
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					
					String[] values = new String[] {rs.getString(2), rs.getString(3)};
					
					ue_list.put(rs.getInt(1), values);
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id : ue_list.keySet()){
			
			String code = ue_list.get(id)[0];
			String nom = ue_list.get(id)[1];
			
			char semesterChar = code.charAt(code.length() - 3);
			String semesterCasted = String.valueOf(semesterChar);
			int semester = Integer.parseInt(semesterCasted);
			
			UE ue = new UE(code, nom, semester);
			
			filiere.addUe(ue);
			InitializeModule(id_eleve, ue, id);
		}
		
		
	}
	
	/**
	 * Méthode permettant de créer un objet Module avec les informations de la base de données
	 */
	private static void InitializeModule(int id_eleve, UE ue, int id_ue) {
		
		String QUERY = "SELECT * FROM module WHERE id_ue = " + id_ue + ";";
		
		HashMap<Integer, Object[]> module_list = new HashMap<>();
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					
					module_list.put(rs.getInt(1), new Object[] {rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getFloat(5), rs.getString(6), rs.getString(7)});
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id : module_list.keySet()){
			
			Object[] values = module_list.get(id);
			
			String code = (String) values[0];
			String nom = (String) values[1];
			float nbHeures = (float) values[2];
			float coeff = (float) values[3];
			String evaluation = (String) values[4];
			String description = (String) values[5];
			
			String color = "";
			
			for(String c : ModuleColor.colors.keySet()) {
				
				if(!ModuleColor.colors.get(c)) {
					
					color = c;
					ModuleColor.colors.put(c, true);
					break;
					
				}
				
			}
			
			Module module = new Module(code, nom, nbHeures, coeff, evaluation, description, color);
			
			ue.addModule(module);
			
			InitializeCours(id_eleve, module, id);
			InitializeEnseigne(id, module);
			InitializeTravail(id, module);
		}
		
	}
	
	/**
	 * Méthode permettant de créer un objet Cours avec les informations de la base de données
	 */
	private static void InitializeCours(int id_eleve, Module module, int id_module) {
		
		String QUERY = "SELECT * FROM cours WHERE id_module = " + id_module + ";";
		
		HashMap<Integer, Object[]> cours_list = new HashMap<>();
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					
					cours_list.put(rs.getInt(1), new Object[] {rs.getInt(2), rs.getString(3), rs.getDouble(4), rs.getDouble(5), rs.getString(6), rs.getInt(7)});
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id_cour : cours_list.keySet()){
			
			Object[] values = cours_list.get(id_cour);
			
			int num_seance = (int) values[0];
			String date_cours = (String) values[1];
			double heure_debut = (double) values[2];
			double duree = (double) values[3];
			String type = (String) values[4];
			int perso = (int) values[5];
			
			if(heure_debut <= 0) {
				return;
			}
			
			if(duree != 0.0) {
				if((perso == 1 && DatabaseRequests.hasCourPerso(id_eleve, id_cour)) || perso == 0) {
					
					CourFactoryImpl factory = new CourFactoryImpl();
					
					Date date = parseDate(date_cours, "dd/MM/yyyy");
					
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
					case "exam":
						module.addCour(factory.createCour(CourEnum.EXAM, num_seance, date, heure_debut, duree, module));
						break;
					case "special":
						module.addCour(factory.createCour(CourEnum.SPECIAL, num_seance, date, heure_debut, duree, module));
						break;
					}
					
				}
			}
			
			
		}
		
	}
	
	/**
	 * Méthode permettant d'appeler la méthode pour créer un obet enseignant en fonction d'un module
	 */
	private static void InitializeEnseigne(int id_module, Module module) {
		
		String QUERY = "SELECT id_enseignant FROM enseigne WHERE id_module = " + id_module + ";";
		
		List<Integer> liste_ids = new ArrayList<>();
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
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

	/**
	 * Méthode permettant de créer un objet Enseignant avec les informations de la base de données
	 */
	private static void InitializeEnseignant(int id_enseignant, Module module) {
		
		
		String QUERY = "SELECT * FROM enseignant WHERE id_enseignant = " + id_enseignant + ";";
		
		HashMap<Integer, Object[]> enseignant_list = new HashMap<>();
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					
					enseignant_list.put(rs.getInt(1), new Object[] {rs.getString(2), rs.getString(3), rs.getString(4)});
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id : enseignant_list.keySet()){
			
			Object[] values = enseignant_list.get(id);
			
			String nom = (String) values[0];
			String prenom = (String) values[1];
			String mail = (String) values[2];
			
			Enseignant enseignant = new Enseignant(nom, prenom, mail);
			module.addEnseignant(enseignant);
			enseignant.addModule(module);
			
		}
		
		
	}
	
	/**
	 * Méthode permettant de créer un objet Travail avec les informations de la base de données
	 */
	private static void InitializeTravail(int id_module, Module module) {
		
		
		String QUERY = "SELECT * FROM travail WHERE id_module = " + id_module + ";";
		
		HashMap<Integer, Object[]> travaux_list = new HashMap<>();
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					
					travaux_list.put(rs.getInt(1), new Object[] {rs.getString(2), rs.getString(3), rs.getString(4)});
					
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		for(int id : travaux_list.keySet()){
			
			Object[] values = travaux_list.get(id);
			
			String nom = (String) values[0];
			String sujet = (String) values[1];
			String date_rendu = (String) values[2];
			
			Date date = parseDate(date_rendu, "dd/MM/yyyy");
			
			Travail travail = new Travail(nom, sujet, date, module);
			
			travaux.put(id, travail);
			module.addTravail(travail);
			
		}
		
		
	}
	
	/**
	 * Méthode permettant de formatter une date
	 */
	private static Date parseDate(String date, String format){
	    SimpleDateFormat formatter = new SimpleDateFormat(format);
	    try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Méthode permettant de créer un objet Note avec les informations de la base de données
	 */
	private static void InitializeNote(Eleve eleve, int id_eleve) {
		
		String QUERY = "SELECT * FROM note WHERE id_eleve = " + id_eleve + ";";
		
		HashMap<Integer, double[]> travaux_list = new HashMap<>();
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
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
