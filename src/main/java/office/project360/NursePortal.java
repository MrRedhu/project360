package office.project360;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class NursePortal extends Application {

    private GridPane createProfileContent() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Username:"), 0, 0);
        grid.add(new TextField(), 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(new TextField(), 1, 1);
        grid.add(new Label("Date of Birth:"), 0, 2);
        grid.add(new TextField(), 1, 2);
        grid.add(new Label("Email:"), 0, 3);
        grid.add(new TextField(), 1, 3);
        grid.add(new Label("Phone Number:"), 0, 4);
        grid.add(new TextField(), 1, 4);

        Button btnSave = new Button("Save");
        Button btnCancel = new Button("Cancel");
        grid.add(btnSave, 1, 5);
        grid.add(btnCancel, 2, 5);

        // Handle Save and Cancel button actions here

        return grid;
    }

    private GridPane createPatientVitalsContent() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Weight:"), 0, 0);
        grid.add(new TextField(), 1, 0);

        grid.add(new Label("Height:"), 0, 1);
        grid.add(new TextField(), 1, 1);

        grid.add(new Label("Body Temperature:"), 0, 2);
        grid.add(new TextField(), 1, 2);

        grid.add(new Label("Blood Pressure:"), 0, 3);
        grid.add(new TextField(), 1, 3);

        grid.add(new Label("Patient Age:"), 0, 4);
        grid.add(new TextField(), 1, 4);

        Button btnSubmitVitals = new Button("Submit Vitals");
        grid.add(btnSubmitVitals, 1, 5);
//        GridPane.setHalignment(btnSubmitVitals, HPos.RIGHT);

        // Event handling for the submit button
        btnSubmitVitals.setOnAction(event -> {
            // Logic to handle submission of vitals
        });

        return grid;
    }

    private GridPane createHealthHistoryContent() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Patient's Medical History"), 0, 0, 2, 1);

        grid.add(new Label("Previous Health Issues"), 0, 1);
        grid.add(new TextField(), 0, 2);

        grid.add(new Label("Previously Prescribed Medications"), 0, 3);
        grid.add(new TextField(), 0, 4);

        grid.add(new Label("Immunization History"), 0, 5);
        grid.add(new TextField(), 0, 6);

        grid.add(new Label("Known Allergies"), 0, 7);
        TextField allergiesField = new TextField();
        grid.add(allergiesField, 0, 8);
        Button btnAddAllergy = new Button("Add Allergy");
        grid.add(btnAddAllergy, 1, 8);

        grid.add(new Label("Health Concerns"), 0, 9);
        TextField healthConcernsField = new TextField();
        grid.add(healthConcernsField, 0, 10);
        Button btnAddHealthConcern = new Button("Add Health Concern");
        grid.add(btnAddHealthConcern, 1, 10);

        Button btnSave = new Button("Save Changes");
        Button btnCancel = new Button("Cancel");
        grid.add(btnSave, 0, 11);
        grid.add(btnCancel, 1, 11);

        // Handle button actions here
        // For example, to add a new allergy to a list or similar
        btnAddAllergy.setOnAction(event -> {
            // Code to handle adding a new allergy
        });

        btnAddHealthConcern.setOnAction(event -> {
            // Code to handle adding a new health concern
        });

        // Handle Save and Cancel button actions here

        return grid;
    }

    public static class Message {
        private final String read;
        private final String from;
        private final String date;
        private final String subject;

        public Message(String read, String from, String date, String subject) {
            this.read = read;
            this.from = from;
            this.date = date;
            this.subject = subject;
        }

        // Getters (no setters for simplicity, assuming data is read-only for this example)
        public String getRead() { return read; }
        public String getFrom() { return from; }
        public String getDate() { return date; }
        public String getSubject() { return subject; }
    }

    private VBox createMessagesContent() {
        VBox messagesBox = new VBox(10);
        messagesBox.setPadding(new Insets(20));

        // Table for displaying messages
        TableView<Message> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Make columns take up all available space equally

        // Define columns
        TableColumn<Message, String> columnRead = new TableColumn<>("Read");
        columnRead.setCellValueFactory(new PropertyValueFactory<>("read"));

        TableColumn<Message, String> columnFrom = new TableColumn<>("From");
        columnFrom.setCellValueFactory(new PropertyValueFactory<>("from"));

        TableColumn<Message, String> columnDate = new TableColumn<>("Date");
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Message, String> columnSubject = new TableColumn<>("Subject");
        columnSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));

        // Add columns to table
        table.getColumns().addAll(columnRead, columnFrom, columnDate, columnSubject);

        // Sample data for the table
        ObservableList<Message> messages = FXCollections.observableArrayList(
                new Message("✓", "ASU HEALTH SERVICES", "08/04/2024 16:36", "Test")
                // ... Add more sample messages here
        );

        table.setItems(messages);

        // Buttons for inbox functionality
        Button btnNewMessage = new Button("New Message");
        Button btnRefresh = new Button("Refresh");

        // Event handling for the buttons
        btnNewMessage.setOnAction(event -> {
            // Handle new message action
        });

        btnRefresh.setOnAction(event -> {
            // Handle refresh action
        });

        // Assemble the messages view
        messagesBox.getChildren().addAll(btnNewMessage, btnRefresh, table);

        return messagesBox;
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();

        // Menu on the left side
        VBox menuBox = new VBox(10);
        HBox buttonsBox = new HBox(10);
        buttonsBox.setPadding(new Insets(20));
        menuBox.setPadding(new Insets(15));
        Label lblProfile = new Label("Profile");
        Label lblPatientVitals = new Label("Patient Vitals");
        Label lblHealthHistory = new Label("Health History");
        Label lblMessages = new Label("Messages");
        Label lblInsuranceCard = new Label("Insurance Card");
        Button btnBack = new Button("Back");
        Button btnLogOut = new Button("Log Out");

        Label[] labels = {lblProfile, lblPatientVitals, lblHealthHistory, lblMessages, lblInsuranceCard};

        for (Label label : labels) {
            label.setOnMouseEntered(e -> label.setCursor(Cursor.HAND));
            label.setOnMouseExited(e -> label.setCursor(Cursor.DEFAULT));
        }

        btnBack.setOnMouseEntered(e-> btnBack.setCursor(Cursor.HAND));
        btnBack.setOnMouseExited(e -> btnBack.setCursor(Cursor.DEFAULT));

        btnLogOut.setOnMouseEntered(e-> btnLogOut.setCursor(Cursor.HAND));
        btnLogOut.setOnMouseExited(e -> btnLogOut.setCursor(Cursor.DEFAULT));

        menuBox.getChildren().addAll(lblProfile, lblPatientVitals, lblHealthHistory, lblMessages, lblInsuranceCard);
        buttonsBox.getChildren().addAll(btnBack, btnLogOut);

        // Content on the right side
        GridPane contentPane = new GridPane();
        contentPane.setPadding(new Insets(20));
        contentPane.setHgap(10);
        contentPane.setVgap(10);

        // Set actions for buttons
        lblProfile.setOnMouseClicked(event -> borderPane.setCenter(createProfileContent()));
        lblPatientVitals.setOnMouseClicked(event -> borderPane.setCenter(createPatientVitalsContent()));
        lblHealthHistory.setOnMouseClicked(event -> borderPane.setCenter(createHealthHistoryContent()));
        lblMessages.setOnMouseClicked(event -> borderPane.setCenter(createMessagesContent()));
        lblInsuranceCard.setOnMouseClicked(event -> borderPane.setCenter(new TextField("Insurance Card Content")));
        btnLogOut.setOnMouseClicked(event -> borderPane.setCenter(new TextField("Insurance Card Content"))); // Just closes the application

        // Assemble the BorderPane
        borderPane.setLeft(menuBox);
        borderPane.setCenter(contentPane);
        borderPane.setBottom(buttonsBox);

        // Initial view
        borderPane.setCenter(createProfileContent());

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setTitle("User Interface Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}