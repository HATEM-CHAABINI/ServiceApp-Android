package com.hatemchaabini.serviceapp;

import com.google.gson.annotations.SerializedName;

public class Video {
    @SerializedName("id")
    private int id;

    @SerializedName("iduser")
    private int iduser;

    @SerializedName("lien")
    private String lien;

    @SerializedName("genre")
    private String genre;

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", iduser=" + iduser +
                ", lien='" + lien + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Video() {
    }

    public Video(int id, int iduser, String lien, String genre) {
        this.id = id;
        this.iduser = iduser;
        this.lien = lien;
        this.genre = genre;
    }
}
