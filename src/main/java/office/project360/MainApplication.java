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

    public void userLoggedIn(String role, String username) {
        switch (role) {
            case "Doctor":
                DoctorsView doctorsView = new DoctorsView(this);
                doctorsView.show(primaryStage); // Use the same stage to show the doctor's dashboard
                break;
            case "Nurse":
                NursePortal nursePortal = new NursePortal();
                nursePortal.show(primaryStage); // Use the same stage for the nurse dashboard
                break;
            case "Patient":
                PatientView patientView = new PatientView(username);
                patientView.show(primaryStage); // Show the patient dashboard
                break;
            default:
                System.out.println("Role not recognized.");
                break;
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
