package fr.usmb.projetidu.interfaceGraphique.pages;

import java.awt.TextArea;
import java.util.HashMap;
import java.util.List;
import java.util.function.UnaryOperator;

import fr.usmb.projetidu.Enseignement.UE;
import fr.usmb.projetidu.Enseignement.Module.Module;
import fr.usmb.projetidu.Enseignement.Module.Travail;
import fr.usmb.projetidu.Personne.Eleve;
import fr.usmb.projetidu.Personne.Enseignant;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class NoteForm extends Parent {
	  
    public NoteForm(Stage primaryStage,Eleve eleve) { 
    	
    	GridPane gridGeneral = new GridPane();
    	gridGeneral.setAlignment(Pos.CENTER);
    	gridGeneral.setHgap(10);
    	gridGeneral.setVgap(10);
    	gridGeneral.setPadding(new Insets(10, 10, 10, 10));
    	gridGeneral.setPrefHeight(700);
    	
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
        
        HashMap<Travail,String> travail2Nom = new HashMap<>();
        ChoiceBox<String> travailChoiceBox = new ChoiceBox<>();
        
        
        for(UE ue: ues) {
        	ueChoiceBox.getItems().add(ue.getNom());
        	ue2Nom.put(ue, ue.getNom());
        }
        
        Text ueText = new Text("UE : ");
        ueText.setId("UEText");
        	
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
	        		
	        		List<Module> modules = currentUE.getModules();
	    	        
	    	        for(Module module: modules) {
	    	        	moduleChoiceBox.getItems().add(module.getNom());
	    	        	module2Nom.put(module, module.getNom());
	    	        }
	        	}
	        }
	        
        });
        
        Text modeleText = new Text("Modele : ");
        modeleText.setId("ModeleText");
        	
        gridParent.add(moduleChoiceBox, 0, count, 1, 1);
        count += 1;
        
        Separator separatorModule = new Separator();
        separatorModule.setHalignment(HPos.CENTER);
        separatorModule.setId("SeparatorId");
        gridParent.add(separatorModule, 0, count, 1, 1);
        count += 1;
        
        moduleChoiceBox.setOnAction((event) -> {
        	travailChoiceBox.getItems().clear();
            int selectedIndex = moduleChoiceBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = moduleChoiceBox.getSelectionModel().getSelectedItem();
            Module currentModule = null;
            
            for(Module module: module2Nom.keySet()) {
	        	if(module.getNom().equals(moduleChoiceBox.getValue())){
	        		currentModule = module;
	        		
	        		List<Travail> travaux = currentModule.getTravaux();
	    	        
	    	        for(Travail travail: travaux) {
	    	        	travailChoiceBox.getItems().add(travail.getNom());
	    	        	travail2Nom.put(travail, travail.getNom());
	    	        }
	        	}
	        }
	        
        });
        
        Text travailText = new Text("Modele : ");
        travailText.setId("ModeleText");
        	
        gridParent.add(travailChoiceBox, 0, count, 1, 1);
        count += 1;
        
        Separator travailModule = new Separator();
        travailModule.setHalignment(HPos.CENTER);
        travailModule.setId("SeparatorId");
        gridParent.add(travailModule, 0, count, 1, 1);
        count += 1;
        
        Text noteText = new Text("Note : ");
        noteText.setId("NoteText");
        
        TextField noteField = new TextField();
        noteField.setId("NoteField");
        
        Text coefText = new Text("Coefficient : ");
        coefText.setId("CoefText");
        
        TextField coefField = new TextField();
        coefField.setId("NoteField");
        
        gridParent.add(noteText, 0, count, 1, 1);
        gridParent.add(noteField, 1, count, 1, 1);
        count += 1;
        
        Separator separatorNote = new Separator();
        separatorNote.setHalignment(HPos.CENTER);
        separatorNote.setId("SeparatorId");
        gridParent.add(separatorNote, 0, count, 1, 1);
        count += 1;
        
        gridParent.add(coefText, 0, count, 1, 1);
        gridParent.add(coefField, 1, count, 1, 1);
        count += 1;
        
        noteField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                	noteField.setText(newValue.replaceAll("[^[0-9.]]", ""));
                }
            } 
        });
        
        coefField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                	noteField.setText(newValue.replaceAll("[^[0-9.]]", ""));
                }
            }
        });
        
        gridGeneral.add(gridParent, 0, 0);
        
        GridPane gridParent2 = new GridPane();
        gridParent2.setAlignment(Pos.CENTER);
        gridParent2.setHgap(10);
        gridParent2.setVgap(10);
        gridParent2.setPadding(new Insets(10, 10, 10, 10));
        gridParent2.setPrefHeight(700);
        
        Text nomText = new Text("Note : ");
        nomText.setId("NoteText");
        
        TextField nomField = new TextField();
        nomField.setId("NoteField");
        
        Text sujetText = new Text("Coefficient : ");
        sujetText.setId("CoefText");
        
        TextField sujetField = new TextField();
        sujetField.setId("NoteField");
        
        gridParent2.add(nomText, 0, 0, 1, 1);
        gridParent2.add(nomField, 1, 0, 1, 1);
        count += 1;
        
        Separator separatorNom = new Separator();
        separatorNom.setHalignment(HPos.CENTER);
        separatorNom.setId("SeparatorId");
        gridParent2.add(separatorNom, 0, 1, 1, 1);
        count += 1;
        
        gridParent2.add(sujetText, 0, 2, 1, 1);
        gridParent2.add(sujetField, 1, 2, 1, 1);
        count += 1;
        
        //gridParent2.add(, 0, 0, 0, 0)
        
        gridGeneral.add(gridParent2, 1, 0);
        
        
        

        // Add the scroll pane to the root node
        this.getChildren().add(gridGeneral);
        
    }
}

