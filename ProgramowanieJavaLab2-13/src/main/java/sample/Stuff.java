package sample;

public class Stuff {
    private int id;
    private float price;
    private String type;
    private String name;
    private String imgPath;
    private String description;


    public Stuff()
    {

    }

    public Stuff(int id, String type, String name, String imgPath, String description) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.imgPath = imgPath;
        this.description = description;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
