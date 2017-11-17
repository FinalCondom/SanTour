package objects;

/**
 * Created by kevin on 17.11.2017.
 */

public class Difficulties {

    private int id_difficulties;
    private String name;
    private int gradient;


    public Difficulties(int id_difficulties, String name, int gradient) {

  this.id_difficulties=id_difficulties;
  this.name=name;
  this.gradient=gradient;
    }


    public int getId_difficulties() {
        return id_difficulties;
    }

    public void setId_difficulties(int id_difficulties) {
        this.id_difficulties = id_difficulties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGradient() {
        return gradient;
    }

    public void setGradient(int gradient) {
        this.gradient = gradient;
    }
}
