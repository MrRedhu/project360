package office.project360;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class MessageApplication extends Application {
    private String username; // Add a field to store the username

    // Constructor to accept the username
    public MessageApplication(String username) {
        this.username = username;
    }
    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:sqlite:identifier.sqlite";


    private TextField senderUsernameField;
    private TextField receiverUsernameField;

    private void sendMessage(String senderUsername, String receiverUsername, String subject, String messageText) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            // Check if receiver username exists in the users table
            if (!isValidReceiverUsername(connection, receiverUsername)) {
                showAlert(Alert.AlertType.ERROR, "Invalid Receiver Username", "The receiver username does not exist.");
                return;
            }

            String sql = "INSERT INTO Messages (SenderUsername, ReceiverUsername, Subject, MessageText) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, senderUsername);
                statement.setString(2, receiverUsername);
                statement.setString(3, subject);
                statement.setString(4, messageText);
                statement.executeUpdate();
            }
            showAlert(Alert.AlertType.INFORMATION, "Message Sent", "Message sent successfully.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to send message: " + e.getMessage());
        }
    }

    private boolean isValidReceiverUsername(Connection connection, String receiverUsername) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE Username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, receiverUsername);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    @Override
    public void start(Stage primaryStage) {
        Label receiverUsernameLabel = new Label("Receiver Username:");
        receiverUsernameField = new TextField();

        Label subjectLabel = new Label("Subject:");
        TextField subjectField = new TextField();

        Label messageLabel = new Label("Message:");
        TextArea messageArea = new TextArea();
        messageArea.setWrapText(true);
        messageArea.setPrefRowCount(10);

        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> {
            String receiverUsername = receiverUsernameField.getText();
            String subject = subjectField.getText();
            String messageText = messageArea.getText();

            if (receiverUsername.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter receiver username.");
                return;
            }

            sendMessage(username, receiverUsername, subject, messageText);
        });

        VBox root = new VBox(10);
        root.getChildren().addAll(
                receiverUsernameLabel, receiverUsernameField,
                subjectLabel, subjectField,
                messageLabel, messageArea,
                sendButton
        );

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Messaging App");
        primaryStage.show();
    }


    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}