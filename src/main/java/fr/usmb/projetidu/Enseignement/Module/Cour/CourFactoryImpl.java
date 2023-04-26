package fr.usmb.projetidu.Enseignement.Module.Cour;

import java.util.Date;

import fr.usmb.projetidu.Enseignement.Module.Module;

public class CourFactoryImpl implements CourFactory {

	@Override
	public Cour createCour(CourEnum cour_enum, int num_seance, Date date, double heure_debut, double duree, Module module) {
		
		Cour cour = null;
		switch (cour_enum) {
		case CM:
			cour = new CM(num_seance, date, heure_debut, duree, module, cour_enum);
			break;
			
		case TD:
			cour = new TD(num_seance, date, heure_debut, duree, module, cour_enum);
			break;
			
		case TP:
			cour = new TP(num_seance, date, heure_debut, duree, module, cour_enum);
			break;
		case EXAM:
			cour = new EXAM(num_seance, date, heure_debut, duree, module, cour_enum);
			break;
		case SPECIAL:
			cour = new SPECIAL(num_seance, date, heure_debut, duree, module, cour_enum);
			break;

		default:
			throw new RuntimeException( "Format de cours incorrect" );
		}
		
		
		return cour;
	}

	
	
}
