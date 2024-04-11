package office.project360;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MessageApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        Button sendMessageButton = new Button("Send Message");
        sendMessageButton.setOnAction(e -> {
            MessageApplication messageApp = new MessageApplication();
            Stage messageStage = new Stage();
            messageApp.start(messageStage);
        });

        Button receiveMessageButton = new Button("Receive Message");
        receiveMessageButton.setOnAction(e -> {
            ReceivedMessagesApplication receivedMessagesApp = new ReceivedMessagesApplication();
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
