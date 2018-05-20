import javafx.application.Platform;
import javafx.concurrent.Task;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class LayerConnector
{
    Server server;
    Controller controller;
    private HashMap<String,PrintWriter> writers=new HashMap<String,PrintWriter>();
    LayerConnector(Controller controller){this.controller=controller;}
    void makeConnection(int port, String id)
    {
        Thread thread = new Thread(() -> {

            Socket socket = null;
            BufferedReader in =null;
            PrintWriter  out = null;
            try {
                socket = new Socket("localhost", port);
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                writers.put(id,out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    String line = in.readLine();
                    controller.getListViewMessages().getItems().add(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    void initialize(String name, int port)
    {
        server=new Server(name,port,controller);
    }
    private HashMap<Character,SOAPMessage> divideMessage(SOAPMessage message)
    {
        HashMap<Character,SOAPMessage> hashOfMessages=new HashMap<Character, SOAPMessage>();
        String typeOfMessage="";
        Node sender=null;
        try {
            for (Iterator<Node> i= message.getSOAPHeader().getChildElements();i.hasNext();)
            {
                Node element=i.next();
                if(element.getLocalName().equals("typeOfMessage"))
                {
                    typeOfMessage=element.getNamespaceURI();
                }
                else if(element.getLocalName().equals("sender"))
                {
                    sender=element;
                }
                else
                {
                    if(hashOfMessages.get(element.getNamespaceURI().charAt(1))==null )
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
                        Character layer=element.getNamespaceURI().charAt(1);
                        QName receiver=new QName(element.getNamespaceURI(),"receiver");
                        soapHeader.addChildElement(receiver);
                        soapMessage.saveChanges();
                        Iterator<Node> mess=message.getSOAPBody().getChildElements();
                        SOAPElement msg=soapBody.addChildElement("message").addTextNode(mess.next().getTextContent());
                        hashOfMessages.put(layer,soapMessage);
                    }
                    else
                    {
                        QName receiver=new QName(element.getNamespaceURI(),"receiver");
                        hashOfMessages.get(element.getNamespaceURI().charAt(1)).getSOAPHeader().addChildElement(receiver);
                    }
                }


            }
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        for(SOAPMessage messages:hashOfMessages.values())
        {
            QName senderQ=new QName(sender.getNamespaceURI(),"sender");
            QName typeOfMessageQ=new QName(typeOfMessage,"typeOfMessage");
            try {
                messages.getSOAPHeader().addChildElement(senderQ);
                messages.getSOAPHeader().addChildElement(typeOfMessageQ);
            } catch (SOAPException e) {
                e.printStackTrace();
            }
        }
        return hashOfMessages;
    }
    private String soapMessageToString(SOAPMessage message) throws Exception {
        StringWriter sw = new StringWriter();
        TransformerFactory.newInstance().newTransformer().transform(
                new DOMSource(message.getSOAPPart()),
                new StreamResult(sw));
        return sw.toString();
    }
    private boolean selfMessage(SOAPMessage message)
    {
        try {
            Node sender=null;
            boolean selfMessage=false;
            int receivers=0;
            for (Iterator<Node> i= message.getSOAPHeader().getChildElements();i.hasNext();)
            {
                receivers++;
                Node element=i.next();
                if(element.getLocalName().equals("sender"))
                {
                    sender=element;
                }
                if(element.getNamespaceURI().equals(controller.getId())&&sender!=element)
                {
                    message.getSOAPHeader().removeChild(element);
                    message.saveChanges();
                    selfMessage=true;
                }
            }
            if(selfMessage)
            {
                Iterator<Node> mess=message.getSOAPBody().getChildElements();
                controller.updateListView(sender.getNamespaceURI()+": "+mess.next().getTextContent());
            }
            if(receivers==2&&selfMessage)return true;
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        return false;
    }
    private void printCommunicate(SOAPMessage message)
    {
        String sender="";
        String typeOfMessage="";
        String receivers="";
        try {
            for (Iterator<Node> i= message.getSOAPHeader().getChildElements();i.hasNext();)
            {
                Node element=i.next();
                if(element.getLocalName().equals("typeOfMessage"))
                {
                    typeOfMessage=element.getNamespaceURI();
                }
                else if(element.getLocalName().equals("sender"))
                {
                    sender=element.getNamespaceURI();
                }
                else
                {
                    receivers+=", "+element.getNamespaceURI();
                }
            }
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        try {
            Iterator<Node> mess=message.getSOAPBody().getChildElements();
            String comunicate="\nWiadomosc wyslana przez "+sender+" do "+receivers+" "+typeOfMessage;
            controller.updateListView(comunicate);
        } catch (SOAPException e) {
            e.printStackTrace();
        }

    }
    void sendMsg(String msg)
    {
        InputStream is = new ByteArrayInputStream(msg.getBytes());
        SOAPMessage message =null;
        MessageFactory factory =null;
        try {
            factory = MessageFactory.newInstance();
            message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(msg.getBytes(Charset.forName("UTF-8"))));
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printCommunicate(message);
        if(selfMessage(message))return;
        HashMap<Character,SOAPMessage> dividedMessages=divideMessage(message);
        ArrayList<Character> myConnectors=new ArrayList<Character>();
        for(String name:writers.keySet())
        {
            myConnectors.add(name.charAt(1));
        }
        for(Character key:dividedMessages.keySet())
        {
            if(myConnectors.contains(key))
            {
                for(char j='X';j<='Z';j++)
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(j);
                    stringBuilder.append(key);
                    if (writers.get(stringBuilder.toString())!=null)
                    {
                        try {
                            writers.get(stringBuilder.toString()).println(soapMessageToString(dividedMessages.get(key)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
            else if(writers.size()==1)
            {

                try {
                    writers.values().iterator().next().println(soapMessageToString(dividedMessages.get(key)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
            {
                for(Character connects:myConnectors)
                {
                    if(!connects.equals(controller.getId().charAt(1)))
                    {

                        for(char j='X';j<='Z';j++)
                        {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(j);
                            stringBuilder.append(connects);

                            if (writers.get(stringBuilder.toString())!=null)
                            {
                                try {
                                    writers.get(stringBuilder.toString()).println(soapMessageToString(dividedMessages.get(key)));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

}
