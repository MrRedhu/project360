package office.project360;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.ScrollPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorsView {

    private MainApplication mainApp;
    private String username;
    private static final double PREF_WIDTH = 300.0;
    private static final double PREF_HEIGHT = 150.0;

    // Modified constructor to accept MainApplication instance

    public DoctorsView(MainApplication mainApp, String username) {
        this.mainApp = mainApp;
        this.username = username;
    }

    public void show(Stage primaryStage) {
        primaryStage.setTitle("Doctor's Dashboard");
        primaryStage.setScene(createMainScene());
        primaryStage.show();
    }


    private Scene createMainScene() {
        VBox navigation = new VBox(10); // Set spacing between elements in the VBox
        navigation.setPrefWidth(250);
        navigation.setPadding(new Insets(10));

        // Label for username
        Label lblUsername = new Label("Username:");

        // Labels for health information
        Label lblHealthHistory = new Label("Health History");
        Label lblPreviousHealthIssues = new Label("Previous Health Issues:");
        Label lblPreviouslyPrescribedMedications = new Label("Previously Prescribed Medications:");
        Label lblHistoryOfImmunization = new Label("History of Immunization:");

        // ScrollPanes to display health information
        ScrollPane previousHealthIssuesScrollPane = new ScrollPane();
        ScrollPane previouslyPrescribedMedicationsScrollPane = new ScrollPane();
        ScrollPane historyOfImmunizationScrollPane = new ScrollPane();

        // Buttons
        Button btnFetchUsername = new Button("Fetch Username");
        Button testFindingsButton = new Button("Add Test Findings");
        Button prescriptionNeededButton = new Button("Add Prescription Needed");
        Button messages = new Button("Messages");
        Button logoutButton = new Button("Logout");

        // TextField for username input
        TextField txtUsername = new TextField();

        // Adding extra space before the logout button
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS); // This makes the spacer expand to fill available space

        StackPane contentArea = new StackPane();

        // Event handler for fetching username data
        btnFetchUsername.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = txtUsername.getText().trim();
                if (!username.isEmpty()) {
                    // Fetch patient information from the database based on the username
                    List<String> patientInfo = fetchPatientInfo(username);
                    if (patientInfo.size() >= 3) {
                        // Set the content of ScrollPanes with the fetched patient information
                        previousHealthIssuesScrollPane.setContent(new Label(patientInfo.get(0)));
                        previouslyPrescribedMedicationsScrollPane.setContent(new Label(patientInfo.get(1)));
                        historyOfImmunizationScrollPane.setContent(new Label(patientInfo.get(2)));
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error", "Patient information not available.");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Please enter a username.");
                }
            }
        });

        // Adding elements to the VBox
        navigation.getChildren().addAll(
                lblUsername, txtUsername, btnFetchUsername,
                lblHealthHistory, previousHealthIssuesScrollPane,
                lblPreviousHealthIssues, previouslyPrescribedMedicationsScrollPane,
                lblPreviouslyPrescribedMedications, historyOfImmunizationScrollPane,
                lblHistoryOfImmunization, testFindingsButton,
                prescriptionNeededButton, messages, spacer, logoutButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(navigation);
        borderPane.setCenter(contentArea);

        Image forkEm = new Image("file:src/main/resources/office/project360/Image_Fork'em.png");
        BackgroundImage bgImg = new BackgroundImage(forkEm,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));
        borderPane.setBackground(new Background(bgImg));

        return new Scene(borderPane, 800, 600);
    }



    private static final String DB_URL = "jdbc:sqlite:identifier.sqlite";

    // Method to fetch patient information from the database based on username
    private List<String> fetchPatientInfo(String username) {
        List<String> patientInfoList = new ArrayList<>(); // String to store the fetched patient information


        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            // SQL query to fetch patient information based on username
            // UPDATE patients SET PreviousHealthIssues = ?, PreviouslyPrescribedMedications = ?, ImmunizationHistory = ?, KnownAllergies = ?, HealthConcerns = ? WHERE username = ?";

            String query = "SELECT PreviousHealthIssues, PreviouslyPrescribedMedications, ImmunizationHistory FROM patients WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Extract health issues, medications, and immunization from the result set
                String healthIssues = resultSet.getString("PreviousHealthIssues");
                String medications = resultSet.getString("PreviouslyPrescribedMedications");
                String immunization = resultSet.getString("ImmunizationHistory");

                // Construct patient information string
                patientInfoList.add(healthIssues);
                patientInfoList.add(medications);
                patientInfoList.add(immunization);

            } else {
                // No patient found with the given username
                patientInfoList.add("No patient found with the username: "+username);
                patientInfoList.add("No patient found with the username: "+username);
                patientInfoList.add("No patient found with the username: "+username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Error occurred while fetching patient information
            System.out.println(e);
        }

        return patientInfoList;
    }

    // Method to show an alert
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private VBox createContentArea(String title,String text) {
        Label label = new Label(title);
        TextArea textArea = new TextArea();
        textArea.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
        VBox box = new VBox(10, label, textArea);
        box.setPadding(new Insets(10));
        return box;
    }

    private VBox createContentAreaWithButtons(String title,String text) {
        VBox box = createContentArea(title,text);
        Button btnSave = new Button("Save");
        Button btnCancel = new Button("Cancel");

        btnSave.setOnAction(event -> saveInformation(((TextArea) box.getChildren().get(1)).getText(), title));
        btnCancel.setOnAction(event -> ((TextArea) box.getChildren().get(1)).clear());

        HBox buttonBar = new HBox(10, btnSave, btnCancel);
        buttonBar.setPadding(new Insets(10));
        box.getChildren().add(buttonBar);

        return box;
    }

    private void saveInformation(String text, String category) {
        System.out.println("Information saved for " + category + ": " + text);
        // Here, you should implement the actual save functionality instead of just printing out the message.
    }
}

