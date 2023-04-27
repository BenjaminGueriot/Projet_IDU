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
import fr.usmb.projetidu.Enseignement.Module.Cour.Cour;
import fr.usmb.projetidu.Enseignement.Module.Cour.CourEnum;
import fr.usmb.projetidu.Enseignement.Module.Cour.CourFactoryImpl;
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
        
        ChoiceBox<Integer> heureChoiceBox = new ChoiceBox<>();
        heureChoiceBox.setVisible(false);
        ChoiceBox<Integer> minChoiceBox = new ChoiceBox<>();
        minChoiceBox.setVisible(false);
        ChoiceBox<Integer> dureeHeureChoiceBox = new ChoiceBox<>();
        dureeHeureChoiceBox.setVisible(false);
        ChoiceBox<Integer> dureeMinChoiceBox = new ChoiceBox<>();
        dureeMinChoiceBox.setVisible(false);
        
        for(int i = 8; i < 20; i++) {
        	heureChoiceBox.getItems().add(i);
        }
        for(int i = 0; i <= 12; i++) {
        	dureeHeureChoiceBox.getItems().add(i);
        }
        for(int i = 0; i <= 45; i+= 15) {
        	minChoiceBox.getItems().add(i);
        	dureeMinChoiceBox.getItems().add(i);
        }
        
        Text dateText = new Text("Date : ");
        dateText.setId("DateText");
        dateText.setVisible(false);
        
        DatePicker datePicker = new DatePicker();
        datePicker.setVisible(false);
        datePicker.setOnAction(e -> {
        	ZoneId defaultZoneId = ZoneId.systemDefault();
        	currentDateTravail = Date.from(datePicker.getValue().atStartOfDay(defaultZoneId).toInstant());
        });
        
        GridPane gridDate = new GridPane();
        gridDate.setAlignment(Pos.BASELINE_LEFT);
        gridDate.setPrefHeight(20);
        
    	
        gridDate.add(dateText, 0, 1, 1, 1);
        gridDate.add(datePicker, 1, 1, 1, 1);
    	
    	gridParent2.add(gridDate, 0,1);
        
        Separator separatorDate = new Separator();
        separatorDate.setHalignment(HPos.CENTER);
        separatorDate.setId("SeparatorId");
        gridParent2.add(separatorDate, 0, 2, 1, 1);
        separatorDate.setVisible(false);
        
        Text nomHeureText = new Text("Heure de début : ");
        nomHeureText.setId("NomText");
        nomHeureText.setVisible(false);
        
        Text separatorHeure = new Text(" : ");
        separatorHeure.setVisible(false);
        
        GridPane GridHeure = new GridPane();
        GridHeure.setAlignment(Pos.BASELINE_LEFT);
    	GridHeure.setPrefHeight(20);
    	
    	GridHeure.add(nomHeureText, 0, 0);
    	GridHeure.add(heureChoiceBox, 1, 0);
    	GridHeure.add(separatorHeure, 2, 0);
    	GridHeure.add(minChoiceBox, 3, 0);
    	
    	gridParent2.add(GridHeure, 0, 3, 1, 1);
        
        Separator separatorNom = new Separator();
        separatorNom.setHalignment(HPos.CENTER);
        separatorNom.setId("SeparatorId");
        gridParent2.add(separatorNom, 0, 4, 1, 1);
        separatorNom.setVisible(false);
        
        Text nomDureeText = new Text("Durée : ");
        nomDureeText.setId("NomText");
        nomDureeText.setVisible(false);
        
        Text separatorDuree = new Text(" : ");
        separatorDuree.setVisible(false);
        
        GridPane GridDuree = new GridPane();
        GridDuree.setAlignment(Pos.BASELINE_LEFT);
        GridDuree.setPrefHeight(20);
    	
        GridDuree.add(nomDureeText, 0, 0);
        GridDuree.add(dureeHeureChoiceBox, 1, 0);
        GridDuree.add(separatorDuree, 2, 0);
        GridDuree.add(dureeMinChoiceBox, 3, 0);
    	
    	gridParent2.add(GridDuree, 0, 5, 1, 1);
        
        ArrayList<Node> listNode = new ArrayList<>(Arrays.asList(nomHeureText, nomDureeText, separatorNom, dateText, datePicker, separatorDate,
        		dureeMinChoiceBox, dureeHeureChoiceBox, minChoiceBox, heureChoiceBox, separatorHeure, separatorDuree));
        
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
        
        Button validateButton = new Button("Ajouter Cour");
        validateButton.setOnAction(event -> {
        	
        	
        	if(this.currentUE != null && this.currentModule != null) {
        		UE validateUE = this.currentUE;
            	
            	Module validateModule = this.currentModule;
            		
        		int heure = heureChoiceBox.getValue();
            	int min = minChoiceBox.getValue();
            	
            	int heureDuree = dureeHeureChoiceBox.getValue();
            	int minDuree = dureeMinChoiceBox.getValue();
        		
            	double minTot = 0;
            	if(min == 15) {
            		minTot = 0.25;
            	}
            	else if(min == 30) {
            		minTot = 0.50;
            	}
            	else if(min == 45) {
            		minTot = 0.75;
            	}
            	
            	double minDureeTot = 0;
            	if(minDuree == 15) {
            		minDureeTot = 0.25;
            	}
            	else if(minDuree == 30) {
            		minDureeTot = 0.50;
            	}
            	else if(minDuree == 45) {
            		minDureeTot = 0.75;
            	}
            	
            	Double heureTot = heure + minTot;
            	Double dureeTot = heureDuree + minDureeTot;
            	
        		//Ajout de la note a la bdd
            	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
                String travailDate = formatter.format(currentDateTravail);
                
                CourFactoryImpl factory = new CourFactoryImpl();
                currentModule.addCour(factory.createCour(CourEnum.SPECIAL, 1, currentDateTravail, heureTot, dureeTot, currentModule));
                
                DatabaseRequests.addCourPerso2Bdd(eleve.getId(), currentModule.getCode(), travailDate, heureTot, dureeTot);
        	
                AccueilEleve.accueilSender(primaryStage,eleve);
            	
    		}
    		else {
    			new ErrorPopup("Un ou plusieurs champs sont manquants.");
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

