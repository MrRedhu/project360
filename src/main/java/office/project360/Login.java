package office.project360;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Login {

    private Scene scene;
    private MainApplication mainApp; // Adjusted for the corrected class name

    public Login(MainApplication mainApp) {
        this.mainApp = mainApp;
        initializeUI();
    }

    private void initializeUI() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 50, 20, 50));

        Label header = new Label("Pediatric Doctor's Office");
        header.getStyleClass().add("header-label");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.getStyleClass().add("text-field");
        usernameField.setPrefSize(200, 35);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("text-field");
        passwordField.setPrefSize(200, 35);

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Doctor", "Patient", "Nurse");
        roleComboBox.setPromptText("Select Role");
        roleComboBox.setPrefSize(200, 35);

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("button-common");
        loginButton.setOnAction(e -> {
            String selectedRole = roleComboBox.getValue();
            if (selectedRole == null) {
                showAlert(Alert.AlertType.WARNING, "Login Failed", "Please select a role.");
                return;
            }
            if (authenticate(usernameField.getText(), passwordField.getText(), selectedRole)) {
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome to Pediatric Doctor's Office!");
                mainApp.userLoggedIn(selectedRole, usernameField.getText()); // Transition to the next scene or dashboard after showing the alert
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username, password, or role.");
            }
        });


        Button registerButton = new Button("Register User");
        registerButton.getStyleClass().add("button-common");
        registerButton.getStyleClass().add("register-button");
        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleComboBox.getValue();

            if (username.isEmpty() || password.isEmpty() || role == null) {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Please fill in all fields.");
                return;
            }

            // Call insertUser and check if the registration was successful
            long userId = DatabaseHelper.insertUser(username, password, role);
            if (userId != -1) {
                showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Please enter your name details.");
                // Transition to the name entry scene
                showNameEntryScene(userId, role); // Assuming showNameEntryScene is defined within this class or accessible from here
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "An error occurred.");
            }
        });


        layout.getChildren().addAll(header, usernameField, passwordField, roleComboBox, loginButton, registerButton);

        Image backgroundImage = new Image(getClass().getResourceAsStream("/office/project360/test.png"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(1054);
        backgroundImageView.setFitHeight(768);

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, layout);

        this.scene = new Scene(root, 1054, 768);
        this.scene.getStylesheets().add(getClass().getResource("/office/project360/style.css").toExternalForm());
    }

    public void showNameEntryScene(long userId, String role) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 50, 20, 50));

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            DatabaseHelper.updateNames(userId, role, firstNameField.getText(), lastNameField.getText());
            // Optionally, you can redirect the user to the login page or dashboard here.
            // For example, to go back to the login scene:
            mainApp.showLoginScreen(); // Assuming 'showLoginScene' is a method in MainApplication to display the login scene.
        });

        layout.getChildren().addAll(new Label("Enter your details"), firstNameField, lastNameField, submitButton);

        Scene nameEntryScene = new Scene(layout, 400, 300);
        // Set this new scene to the stage
        mainApp.getPrimaryStage().setScene(nameEntryScene); // Assuming 'mainApp' has a method 'getPrimaryStage' that returns the primary stage of the application
    }


    public Scene getScene() {
        return this.scene; // Ensure this method returns the initialized scene
    }

    private boolean authenticate(String username, String password, String selectedRole) {
        return DatabaseHelper.checkUserCredentials(username, password, selectedRole);
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}