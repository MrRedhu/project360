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

    private void showLoginScreen() {
        Login loginScreen = new Login(this); // Adjusted the class name to follow Java naming conventions
        primaryStage.setScene(loginScreen.getScene());
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public void userLoggedIn() {
        // Here you would switch to another scene after successful login,
        // For example, show the main dashboard of your application.
        // Dashboard dashboard = new Dashboard(this);
        // primaryStage.setScene(dashboard.getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
