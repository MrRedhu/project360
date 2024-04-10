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

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("text-field");

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Doctor", "Patient", "Nurse");
        roleComboBox.setPromptText("Select Role");

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("button-common");
        loginButton.setOnAction(e -> {
            String selectedRole = roleComboBox.getValue();
            if (selectedRole == null) {
                showAlert(Alert.AlertType.WARNING, "Login Failed", "Please select a role.");
                return;
            }
            // Further login logic depending on the role...
            if (authenticate(usernameField.getText(), passwordField.getText(), selectedRole)) {
                mainApp.userLoggedIn();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username, password, or role.");
            }
        });

        Button registerButton = new Button("Register User");
        registerButton.getStyleClass().add("button-common");
        registerButton.getStyleClass().add("register-button");

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



    public Scene getScene() {
        return this.scene; // Ensure this method returns the initialized scene
    }

    private boolean authenticate(String username, String password, String selectedRole) {
        // Implement authentication logic here
        return "admin".equals(username) && "password".equals(password);
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}