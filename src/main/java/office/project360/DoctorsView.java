package office.project360;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

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

        Accordion healthHistoryAccordion = new Accordion();

        Label lblUsername = new Label("Username:");
        TextField txtUsername = new TextField();
        Button btnFetchUsername = new Button("Fetch Username");
        TitledPane healthIssuesPane = new TitledPane("Previous Health Issues", createContentArea("Previous Health Issues:"));
        TitledPane medicationsPane = new TitledPane("Previously Prescribed Medications", createContentArea("Previously Prescribed Medications:"));
        TitledPane immunizationPane = new TitledPane("History of Immunization", createContentArea("History of Immunization:"));

        healthHistoryAccordion.getPanes().addAll(healthIssuesPane, medicationsPane, immunizationPane);

        Button testFindingsButton = new Button("Add Test Findings");
        Button prescriptionNeededButton = new Button("Add Prescription Needed");
        Button messages = new Button("Messages");
        Button logoutButton = new Button("Logout"); // Logout button

        // Adding extra space before the logout button
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS); // This makes the spacer expand to fill available space

        StackPane contentArea = new StackPane();

        testFindingsButton.setOnAction(e -> contentArea.getChildren().setAll(createContentAreaWithButtons("Add Test Findings:")));
        prescriptionNeededButton.setOnAction(e -> contentArea.getChildren().setAll(createContentAreaWithButtons("Add Prescription Needed:")));
        logoutButton.setOnAction(e -> mainApp.showLoginScreen()); // Set action for logout button
        messages.setOnMouseClicked(event -> {
            // This line instantiates your MessageApp
            MessageApp messageApp = new MessageApp(username);
            // Creates a new stage for the message application
            Stage messageStage = new Stage();
            // Starts the MessageApp using the new stage
            messageApp.start(messageStage);
        });

        // Adding the spacer before the logout button
        navigation.getChildren().addAll(lblUsername, txtUsername, btnFetchUsername, new TitledPane("Health History", healthHistoryAccordion), testFindingsButton, prescriptionNeededButton, messages, spacer, logoutButton);

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


    private VBox createContentArea(String title) {
        Label label = new Label(title);
        TextArea textArea = new TextArea();
        textArea.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
        VBox box = new VBox(10, label, textArea);
        box.setPadding(new Insets(10));
        return box;
    }

    private VBox createContentAreaWithButtons(String title) {
        VBox box = createContentArea(title);
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

