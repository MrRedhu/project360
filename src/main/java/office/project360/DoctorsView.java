package office.project360;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DoctorsView {

    private static final double PREF_WIDTH = 300.0;
    private static final double PREF_HEIGHT = 150.0;

    public void show(Stage primaryStage) {
        primaryStage.setTitle("Doctor's Dashboard");
        primaryStage.setScene(createMainScene());
        primaryStage.show();
    }

    private Scene createMainScene() {
        VBox navigation = new VBox();
        navigation.setPrefWidth(250);
        navigation.setPadding(new Insets(10));

        Accordion healthHistoryAccordion = new Accordion();

        TitledPane healthIssuesPane = new TitledPane("Previous Health Issues", createContentArea("Previous Health Issues:"));
        TitledPane medicationsPane = new TitledPane("Previously Prescribed Medications", createContentArea("Previously Prescribed Medications:"));
        TitledPane immunizationPane = new TitledPane("History of Immunization", createContentArea("History of Immunization:"));

        healthHistoryAccordion.getPanes().addAll(healthIssuesPane, medicationsPane, immunizationPane);

        Button testFindingsButton = new Button("Add Test Findings");
        Button prescriptionNeededButton = new Button("Add Prescription Needed");

        StackPane contentArea = new StackPane();

        testFindingsButton.setOnAction(e -> contentArea.getChildren().setAll(createContentAreaWithButtons("Add Test Findings:")));
        prescriptionNeededButton.setOnAction(e -> contentArea.getChildren().setAll(createContentAreaWithButtons("Add Prescription Needed:")));

        navigation.getChildren().addAll(new TitledPane("Health History", healthHistoryAccordion), testFindingsButton, prescriptionNeededButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(navigation);
        borderPane.setCenter(contentArea);

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
        // TODO: Replace the above line with actual save functionality
    }
}
