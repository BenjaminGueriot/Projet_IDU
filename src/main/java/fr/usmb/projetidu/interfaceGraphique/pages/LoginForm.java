package fr.usmb.projetidu.interfaceGraphique.pages;

import fr.usmb.projetidu.Personne.Eleve;
import fr.usmb.projetidu.utils.Initialize;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginForm extends Parent {

    public LoginForm(Stage primaryStage) {
        primaryStage.setTitle("MetaCampus");

        // Create the login form grid pane
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setId("login");
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Add a welcome message
        Text sceneTitle = new Text("Connexion :");
        sceneTitle.setId("Connexion");
        sceneTitle.setFont(Font.font("", FontWeight.BOLD, 15));
        grid.add(sceneTitle, 1, 0, 2, 1);

        // Add the username label and field
        Label userName = new Label("Login:");
        grid.add(userName, 0, 2);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 2);

        // Add the password label and field
        Label password = new Label("Mot de passe:");
        grid.add(password, 0, 3);

        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 3);

        // Add the login button
        Button btn = new Button("Se connecter");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 5);

        // Add the action target (displayed message after button is clicked)
        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 6);
        actionTarget.setId("action-target");

        // Set the action for the sign in button
        btn.setOnAction(new EventHandler<ActionEvent>() {

		@Override
        public void handle(ActionEvent e) {
            String username = userTextField.getText();
            String password = passwordField.getText();
            
            // Connect to the database and check the username and password
            if (Initialize.checkConnexion(username, password)) {
                actionTarget.setText("Bien jou� mec!");
                Eleve eleve = Initialize.InitializeEleve(username);
        	 try {
	        		 AccueilEleve accueilEleve = new AccueilEleve(primaryStage,eleve);
	                 Scene scene = new Scene(accueilEleve);
	                 scene.getStylesheets().add(getClass().getResource("/accueil.css").toExternalForm());
	                 primaryStage.setScene(scene);
	                 primaryStage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                actionTarget.setText("Erreur lors de la connexion");
            }
            
        }});

        grid.getStyleClass().add("grid-pane");
     // Add the grid pane to the root node
        this.getChildren().add(grid);
        
    }
}



