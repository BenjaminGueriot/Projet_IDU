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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class InfoModule extends Parent {
	
	private UE currentUE = null;
	private Module currentModule = null;
	
	
	
    public InfoModule(Stage primaryStage,Eleve eleve) { 
    	
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
        gridParent2.setHgap(15);
        gridParent2.setVgap(10);
        gridParent2.setPadding(new Insets(10, 10, 10, 10));
        gridParent2.setPrefHeight(400);
        
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
        	AccueilEleve accueilEleve = new AccueilEleve(primaryStage,eleve);
            Scene scene = new Scene(accueilEleve);
            scene.getStylesheets().add(getClass().getResource("/accueil.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        });

        // Add the back button to the top left corner of the root node
        gridGeneral.add(backButton,0,0);
        gridGeneral.setAlignment(Pos.TOP_LEFT);
        
        int count = 0;
        
        List<UE> ues = eleve.getPromo().getFiliere().getListe_ue();
        HashMap<UE,String> ue2Nom = new HashMap<>();
        ChoiceBox<String> ueChoiceBox = new ChoiceBox<>();
        
        HashMap<Module,String> module2Nom = new HashMap<>();
        ChoiceBox<String> moduleChoiceBox = new ChoiceBox<>();
        
        for(UE ue: ues) {
        	ueChoiceBox.getItems().add(ue.getNom());
        	ue2Nom.put(ue, ue.getNom());
        }
        
        Text ueText = new Text("UE : ");
        ueText.setId("UEText");
        
        gridParent.add(ueText, 0, count, 1, 1);
        gridParent.add(ueChoiceBox, 0, count+1, 1, 1);
        count += 2;
        
        Separator separatorUE = new Separator();
        separatorUE.setHalignment(HPos.CENTER);
        separatorUE.setId("SeparatorId");
        gridParent.add(separatorUE, 0, count, 1, 1);
        count += 1;
        
        Text moduleText = new Text("Module : ");
        moduleText.setId("ModeleText");
        
        gridParent.add(moduleText, 0, count, 1, 1);
        gridParent.add(moduleChoiceBox, 0, count+1, 1, 1);
        count += 2;
        
        Separator separatorModule = new Separator();
        separatorModule.setHalignment(HPos.CENTER);
        separatorModule.setId("SeparatorId");
        gridParent.add(separatorModule, 0, count, 1, 1);
        count += 1;
        
        
        /*------------------------------------------PARTIE 2---------------------------------------*/
        
        Text nomModuleText = new Text("DATA832 : Machine Learning");
        nomModuleText.setId("SujetText");
        nomModuleText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: black;");
        nomModuleText.setVisible(false);
        
        GridPane GridModule = new GridPane();
        GridModule.setAlignment(Pos.BASELINE_LEFT);
        GridModule.setPrefHeight(20);
    	
        GridModule.add(nomModuleText, 0, 0);
    	
    	gridParent2.add(GridModule, 0, 0, 1, 1);
    	
    	Separator separatorNom = new Separator();
        separatorNom.setHalignment(HPos.CENTER);
        separatorNom.setId("SeparatorId");
        gridParent2.add(separatorNom, 0, 1, 1, 1);
        separatorNom.setVisible(false);
        
        Text labelUE = new Text("UE : ");
        labelUE.setId("UEText");
        labelUE.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: black;");
        labelUE.setVisible(false);
        
        Text ueDataText = new Text("UE803 : Données et Aide à la Décision");
        ueDataText.setId("NomText");
        ueDataText.setVisible(false);
        
        GridPane GridUE = new GridPane();
        GridUE.setAlignment(Pos.BASELINE_LEFT);
    	GridUE.setPrefHeight(20);
    	
    	GridUE.add(labelUE, 0, 0);
    	GridUE.add(ueDataText, 1, 0);
    	
    	gridParent2.add(GridUE, 0, 2, 1, 1);
        
        Text labelResponsable = new Text("Responsable : ");
        labelResponsable.setId("labelResponsable");
        labelResponsable.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: black;");
        labelResponsable.setVisible(false);
        
        Text responsableText = new Text("Marc-Philippe Huget" + "  ");
        responsableText.setId("responsableText");
        responsableText.setVisible(false);
        
        Text labelMail = new Text("Mail : ");
        labelMail.setId("labelMail");
        labelMail.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: black;");
        labelMail.setVisible(false);
        
        Text mailText = new Text("Marc-Philippe.Huget@univ-smb.fr");
        mailText.setId("mailText");
        mailText.setVisible(false);
        
        GridPane GridRespo = new GridPane();
        GridRespo.setAlignment(Pos.BASELINE_LEFT);
        GridRespo.setPrefHeight(20);
    	
        GridRespo.add(labelResponsable, 0, 0);
        GridRespo.add(responsableText, 1, 0);
        
        Separator separatorMail = new Separator(Orientation.VERTICAL);
        separatorMail.setHalignment(HPos.CENTER);
        GridRespo.add(separatorMail, 2, 0);
        separatorMail.setVisible(false);
    	
        GridRespo.add(labelMail, 3, 0);
        GridRespo.add(mailText, 4, 0);
        
    	gridParent2.add(GridRespo, 0, 3, 1, 1);
    	
    	Text labelHeure = new Text("Total Horaire (h) : ");
    	labelHeure.setId("labelHeure");
    	labelHeure.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: black;");
    	labelHeure.setVisible(false);
        
        Text heureText = new Text("40.5");
        heureText.setId("heureText");
        heureText.setVisible(false);
        
        GridPane GridHeure = new GridPane();
        GridHeure.setAlignment(Pos.BASELINE_LEFT);
        GridHeure.setPrefHeight(20);
    	
        GridHeure.add(labelHeure, 0, 0);
        GridHeure.add(heureText, 1, 0);
    	
    	gridParent2.add(GridHeure, 0, 4, 1, 1);
    	
    	Text labelEvaluation = new Text("Evaluation : ");
    	labelEvaluation.setId("labelNotation");
    	labelEvaluation.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: black;");
    	labelEvaluation.setVisible(false);
        
        Text evaluationText = new Text("CC" + "  ");
        evaluationText.setId("evaluationText");
        evaluationText.setVisible(false);
        
        Text labelCoef = new Text("Coefficient : ");
        labelCoef.setId("labelCoef");
        labelCoef.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: black;");
        labelCoef.setVisible(false);
        
        Text coefText = new Text("3");
        coefText.setId("mailText");
        coefText.setVisible(false);
        
        GridPane GridEvaluation = new GridPane();
        GridEvaluation.setAlignment(Pos.BASELINE_LEFT);
        GridEvaluation.setPrefHeight(20);
    	
        GridEvaluation.add(labelEvaluation, 0, 0);
        GridEvaluation.add(evaluationText, 1, 0);
        
        Separator separatorEvaluation = new Separator(Orientation.VERTICAL);
        separatorEvaluation.setHalignment(HPos.CENTER);
        GridEvaluation.add(separatorEvaluation, 2, 0);
        separatorEvaluation.setVisible(false);
    	
        GridEvaluation.add(labelCoef, 3, 0);
        GridEvaluation.add(coefText, 4, 0);
        
    	gridParent2.add(GridEvaluation, 0, 5, 1, 1);
    	
    	Text labelDesc = new Text("Descriptif : ");
    	labelDesc.setId("labelDesc");
    	labelDesc.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: black;");
    	labelDesc.setVisible(false);
        
        Label descText = new Label("Ce cours vise à acquérir les compétences nécessaires à la mise en place de méthodes \"Agile\" " + "pour le développement de logiciels. Dans ces contextes, visant la qualité logicielle, le test logiciel sera introduit à travers la conception de suite de tests et leur implémentation via différentes unité de test.");
        descText.setId("descText");
        descText.setVisible(false);
        descText.setWrapText(true);
        
        GridPane GridDesc = new GridPane();
        GridDesc.setAlignment(Pos.TOP_LEFT);
        GridDesc.setMinHeight(20);
        GridDesc.setMaxHeight(300);
        GridDesc.setPrefWidth(500);
    	
        GridDesc.add(labelDesc, 0, 0);
        GridDesc.add(descText, 1, 1);
    	
    	gridParent2.add(GridDesc, 0, 6, 1, 1);
    	
		/*Separator separatorRespo = new Separator();
		separatorRespo.setHalignment(HPos.CENTER);
		separatorRespo.setId("SeparatorId");
	    gridParent2.add(separatorRespo, 0, 5, 1, 1);
	    separatorRespo.setVisible(false);*/
        
        ArrayList<Node> listNode = new ArrayList<>(Arrays.asList(labelUE, ueDataText, nomModuleText, separatorNom, labelResponsable, responsableText, mailText,
        		labelMail,labelHeure, heureText, labelEvaluation, evaluationText, separatorEvaluation, labelCoef, coefText, labelDesc, descText));
        
        ueChoiceBox.setOnAction((event) -> {
        	moduleChoiceBox.getItems().clear();
        	setAllVisible(listNode,false);
            int selectedIndex = ueChoiceBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = ueChoiceBox.getSelectionModel().getSelectedItem();
            
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
        
        
        moduleChoiceBox.setOnAction((event) -> {
        	setAllVisible(listNode,true);
        	
            int selectedIndex = moduleChoiceBox.getSelectionModel().getSelectedIndex();
            Object selectedItem = moduleChoiceBox.getSelectionModel().getSelectedItem();
            
            for(Module module: module2Nom.keySet()) {
	        	if(module.getNom().equals(moduleChoiceBox.getValue())){
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


