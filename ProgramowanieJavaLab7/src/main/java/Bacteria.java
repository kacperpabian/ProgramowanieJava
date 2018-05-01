import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Bacteria")
public class Bacteria {
    int genotype;
    String classOfBacteria;

    @XmlElement
    public int getGenotype() {
        return genotype;
    }

    public void setGenotype(int genotype) {
        this.genotype = genotype;
    }

    @XmlElement
    public String getClassOfBacteria() {
        return classOfBacteria;
    }

    public void setClassOfBacteria(String classOfBacteria) {
        this.classOfBacteria = classOfBacteria;
    }

    public Bacteria(int genotype, String classOfBacteria) {
        this.genotype = genotype;
        this.classOfBacteria = classOfBacteria;
    }

    public Bacteria() {
    }
}
