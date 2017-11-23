package Models;

/**
 * Created by kevin on 17.11.2017.
 */

public class POD {

    private int id_pod;
    private String name;
    private String description;
    private String picture;

    public POD(int id_pod, String name, String description, String picture) {
        this.id_pod = id_pod;
        this.name = name;
        this.description = description;
        this.picture = picture;
    }


    public int getId_pod() {
        return id_pod;
    }

    public void setId_pod(int id_pod) {
        this.id_pod = id_pod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
