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
public class NoteForm extends Parent {
	  
    public NoteForm(Stage primaryStage,Eleve eleve) { 
    	
    	GridPane gridParent = new GridPane();
    	gridParent.setAlignment(Pos.CENTER);
    	gridParent.setHgap(10);
        gridParent.setVgap(10);
        gridParent.setPadding(new Insets(10, 10, 10, 10));
        gridParent.setPrefHeight(700);
        
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
        	AccueilEleve accueilEleve = new AccueilEleve(primaryStage,eleve);
            Scene scene = new Scene(accueilEleve);
            scene.getStylesheets().add(getClass().getResource("/accueil.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        // Add the back button to the top left corner of the root node
        gridParent.add(backButton,0,0);
        gridParent.setAlignment(Pos.TOP_LEFT);
        Separator topseparator = new Separator();
        topseparator.setHalignment(HPos.CENTER);
        gridParent.add(topseparator, 0, 1, 1, 1);
        
        int count = 2;
        
        List<UE> ues = eleve.getPromo().getFiliere().getListe_ue();
        HashMap<UE,String> ue2Nom = new HashMap<>();
        ChoiceBox<String> ueChoiceBox = new ChoiceBox<>();
        
        HashMap<Module,String> module2Nom = new HashMap<>();
        ChoiceBox<String> moduleChoiceBox = new ChoiceBox<>();
        
        
        for(UE ue: ues) {
        	ueChoiceBox.getItems().add(ue.getNom());
        	ue2Nom.put(ue, ue.getNom());
        }
        	
	        gridParent.add(ueChoiceBox, 0, count, 1, 1);
	        count += 1;
	        
	        Separator separatorUE = new Separator();
            separatorUE.setHalignment(HPos.CENTER);
            separatorUE.setId("SeparatorId");
            gridParent.add(separatorUE, 0, count, 1, 1);
            count += 1;
        
	        ueChoiceBox.setOnAction((event) -> {
	        	moduleChoiceBox.getItems().clear();
	            int selectedIndex = ueChoiceBox.getSelectionModel().getSelectedIndex();
	            Object selectedItem = ueChoiceBox.getSelectionModel().getSelectedItem();
	            UE currentUE = null;
	            
	            for(UE ue: ue2Nom.keySet()) {
		        	if(ue.getNom().equals(ueChoiceBox.getValue())){
		        		currentUE = ue;
		        	}
		        }
		        List<Module> modules = currentUE.getModules();
		        
		        for(Module module: modules) {
		        	moduleChoiceBox.getItems().add(module.getNom());
		        	module2Nom.put(module, module.getNom());
		        }
	        });
	        	
		        gridParent.add(moduleChoiceBox, 0, count, 1, 1);
		        count += 1;
		        
		        Separator separatorModule = new Separator();
		        separatorModule.setHalignment(HPos.CENTER);
		        separatorModule.setId("SeparatorId");
	            gridParent.add(separatorModule, 0, count, 1, 1);
	            count += 1;
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(gridParent);
        
        
        

        // Add the scroll pane to the root node
        this.getChildren().add(scrollPane);
        
    }
}

