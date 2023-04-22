package fr.usmb.projetidu.interfaceGraphique.pages;

import fr.usmb.projetidu.Personne.Eleve;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class CompteEleve extends Parent{
	
	public CompteEleve(Stage primaryStage,Eleve eleve) {
		GridPane gridParent = new GridPane();
		gridParent.setAlignment(Pos.CENTER);
		gridParent.setHgap(10);
	    gridParent.setVgap(10);
	    gridParent.setPadding(new Insets(10, 10, 10, 10));
	    gridParent.setAlignment(Pos.CENTER_LEFT);
	    
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
	    
	    Text compte = new Text("Compte :");
	    compte.setId("compte");
	    compte.setTextAlignment(TextAlignment.LEFT);
        gridParent.add(compte, 0, 1, 1, 1);
        
        Separator separator = new Separator();
        separator.setHalignment(HPos.CENTER);
        gridParent.add(separator, 0, 2, 1, 1);
        
        Text nomText = new Text("Nom : ");
        String nom = eleve.getNom();
        Text nomEleve = new Text(nom);
        
        
        Text prenomText = new Text("Prenom :");
        String prenom = eleve.getPrenom();
        Text prenomEleve = new Text(prenom);
        
        
        
        Text filiereText = new Text("Fili�re :");
        String filiere = eleve.getPromo().getFiliere().getNom();
        Text filiereEleve = new Text(filiere);
        
        Text DescriptionText = new Text("Description de la fili�re :");
        String descfiliere = eleve.getPromo().getFiliere().getDescription();
        Text descfiliereEleve = new Text(descfiliere);
        
        
        
        
        gridParent.add(nomText, 0, 3, 1, 1);
        gridParent.add(nomEleve, 1, 3, 1, 1);
        gridParent.add(prenomText, 0, 5, 1, 1);
        gridParent.add(prenomEleve, 1, 5, 1, 1);
        gridParent.add(filiereText, 0, 7, 1, 1);
        gridParent.add(filiereEleve, 1, 7, 1, 1);
        gridParent.add(DescriptionText, 0, 9, 1, 1);
        gridParent.add(descfiliereEleve, 1, 9, 1, 1);
        
	    
	    
	    
	    
	    
	    
	    
	 // Add the scroll pane to the root node
        this.getChildren().add(gridParent);
	}
}
