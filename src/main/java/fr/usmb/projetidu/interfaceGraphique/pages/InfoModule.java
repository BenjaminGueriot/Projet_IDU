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
import fr.usmb.projetidu.utils.Initialize;
import fr.usmb.projetidu.utils.ScrappingData;
import io.netty.handler.codec.string.StringDecoder;
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
	
	private Label nomModuleText;
	private Label ueDataText;
	private Label responsableText;
	private Label mailText;
	private Label heureText;
	private Label evaluationText;
	private Label coefText;
	private Label descText;
	
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

        Button refreshButton = new Button("Recharger les informations des modules");
        refreshButton.setOnAction(event -> {
        	
        	ScrappingData.getAllModulesInfos();
        	String login = eleve.getLogin();
        	System.out.println(login);
        	Initialize.resetObject(eleve);
        	Eleve eleve2 = Initialize.InitializeEleve(login);
        	
        	InfoModule infoModule = new InfoModule(primaryStage,eleve2);
            Scene scene = new Scene(infoModule,800,500);
            primaryStage.setScene(scene);
            primaryStage.show();
        	
        });
        // Add the back button to the top left corner of the root node
        gridGeneral.add(backButton,0,0);
        gridGeneral.add(refreshButton,2,0);
        gridGeneral.setAlignment(Pos.TOP_LEFT);
        
        int count = 0;
        
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
        
        this.nomModuleText = new Label();
        this.nomModuleText.setId("nomModuleText");
        this.nomModuleText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: black;");
        this.nomModuleText.setVisible(false);
        this.nomModuleText.setWrapText(true);
        
        GridPane GridModule = new GridPane();
        GridModule.setAlignment(Pos.BASELINE_LEFT);
        GridModule.setMaxHeight(50);
        GridModule.setPrefWidth(500);
    	
        GridModule.add(this.nomModuleText, 0, 0);
    	
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
        
        this.ueDataText = new Label();
        this.ueDataText.setId("ueDataText");
        this.ueDataText.setVisible(false);
        this.ueDataText.setWrapText(true);
        
        GridPane GridUE = new GridPane();
        GridUE.setAlignment(Pos.BASELINE_LEFT);
    	GridUE.setMaxHeight(50);
    	GridUE.setPrefWidth(500);
    	
    	GridUE.add(labelUE, 0, 0);
    	GridUE.add(this.ueDataText, 1, 0);
    	
    	gridParent2.add(GridUE, 0, 2, 1, 1);
        
        Text labelResponsable = new Text("Responsable : ");
        labelResponsable.setId("labelResponsable");
        labelResponsable.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: black;");
        labelResponsable.setVisible(false);
        
        this.responsableText = new Label();
        this.responsableText.setId("responsableText");
        this.responsableText.setVisible(false);
        this.responsableText.setWrapText(true);
        
        GridPane GridRespo = new GridPane();
        GridRespo.setAlignment(Pos.BASELINE_LEFT);
        GridRespo.setMaxHeight(50);
        GridRespo.setPrefWidth(500);
    	
        GridRespo.add(labelResponsable, 0, 0);
        GridRespo.add(this.responsableText, 1, 0);
        
        gridParent2.add(GridRespo, 0, 3, 1, 1);
        
        Text labelMail = new Text("Mail : ");
        labelMail.setId("labelMail");
        labelMail.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: black;");
        labelMail.setVisible(false);
        
        this.mailText = new Label();
        this.mailText.setId("mailText");
        this.mailText.setVisible(false);
        this.mailText.setWrapText(true);
        
        GridPane GridMail = new GridPane();
        GridMail.setAlignment(Pos.BASELINE_LEFT);
        GridMail.setMaxHeight(50);
        GridMail.setPrefWidth(500);
    	
        GridMail.add(labelMail, 0, 0);
        GridMail.add(this.mailText, 1, 0);
        
    	gridParent2.add(GridMail, 0, 4, 1, 1);
    	
    	Text labelHeure = new Text("Total Horaire (h) : ");
    	labelHeure.setId("labelHeure");
    	labelHeure.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: black;");
    	labelHeure.setVisible(false);
        
    	this.heureText = new Label();
    	this.heureText.setId("heureText");
    	this.heureText.setVisible(false);
        
        GridPane GridHeure = new GridPane();
        GridHeure.setAlignment(Pos.BASELINE_LEFT);
        GridHeure.setMaxHeight(50);
    	
        GridHeure.add(labelHeure, 0, 0);
        GridHeure.add(this.heureText, 1, 0);
    	
    	gridParent2.add(GridHeure, 0, 5, 1, 1);
    	
    	Text labelEvaluation = new Text("Evaluation : ");
    	labelEvaluation.setId("labelNotation");
    	labelEvaluation.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: black;");
    	labelEvaluation.setVisible(false);
        
    	this.evaluationText = new Label();
    	this.evaluationText.setId("evaluationText");
    	this.evaluationText.setVisible(false);
        
        Text labelCoef = new Text("Coefficient : ");
        labelCoef.setId("labelCoef");
        labelCoef.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: black;");
        labelCoef.setVisible(false);
        
        this.coefText = new Label();
        this.coefText.setId("mailText");
        this.coefText.setVisible(false);
        
        GridPane GridEvaluation = new GridPane();
        GridEvaluation.setAlignment(Pos.BASELINE_LEFT);
        GridEvaluation.setMaxHeight(50);
    	
        GridEvaluation.add(labelEvaluation, 0, 0);
        GridEvaluation.add(this.evaluationText, 1, 0);
        
        Separator separatorEvaluation = new Separator(Orientation.VERTICAL);
        separatorEvaluation.setHalignment(HPos.CENTER);
        GridEvaluation.add(separatorEvaluation, 2, 0);
        separatorEvaluation.setVisible(false);
    	
        GridEvaluation.add(labelCoef, 3, 0);
        GridEvaluation.add(this.coefText, 4, 0);
        
    	gridParent2.add(GridEvaluation, 0, 6, 1, 1);
    	
    	Text labelDesc = new Text("Descriptif : ");
    	labelDesc.setId("labelDesc");
    	labelDesc.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-fill: black;");
    	labelDesc.setVisible(false);
        
    	this.descText = new Label();
    	this.descText.setId("descText");
    	this.descText.setVisible(false);
    	this.descText.setWrapText(true);
        
        GridPane GridDesc = new GridPane();
        GridDesc.setAlignment(Pos.TOP_LEFT);
        GridDesc.setMinHeight(20);
        GridDesc.setMaxHeight(300);
        GridDesc.setPrefWidth(500);
    	
        GridDesc.add(labelDesc, 0, 0);
        GridDesc.add(this.descText, 1, 1);
    	
    	gridParent2.add(GridDesc, 0, 7, 1, 1);
    	
		/*Separator separatorRespo = new Separator();
		separatorRespo.setHalignment(HPos.CENTER);
		separatorRespo.setId("SeparatorId");
	    gridParent2.add(separatorRespo, 0, 5, 1, 1);
	    separatorRespo.setVisible(false);*/
        
        ArrayList<Node> listNode = new ArrayList<>(Arrays.asList(labelUE, this.ueDataText, this.nomModuleText, separatorNom, labelResponsable, this.responsableText, this.mailText,
        		labelMail,labelHeure, this.heureText, labelEvaluation, this.evaluationText, separatorEvaluation, labelCoef, this.coefText, labelDesc, this.descText));
        
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
	        		
	        		this.nomModuleText.setText(module.getCode() + " : " + module.getNom());
	        		this.ueDataText.setText(currentUE.getCode() + " - " + currentUE.getNom());
	        		
	        		String respos = "";
	        		String mails = "";
	        		
	        		for(Enseignant respo : module.getEnseignants()) {
	        			
	        			if(module.getEnseignants().get(module.getEnseignants().size() - 1).equals(respo)) {
	        				
	        				respos += respo.getNom().toUpperCase() + " " + respo.getPrenom();
	        				mails += respo.getMail();
	        				
	        			} else {
	        				
	        				respos += respo.getNom() + " " + respo.getPrenom() + ", ";
	        				mails += respo.getMail() + ", ";
	        				
	        			}
	        			
	        		}
	        		
	        		this.responsableText.setText(respos);
	        		this.mailText.setText(mails);
	        		
	        		
	        		this.heureText.setText(String.valueOf(module.getNbHeures()));
	        		this.evaluationText.setText(module.getEvaluation() + "  ");
	        		this.coefText.setText(String.valueOf(module.getCoeff()));
	        		this.descText.setText(module.getDescription());
	        		
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


