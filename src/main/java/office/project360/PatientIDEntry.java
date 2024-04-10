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
        Scene idScene = new Scene(idGridPane, 300, 100);

        // Set action for submit button
        submitButton.setOnAction(event -> {
            String patientID = idTextField.getText();
            if (validateID(patientID)) {
                // If the ID is valid, create a new scene for displaying patient's information
                VBox doctorsView = createDoctorsView(patientID);
                Scene doctorsScene = new Scene(doctorsView, 1054, 768);
                primaryStage.setScene(doctorsScene);
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

    private boolean validateID(String id) {
        return id != null && id.matches("\\d{5}");
    }

    private VBox createDoctorsView(String patientID) {
        // Create labels for patient's history components
        Label healthIssuesLabel = new Label("Previous Health Issues:");
        Label medicationsLabel = new Label("Previously Prescribed Medications:");
        Label immunizationLabel = new Label("History of Immunization:");
        Label recommendationsLabel = new Label("Any Other Recommendations:");

        // Create text areas for patient's history components
        TextArea healthIssuesTextArea = new TextArea();
        TextArea medicationsTextArea = new TextArea();
        TextArea immunizationTextArea = new TextArea();
        TextArea recommendationsTextArea = new TextArea();

        // Set preferred size for the first four text areas
        healthIssuesTextArea.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
        medicationsTextArea.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
        immunizationTextArea.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
        recommendationsTextArea.setPrefSize(PREF_WIDTH, PREF_HEIGHT);

        // Create labels for test findings and prescription needed
        Label testFindingsLabel = new Label("Test Findings:");
        Label prescriptionNeededLabel = new Label("Prescription Needed:");

        // Create text areas for entering test findings and prescription needed
        TextArea testFindingsTextArea = new TextArea();
        TextArea prescriptionNeededTextArea = new TextArea();

        // Create a button for submitting test findings and prescription needed
        Button submitButton = new Button("Submit");

        // Create a grid pane to organize the layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Add components to the grid pane
        gridPane.add(healthIssuesLabel, 0, 0);
        gridPane.add(healthIssuesTextArea, 1, 0);
        gridPane.add(medicationsLabel, 0, 1);
        gridPane.add(medicationsTextArea, 1, 1);
        gridPane.add(immunizationLabel, 0, 2);
        gridPane.add(immunizationTextArea, 1, 2);
        gridPane.add(recommendationsLabel, 0, 3);
        gridPane.add(recommendationsTextArea, 1, 3);
        gridPane.add(testFindingsLabel, 0, 4);
        gridPane.add(testFindingsTextArea, 1, 4);
        gridPane.add(prescriptionNeededLabel, 0, 5);
        gridPane.add(prescriptionNeededTextArea, 1, 5);
        gridPane.add(submitButton, 1, 6);

        // Set action for submit button (dummy action for demonstration)
        submitButton.setOnAction(event -> {
            String healthIssues = healthIssuesTextArea.getText();
            String medications = medicationsTextArea.getText();
            String immunization = immunizationTextArea.getText();
            String recommendations = recommendationsTextArea.getText();
            // No need to change the size of test findings and prescription text areas
            String testFindings = testFindingsTextArea.getText();
            String prescriptionNeeded = prescriptionNeededTextArea.getText();

            // Output information to the console
            System.out.println("Previous Health Issues: " + healthIssues);
            System.out.println("Previously Prescribed Medications: " + medications);
            System.out.println("History of Immunization: " + immunization);
            System.out.println("Any Other Recommendations: " + recommendations);
            System.out.println("Test Findings: " + testFindings);
            System.out.println("Prescription Needed: " + prescriptionNeeded);
        });

        // Create a vertical box to hold the grid pane
        VBox vbox = new VBox();
        vbox.getChildren().add(gridPane);
        return vbox;
    }

    public static void main(String[] args) {
        // Launch the application
        launch(args);
    }
}










