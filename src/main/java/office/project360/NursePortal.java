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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class NursePortal {

    private BorderPane borderPane = new BorderPane();
    private String currentUser = "";

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

    // Database connection parameters
    private static final String DB_URL = "jdbc:sqlite:identifier.sqlite";

    private GridPane createProfileContent() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.TOP_CENTER);

        Label lblUsername = new Label("Username:");
        lblUsername.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblUsername, 0, 0);
        TextField txtUsername = new TextField();
        grid.add(txtUsername, 1, 0);

        Button btnFetchName = new Button("Fetch Name");
        grid.add(btnFetchName, 2, 0);

        Label lblFirstName = new Label("First Name:");
        lblFirstName.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblFirstName, 0, 1);

        // Label for displaying patient's first name
        Label lblFirstNameValue = new Label();
        lblFirstNameValue.setStyle("-fx-text-fill: black;");
        grid.add(lblFirstNameValue, 1, 1);

        Label lblLastName = new Label("Last Name:");
        lblLastName.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblLastName, 0, 2);

        // Label for displaying patient's last name
        Label lblLastNameValue = new Label();
        lblLastNameValue.setStyle("-fx-text-fill: black;");
        grid.add(lblLastNameValue, 1, 2);

        Label lblDateOfBirth = new Label("Date of Birth:");
        lblDateOfBirth.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblDateOfBirth, 0, 3);
        TextField txtDateOfBirth = new TextField(); // Store the reference for later use
        grid.add(txtDateOfBirth, 1, 3);

        Label lblEmail = new Label("Email:");
        lblEmail.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblEmail, 0, 4);
        TextField txtEmail = new TextField(); // Store the reference for later use
        grid.add(txtEmail, 1, 4);

        Label lblPhoneNumber = new Label("Phone Number:");
        lblPhoneNumber.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblPhoneNumber, 0, 5);
        TextField txtPhoneNumber = new TextField(); // Store the reference for later use
        grid.add(txtPhoneNumber, 1, 5);

        Button btnSave = new Button("Save");
        Button btnCancel = new Button("Cancel");
        grid.add(btnSave, 1, 6);
        grid.add(btnCancel, 2, 6);

        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtUsername.setText("");
                lblFirstNameValue.setText("");
                lblLastNameValue.setText("");
                txtDateOfBirth.setText("");
                txtEmail.setText("");
                txtPhoneNumber.setText("");
            }
        });

        // Event handler for Save button
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = txtUsername.getText().trim();
                String dateOfBirth = txtDateOfBirth.getText().trim();
                String email = txtEmail.getText().trim();
                String phoneNumber = txtPhoneNumber.getText().trim();
                if (!username.isEmpty() && !dateOfBirth.isEmpty() && !email.isEmpty() && !phoneNumber.isEmpty()) {
                    updatePatientInfo(username, dateOfBirth, email, phoneNumber);
                    // Reset all fields
                    txtUsername.setText("");
                    lblFirstNameValue.setText("");
                    lblLastNameValue.setText("");
                    txtDateOfBirth.setText("");
                    txtEmail.setText("");
                    txtPhoneNumber.setText("");
                    // Show success alert
                    showAlert(AlertType.INFORMATION, "Success", "Information updated successfully.");
                } else {
                    // Handle empty fields error
                    showAlert(AlertType.ERROR, "Error", "Please fill in all fields.");
                }
            }
        });

        // Event handler for fetching name when button is clicked
        btnFetchName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = txtUsername.getText().trim();
                if (!username.isEmpty()) {
                    String[] names = fetchPatientName(username);
                    if (names != null && names.length == 2) {
                        lblFirstNameValue.setText(names[0]);
                        lblLastNameValue.setText(names[1]);
                    }
                }
            }
        });

        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtUsername.setText("");
                lblFirstNameValue.setText("");
                lblLastNameValue.setText("");
                txtDateOfBirth.setText("");
                txtEmail.setText("");
                txtPhoneNumber.setText("");
            }
        });

        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = txtUsername.getText().trim();
                String dateOfBirth = txtDateOfBirth.getText().trim();
                String email = txtEmail.getText().trim();
                String phoneNumber = txtPhoneNumber.getText().trim();
                if (!username.isEmpty() && !dateOfBirth.isEmpty() && !email.isEmpty() && !phoneNumber.isEmpty()) {
                    updatePatientInfo(username, dateOfBirth, email, phoneNumber);
                    // Reset all fields
                    txtUsername.setText("");
                    lblFirstNameValue.setText("");
                    lblLastNameValue.setText("");
                    txtDateOfBirth.setText("");
                    txtEmail.setText("");
                    txtPhoneNumber.setText("");
                    // Show success alert
                    showAlert(AlertType.INFORMATION, "Success", "Information updated successfully.");
                } else {
                    // Handle empty fields error
                    showAlert(AlertType.ERROR, "Error", "Please fill in all fields.");
                }
            }
        });

        return grid;
    }

    // Method to fetch patient's name from the database based on username
    private String[] fetchPatientName(String username) {
        String[] names = new String[2];
        currentUser = username;

        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String query = "SELECT FirstName, LastName FROM patients WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                names[0] = resultSet.getString("FirstName");
                names[1] = resultSet.getString("LastName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return names;
    }

    // Method to show an alert
    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Method to update patient info in the database
    private void updatePatientInfo(String username, String dateOfBirth, String email, String phoneNumber) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String query = "UPDATE patients SET DateOfBirth = ?, Email = ?, PhoneNumber = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dateOfBirth);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phoneNumber);
            preparedStatement.setString(4, username);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Added Patient Info");
            } else {
                System.out.println("Update Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private GridPane createPatientVitalsContent() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.TOP_CENTER);

        Label lblWeight = new Label("Weight:");
        lblWeight.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblWeight, 0, 0);
        TextField txtWeight = new TextField();
        grid.add(txtWeight, 1, 0);

        Label lblHeight = new Label("Height:");
        lblHeight.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblHeight, 0, 1);
        TextField txtHeight = new TextField();
        grid.add(txtHeight, 1, 1);

        Label lblBodyTemperature = new Label("Body Temperature:");
        lblBodyTemperature.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblBodyTemperature, 0, 2);
        TextField txtBodyTemperature = new TextField();
        grid.add(txtBodyTemperature, 1, 2);

        Label lblBloodPressure = new Label("Blood Pressure:");
        lblBloodPressure.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblBloodPressure, 0, 3);
        TextField txtBloodPressure = new TextField();
        grid.add(txtBloodPressure, 1, 3);

        Label lblPatientAge = new Label("Patient Age:");
        lblPatientAge.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblPatientAge, 0, 4);
        TextField txtPatientAge = new TextField();
        grid.add(txtPatientAge, 1, 4);

        Button btnSubmitVitals = new Button("Submit Vitals");
        grid.add(btnSubmitVitals, 1, 5);
        GridPane.setHalignment(btnSubmitVitals, javafx.geometry.HPos.RIGHT);



        // Event handling for the submit button
        btnSubmitVitals.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String weightStr = txtWeight.getText().trim();
                String heightStr = txtHeight.getText().trim();
                String bodyTemperatureStr = txtBodyTemperature.getText().trim();
                String bloodPressureStr = txtBloodPressure.getText().trim();
                String patientAge = txtPatientAge.getText().trim();

                // Check if any field is empty
                if (weightStr.isEmpty() || heightStr.isEmpty() || bodyTemperatureStr.isEmpty() || bloodPressureStr.isEmpty()) {
                    showAlert(AlertType.ERROR, "Error", "Please fill in all fields.");
                    return;
                }

                try {
                    // Convert strings to double
                    double weight = Double.parseDouble(weightStr);
                    double height = Double.parseDouble(heightStr);
                    double bodyTemperature = Double.parseDouble(bodyTemperatureStr);
                    int age = Integer.parseInt(patientAge);
                    // Update patient table with vitals
                    updatePatientVitals(weight, height, bodyTemperature, bloodPressureStr,age);

                    // Clear all fields
                    txtWeight.clear();
                    txtHeight.clear();
                    txtBodyTemperature.clear();
                    txtBloodPressure.clear();
                    txtPatientAge.clear();

                    // Show success alert
                    showAlert(AlertType.INFORMATION, "Success", "Vitals submitted successfully.");
                } catch (NumberFormatException e) {
                    // Handle invalid input format
                    showAlert(AlertType.ERROR, "Error", "Please enter valid numerical values for weight, height, and body temperature.");
                }
            }
        });


        return grid;
    }


    // Method to update patient vitals in the database
    private void updatePatientVitals(Double weight, Double height, Double bodyTemperature, String bloodPressure, int age) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String query = "UPDATE patients SET Weight = ?, Height = ?, BodyTemperature = ?, BloodPressure = ?, Age = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, weight);
            preparedStatement.setDouble(2, height);
            preparedStatement.setDouble(3, bodyTemperature);
            preparedStatement.setString(4, bloodPressure);
            preparedStatement.setInt(5, age);
            // Assuming patient_id is the primary key of the patient table and is used for identification
            preparedStatement.setString(6, currentUser);
            System.out.println("currentUser");
            System.out.println(currentUser);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Vitals updated successfully.");
            } else {
                System.out.println("Failed to update vitals.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private GridPane createHealthHistoryContent() {

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        grid.setAlignment(Pos.TOP_CENTER);

        Label lblMedicalHistory = new Label("Patient's Medical History");
        lblMedicalHistory.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblMedicalHistory, 0, 0, 2, 1);

        Label lblPreviousHealthIssues = new Label("Previous Health Issues");
        lblPreviousHealthIssues.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblPreviousHealthIssues, 0, 1);
        TextField previousHealthIssuesField = new TextField();
        grid.add(previousHealthIssuesField, 0, 2);

        Label lblPrescribedMedications = new Label("Previously Prescribed Medications");
        lblPrescribedMedications.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblPrescribedMedications, 0, 3);
        TextField prescribedMedicationsField = new TextField();
        grid.add(prescribedMedicationsField, 0, 4);

        Label lblImmunizationHistory = new Label("Immunization History");
        lblImmunizationHistory.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblImmunizationHistory, 0, 5);
        TextField immunizationHistoryField = new TextField();
        grid.add(immunizationHistoryField, 0, 6);

        Label lblKnownAllergies = new Label("Known Allergies");
        lblKnownAllergies.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblKnownAllergies, 0, 7);
        TextField allergiesField = new TextField();
        grid.add(allergiesField, 0, 8);

        Label lblHealthConcerns = new Label("Health Concerns");
        lblHealthConcerns.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
        grid.add(lblHealthConcerns, 0, 9);
        TextField healthConcernsField = new TextField();
        grid.add(healthConcernsField, 0, 10);

        Button btnSave = new Button("Save Changes");
        Button btnCancel = new Button("Cancel");
        grid.add(btnSave, 0, 11);
        grid.add(btnCancel, 1, 11);

        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Clear all fields
                previousHealthIssuesField.clear();
                prescribedMedicationsField.clear();
                immunizationHistoryField.clear();
                allergiesField.clear();
                healthConcernsField.clear();
            }
        });

        // Event handling for the save button
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Get the values from the text fields
                String previousHealthIssues = previousHealthIssuesField.getText().trim();
                String prescribedMedications = prescribedMedicationsField.getText().trim();
                String immunizationHistory = immunizationHistoryField.getText().trim();
                String knownAllergies = allergiesField.getText().trim();
                String healthConcerns = healthConcernsField.getText().trim();

                // Check if any field is empty
                if (previousHealthIssues.isEmpty() || prescribedMedications.isEmpty() || immunizationHistory.isEmpty() || knownAllergies.isEmpty() || healthConcerns.isEmpty()) {
                    showAlert(AlertType.ERROR, "Error", "Please fill in all fields.");
                    return;
                }

                // Update patient table with the values
                updatePatientHealthHistory(previousHealthIssues, prescribedMedications, immunizationHistory, knownAllergies, healthConcerns);

                // Clear all fields
                previousHealthIssuesField.clear();
                prescribedMedicationsField.clear();
                immunizationHistoryField.clear();
                allergiesField.clear();
                healthConcernsField.clear();

                // Show success alert
                showAlert(AlertType.INFORMATION, "Success", "Health history updated successfully.");
            }
        });


        return grid;
    }

    private void updatePatientHealthHistory(String previousHealthIssues, String prescribedMedications, String immunizationHistory, String knownAllergies, String healthConcerns) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            String query = "UPDATE patients SET PreviousHealthIssues = ?, PreviouslyPrescribedMedications = ?, ImmunizationHistory = ?, KnownAllergies = ?, HealthConcerns = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, previousHealthIssues);
            preparedStatement.setString(2, prescribedMedications);
            preparedStatement.setString(3, immunizationHistory);
            preparedStatement.setString(4, knownAllergies);
            preparedStatement.setString(5, healthConcerns);
            // Assuming patient_id is the primary key of the patient table and is used for identification
            preparedStatement.setString(6, currentUser); // Replace patientId with the actual patient ID
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Health history updated successfully.");
            } else {
                System.out.println("Failed to update health history.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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