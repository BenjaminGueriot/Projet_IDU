package fr.usmb.projetidu.interfaceGraphique.pages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorPopup {
	
	private String errorMessage;
	
	public ErrorPopup(String errorMessage) {
		this.errorMessage = errorMessage;
		display();
	}
	
    public void display() {
        Stage window = new Stage();

        // Block user interaction with other windows until this window is closed
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Error");
        window.setResizable(false);
        window.setMinWidth(500);

        // Create a label with the error message
        Label label = new Label(this.errorMessage);
        label.setStyle("-fx-font-size: 16px;");

        // Create a VBox layout for the label
        VBox layout = new VBox(10);
        layout.getChildren().add(label);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), null)));
        layout.setPrefWidth(300);
        layout.setPrefHeight(100);
        layout.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-padding: 10px;");

        // Create a scene with the layout
        Scene scene = new Scene(layout);

        // Set the scene and show the window
        window.setScene(scene);
        window.showAndWait();
    }
}
