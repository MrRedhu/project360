package office.project360;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Patientview extends Application {

    private Stage primaryStage;
    private BorderPane borderPane;
    private VBox sidebar;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Initialize sidebar
        initSidebar();

        // Create BorderPane to divide the window
        borderPane = new BorderPane();

        // Set sidebar in the left region
        borderPane.setLeft(sidebar);

        // Create an HBox for back, logout, and empty space to center them
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);

        // Add back button
        Button backButton = createButton("Back");
        buttonBox.getChildren().add(backButton);

        // Add an empty region to push the buttons to the center
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        buttonBox.getChildren().add(spacer);

        // Add logout button
        Button logoutButton = createButton("Log Out");
        buttonBox.getChildren().add(logoutButton);

        // Set the HBox containing buttons at the bottom of the BorderPane
        borderPane.setBottom(buttonBox);

        // Load the image
        Image image = new Image("file:///Users/soohumkaushik/IdeaProjects/project360/src/main/resources/office/project360/test.png");

        // Create an ImageView for the image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100); // Set the width of the image
        imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image

        // Add the image to the bottom left of the BorderPane
        borderPane.setBottom(imageView);
        BorderPane.setAlignment(imageView, Pos.BOTTOM_LEFT); // Align the image to the bottom left
        BorderPane.setMargin(imageView, new Insets(10)); // Set margin around the image

        Scene scene = new Scene(borderPane, 1054, 768);

        // Add CSS file to the scene's stylesheets
        scene.getStylesheets().add("file:/Users/soohumkaushik/IdeaProjects/project360/src/main/resources/office/project360/patientview.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("Patient View Page");
        primaryStage.show();
    }

    private void initSidebar() {
        // Sidebar options
        sidebar = new VBox();
        sidebar.getStyleClass().add("maroon-sidebar"); // Add maroon-sidebar style class
        sidebar.setSpacing(20); // Increase spacing between labels
        sidebar.setAlignment(Pos.TOP_LEFT);
        sidebar.setPadding(new Insets(10));

        // Create labels with custom styles
        Label profileOption = new Label("Profile");
        profileOption.getStyleClass().add("sidebar-label"); // Apply sidebar-label style class
        profileOption.setStyle("-fx-font-size: 20px;"); // Set font size to 20px

        Label testResultOption = new Label("Test Result");
        testResultOption.getStyleClass().add("sidebar-label"); // Apply sidebar-label style class
        testResultOption.setStyle("-fx-font-size: 20px;"); // Set font size to 20px

        Label diagnosisOption = new Label("Doctor's Diagnosis");
        diagnosisOption.getStyleClass().add("sidebar-label"); // Apply sidebar-label style class
        diagnosisOption.setStyle("-fx-font-size: 20px;"); // Set font size to 20px

        // Adding click event handlers for sidebar options
        profileOption.setOnMouseClicked(e -> showProfile());
        testResultOption.setOnMouseClicked(e -> showTestResult());
        diagnosisOption.setOnMouseClicked(e -> showDiagnosis());

        // Add labels to the sidebar
        sidebar.getChildren().addAll(profileOption, testResultOption, diagnosisOption);
    }

    private void showProfile() {
        // Sample patient name
        String patientName = "John Doe";

        // Create label for patient name
        Label patientNameLabel = new Label("Hello, " + patientName);
        patientNameLabel.getStyleClass().add("header-label"); // Apply CSS style
        patientNameLabel.setStyle("-fx-font-size: 35pt;");

        // Create text fields for patient's height, weight, body temperature, and blood pressure
        TextField heightTextField = new TextField();
        heightTextField.setPromptText("Height");

        TextField weightTextField = new TextField();
        weightTextField.setPromptText("Weight");

        TextField temperatureTextField = new TextField();
        temperatureTextField.setPromptText("Body Temperature");

        TextField pressureTextField = new TextField();
        pressureTextField.setPromptText("Blood Pressure");

        // Create VBox for patient's details
        VBox patientDetailsBox = new VBox(10);
        patientDetailsBox.setAlignment(Pos.CENTER);

        patientDetailsBox.getChildren().addAll(
                patientNameLabel,
                new Label("Patient Details"),
                heightTextField,
                weightTextField,
                temperatureTextField,
                pressureTextField
        );

        // Displaying profile in the main content area
        borderPane.setCenter(patientDetailsBox);
    }

    private void showTestResult() {
        // Create heading for patient's results
        Label patientResultsHeading = new Label("Patient's Results");
        patientResultsHeading.getStyleClass().add("header-label");
        patientResultsHeading.setStyle("-fx-font-size: 35px;");// Apply CSS style

        // Create text area for patient's results
        TextArea patientResultsTextArea = new TextArea();
        patientResultsTextArea.getStyleClass().add("text-field"); // Apply CSS style
        patientResultsTextArea.setPrefColumnCount(20);
        patientResultsTextArea.setPrefRowCount(10);

        // Create VBox for patient's results
        VBox patientResultsBox = new VBox(10);
        patientResultsBox.setAlignment(Pos.CENTER);
        patientResultsBox.getChildren().addAll(patientResultsHeading, patientResultsTextArea);

        // Displaying test results in the main content area
        borderPane.setCenter(patientResultsBox);
    }

    private void showDiagnosis() {
        // Create heading for doctor's diagnosis
        Label diagnosisHeadingLabel = new Label("Doctor's Diagnosis");
        diagnosisHeadingLabel.getStyleClass().add("header-label");
        diagnosisHeadingLabel.setStyle("-fx-font-size: 35px;");// Apply CSS style

        // Create text area for doctor's diagnosis
        TextArea diagnosisTextArea = new TextArea();
        diagnosisTextArea.getStyleClass().add("text-field"); // Apply CSS style
        diagnosisTextArea.setPrefColumnCount(20);
        diagnosisTextArea.setPrefRowCount(10);

        // Create VBox for doctor's diagnosis
        VBox diagnosisBox = new VBox(10);
        diagnosisBox.setAlignment(Pos.CENTER);
        diagnosisBox.getChildren().addAll(diagnosisHeadingLabel, diagnosisTextArea);

        // Displaying diagnosis in the main content area
        borderPane.setCenter(diagnosisBox);
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #0077cc; -fx-text-fill: white;");
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
