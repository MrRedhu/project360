package office.project360;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PatientIDEntry extends Application {

    private static final double PREF_WIDTH = 300.0;
    private static final double PREF_HEIGHT = 150.0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Patient Information System");
        primaryStage.setScene(createMainScene());
        primaryStage.show();
    }

    private Scene createMainScene() {
        ListView<String> navigation = new ListView<>();
        navigation.getItems().addAll(
                "Previous Health Issues",
                "Previously Prescribed Medications",
                "History of Immunization",
                "Add Test Findings",
                "Add Prescription Needed"
        );
        navigation.setPrefWidth(250);
        navigation.setPadding(new Insets(10));

        StackPane contentArea = new StackPane();

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(navigation);
        borderPane.setCenter(contentArea);

        navigation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            contentArea.getChildren().clear();
            if (newValue != null) {
                contentArea.getChildren().add(createContentArea(newValue));
            }
        });

        navigation.getSelectionModel().select(0);

        return new Scene(borderPane, 800, 600);
    }

    private VBox createContentArea(String title) {
        Label label = new Label(title);
        TextArea textArea = new TextArea();
        textArea.setPrefSize(PREF_WIDTH, PREF_HEIGHT);

        Button btnSave = new Button("Save");
        btnSave.setOnAction(event -> saveInformation(textArea.getText(), title));

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> textArea.clear());

        HBox buttonBar = null;
        if (title.equals("Add Test Findings") || title.equals("Add Prescription Needed")) {
            buttonBar = new HBox(10, btnSave, btnCancel);
            buttonBar.setPadding(new Insets(10));
        }

        VBox box = new VBox(10, label, textArea);
        if (buttonBar != null) {
            box.getChildren().add(buttonBar);
        }
        box.setPadding(new Insets(10));
        return box;
    }

    private void saveInformation(String text, String category) {
        // Implement the save logic here
        // For example, save to a database, or a file
        System.out.println("Information saved for " + category + ": " + text);
        // TODO: Replace the above line with actual save functionality
    }

    public static void main(String[] args) {
        launch(args);
    }
}














