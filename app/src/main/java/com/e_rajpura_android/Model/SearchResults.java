package com.erajpura.Model;

/**
 * Created by erginus on 3/21/2017.
 */

public class SearchResults {
    int id;
    String name;
    String image;
    String specialization;
    String location;
    int offerOrNot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getOfferOrNot() {
        return offerOrNot;
    }

    public void setOfferOrNot(int offerOrNot) {
        this.offerOrNot = offerOrNot;
    }
}
