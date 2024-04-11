package office.project360;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MessageApp extends Application {
    private String username; // Add a field to store the username

    // Constructor to accept the username
    public MessageApp(String username) {
        this.username = username;
    }
    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        Button sendMessageButton = new Button("Send Message");
        sendMessageButton.setOnAction(e -> {
            MessageApplication messageApp = new MessageApplication(username);
            Stage messageStage = new Stage();
            messageApp.start(messageStage);
        });

        Button receiveMessageButton = new Button("Receive Message");
        receiveMessageButton.setOnAction(e -> {
            ReceivedMessagesApplication receivedMessagesApp = new ReceivedMessagesApplication(username);
            Stage receivedMessagesStage = new Stage();
            receivedMessagesApp.start(receivedMessagesStage);
        });

        // Layout for buttons
        VBox root = new VBox(10);
        root.getChildren().addAll(sendMessageButton, receiveMessageButton);

        // Set up the scene
        Scene scene = new Scene(root, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Message App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
