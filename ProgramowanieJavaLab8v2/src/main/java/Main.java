import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;

public class Main extends Application {
    private Controller mainController;
    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("StageTemplate.fxml"));
        Parent root = fxmlLoader.load();
        mainController = fxmlLoader.getController();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 630, 380));
        //primaryStage.show();
        int port=2222;
        HashMap<Integer,Controller> controllers=new HashMap<Integer,Controller>();
        for (int i=1;i<=3;i++)
        {
            for(char j='X';j<='Z';j++)
            {
                FXMLLoader loader =new FXMLLoader();
                loader.setLocation(getClass().getResource("StageTemplate.fxml"));
                Stage stage=new Stage();
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setTitle(j+""+i);
                stage.setScene(new Scene((Pane)loader.load()));
                Controller controller = loader.<Controller>getController();
                controller.setLayerConnector(new LayerConnector(controller));
                controller.getLayerConnector().initialize("localhost",port);
                controller.initialization();
                controller.setId(j+""+i);
                stage.show();
                controllers.put(port,controller);
                port++;
            }
        }
        port=port-9;
        for (int i=1;i<=3;i++)
        {
            for(char j='Y';j<='Z';j++)
            {
                Controller controller=controllers.get(port);
                controller.layerConnector.makeConnection(port+1,j+""+i);
                port++;
            }
            Controller controller=controllers.get(port);
            controller.layerConnector.makeConnection(port-2,"X"+i);
            port++;
        }
        port=port-9;
        for(int i=1;i<=3;i++)
        {
            Controller controller=controllers.get(port);
            if(i<3)
            {
                controller.layerConnector.makeConnection(port+3,'X'+""+String.valueOf(i+1));
            }
            if(i>1)
            {
                controller.layerConnector.makeConnection(port-3,'X'+""+String.valueOf(i-1));
            }
            port+=3;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
