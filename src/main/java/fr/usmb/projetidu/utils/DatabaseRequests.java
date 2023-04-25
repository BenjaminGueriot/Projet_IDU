package fr.usmb.projetidu.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.usmb.projetidu.Main;
import fr.usmb.projetidu.Enseignement.Filiere;
import fr.usmb.projetidu.handler.MySQL;

public class DatabaseRequests {
	
	private static MySQL database = Main.getDatabase();
	
	public static void addStudentToBdd(String nom, String prenom, String date, String mail, int polypoints, String ine, int id_promo) {
		
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
	
	public static void addFiliereToBdd(String nom) {
		
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
	
	public static void addEcoleToBdd(String nom) {
		
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
	
	public static void addPromoToBdd(int firstJoin, String filiere, String nomEcole) {
		
		
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
	
	

}
