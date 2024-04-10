package office.project360;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatientIDEntry extends Application {

    // Constants for preferred width and height for the first four text areas
    final double PREF_WIDTH = 300.0; // Adjust this value to match the width in your interface
    final double PREF_HEIGHT = 10.0; // Adjust this value to match the height in your interface

    @Override
    public void start(Stage primaryStage) {
        // Create labels and text field for entering patient ID
        Label idLabel = new Label("Enter Patient ID:");
        TextField idTextField = new TextField();
        Button submitButton = new Button("Submit");

        // Create a grid pane to organize the layout
        GridPane idGridPane = new GridPane();
        idGridPane.setPadding(new Insets(20));
        idGridPane.setVgap(10);
        idGridPane.setHgap(10);
        idGridPane.add(idLabel, 0, 0);
        idGridPane.add(idTextField, 1, 0);
        idGridPane.add(submitButton, 1, 1);

        // Create a scene for entering patient ID
        Scene idScene = new Scene(idGridPane, 300, 120);

        // Set action for submit button
        submitButton.setOnAction(event -> {
            String patientID = idTextField.getText();
            if (patientID != null && patientID.matches("\\d{5}")) {
                // If the ID is valid, create a new scene for displaying patient's information
                primaryStage.setScene(idScene);
            } else {
                // Display error message for invalid ID
                Label errorLabel = new Label("Invalid ID. Please enter a valid 5-digit ID.");
                idGridPane.add(errorLabel, 0, 2, 2, 1);
            }
        });

        // Set the initial scene
        primaryStage.setScene(idScene);
        primaryStage.setTitle("Patient ID Entry");
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the application
        launch(args);
    }
}