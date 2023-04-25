package fr.usmb.projetidu.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import fr.usmb.projetidu.Main;
import fr.usmb.projetidu.Enseignement.Filiere;
import fr.usmb.projetidu.handler.MySQL;

public class DatabaseRequests {
	
	private static MySQL database = Main.getDatabase();
	
	public static void addStudent2Bdd(String nom, String prenom, String date, String mail, int polypoints, String ine, int id_promo) {
		
		if(isNewStudent(ine)) {
			String request = "INSERT INTO eleve(nom, prenom, date_naissance, mail, polypoints, ine, id_promo) VALUES ('" + nom  + "', '" + prenom  + "', '" + date + "', '" + mail + "', '" + polypoints + "', '" + ine + "', '" + id_promo + "')";
			
			try {
				database.updateSQL(request);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} else {
			
			String request = "UPDATE eleve SET mail = '" + mail + "', polypoints = " + polypoints + " WHERE ine = '" + ine + "';";
			
			try {
				database.updateSQL(request);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private static boolean isNewStudent(String ine) {
	
		int count = 0;
		try {
			ResultSet rs = database.querySQL("SELECT COUNT(*) AS rowcount FROM eleve WHERE ine = '" + ine + "';");
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {	
		}
		
		return count < 1;
		
	}
	
	public static void addFiliere2Bdd(String nom) {
		
		if(isNewFiliere(nom)) {
			
			String description = "";
			for (Filiere filiere : Filiere.values()) { 
				
				if(filiere.getNom().equalsIgnoreCase(nom)) {
					description = filiere.getDescription();
				}
				
			}
			
			String request = "INSERT INTO filiere(nom, description) VALUES ('" + nom  + "', '" + description  + "')";
			
			try {
				database.updateSQL(request);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} 
		
	}
	
	private static boolean isNewFiliere(String nom) {
		
		int count = 0;
		try {
			ResultSet rs = database.querySQL("SELECT COUNT(*) AS rowcount FROM filiere WHERE nom = '" + nom + "';");
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {	
		}
		
		return count < 1;
		
	}
	
	public static void addEcole2Bdd(String nom) {
		
		if(isNewEcole(nom)) {
			String request = "INSERT INTO ecole(nom) VALUES ('" + nom  + "')";
			
			try {
				database.updateSQL(request);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} 
		
	}
	
	private static boolean isNewEcole(String nom) {
		
		int count = 0;
		try {
			ResultSet rs = database.querySQL("SELECT COUNT(*) AS rowcount FROM ecole WHERE nom = '" + nom + "';");
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {	
		}
		
		return count < 1;
		
	}
	
	public static void addPromo2Bdd(int firstJoin, String filiere, String nomEcole) {
		
		
		String annee = String.valueOf(firstJoin + 3);
		
		int id_ecole = getIdOfEcole(nomEcole);
		
		int id_filiere = getIdOfFiliere(filiere);
		
		if(isNewPromo(annee, id_ecole, id_filiere)) {
			String request = "INSERT INTO promo(annee, id_filiere, id_ecole) VALUES ('" + annee  + "', '" + id_filiere + "', '" + id_ecole + "')";
			
			try {
				database.updateSQL(request);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} 
		
		
	}
	
	private static Integer getIdOfFiliere(String nom) {
		
		String QUERY = "SELECT * FROM filiere WHERE nom = '" + nom + "';";
		
		int result = -1;
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					result = rs.getInt(1);
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public static Integer getIdOfPromo(int firstJoin, String filiere, String nomEcole) {
		
		String annee = String.valueOf(firstJoin + 3);
		
		int id_ecole = getIdOfEcole(nomEcole);
		
		int id_filiere = getIdOfFiliere(filiere);
		
		
		String QUERY = "SELECT * FROM promo WHERE annee = '" + annee + "' and id_filiere = " + id_filiere + " and id_ecole = " + id_ecole + ";";
		
		int result = -1;
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					result = rs.getInt(1);
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static Integer getIdOfEcole(String nom) {
		
		String QUERY = "SELECT * FROM ecole WHERE nom = '" + nom + "';";
		
		int result = -1;
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					result = rs.getInt(1);
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static boolean isNewPromo(String annee, int id_ecole, int id_filiere) {
	
		int count = 0;
		try {
			ResultSet rs = database.querySQL("SELECT COUNT(*) AS rowcount FROM promo WHERE annee = '" + annee + "' and id_ecole = " + id_ecole + " and id_filiere = " + id_filiere + ";");
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {	
		}
		
		return count < 1;
		
	}
	
	public static void addUe2Bdd(String code, String nom) {
		
		if(isNewUe(code, nom)) {
			String request = "INSERT INTO ue(code, nom) VALUES ('" + code  + "', '" + nom + "')";
			
			try {
				database.updateSQL(request);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} 
		
		
	}
	
	private static boolean isNewUe(String code, String nom) {
		
		int count = 0;
		try {
			ResultSet rs = database.querySQL("SELECT COUNT(*) AS rowcount FROM ue WHERE code = '" + code + "' and nom = '" + nom + "';");
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {	
		}
		
		return count < 1;
		
	}
	
	public static void addProf2Bdd(String nom, String prenom, String mail) {
		
		if(isNewProf(nom, prenom)) {
			String request = "INSERT INTO enseignant(nom, prenom, mail) VALUES ('" + nom  + "', '" + prenom + "', '" + mail + "')";
			
			try {
				database.updateSQL(request);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} else {
			
			String request = "UPDATE enseignant SET mail = '" + mail + "' WHERE nom = '" + nom + "' and prenom = '" + prenom + "';";
			
			try {
				database.updateSQL(request);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	private static boolean isNewProf(String nom, String prenom) {
		
		int count = 0;
		try {
			ResultSet rs = database.querySQL("SELECT COUNT(*) AS rowcount FROM enseignant WHERE nom = '" + nom + "' and prenom = '" + prenom + "';");
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {	
		}
		
		return count < 1;
		
	}
	
	public static void addModule2Bdd(String code, String nom, double nbHeures, double coeff, String evaluation, String description, String ueCode) {
		
		int id_ue = getIdOfUe(ueCode);
		
		if(isNewModule(code)) {
			String request = "INSERT INTO module(code, nom, nb_heures, coeff, evaluation, description, id_ue) VALUES ('" + code  + "', '" + nom  + "', '" + nbHeures + "', '" + coeff + "', '" + evaluation + "', '" + description + "', '" + id_ue + "')";
			
			try {
				database.updateSQL(request);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} 
		
	}
	
	private static Integer getIdOfUe(String code) {
		
		String QUERY = "SELECT * FROM ue WHERE code = '" + code + "';";
		
		int result = -1;
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					result = rs.getInt(1);
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static boolean isNewModule(String code) {
		
		int count = 0;
		try {
			ResultSet rs = database.querySQL("SELECT COUNT(*) AS rowcount FROM module WHERE code = '" + code + "';");
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {	
		}
		
		return count < 1;
		
	}
	
	public static void addEnseigne2Bdd(String nom, String prenom, String code) {
		
		int id_module = getIdOfModule(code);
		int id_enseignant = getIdOfEnseignant(nom, prenom);
		
		if(isEnseignantModuleNotAlreadyLinked(id_module, id_enseignant)) {
			String request = "INSERT INTO enseigne(id_module, id_enseignant) VALUES ('" + id_module  + "', '" + id_enseignant  + "')";
			
			try {
				database.updateSQL(request);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} 
		
	}
	
	private static Integer getIdOfModule(String code) {
		
		String QUERY = "SELECT * FROM module WHERE code = '" + code + "';";
		
		int result = -1;
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					result = rs.getInt(1);
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static Integer getIdOfEnseignant(String nom, String prenom) {
		
		String QUERY = "SELECT * FROM enseignant WHERE nom = '" + nom + "' and prenom = '" + prenom + "';";
		
		int result = -1;
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					result = rs.getInt(1);
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static boolean isEnseignantModuleNotAlreadyLinked(int id_module, int id_enseignant) {
		
		int count = 0;
		try {
			ResultSet rs = database.querySQL("SELECT COUNT(*) AS rowcount FROM enseigne WHERE id_module = '" + id_module + "' and id_enseignant = '" + id_enseignant + "';");
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {	
		}
		
		return count < 1;
		
	}
	
	public static void addRelationUeFiliere2Bdd(String nom, String code) {
		
		int id_filiere = getIdOfFiliere(nom);
		int id_ue = getIdOfUe(code);
		
		if(isUeFiliereNotAlreadyLinked(id_filiere, id_ue)) {
			String request = "INSERT INTO ue_filiere(id_filiere, id_ue) VALUES ('" + id_filiere  + "', '" + id_ue  + "')";
			
			try {
				database.updateSQL(request);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} 
		
	}
	
	private static boolean isUeFiliereNotAlreadyLinked(int id_filiere, int id_ue) {
		
		int count = 0;
		try {
			ResultSet rs = database.querySQL("SELECT COUNT(*) AS rowcount FROM ue_filiere WHERE id_filiere = '" + id_filiere + "' and id_ue = '" + id_ue + "';");
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {	
		}
		
		return count < 1;
		
	}
	
	public static void addTravail2Bdd(String nomTravail, String sujet, Date date_rendu, String code, String nom, String prenom) {
		
		int id_module = getIdOfModule(code);
		int id_enseignant = getIdOfEnseignant(nom, prenom);
		
		if(isNewTravail(nomTravail)) {
			String request = "INSERT INTO travail(nom, sujet, date_rendu, id_module, id_enseignant) VALUES ('" + nomTravail  + "', '" + sujet  + "', '" + date_rendu  + "', '" + id_module  + "', '" + id_enseignant  + "')";
			
			try {
				database.updateSQL(request);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} 
		
	}
	
	private static boolean isNewTravail(String nom) {
		
		int count = 0;
		try {
			ResultSet rs = database.querySQL("SELECT COUNT(*) AS rowcount FROM travail WHERE nom = '" + nom + "';");
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {	
		}
		
		return count < 1;
		
	}
	
	public static void addNote2Bdd(int id_eleve, String codeModule, String nomTravail, double note, double coeff) {
		
		int id_travail = getIdOfTravail(codeModule, nomTravail);
		
		if(isNewTravail(nomTravail)) {
			String request = "INSERT INTO note(id_eleve, id_travail, note, coeff) VALUES ('" + id_eleve  + "', '" + id_travail  + "', '" + note  + "', '" + coeff  + "')";
			
			try {
				database.updateSQL(request);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} 
		
	}
	
	private static Integer getIdOfTravail(String codeModule, String nomTravail) {
		
		int id_module = getIdOfModule(codeModule);
		
		String QUERY = "SELECT * FROM travail WHERE nom = '" + nomTravail + "' and id_module = '" + id_module + "';";
		
		int result = -1;
		
		try {
			ResultSet rs = database.querySQL(QUERY);
			
				while(rs.next()){
					result = rs.getInt(1);
				}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void addCour2Bdd(int num_seance, String codeModule, String date, double heuredebut, double duree, String type) {
		
		int id_module = getIdOfModule(codeModule);
		
		if(codeModule == null) return;
		
		if(isNewCour(num_seance, type)) {
			String request = "INSERT INTO cours(num_seance, date, heuredebut, duree, type, id_module) VALUES ('" + num_seance  + "', '" + date  + "', '" + heuredebut  + "', '" + duree  + "', '" + type  + "', '" + id_module  + "')";
			
			System.out.println(request + "   " + codeModule);
			
			try {
				database.updateSQL(request);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		} 
		
	}
	
	private static boolean isNewCour(int num_seance, String type) {
		
		int count = 0;
		try {
			ResultSet rs = database.querySQL("SELECT COUNT(*) AS rowcount FROM cours WHERE num_seance = '" + num_seance + "' and type = " + type + ";");
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {	
		}
		
		return count < 1;
		
	}
	
	
	
	

}
