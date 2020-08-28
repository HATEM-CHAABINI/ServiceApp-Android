package com.hatemchaabini.serviceapp;

import com.google.gson.annotations.SerializedName;

public class Commentaire {



    @SerializedName("commentaire")
    private String commentaire;

    @SerializedName("name")

    private String name;

    @SerializedName("image")

    private String image;

    public Commentaire(String commentaire, String name, String image) {
        this.commentaire = commentaire;
        this.name = name;
        this.image = image;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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

    @Override
    public String toString() {
        return "Commentaire{" +
                "commentaire='" + commentaire + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
