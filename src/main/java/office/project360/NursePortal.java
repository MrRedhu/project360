package office.project360;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NursePortal {

    private BorderPane borderPane = new BorderPane();

    public void show(Stage primaryStage) {
        VBox menuBox = setupMenuBox();
        HBox buttonsBox = setupButtonsBox();
        borderPane.setBottom(buttonsBox);
        borderPane.setLeft(menuBox);

        // Initially set the profile content
        borderPane.setCenter(createProfileContent());

        Image forkEm = new Image("file:src/main/resources/office/project360/Image_Fork'em.png");
        BackgroundImage bgImg = new BackgroundImage(forkEm,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));
        ImageView imageView = new ImageView(forkEm);
        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        imageView.fitHeightProperty().bind(primaryStage.heightProperty());
        borderPane.setBackground(new Background(bgImg));

        Color backgroundColor = Color.web("#32051e");
        BackgroundFill backgroundFill = new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        menuBox.setBackground(background);
        buttonsBox.setBackground(background);

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setTitle("Nurse Dashboard");
        primaryStage.setScene(scene);
    }

    private HBox setupButtonsBox() {
        HBox buttonsBox = new HBox(13);
        buttonsBox.setPadding(new Insets(20));
        buttonsBox.setAlignment(Pos.CENTER);
        Button btnBack = new Button("Back");
        Button btnLogOut = new Button("Log Out");

        btnBack.setOnMouseEntered(e-> btnBack.setCursor(Cursor.HAND));
        btnBack.setOnMouseExited(e -> btnBack.setCursor(Cursor.DEFAULT));
        btnLogOut.setOnMouseEntered(e-> btnLogOut.setCursor(Cursor.HAND));
        btnLogOut.setOnMouseExited(e -> btnLogOut.setCursor(Cursor.DEFAULT));

        buttonsBox.getChildren().addAll(btnBack, btnLogOut);
        return buttonsBox;
    }

    private VBox setupMenuBox() {
        VBox menuBox = new VBox(10);
        menuBox.setPadding(new Insets(20));
        Label lblProfile = new Label("Profile");
        Label lblPatientVitals = new Label("Patient's Vitals");
        Label lblHealthHistory = new Label("Health History");
        Label lblMessages = new Label("Messages");
        Label lblInsuranceCard = new Label("Insurance Card");

        lblProfile.setOnMouseClicked(event -> borderPane.setCenter(createProfileContent()));
        lblPatientVitals.setOnMouseClicked(event -> borderPane.setCenter(createPatientVitalsContent()));
        lblHealthHistory.setOnMouseClicked(event -> borderPane.setCenter(createHealthHistoryContent()));
        lblMessages.setOnMouseClicked(event -> borderPane.setCenter(createMessagesContent()));
//        lblInsuranceCard.setOnMouseClicked(event -> borderPane.setCenter(createInsuranceCardContent()));
//        Implement createInsuranceCardContent() or similar methods for handling clicks on other labels

        for (Label label : new Label[]{lblProfile, lblPatientVitals, lblHealthHistory, lblMessages, lblInsuranceCard}) {
            label.setCursor(Cursor.HAND);
            label.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        }

        menuBox.getChildren().addAll(lblProfile, lblPatientVitals, lblHealthHistory, lblMessages, lblInsuranceCard);
        return menuBox;
    }

    private GridPane createProfileContent() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.TOP_CENTER);

        Label lblUsername = new Label("Username:");
        lblUsername.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Set the text color to #858585
        grid.add(lblUsername, 0, 0);
        grid.add(new TextField(), 1, 0);

        Label lblName = new Label("Name:");
        lblName.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Set the text color to #858585
        grid.add(lblName, 0, 1);
        grid.add(new TextField(), 1, 1);

        Label lblDateOfBirth = new Label("Date of Birth:");
        lblDateOfBirth.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Set the text color to #858585
        grid.add(lblDateOfBirth, 0, 2);
        grid.add(new TextField(), 1, 2);

        Label lblEmail = new Label("Email:");
        lblEmail.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Set the text color to #858585
        grid.add(lblEmail, 0, 3);
        grid.add(new TextField(), 1, 3);

        Label lblPhoneNumber = new Label("Phone Number:");
        lblPhoneNumber.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Set the text color to #858585
        grid.add(lblPhoneNumber, 0, 4);
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
        grid.setAlignment(Pos.TOP_CENTER);

        Label lblWeight = new Label("Weight:");
        lblWeight.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Make text black and bold
        grid.add(lblWeight, 0, 0);
        grid.add(new TextField(), 1, 0);

        Label lblHeight = new Label("Height:");
        lblHeight.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Make text black and bold
        grid.add(lblHeight, 0, 1);
        grid.add(new TextField(), 1, 1);

        Label lblBodyTemperature = new Label("Body Temperature:");
        lblBodyTemperature.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Make text black and bold
        grid.add(lblBodyTemperature, 0, 2);
        grid.add(new TextField(), 1, 2);

        Label lblBloodPressure = new Label("Blood Pressure:");
        lblBloodPressure.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Make text black and bold
        grid.add(lblBloodPressure, 0, 3);
        grid.add(new TextField(), 1, 3);

        Label lblPatientAge = new Label("Patient Age:");
        lblPatientAge.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Make text black and bold
        grid.add(lblPatientAge, 0, 4);
        grid.add(new TextField(), 1, 4);

        Button btnSubmitVitals = new Button("Submit Vitals");
        grid.add(btnSubmitVitals, 1, 5);
        GridPane.setHalignment(btnSubmitVitals, HPos.RIGHT);

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
        grid.setAlignment(Pos.TOP_CENTER);

        Label lblMedicalHistory = new Label("Patient's Medical History");
        lblMedicalHistory.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Make text black and bold
        grid.add(lblMedicalHistory, 0, 0, 2, 1);

        Label lblPreviousHealthIssues = new Label("Previous Health Issues");
        lblPreviousHealthIssues.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Make text black and bold
        grid.add(lblPreviousHealthIssues, 0, 1);
        grid.add(new TextField(), 0, 2);

        Label lblPrescribedMedications = new Label("Previously Prescribed Medications");
        lblPrescribedMedications.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Make text black and bold
        grid.add(lblPrescribedMedications, 0, 3);
        grid.add(new TextField(), 0, 4);

        Label lblImmunizationHistory = new Label("Immunization History");
        lblImmunizationHistory.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Make text black and bold
        grid.add(lblImmunizationHistory, 0, 5);
        grid.add(new TextField(), 0, 6);

        Label lblKnownAllergies = new Label("Known Allergies");
        lblKnownAllergies.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Make text black and bold
        grid.add(lblKnownAllergies, 0, 7);
        TextField allergiesField = new TextField();
        grid.add(allergiesField, 0, 8);
        Button btnAddAllergy = new Button("Add Allergy");
        grid.add(btnAddAllergy, 1, 8);

        Label lblHealthConcerns = new Label("Health Concerns");
        lblHealthConcerns.setStyle("-fx-text-fill: black; -fx-font-weight: bold;"); // Make text black and bold
        grid.add(lblHealthConcerns, 0, 9);
        TextField healthConcernsField = new TextField();
        grid.add(healthConcernsField, 0, 10);
        Button btnAddHealthConcern = new Button("Add Health Concern");
        grid.add(btnAddHealthConcern, 1, 10);

        Button btnSave = new Button("Save Changes");
        Button btnCancel = new Button("Cancel");
        grid.add(btnSave, 0, 11);
        grid.add(btnCancel, 1, 11);

        // Handle button actions here
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

    }