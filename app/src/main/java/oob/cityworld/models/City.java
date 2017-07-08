package oob.cityworld.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import oob.cityworld.application.MyApplication;

public class City extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String name;
    @Required
    private String description;
    private int background;
    private int stars;

    public City() {
    }

    public City(String name, String description, int background, int stars) {
        this.id = MyApplication.cityId.incrementAndGet();
        this.name = name;
        this.description = description;
        this.background = background;
        this.stars = stars;
    }

    public int getId() {
        return id;
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

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
