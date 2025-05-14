package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file from the Main package
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main/MainView.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 700, 500);
            scene.getStylesheets().add(getClass().getResource("/Main/styles.css").toExternalForm());

            primaryStage.setTitle("📊 RetailRadar - Product Insights");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Failed to load MainView.fxml. Please check the file path.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
