package office.project360;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class ReceivedMessagesApplication extends Application {

    private String username; // Add a field to store the username

    // Constructor to accept the username
    public ReceivedMessagesApplication(String username) {
        this.username = username;
    }

    // JDBC URL, username, and password
    private static final String JDBC_URL = "jdbc:sqlite:identifier.sqlite";

    public TextField usernameField;
    private TableView<Message> tableView;

    @Override

    public void start(Stage primaryStage) {
        Button showMessagesButton = new Button("Show Messages");
        showMessagesButton.setOnAction(e -> {
            displayReceivedMessages(username);
        });

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                showMessagesButton
        );

        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Message, String> columnSubject = new TableColumn<>("Message");
        columnSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));

        TableColumn<Message, String> columnTimestamp = new TableColumn<>("Timestamp");
        columnTimestamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        TableColumn<Message, Button> columnReply = new TableColumn<>("Reply");
        columnReply.setCellValueFactory(new PropertyValueFactory<>("replyButton"));
        columnReply.setCellFactory(tc -> new TableCell<>() {
            private final Button replyButton = new Button("Reply");

            {
                replyButton.setOnAction(event -> {
                    Message message = getTableView().getItems().get(getIndex());
                    openReplyWindow(message.getFrom(), message.getSubject());
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(replyButton);
                }
            }
        });

        tableView.getColumns().addAll(columnSubject, columnTimestamp, columnReply);

        root.getChildren().add(tableView);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Received Messages");

        tableView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) { // Double-click
                Message selectedMessage = tableView.getSelectionModel().getSelectedItem();
                if (selectedMessage != null) {
                    showFullMessageDialog(selectedMessage);
                }
            }
        });

        primaryStage.show();
    }


    private void displayReceivedMessages(String username) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
            String sql = "SELECT * FROM Messages WHERE ReceiverUsername = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    ObservableList<Message> messages = FXCollections.observableArrayList();
                    while (resultSet.next()) {
                        String senderUsername = resultSet.getString("SenderUsername");
                        String subject = resultSet.getString("Subject");
                        String messageText = resultSet.getString("MessageText");
                        Timestamp timestamp = resultSet.getTimestamp("Timestamp");
                        messages.add(new Message(senderUsername, subject, messageText, timestamp.toString()));
                    }
                    tableView.setItems(messages);
                    System.out.println("Messages retrieved: " + messages.size());
                }
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to fetch messages: " + e.getMessage());
            e.printStackTrace(); // Print the exception stack trace for debugging
        }
    }

    private void showFullMessageDialog(Message message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message Details");
        alert.setHeaderText(null);
        alert.setContentText("Sender: " + message.getFrom() + "\nReceiver: " + username + "\nSubject: " + message.getSubject() + "\nTimestamp: " + message.getTimestamp() + "\n\nMessage:\n" + message.getMessageText());
        alert.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void openReplyWindow(String receiverUsername, String subject) {
        Stage replyStage = new Stage();
        VBox replyRoot = new VBox(10);
        replyRoot.setPadding(new Insets(10));

        Label receiverLabel = new Label("To: " + receiverUsername);
        Label subjectLabel = new Label("Subject: " + subject);

        TextArea messageArea = new TextArea();
        messageArea.setWrapText(true);
        messageArea.setPrefRowCount(10);

        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> {
            sendMessage(username, receiverUsername, subject, messageArea.getText());
            replyStage.close();
        });

        replyRoot.getChildren().addAll(receiverLabel, subjectLabel, messageArea, sendButton);
        Scene replyScene = new Scene(replyRoot, 400, 300);
        replyStage.setScene(replyScene);
        replyStage.setTitle("Reply");
        replyStage.show();
    }

    private void sendMessage(String senderUsername, String receiverUsername, String subject, String messageText) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL)) {
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

    public static void main(String[] args) {
        launch(args);
    }

    // Define the Message class as a static nested class within ReceivedMessagesApplication
    public static class Message {
        private final String from;
        private final String subject;
        private final String messageText;
        private final String timestamp;
        private final Button replyButton = new Button("Reply");

        public Message(String from, String subject, String messageText, String timestamp) {
            this.from = from;
            this.subject = subject;
            this.messageText = messageText;
            this.timestamp = timestamp;
        }

        public String getFrom() {
            return from;
        }

        public String getSubject() {
            return subject;
        }

        public String getMessageText() {
            return messageText;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public Button getReplyButton() {
            return replyButton;
        }
    }
}