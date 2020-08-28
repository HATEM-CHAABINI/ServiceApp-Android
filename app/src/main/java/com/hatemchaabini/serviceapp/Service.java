package com.hatemchaabini.serviceapp;

import com.google.gson.annotations.SerializedName;

public class Service {
    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("username")
    private String username;
    @SerializedName("numtel")
    private String numtel;
    @SerializedName("adresse")
    private String adresse;
    @SerializedName("metier")
    private String metier;
    @SerializedName("ville")
    private String ville;
    @SerializedName("codepostale")
    private String codepostale;
    @SerializedName("prix")
    private Float prix;
    @SerializedName("image")
    private String image;
    @SerializedName("idS")
    private int idS;
    @SerializedName("dateD")
    private String dateD;

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", numtel='" + numtel + '\'' +
                ", adresse='" + adresse + '\'' +
                ", metier='" + metier + '\'' +
                ", ville='" + ville + '\'' +
                ", codepostale='" + codepostale + '\'' +
                ", prix=" + prix +
                ", image='" + image + '\'' +
                ", idS=" + idS +
                ", dateD='" + dateD + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNumtel() {
        return numtel;
    }

    public void setNumtel(String numtel) {
        this.numtel = numtel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getMetier() {
        return metier;
    }

    public void setMetier(String metier) {
        this.metier = metier;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodepostale() {
        return codepostale;
    }

    public void setCodepostale(String codepostale) {
        this.codepostale = codepostale;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getIdS() {
        return idS;
    }

    public void setIdS(int idS) {
        this.idS = idS;
    }

    public String getDateD() {
        return dateD;
    }

    public void setDateD(String dateD) {
        this.dateD = dateD;
    }

    public Service() {
    }

    public Service(int id, String email, String name, String username, String numtel, String adresse, String metier, String ville, String codepostale, Float prix, String image, int idS, String dateD) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.username = username;
        this.numtel = numtel;
        this.adresse = adresse;
        this.metier = metier;
        this.ville = ville;
        this.codepostale = codepostale;
        this.prix = prix;
        this.image = image;
        this.idS = idS;
        this.dateD = dateD;
    }
}
