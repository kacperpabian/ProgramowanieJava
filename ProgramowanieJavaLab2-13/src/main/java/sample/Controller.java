package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Controller {
    @FXML
    private Button welcomeButton;

    public void generate (ActionEvent event) {

        try {
            final Node source = (Node) event.getSource();
            final Stage stage1 = (Stage) source.getScene().getWindow();
            stage1.close();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Stuff2.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            ResourceBundle bundle = ResourceBundle.getBundle("rBundles.languages");
            stage.setTitle(bundle.getString("title"));
            stage.setScene(new Scene(root1));
            stage.getIcons().add(new Image("/icon.png"));
            stage.show();
            stage.setOnCloseRequest(t -> {
                Platform.exit();
                System.exit(0);
            });

        } catch (Exception e) {
            System.out.println("Błąd przy załadowywaniu drugiego okna.");
        }

    }
}
