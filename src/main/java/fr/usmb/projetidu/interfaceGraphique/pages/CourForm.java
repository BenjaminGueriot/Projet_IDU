package fr.usmb.projetidu.interfaceGraphique.pages;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import fr.usmb.projetidu.Enseignement.UE;
import fr.usmb.projetidu.Enseignement.Module.Module;
import fr.usmb.projetidu.Enseignement.Module.Travail;
import fr.usmb.projetidu.Personne.Eleve;
import fr.usmb.projetidu.Personne.Enseignant;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class CourForm extends Parent {
	
	private UE currentUE = null;
	private Module currentModule = null;
	private Travail currentTravail = null;
	private Boolean isTravail = null;
	private Boolean isAfter = null;
	private Date currentDateTravail = null;
	  
    public CourForm(Stage primaryStage,Eleve eleve) { 
    	
    	GridPane gridGeneral = new GridPane();
    	gridGeneral.setAlignment(Pos.CENTER);
    	gridGeneral.setHgap(10);
    	gridGeneral.setVgap(10);
    	gridGeneral.setPadding(new Insets(10, 10, 10, 10));
    	gridGeneral.setPrefHeight(700);
    	
    	GridPane gridParent = new GridPane();
    	gridParent.setAlignment(Pos.TOP_LEFT);
    	gridParent.setHgap(10);
        gridParent.setVgap(10);
        gridParent.setPadding(new Insets(10, 10, 10, 10));
        gridParent.setPrefHeight(400);
        
        GridPane gridParent2 = new GridPane();
        gridParent2.setAlignment(Pos.TOP_LEFT);
        gridParent2.setHgap(10);
        gridParent2.setVgap(10);
        gridParent2.setPadding(new Insets(10, 10, 10, 10));
        gridParent2.setPrefHeight(400);
        
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            
            Planning c = new Planning(primaryStage,eleve);
            Scene scene = new Scene(c);
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        // Add the back button to the top left corner of the root node
        gridGeneral.add(backButton,0,0);
        gridGeneral.setAlignment(Pos.TOP_LEFT);
        
        int count = 2;
        
        List<UE> ues = eleve.getPromo().getFiliere().getListe_ue();
        HashMap<UE,String> ue2Nom = new HashMap<>();
        ChoiceBox<String> ueChoiceBox = new ChoiceBox<>();
        
        HashMap<Module,String> module2Nom = new HashMap<>();
        ChoiceBox<String> moduleChoiceBox = new ChoiceBox<>();
        
        for(UE ue: ues) {
        	ueChoiceBox.getItems().add(ue.getCode());
        	ue2Nom.put(ue, ue.getCode());
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
        
        Text modeleText = new Text("Modele : ");
        modeleText.setId("ModeleText");
        	
        gridParent.add(moduleChoiceBox, 0, count, 1, 1);
        count += 1;
        
        Separator separatorModule = new Separator();
        separatorModule.setHalignment(HPos.CENTER);
        separatorModule.setId("SeparatorId");
        gridParent.add(separatorModule, 0, count, 1, 1);
        count += 1;
        
        
        /*------------------------------------------PARTIE 2---------------------------------------*/
        
        Text nomUEText = new Text("Nom du travail : ");
        nomUEText.setId("NomText");
        nomUEText.setVisible(false);
        
        Text nomText = new Text(" ");
        nomText.setId("NomText");
        nomText.setVisible(false);
        
        GridPane GridUE = new GridPane();
        GridUE.setAlignment(Pos.BASELINE_LEFT);
    	GridUE.setPrefHeight(20);
    	
    	GridUE.add(nomUEText, 0, 0);
    	GridUE.add(nomText, 1, 0);
    	
    	gridParent2.add(GridUE, 0, 0, 1, 1);
        
        Separator separatorNom = new Separator();
        separatorNom.setHalignment(HPos.CENTER);
        separatorNom.setId("SeparatorId");
        gridParent2.add(separatorNom, 0, 1, 1, 1);
        separatorNom.setVisible(false);
        
        Text sujetText = new Text("Sujet : ");
        sujetText.setId("SujetText");
        sujetText.setVisible(false);
        
        TextField sujetField = new TextField();
        sujetField.setId("SujetField");
        sujetField.setVisible(false);
        
        gridParent2.add(sujetText, 0, 2, 1, 1);
        gridParent2.add(sujetField, 1, 2, 1, 1);
        
        Separator separatorSujet = new Separator();
        separatorSujet.setHalignment(HPos.CENTER);
        separatorSujet.setId("SeparatorId");
        gridParent2.add(separatorSujet, 0, 3, 1, 1);
        separatorSujet.setVisible(false);
        
        Text dateText = new Text("Date : ");
        dateText.setId("DateText");
        dateText.setVisible(false);
        
        DatePicker datePicker = new DatePicker();
        datePicker.setVisible(false);
        datePicker.setOnAction(e -> {
        	ZoneId defaultZoneId = ZoneId.systemDefault();
        	currentDateTravail = Date.from(datePicker.getValue().atStartOfDay(defaultZoneId).toInstant());
        });
        
        gridParent2.add(dateText, 0, 6, 1, 1);
        gridParent2.add(datePicker, 1, 6, 1, 1);
        
        ArrayList<Node> listNode = new ArrayList<>(Arrays.asList(nomUEText, nomText, sujetText, sujetField, separatorNom, separatorSujet, dateText, datePicker));
        
        ueChoiceBox.setOnAction((event) -> {
        	moduleChoiceBox.getItems().clear();
        	setAllVisible(listNode,false);
            int selectedIndex = ueChoiceBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = ueChoiceBox.getSelectionModel().getSelectedItem();
            
            for(UE ue: ue2Nom.keySet()) {
	        	if(ue.getCode().equals(ueChoiceBox.getValue())){
	        		currentUE = ue;
	        		
	        		List<Module> modules = currentUE.getModules();
	    	        
	    	        for(Module module: modules) {
	    	        	moduleChoiceBox.getItems().add(module.getCode());
	    	        	module2Nom.put(module, module.getCode());
	    	        }
	        	}
	        }
	        
        });
        
        
        moduleChoiceBox.setOnAction((event) -> {
        	setAllVisible(listNode,true);
        	
            int selectedIndex = moduleChoiceBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = moduleChoiceBox.getSelectionModel().getSelectedItem();
            
            for(Module module: module2Nom.keySet()) {
	        	if(module.getCode().equals(moduleChoiceBox.getValue())){
	        		currentModule = module;
	        		
	        		
	        	}
	        }
	        
        });
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(gridParent);
        gridGeneral.add(scrollPane,0,1);
        
        Separator Vseparator = new Separator(Orientation.VERTICAL);
        Vseparator.setHalignment(HPos.CENTER);
        gridGeneral.add(Vseparator, 1, 1);
        
        gridGeneral.add(gridParent2,2,1);
        
        this.getChildren().add(gridGeneral);
        
    }
    
    private void setAllVisible(ArrayList<Node> listNode,Boolean bool) {
    	
    	for(Node node : listNode) {
    		node.setVisible(bool);
    	}
    }
}

