package com.pashto.sex.larshod.Models;

public class ItemModel {
    String title;
    String topic;
    String image;
    int id;
    int cat;

    public ItemModel(String title, String topic, String image, int id, int cat) {
        this.title = title;
        this.topic = topic;
        this.image = image;
        this.id = id;
        this.cat = cat;
    }

    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public int getCat() {
        return cat;
    }
}
