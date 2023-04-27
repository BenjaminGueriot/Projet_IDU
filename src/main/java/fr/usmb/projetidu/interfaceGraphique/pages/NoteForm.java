package fr.usmb.projetidu.interfaceGraphique.pages;

import java.text.SimpleDateFormat;
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
import fr.usmb.projetidu.utils.DatabaseRequests;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
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
public class NoteForm extends Parent {
	
	private UE currentUE = null;
	private Module currentModule = null;
	private Travail currentTravail = null;
	private Boolean isTravail = null;
	private Boolean isAfter = null;
	private Date currentDateTravail = null;
	private Enseignant enseignantTravail = null;
	  
    public NoteForm(Stage primaryStage,Eleve eleve) { 
    	
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
        	AccueilEleve.accueilSender(primaryStage,eleve);
        });

        // Add the back button to the top left corner of the node
        gridGeneral.add(backButton,0,0);
        gridGeneral.setAlignment(Pos.TOP_LEFT);
        
        int count = 2;
        
        List<UE> ues = eleve.getPromo().getFiliere().getListe_ue();
        HashMap<UE,String> ue2Nom = new HashMap<>();
        ChoiceBox<String> ueChoiceBox = new ChoiceBox<>();
        
        HashMap<Module,String> module2Nom = new HashMap<>();
        ChoiceBox<String> moduleChoiceBox = new ChoiceBox<>();
        
        
        HashMap<Travail,String> travail2Nom = new HashMap<>();
        ChoiceBox<String> travailChoiceBox = new ChoiceBox<>();
        
        HashMap<Enseignant,String> enseignant2Nom = new HashMap<>();
        ChoiceBox<String> enseignantChoiceBox = new ChoiceBox<>();
        enseignantChoiceBox.setVisible(false);
        
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
        
        ArrayList<Node> listNode1 = new ArrayList<>(Arrays.asList(noteText, noteField, coefText, coefField, separatorNote));
        
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
                	coefField.setText(newValue.replaceAll("[^[0-9.]]", ""));
                }
            } 
        });
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setContent(gridParent);
        gridGeneral.add(scrollPane,0,1);
        
        
        Text nomText = new Text("Nom du travail : ");
        nomText.setId("NomText");
        nomText.setVisible(false);
        
        TextField nomField = new TextField();
        nomField.setId("NomField");
        nomField.setVisible(false);
        
        Text sujetText = new Text("Sujet : ");
        sujetText.setId("SujetText");
        sujetText.setVisible(false);
        
        TextField sujetField = new TextField();
        sujetField.setId("SujetField");
        sujetField.setVisible(false);
        
        gridParent2.add(nomText, 0, 0, 1, 1);
        gridParent2.add(nomField, 1, 0, 1, 1);
        
        Separator separatorNom = new Separator();
        separatorNom.setHalignment(HPos.CENTER);
        separatorNom.setId("SeparatorId");
        gridParent2.add(separatorNom, 0, 1, 1, 1);
        separatorNom.setVisible(false);
        
        gridParent2.add(sujetText, 0, 2, 1, 1);
        gridParent2.add(sujetField, 1, 2, 1, 1);
        
        Separator separatorSujet = new Separator();
        separatorSujet.setHalignment(HPos.CENTER);
        separatorSujet.setId("SeparatorId");
        gridParent2.add(separatorSujet, 0, 3, 1, 1);
        separatorSujet.setVisible(false);
        
        Text enseignantText = new Text("Enseignant : ");
        enseignantText.setId("EnseignantText");
        enseignantText.setVisible(false);
        
        gridParent2.add(enseignantText, 0, 4, 1, 1);
        gridParent2.add(enseignantChoiceBox, 1, 4, 1, 1);
        
        Separator separatorEnseignant = new Separator();
        separatorEnseignant.setHalignment(HPos.CENTER);
        separatorEnseignant.setId("SeparatorId");
        gridParent2.add(separatorEnseignant, 0, 5, 1, 1);
        separatorEnseignant.setVisible(false);
        
        Text dateText = new Text("Date : ");
        dateText.setId("DateText");
        dateText.setVisible(false);
        
        DatePicker datePicker = new DatePicker();
        datePicker.setVisible(false);
        datePicker.setOnAction(e -> {
        	ZoneId defaultZoneId = ZoneId.systemDefault();
        	currentDateTravail = Date.from(datePicker.getValue().atStartOfDay(defaultZoneId).toInstant());
        	
        	if(new Date().getTime() < currentDateTravail.getTime()) {
        		isAfter = true;
        		setAllVisible(listNode1, false);
        	}
        	else {
        		isAfter = false;
        		setAllVisible(listNode1, true);
        	}
        });
        
        gridParent2.add(dateText, 0, 6, 1, 1);
        gridParent2.add(datePicker, 1, 6, 1, 1);
        
        ArrayList<Node> listNode = new ArrayList<>(Arrays.asList(nomText, nomField, sujetText, sujetField, separatorNom, separatorSujet, enseignantText, enseignantChoiceBox, separatorEnseignant, dateText, datePicker));
        
        ueChoiceBox.setOnAction((event) -> {
        	moduleChoiceBox.getItems().clear();
        	travailChoiceBox.getItems().clear();
        	enseignantChoiceBox.getItems().clear();
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
        	travailChoiceBox.getItems().clear();
        	enseignantChoiceBox.getItems().clear();
        	setAllVisible(listNode,false);
        	travailChoiceBox.getItems().add("Saisir un travail");
            int selectedIndex = moduleChoiceBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = moduleChoiceBox.getSelectionModel().getSelectedItem();
            
            for(Module module: module2Nom.keySet()) {
	        	if(module.getCode().equals(moduleChoiceBox.getValue())){
	        		currentModule = module;
	        		
	        		List<Travail> travaux = currentModule.getTravaux();
	    	        
	    	        for(Travail travail: travaux) {
	    	        	if(!eleve.getInformations().get(module).keySet().contains(travail) && new Date().getTime() > travail.getDate().getTime()) {
	    	        		travailChoiceBox.getItems().add(travail.getNom());
		    	        	travail2Nom.put(travail, travail.getNom());
	    	        	}
	    	        }
	        	}
	        }
	        
        });
        
        travailChoiceBox.setOnAction((event) -> {
        	enseignantChoiceBox.getItems().clear();
            int selectedIndex = travailChoiceBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = travailChoiceBox.getSelectionModel().getSelectedItem();
            String currentTravailString = travailChoiceBox.getValue();
            
            	if(currentTravailString != null) {
            		if(currentTravailString.equals("Saisir un travail")){
            			isTravail = false;
            			setAllVisible(listNode,true);
            			
            			List<Enseignant> enseignants = currentModule.getEnseignants();
            	        
            	        for(Enseignant enseignant : enseignants) {
            	        	enseignantChoiceBox.getItems().add(enseignant.getNom());
            	        	enseignant2Nom.put(enseignant, enseignant.getNom());
            	        }
		        	}
		        	else {
		        		setAllVisible(listNode,false);
		        		isTravail = true;
		        		for(Travail travail : travail2Nom.keySet()) {
		        			if(travail.getNom().equals(currentTravailString)){
		        				currentTravail = travail;
		    	        	}
		        		}
		        	}
            	}	
        });
        
        Separator Vseparator = new Separator(Orientation.VERTICAL);
        Vseparator.setHalignment(HPos.CENTER);
        gridGeneral.add(Vseparator, 1, 1);
        
        gridGeneral.add(gridParent2,2,1);
        
        Button validateButton = new Button("Ajouter Note");
        validateButton.setOnAction(event -> {
        	
        	
        	if(this.currentUE != null && this.currentModule != null && this.isTravail != null) {
        		UE validateUE = this.currentUE;
            	
            	Module validateModule = this.currentModule;
            	
            	
            	
            	if(this.isTravail && !noteField.getText().isBlank() && !coefField.getText().isBlank()) {
            		
            		Double note = Double.parseDouble(noteField.getText());
                	Double coef = Double.parseDouble(coefField.getText());
                	double[] infos = new double[]{note, coef};
            		
            		Travail validateTravail = this.currentTravail;
            		
            		// Ajout de la note au travail
                    eleve.getInformations().get(currentModule).put(validateTravail, infos);
            		
            		//Ajout de la note a la bdd
                    DatabaseRequests.addNote2Bdd(eleve.getId(), currentModule.getCode(), currentTravail.getNom(), note, coef);
            		
                    AccueilEleve.accueilSender(primaryStage,eleve);
            	}
            	else {
            		if(!this.isTravail && this.currentDateTravail != null && !nomField.getText().isBlank() && !sujetField.getText().isBlank() && enseignantChoiceBox.getValue() != null) {
            			
            			int selectedIndex = enseignantChoiceBox.getSelectionModel().getSelectedIndex();
                        Object selectedItem = enseignantChoiceBox.getSelectionModel().getSelectedItem();
                		
                        for(Enseignant enseignant: enseignant2Nom.keySet()) {
            	        	if(enseignant.getNom().equals(enseignantChoiceBox.getValue())){
            	        		enseignantTravail = enseignant;
            	        	}
            	        }
            			            			
            			if(this.isAfter) {
                			
                			String nomTravail = nomField.getText();
                    		String sujetTravail = sujetField.getText();
                    		Date dateTravail = currentDateTravail;
                  
                    		// Création du travail
                            Travail newTravail = new Travail(nomTravail,sujetTravail,dateTravail,currentModule);
                            currentModule.addTravail(newTravail);
                            
                            //Ajout Travail bdd
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
                            String travailDate = formatter.format(dateTravail);                            
                            DatabaseRequests.addTravail2Bdd(nomTravail, sujetTravail, travailDate, currentModule.getCode(), enseignantTravail.getNom(), enseignantTravail.getPrenom());
                            
                            AccueilEleve.accueilSender(primaryStage,eleve);
            			}
            			else {
            				if(!noteField.getText().isBlank() && !coefField.getText().isBlank()) {
            					Double note = Double.parseDouble(noteField.getText());
                            	Double coef = Double.parseDouble(coefField.getText());
                            	double[] infos = new double[]{note, coef};
                    			
                    			String nomTravail = nomField.getText();
                        		String sujetTravail = sujetField.getText();
                        		Date dateTravail = currentDateTravail;

                        		// Création du travail et ajout de la note
                                Travail newTravail = new Travail(nomTravail,sujetTravail,dateTravail,currentModule);
                                currentModule.addTravail(newTravail);
                                
                                eleve.getInformations().get(currentModule).put(newTravail, infos);
                        		
                        		//Ajout du travail a la bdd
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
                                String travailDate = formatter.format(dateTravail);  
                                DatabaseRequests.addTravail2Bdd(nomTravail, sujetTravail, travailDate, currentModule.getCode(), enseignantTravail.getNom(), enseignantTravail.getPrenom());
                        		
                        		//Ajout de la note a la bdd
                                DatabaseRequests.addNote2Bdd(eleve.getId(), currentModule.getCode(), newTravail.getNom(), note, coef);
                                
                        		AccueilEleve.accueilSender(primaryStage,eleve);
            				}
            			}
            			
            			
                		
            		}
            		else {
            			ErrorPopup.display("Un ou plusieurs champs sont manquants.");
            		}
            	}
        	}
        	else {
        		ErrorPopup.display("Un ou plusieurs champs sont manquants.");
    		}
        	
        });
        
        validateButton.setAlignment(Pos.BOTTOM_RIGHT);
        gridGeneral.add(validateButton,3,2);
        
        this.getChildren().add(gridGeneral);
        
    }
    
    private void setAllVisible(ArrayList<Node> listNode,Boolean bool) {
    	
    	for(Node node : listNode) {
    		node.setVisible(bool);
    	}
    }
    
}

