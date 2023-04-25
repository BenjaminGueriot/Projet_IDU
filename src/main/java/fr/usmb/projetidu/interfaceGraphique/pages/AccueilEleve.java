package fr.usmb.projetidu.interfaceGraphique.pages;

import java.util.Date;
import java.util.HashMap;

import fr.usmb.projetidu.Enseignement.Module.Travail;
import fr.usmb.projetidu.Personne.Eleve;
import fr.usmb.projetidu.utils.Initialize;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class AccueilEleve extends Parent {

	  
    public AccueilEleve(Stage primaryStage,Eleve eleve) {
    	
    	GridPane gridParent = new GridPane();
    	gridParent.setAlignment(Pos.CENTER);
    	gridParent.setHgap(10);
        gridParent.setVgap(10);
        gridParent.setPadding(new Insets(25, 25, 25, 25));
        
        String nom = eleve.getNom();
        String prenom = eleve.getPrenom();
        String filiere = eleve.getPromo().getFiliere().getNom();
        
        // Ajout Nom Prenom
        Text NomPrenomText = new Text(nom.toUpperCase() + " " + prenom + "   " + filiere);
        NomPrenomText.setId("NomPrenomText");
        gridParent.add(NomPrenomText, 0, 0, 1, 1);
        
        NomPrenomText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Load the next page
                try {
                	CompteEleve compteEleve = new CompteEleve(primaryStage,eleve);
                    Scene scene = new Scene(compteEleve);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Ajout Lien Module
        Text PlanningText = new Text("Planning");
        PlanningText.setId("PlanningText");
        gridParent.add(PlanningText, 5, 0, 1, 1);
        
        PlanningText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	try {

                	Calendar c = new Calendar(primaryStage,eleve);
                	
                    Scene scene = new Scene(c);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        Button decoButton = new Button("Déconnexion");
        decoButton.setOnAction(event -> {
        	Initialize.resetObject(eleve);
        	
        	LoginForm loginForm = new LoginForm(primaryStage);
    		// Add a style sheet to the scene
            Scene scene = new Scene(loginForm, 290, 220);
            scene.getStylesheets().add(getClass().getResource("/login.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        });
        
        decoButton.setAlignment(Pos.BOTTOM_RIGHT);
        gridParent.add(decoButton, 20, 0, 1, 1);
        

        // Ajout Lien Module
        Text lienNoteText = new Text("Mes Notes");
        lienNoteText.setId("LienNoteText");
        gridParent.add(lienNoteText, 10, 0, 1, 1);
        
        lienNoteText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                	NoteModule noteModule = new NoteModule(primaryStage,eleve);
                    Scene scene = new Scene(noteModule);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Ajout Lien Module
        Text moduleInfoText = new Text("Modules");
        moduleInfoText.setId("ModuleInfoText");
        gridParent.add(moduleInfoText, 15, 0, 1, 1);
        
        moduleInfoText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                	InfoModule infoModule = new InfoModule(primaryStage,eleve);
                    Scene scene = new Scene(infoModule,800,500);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        
        GridPane MoyenneGrid = new GridPane();
        MoyenneGrid.setId("MoyenneGrid");
        // Ajout Semaine
        Text MoyenneText = new Text("Moyenne genérale : ");
        MoyenneText.setId("MoyenneText");
        Text MoyenneNote = new Text("" + eleve.getGlobalMean());
        
        
        if(eleve.getGlobalMean() < 10.0) {
        	MoyenneNote.setStyle("-fx-font-size: 15px; -fx-fill: red;");
        }
        else if(eleve.getGlobalMean() < 13.0){
        	MoyenneNote.setStyle("-fx-font-size: 15px; -fx-fill: orange;");
        }
        else {
        	MoyenneNote.setStyle("-fx-font-size: 15px; -fx-fill: green;");
        }
        
        MoyenneGrid.add(MoyenneText, 0, 0);
        MoyenneGrid.add(MoyenneNote, 1, 0);
        gridParent.add(MoyenneGrid, 5, 7, 1, 1);
        
  
        Button addNoteButton = new Button("Ajouter une note");
        addNoteButton.setOnAction(event -> {
        	
        	NoteForm noteForm = new NoteForm(primaryStage,eleve);
            Scene scene = new Scene(noteForm,800,500);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
        
        addNoteButton.setId("addNoteButton");
        gridParent.add(addNoteButton, 10, 7, 1, 1);
        
        // Create the child grid panes
        GridPane childGrid1 = new GridPane();
        childGrid1.setHgap(10);
        childGrid1.setVgap(10);
        childGrid1.setPadding(new Insets(10, 10, 10, 10));
        
        HashMap<Travail, Date> rendu = eleve.getNextTravaux();
        
        int countrendu = 0;
        for (Travail travail : rendu.keySet()) {
        	Text noteText = new Text("[" + travail.getModule().getNom() + "] " + travail.getNom() + " -> Date : " + rendu.get(travail));
        	noteText.setId("RenduText");
        	childGrid1.add(noteText, 0, countrendu, 1, 1);
    
        	countrendu+=1;
		}
        
        GridPane childGrid2 = new GridPane();
        childGrid2.setHgap(10);
        childGrid2.setVgap(10);
        childGrid2.setPadding(new Insets(10, 10, 10, 10));
        
        int[] charge = eleve.getCharged();
        // Ajout Semaine
        Text SemaineText = new Text("Semaine " + charge[0] +" : ");
        SemaineText.setId("SemaineText");
        childGrid2.add(SemaineText, 0, 0, 1, 1);
        
        
        GridPane SemaineGrid = new GridPane();
        SemaineGrid.setId("SemaineGrid");
        
        
        Text gap = new Text("            ");
        SemaineGrid.add(gap, 0, 0, 1, 1);
        
        switch (charge[1]) {
			case 1:
				SemaineGrid.setStyle("-fx-background-color: #19bf1e; -fx-border-color: black; -fx-border-width: 1;");
				break;
			case 2:
				SemaineGrid.setStyle("-fx-background-color: #e3a214; -fx-border-color: black; -fx-border-width: 1;");
				break;
			case 3:
				SemaineGrid.setStyle("-fx-background-color: #ab0e0e; -fx-border-color: black; -fx-border-width: 1;");
				break;
	
			default:
				break;
		}
        
        childGrid2.add(SemaineGrid, 1, 0, 1, 1);
        GridPane childGrid3 = new GridPane();
        childGrid3.setHgap(10);
        childGrid3.setVgap(10);
        childGrid3.setPadding(new Insets(10, 10, 10, 10));
        
        HashMap<Travail, double[]> lastNotes = eleve.getLastNotes();
        
        int count = 0;
        for (Travail travail : lastNotes.keySet()) {
        	Text noteText = new Text("[" + travail.getModule().getNom() + "] " + travail.getNom() + " -> Note : " + lastNotes.get(travail)[0] +" | Coef : " + lastNotes.get(travail)[1]);
        	noteText.setId("NoteText");
        	childGrid3.add(noteText, 0, count, 1, 1);
    
        	count+=1;
		}
        
        
        
        
        gridParent.setId("parent-grid-pane");
        childGrid1.getStyleClass().add("grid-pane");
        childGrid2.getStyleClass().add("grid-pane");
        childGrid3.getStyleClass().add("grid-pane");
        
        // Add the child grid panes to the parent grid pane
        Text titreGrid1 = new Text("Prochaine échéance");
        titreGrid1.setId("titre-grid");
        Text titreGrid2 = new Text("Niveau de charge");
        titreGrid2.setId("titre-grid");
        Text titreGrid3 = new Text("Dernières notes");
        titreGrid3.setId("titre-grid");
        
        gridParent.add(titreGrid1, 0, 4, 1, 1);
        gridParent.add(titreGrid2, 5, 4, 1, 1);
        gridParent.add(titreGrid3, 10, 4, 1, 1);
        
        gridParent.add(childGrid1, 0, 5, 1, 1);
        gridParent.add(childGrid2, 5, 5, 1, 1);
        gridParent.add(childGrid3, 10, 5, 1, 1);
        

        // Add the grid pane to the root node
        this.getChildren().add(gridParent);
    }
    
    public static void accueilSender(Stage primaryStage,Eleve eleve) {
    	AccueilEleve accueilEleve = new AccueilEleve(primaryStage,eleve);
        Scene scene = new Scene(accueilEleve);
        scene.getStylesheets().add("accueil.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
