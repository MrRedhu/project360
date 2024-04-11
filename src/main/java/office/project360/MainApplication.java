package office.project360;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginScreen();
    }
    public Stage getPrimaryStage() {
        return primaryStage; // Return the primary stage to other classes
    }
    public void showLoginScreen() {
        Login loginScreen = new Login(this); // Adjusted the class name to follow Java naming conventions
        primaryStage.setScene(loginScreen.getScene());
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public void userLoggedIn(String role) {
        switch (role) {
            case "Doctor":
                DoctorsView doctorsView = new DoctorsView();
                doctorsView.show(primaryStage); // Use the same stage to show the doctor's dashboard
                break;
            // Add cases for other roles like "Patient", "Nurse" as needed
            default:
                System.out.println("Role not recognized.");
                break;
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
