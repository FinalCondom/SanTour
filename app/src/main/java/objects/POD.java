package objects;

import android.media.Image;

/**
 * Created by kevin on 17.11.2017.
 */

public class POD {

    private int id_pod;
    private String name;
    private String description;
    private Image picture;

    public POD(int id_pod, String name, String description, Image picture) {
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

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }
}
