import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "BacteriaDataBase")
public class BacteriaDataBase {
    private ObservableList<Bacteria> data;

    public ObservableList<Bacteria> getData() {
        return data;
    }

    public void setData(ObservableList<Bacteria> data) {
        this.data = data;
    }

    public BacteriaDataBase(ObservableList<Bacteria> data) {
        this.data = data;
    }

    public BacteriaDataBase() {
    }
}
