package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

public class ControllerStuff implements Initializable {

    @FXML
    private TableView<TableViewStuff> tableStuff;
    @FXML
    private TableColumn<TableViewStuff, String> columnType;
    @FXML
    private TableColumn<TableViewStuff, String> columnName;
    @FXML
    private TableColumn<TableViewStuff, String> columnDescription;
    @FXML
    private TableColumn<TableViewStuff, ImageView> columnImage;

    @FXML
    private TextField inputDescription;

    @FXML
    private TextField inputType;

    @FXML
    private TextField inputName;

    @FXML
    private Button buttonDelete;
    @FXML
    private Button saveState;
    @FXML
    private Label labelType;
    @FXML
    private Label labelName;
    @FXML
    private Label labelDescription;
    @FXML
    private Button buttonAddImage;
    @FXML
    private Button buttonAdd;
    @FXML
    ComboBox comboSetLanguage;

    private String jsonStuffInfo = "Resources/stuffInfo.json";
    private String jsonStuffString = "";
    List<Stuff> stuffList;
    Stuff newStuff = new Stuff();

    private Locale plLocale = new Locale("pl", "PL");
    private Locale enLocale = new Locale("en", "GB");

    private ResourceBundle bundle;

    public String getJsonStuffString() {
        return jsonStuffString;
    }

    public void setJsonStuffString(String jsonStuffString) {
        this.jsonStuffString = jsonStuffString;
    }

    public String getJsonStuffInfo() {
        return jsonStuffInfo;
    }

    public void setJsonStuffInfo(String jsonStuff) {
        this.jsonStuffInfo = jsonStuff;
    }

    public String chooseTemplate()
    {

        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getDirectory() + dialog.getFile();
        return file;
    }

    public void readFile() throws FileNotFoundException {
        String jsonPath = chooseTemplate();
        setJsonStuffInfo(jsonPath);

        File file = new File(getJsonStuffInfo());
        Scanner sc = new Scanner(file);

        while(sc.hasNextLine()) {
            setJsonStuffString(getJsonStuffString()+sc.nextLine());
        }

        String temp = "";
        temp = getJsonStuffString().replace("\t", "");
        setJsonStuffString(temp);

    }

    public void getJson() throws JSONException, FileNotFoundException {

        readFile();
        Stuff[] stuffs = new Gson().fromJson(getJsonStuffString(), Stuff[].class);
        stuffList = new ArrayList<Stuff>(Arrays.asList(stuffs));;
        setTableStuff();
    }

    public void getStuffToTable(ObservableList<TableViewStuff> data)
    {
        for (int i = 0; i < stuffList.size(); i++)
        {
            TableViewStuff tableViewStuffTemp = new TableViewStuff(stuffList.get(i).getId(), stuffList.get(i).getType(), stuffList.get(i).getName(), stuffList.get(i).getDescription(),
            new ImageView(new Image(stuffList.get(i).getImgPath())));
            data.add(tableViewStuffTemp);
        }
    }

    public void deleteButton()
    {
        TableViewStuff selectedItem = tableStuff.getSelectionModel().getSelectedItem();
        tableStuff.getItems().remove(selectedItem);
        System.out.println(selectedItem.getId());
        for(int i=0; i<stuffList.size(); i++)
        {
            if(stuffList.get(i).getId() == selectedItem.getId())
            {
                stuffList.remove(i);
            }
        }
    }
    public void setTableStuff ()
    {
        bundle = ResourceBundle.getBundle("rBundles.languages",Locale.getDefault());
        final ObservableList<TableViewStuff> data =
                FXCollections.observableArrayList();
        getStuffToTable(data);

        columnName.setMinWidth(100);
        columnName.setCellValueFactory(new PropertyValueFactory<TableViewStuff, String>("name"));

        columnDescription.setMinWidth(100);
        columnDescription.setCellValueFactory(new PropertyValueFactory<TableViewStuff, String>("description"));

        columnType.setMinWidth(100);
        columnType.setCellValueFactory(new PropertyValueFactory<TableViewStuff, String>("type"));

        columnImage.setCellValueFactory(new PropertyValueFactory<TableViewStuff, ImageView>("image"));
        columnImage.setPrefWidth(300);

        tableStuff.setItems(data);

    }

    public void addItem(){
        passStuff();
        stuffList.add(newStuff);
        int m = stuffList.size() -1;
        TableViewStuff stuffTemp = new TableViewStuff(stuffList.get(m).getId(), stuffList.get(m).getType(), stuffList.get(m).getName(), stuffList.get(m).getDescription(),
                new ImageView(new Image(stuffList.get(m).getImgPath())));
        tableStuff.getItems().add(stuffTemp);
        clearBoxes();
    }

    public void clearBoxes()
    {
        inputType.setText("");
        inputDescription.setText("");
        inputName.setText("");
    }
    public void saveJson()
    {
        System.out.println(getJsonStuffInfo());
        try (Writer writer = new FileWriter(getJsonStuffInfo())) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(stuffList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String chooseTemplateFile()
    {

        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = "/" + dialog.getFile();
        return file;
    }
    public void addImage()
    {
        newStuff.setImgPath(chooseTemplateFile());
    }
    public void passStuff()
    {
        newStuff.setId(stuffList.size());
        newStuff.setType(inputType.getText());
        newStuff.setName(inputName.getText());
        newStuff.setDescription(inputDescription.getText());
    }

    public void readFile2() throws FileNotFoundException {
        String jsonPath;
        if(comboSetLanguage.getSelectionModel().getSelectedIndex()==1)
        {
            jsonPath = "C:\\Users\\Student226123\\Desktop\\Zajecia\\ProgramowanieJava\\ProgramowanieJavaLab2\\stuffInfoEng.json";
        }
        else
        {
            jsonPath = "C:\\Users\\Student226123\\Desktop\\Zajecia\\ProgramowanieJava\\ProgramowanieJavaLab2\\stuffInfo.json";

        }
        setJsonStuffInfo(jsonPath);

        File file = new File(getJsonStuffInfo());
        Scanner sc = new Scanner(file);
        setJsonStuffString("");
        while(sc.hasNextLine()) {
            setJsonStuffString(getJsonStuffString()+sc.nextLine());
        }

        String temp = "";
        temp = getJsonStuffString().replace("\t", "");
        setJsonStuffString(temp);

    }

    public void getJson2() throws JSONException, FileNotFoundException {

        readFile2();
        Stuff[] stuffs = new Gson().fromJson(getJsonStuffString(), Stuff[].class);
        stuffList = new ArrayList<Stuff>(Arrays.asList(stuffs));;
        setTableStuff();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = ResourceBundle.getBundle("rBundles.languages",Locale.getDefault());
        List<String> list = new ArrayList<String>();
        list.add("Polski");
        list.add("English");
        ObservableList obList = FXCollections.observableList(list);
        comboSetLanguage.getItems().clear();
        comboSetLanguage.setItems(obList);
        labelType.setText(bundle.getString("labelType"));
        labelDescription.setText(bundle.getString("labelDescription"));
        labelName.setText(bundle.getString("labelName"));
        buttonAddImage.setText(bundle.getString("buttonAddPic"));
        buttonDelete.setText(bundle.getString("buttonDeleteItem"));
        saveState.setText(bundle.getString("saveState"));
        buttonAdd.setText(bundle.getString("buttonAddItem"));
    }

    public void selectLanguage() throws FileNotFoundException {
        try
        {
            getJson2();
            setGUI();
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,"Wybór może wystąpić tylko raz");
        }

    }

    public void setGUI()
    {
        bundle = ResourceBundle.getBundle("rBundles.languages",Locale.getDefault());

        if(comboSetLanguage.getSelectionModel().getSelectedIndex()==0)
        {
            Locale.setDefault(enLocale);
            Stage stage = (Stage) buttonAdd.getScene().getWindow();
            labelType.setText(bundle.getString("labelType"));
            labelDescription.setText(bundle.getString("labelDescription"));
            labelName.setText(bundle.getString("labelName"));
            buttonAddImage.setText(bundle.getString("buttonAddPic"));
            buttonDelete.setText(bundle.getString("buttonDeleteItem"));
            saveState.setText(bundle.getString("saveState"));
            buttonAdd.setText(bundle.getString("buttonAddItem"));
            stage.setTitle(bundle.getString("title"));
        }
        else
        {
            Locale.setDefault(plLocale);
            Stage stage = (Stage) buttonAdd.getScene().getWindow();
            labelType.setText(bundle.getString("labelType"));
            labelDescription.setText(bundle.getString("labelDescription"));
            labelName.setText(bundle.getString("labelName"));
            buttonAddImage.setText(bundle.getString("buttonAddPic"));
            buttonDelete.setText(bundle.getString("buttonDeleteItem"));
            saveState.setText(bundle.getString("saveState"));
            buttonAdd.setText(bundle.getString("buttonAddItem"));
            stage.setTitle(bundle.getString("title"));
        }
    }

}
