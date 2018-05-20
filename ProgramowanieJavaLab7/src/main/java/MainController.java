import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.sqlite.SQLiteConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.util.Properties;

public class MainController {
    private Connection con;
    private ResultSet rs;
    private PreparedStatement ps;
    private BacteriaDataBase bacteriaDataBase=new BacteriaDataBase();
    private TableColumn<Bacteria, Integer> firstTableColumn=new TableColumn<Bacteria, Integer>("Genotyp");
    private TableColumn<Bacteria, String> secondTableColumn=new TableColumn<>("Klasa");
    private ObservableList<String> bacteriaToTestList;
    private List<TestBacteria> toTestList=new ArrayList<>();

    @FXML
    private TableView<Bacteria> historyTableView;
    @FXML
    private TextField genotypeTextField;
    @FXML
    private ListView<String> historyListView;

    void getConnection() throws SQLException, ClassNotFoundException{
        Class.forName("org.sqlite.JDBC");

        SQLiteConfig sqLiteConfig = new SQLiteConfig();
        Properties properties = sqLiteConfig.toProperties();
        con = DriverManager.getConnection("jdbc:sqlite:SQLiteBacteria.db", properties);
        //fillDB();
    }


    public void fillDB() throws SQLException, ClassNotFoundException {

            Statement state = con.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='events'");
            if(!res.next()){
                System.out.println("helomoto");
                Statement state1 = con.createStatement();
                Statement state2 = con.createStatement();
                Statement state3 = con.createStatement();
                state1.execute("CREATE TABLE IF NOT EXISTS flagella(id INTEGER PRIMARY KEY AUTOINCREMENT, alpha int, beta int, number int);");
                state2.execute("CREATE TABLE IF NOT EXISTS toughness(id INTEGER PRIMARY KEY AUTOINCREMENT, beta int, gamma int, rank varchar(20));");
                state3.execute("CREATE TABLE IF NOT EXISTS examined(id INTEGER PRIMARY KEY AUTOINCREMENT, genotype int, class varchar(20));");

				PreparedStatement prep = con.prepareStatement("INSERT INTO flagella values(?, ?, ?, ?);");
                prep.setInt(2, 12 );
                prep.setInt(3, 43);
                prep.setInt(4, 1);
                prep.execute();

                prep = con.prepareStatement("INSERT INTO flagella values(?, ?, ?, ?);");
                prep.setInt(2, 33 );
                prep.setInt(3, 24);
                prep.setInt(4, 3);
                prep.execute();

                prep = con.prepareStatement("INSERT INTO flagella values(?, ?, ?, ?);");
                prep.setInt(2, 34 );
                prep.setInt(3, 54);
                prep.setInt(4, 2);
                prep.execute();

                prep = con.prepareStatement("INSERT INTO flagella values(?, ?, ?, ?);");
                prep.setInt(2, 32 );
                prep.setInt(3, 43);
                prep.setInt(4, 2);
                prep.execute();

                prep = con.prepareStatement("INSERT INTO toughness values(?, ?, ?, ?);");
                prep.setInt(2, 43 );
                prep.setInt(3, 23);
                prep.setString(4, "d");
                prep.execute();

                prep = con.prepareStatement("INSERT INTO toughness values(?, ?, ?, ?);");
                prep.setInt(2, 24 );
                prep.setInt(3, 43);
                prep.setString(4, "b");
                prep.execute();

                prep = con.prepareStatement("INSERT INTO toughness values(?, ?, ?, ?);");
                prep.setInt(2, 54 );
                prep.setInt(3, 12);
                prep.setString(4, "b");
                prep.execute();

                prep = con.prepareStatement("INSERT INTO toughness values(?, ?, ?, ?);");
                prep.setInt(2, 43 );
                prep.setInt(3, 43);
                prep.setString(4, "a");
                prep.execute();

                prep = con.prepareStatement("INSERT INTO examined values(?, ?, ?);");
                prep.setInt(2, 328734 );
                prep.setString(3, "1d");
                prep.execute();

                prep = con.prepareStatement("INSERT INTO examined values(?, ?, ?);");
                prep.setInt(2, 653313 );
                prep.setString(3, "3c");
                prep.execute();

                prep = con.prepareStatement("INSERT INTO examined values(?, ?, ?);");
                prep.setInt(2, 239322 );
                prep.setString(3, "2a");
                prep.execute();

                prep = con.prepareStatement("INSERT INTO examined values(?, ?, ?);");
                prep.setInt(2, 853211 );
                prep.setString(3, "2a");
                prep.execute();


            }
    }

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        getConnection();
        firstTableColumn.setMinWidth(185);
        secondTableColumn.setMinWidth(185);
        firstTableColumn.setCellValueFactory(new PropertyValueFactory<Bacteria,Integer>("genotype"));
        secondTableColumn.setCellValueFactory(new PropertyValueFactory<Bacteria,String>("classOfBacteria"));
        historyTableView.getColumns().addAll(firstTableColumn,secondTableColumn);
        bacteriaDataBase.setData(historyTableView.getItems());
        bacteriaToTestList=historyListView.getItems();
        updateHistory();
    }


    private void updateHistory(){
        bacteriaDataBase.getData().clear();
        try {
            if(con == null){
                getConnection();
            }
            ps =con.prepareStatement("SELECT * From examined");
            ps.execute();
            rs= ps.getResultSet();
            while (rs.next())
            {
                Bacteria bacteria=new Bacteria();
                bacteria.genotype=rs.getInt(2);
                bacteria.classOfBacteria=rs.getString(3);
                bacteriaDataBase.getData().add(bacteria);
            }
            historyTableView.setItems(bacteriaDataBase.getData());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void saveToXml(){
        updateHistory();
        try {
            JAXBContext jc=JAXBContext.newInstance(BacteriaDataBase.class);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            m.marshal(bacteriaDataBase, new File("Genes.xml"));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void checkBacteria() throws SQLException, ClassNotFoundException {
        TestBacteria testBacteria=new TestBacteria();
        testBacteria.setGenotype(Integer.parseInt(genotypeTextField.getText()));
        insertToExamined(NnAlgorithm(testBacteria));
        updateHistory();
        genotypeTextField.clear();
    }

    public void addToCheckList(){
        TestBacteria testBacteria=new TestBacteria();
        testBacteria.setGenotype(Integer.parseInt(genotypeTextField.getText()));
        bacteriaToTestList.add(String.valueOf(testBacteria.getGenotype()));
        toTestList.add(testBacteria);
        historyListView.setItems(bacteriaToTestList);
        genotypeTextField.clear();
    }

    public Bacteria NnAlgorithm(TestBacteria testBacteria) throws SQLException, ClassNotFoundException {
            Bacteria bacteria=new Bacteria();
            int firstDifference= Integer.MAX_VALUE;
            int secondDifference= Integer.MAX_VALUE;
            int searchAlpha=0;
            int searchBeta=0;
            int searchGamma=0;
            if(con == null){
                getConnection();
            }
            try {
                ps =con.prepareStatement("SELECT alpha From flagella");
                ps.execute();
                rs= ps.getResultSet();
                while (rs.next())
                {
                    if(firstDifference>Math.abs(rs.getInt(1)-testBacteria.getAlpha())){
                        firstDifference=Math.abs(rs.getInt(1)-testBacteria.getAlpha());
                        searchAlpha=rs.getInt(1);
                    }
                }

                ps =con.prepareStatement("SELECT beta From flagella WHERE alpha=(?)");
                ps.setInt(1,searchAlpha);
                ps.execute();
                rs= ps.getResultSet();
                while (rs.next())
                {
                    if(secondDifference>Math.abs(rs.getInt(1)-testBacteria.getBeta())){
                        secondDifference=Math.abs(rs.getInt(1)-testBacteria.getBeta());
                        searchBeta=rs.getInt(1);
                    }
                }

                ps =con.prepareStatement("SELECT number From flagella WHERE alpha=(?) AND beta=(?)");
                ps.setInt(1,searchAlpha);
                ps.setInt(2,searchBeta);
                ps.execute();
                rs= ps.getResultSet();
                rs.next();
                bacteria.setClassOfBacteria(String.valueOf(rs.getInt(1)));

                firstDifference= Integer.MAX_VALUE;
                secondDifference= Integer.MAX_VALUE;

                ps =con.prepareStatement("SELECT beta From toughness");
                ps.execute();
                rs= ps.getResultSet();
                while (rs.next())
                {
                    if(firstDifference>Math.abs(rs.getInt(1)-testBacteria.getBeta())){
                        firstDifference=Math.abs(rs.getInt(1)-testBacteria.getBeta());
                        searchBeta=rs.getInt(1);
                    }
                }

                ps =con.prepareStatement("SELECT gamma From toughness WHERE beta=(?)");
                ps.setInt(1,searchBeta);
                ps.execute();
                rs= ps.getResultSet();
                while (rs.next())
                {
                    if(secondDifference>Math.abs(rs.getInt(1)-testBacteria.getGamma())){
                        secondDifference=Math.abs(rs.getInt(1)-testBacteria.getGamma());
                        searchGamma=rs.getInt(1);
                    }
                }

                ps =con.prepareStatement("SELECT rank From toughness WHERE beta=(?) AND gamma=(?)");
                ps.setInt(1,searchBeta);
                ps.setInt(2,searchGamma);
                ps.execute();
                rs= ps.getResultSet();
                rs.next();
                bacteria.setGenotype(testBacteria.getGenotype());
                bacteria.setClassOfBacteria(bacteria.getClassOfBacteria().concat(rs.getString(1)));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return bacteria;
    }

    private void insertToExamined(Bacteria bacteria){
        try {
            if(con == null){
                getConnection();
            }

            PreparedStatement prep = con.prepareStatement("INSERT INTO examined values(?, ?, ?);");
            prep.setInt(2, bacteria.getGenotype() );
            prep.setString(3, bacteria.getClassOfBacteria());
            prep.execute();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void classifyAll(){
        try {
            if(con == null){
                getConnection();
            }
            con.setAutoCommit(false);
            for(TestBacteria testBacteria:toTestList){
                insertToExamined(NnAlgorithm(testBacteria));
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        toTestList.clear();
        bacteriaToTestList.clear();
        historyListView.setItems(bacteriaToTestList);
        updateHistory();
    }
}
