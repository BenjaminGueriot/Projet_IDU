package fr.usmb.projetidu.interfaceGraphique.pages;

import java.util.HashMap;
import java.util.List;

import fr.usmb.projetidu.Enseignement.UE;
import fr.usmb.projetidu.Enseignement.Module.Module;
import fr.usmb.projetidu.Enseignement.Module.Travail;
import fr.usmb.projetidu.Personne.Eleve;
import fr.usmb.projetidu.Personne.Enseignant;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class NoteModule extends Parent {
	  
    public NoteModule(Stage primaryStage,Eleve eleve) { 
    	
    	GridPane gridGeneral = new GridPane();
    	gridGeneral.setAlignment(Pos.TOP_LEFT);
    	gridGeneral.setHgap(10);
    	gridGeneral.setVgap(10);
    	gridGeneral.setPadding(new Insets(10, 10, 10, 10));
    	gridGeneral.setPrefHeight(700);
    	gridGeneral.setPrefWidth(1000);
        
        GridPane gridMenu = new GridPane();
        gridMenu.setAlignment(Pos.TOP_LEFT);
        gridMenu.setHgap(10);
        gridMenu.setPrefHeight(30);
        
        
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
        	AccueilEleve accueilEleve = new AccueilEleve(primaryStage,eleve);
            Scene scene = new Scene(accueilEleve);
            scene.getStylesheets().add(getClass().getResource("/accueil.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        // Add the back button to the top left corner of the root node
        gridMenu.add(backButton,0,0);
        gridMenu.setAlignment(Pos.TOP_LEFT);
        
        List<UE> ues = eleve.getPromo().getFiliere().getListe_ue();
        
        ChoiceBox<Integer> ueChoiceBox = new ChoiceBox<>();
        
        for(UE ue: ues) {
        	if(!ueChoiceBox.getItems().contains(ue.getSemester())){
        		ueChoiceBox.getItems().add(ue.getSemester());
        	}
        }
        
        gridMenu.add(ueChoiceBox,1,0);
        gridGeneral.add(gridMenu,0,0);
        
        Separator topseparator = new Separator();
        topseparator.setHalignment(HPos.CENTER);
        gridGeneral.add(topseparator, 0, 1);
        
        ueChoiceBox.setOnAction((event) -> {
        	int count = 1;
        	
        	this.getChildren().remove(gridGeneral);
        	
        	GridPane gridParent = new GridPane();
        	gridParent.setAlignment(Pos.TOP_LEFT);
        	gridParent.setHgap(10);
            gridParent.setVgap(10);
            gridParent.setPadding(new Insets(10, 10, 10, 10));
            gridParent.setPrefHeight(600);
            gridParent.setPrefWidth(1000);
        	
            for(UE ue: ues) {
            	
            	if(ue.getSemester().equals(ueChoiceBox.getSelectionModel().getSelectedItem())) {
            		
            		Text UEText = new Text(ue.getCode() + " | " + ue.getNom().toUpperCase());
                	UEText.setId("UEText");
                	UEText.setStyle("-fx-font-weight: bold;");
                	UEText.setTextAlignment(TextAlignment.CENTER);
                    gridParent.add(UEText, 0, count, 1, 1);
                    Text MoyenneUEText = new Text("Moyenne de l'UE: " + eleve.getMeanOfUe(ue));
                    MoyenneUEText.setId("MoyenneUEText");
                    gridParent.add(MoyenneUEText, 0, count+1, 1, 1);
                    count += 2;
                    
                    Separator separatorUE = new Separator();
                    separatorUE.setHalignment(HPos.CENTER);
                    separatorUE.setId("SeparatorId");
                    gridParent.add(separatorUE, 0, count, 1, 1);
                    count += 1;
                	
                	List<Module> modules = ue.getModules();
                	
        	        for (Module module : modules) {
        				
        	        	// Ajout Nom Prenom
        	            Text ModuleText = new Text(module.getCode() + " | " + module.getNom());
        	            ModuleText.setId("ModuleText");
        	            gridParent.add(ModuleText, 0, count, 1, 1);
        	            
        	            int progressValue = module.getAvancement();
        	            ProgressBar progressBar = new ProgressBar();
        	            progressBar.setProgress(progressValue / 100.0);
        	            gridParent.add(progressBar, 5, count+0, 1, 1);
        	            
        	            HashMap<Travail, double[]> travaux = eleve.getInfosFromModule(module);
        	            
        	            for (Travail travail : travaux.keySet()) {
        	            	
        	            	GridPane GridTravail = new GridPane();
        	            	GridTravail.setAlignment(Pos.BASELINE_LEFT);
        	            	GridTravail.setPrefHeight(20);
        	            	
        	            	
        	            	Text travailText = new Text(travail.getNom() + ": " + travaux.get(travail)[0]);
        	            	travailText.setId("TravailText");
        	            	GridTravail.add(travailText, 0, 0);
        	            	//GridTravail.add();
        	            	gridParent.add(GridTravail, 0, count+3, 1, 1);
        	            	
        	            	Text coefText = new Text("coefficient: " + travaux.get(travail)[1]);
        	            	coefText.setId("CoefText");
        	            	gridParent.add(coefText, 5, count+3, 1, 1);
        	            	count+=1;
        	    		}
        	            
        	            Text MoyenneText = new Text("Moyenne du module: " + eleve.getMeanOfModule(module));
        	            MoyenneText.setId("MoyenneText");
        	        	gridParent.add(MoyenneText, 0, count+3, 1, 1);
        	            
        	            
        	            
        	            Separator separator = new Separator();
        	            separator.setHalignment(HPos.CENTER);
        	            gridParent.add(separator, 0, count+4, 1, 1);
        	            
        	            count += 5;
        			}
            	}
            }
            
            ScrollPane scroller = new ScrollPane(gridParent);
            scroller.setFitToWidth(true);
            gridGeneral.add(scroller, 0, 1);
            
            this.getChildren().add(gridGeneral);
        });
        
        

        // Add the scroll pane to the root node
        this.getChildren().add(gridGeneral);
        
    }
}
