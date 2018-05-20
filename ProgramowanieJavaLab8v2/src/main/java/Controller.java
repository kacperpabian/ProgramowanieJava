import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

public class Controller
{
    @FXML
    private ListView listViewMessages =new ListView();
    @FXML
    private Button buttonSend;
    @FXML
    private TextField textFieldMessage;
    @FXML
    private CheckBox broadcast;
    @FXML
    private CheckBox unicast;
    @FXML
    private CheckBox layerbroadcast;
    @FXML
    private ComboBox chooseLayer;
    @FXML
    private ComboBox chooseReceiver;

    private String id;

    LayerConnector layerConnector;

    private int typeChoosen=0;

    public Controller(){};

    public LayerConnector getLayerConnector() {
        return layerConnector;
    }

    public void brnSendActionPerformed(ActionEvent event)
    {
        MessageFactory messageFactory = null;
        SOAPMessage soapMessage = null;
        SOAPPart soapPart= null;
        SOAPEnvelope envelope= null;
        SOAPBody soapBody = null;
        SOAPHeader soapHeader= null;
        try {
            messageFactory = MessageFactory.newInstance();
            soapMessage = messageFactory.createMessage();
            soapPart = soapMessage.getSOAPPart();
            envelope = soapPart.getEnvelope();
            soapBody = envelope.getBody();
            soapHeader= envelope.getHeader();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        switch (typeChoosen) {
            case 1:
                for(int start=1;start<=3;start++)
                {
                    for(char i='X';i<='Z';i++)
                    {
                        try {
                            QName element=new QName(i+""+start,"receiver");
                            soapHeader.addChildElement(element);
                        } catch (SOAPException e) {
                            e.printStackTrace();
                        }
                    }

                }
                QName type=new QName("rozgloszeniowy","typeOfMessage");
                try {
                    soapHeader.addChildElement(type);
                } catch (SOAPException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                for(char i='X';i<='Z';i++)
                {
                    try {
                        QName element=new QName(i+chooseLayer.getSelectionModel().getSelectedItem().toString(),"receiver");
                        soapHeader.addChildElement(element);
                    } catch (SOAPException e) {
                        e.printStackTrace();
                    }
                }
                QName type2=new QName("warstwowy","typeOfMessage");
                try {
                    soapHeader.addChildElement(type2);
                } catch (SOAPException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    QName element=new QName(chooseReceiver.getSelectionModel().getSelectedItem().toString(),"receiver");
                    soapHeader.addChildElement(element);
                } catch (SOAPException e) {
                    e.printStackTrace();
                }
                QName type3=new QName("bezposredni","typeOfMessage");
                try {
                    soapHeader.addChildElement(type3);
                } catch (SOAPException e) {
                    e.printStackTrace();
                }
                break;
            default:
                return;
        }
        try {
            SOAPElement msg=soapBody.addChildElement("message").addTextNode(textFieldMessage.getText());
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        QName element=new QName(getId(),"sender");
        try {
            soapHeader.addChildElement(element);
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        StringWriter sw = new StringWriter();

        try {
            TransformerFactory.newInstance().newTransformer().transform(
                    new DOMSource(soapMessage.getSOAPPart()),
                    new StreamResult(sw));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

        layerConnector.sendMsg(sw.toString());
    }
    public void setLayerConnector(LayerConnector layerConnector) {

        this.layerConnector = layerConnector;
    }

    public ListView getListViewMessages() {
        return listViewMessages;
    }

    public void initialization()
    {
        for (int start=1;start<=3;start++)
        {
            chooseLayer.getItems().add(start);
            for(char i='X';i<='Z';i++)
            {
                chooseReceiver.getItems().add(i+""+start);
            }
        }
    }
    public void checkBoxesActionPerformed(ActionEvent event)
    {
        CheckBox check=(CheckBox)event.getSource();
        switch (check.getId())
        {
            case "broadcast":
            {
                unicast.setSelected(false);
                layerbroadcast.setSelected(false);
                chooseLayer.setDisable(true);
                chooseReceiver.setDisable(true);
                typeChoosen=1;
                break;
            }
            case "layerbroadcast":
            {
                broadcast.setSelected(false);
                unicast.setSelected(false);
                chooseLayer.setDisable(false);
                chooseReceiver.setDisable(true);
                typeChoosen=2;
                break;
            }
            case "unicast":
            {
                broadcast.setSelected(false);
                layerbroadcast.setSelected(false);
                chooseReceiver.setDisable(false);
                chooseLayer.setDisable(true);
                typeChoosen=3;
                break;
            }
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void updateListView(String msg)
    {
        Task<Void> task = new Task<>() {

            @Override
            protected Void call() {
                {
                    Platform.runLater(() -> listViewMessages.getItems().add(msg));
                    return null;
                }
            }

        };
        task.run();
    }
}
