package office.project360;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageApplication extends Application {

    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:sqlite:identifier.sqlite";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    private void sendMessage(int senderId, int receiverId, String subject, String messageText) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO Messages (SenderID, ReceiverID, MessageText, Subject) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, senderId);
                statement.setInt(2, receiverId);
                statement.setString(3, messageText);
                statement.setString(4, subject);
                statement.executeUpdate();
            }
            showAlert(Alert.AlertType.INFORMATION, "Message Sent", "Message sent successfully.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to send message: " + e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        Label senderIdLabel = new Label("Sender ID:");
        TextField senderIdField = new TextField();

        Label receiverIdLabel = new Label("Receiver ID:");
        TextField receiverIdField = new TextField();

        Label subjectLabel = new Label("Subject:");
        TextField subjectField = new TextField();

        Label messageLabel = new Label("Message:");
        TextArea messageArea = new TextArea();
        messageArea.setWrapText(true); // Enable text wrapping for long messages
        messageArea.setPrefRowCount(10); // Set preferred row count for message area

        Button sendButton = new Button("Send");

        // Set event handler for send button
        sendButton.setOnAction(e -> {
            try {
                int senderId = Integer.parseInt(senderIdField.getText());
                int receiverId = Integer.parseInt(receiverIdField.getText());
                String subject = subjectField.getText();
                String messageText = messageArea.getText();

                // Check if receiverId is valid (exists in the database)
                // If not valid, show an error message
                if (!isValidReceiverId(receiverId)) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Receiver ID", "The receiver ID is not valid.");
                    return;
                }

                // Send the message
                sendMessage(senderId, receiverId, subject, messageText);
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid ID", "Please enter valid numeric IDs.");
            }
        });

        // Create layout
        VBox root = new VBox(10);
        root.getChildren().addAll(
                senderIdLabel, senderIdField,
                receiverIdLabel, receiverIdField,
                subjectLabel, subjectField,
                messageLabel, messageArea,
                sendButton
        );

        // Set scene
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Messaging App");
        primaryStage.show();
    }

    private boolean isValidReceiverId(int receiverId) {
        // Implement validation logic to check if the receiverId is valid
        // You can query the database to check if the ID exists
        return true; // Placeholder for demonstration
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
