package com.hatemchaabini.serviceapp;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private int id;
    @SerializedName("email")

    private String email;
    @SerializedName("name")

    private String name;
    @SerializedName("username")

    private String username;
    @SerializedName("encrypted_password")

    private String encrypted_password;
    @SerializedName("salt")

    private String salt;
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
    @SerializedName("created_at")

    private String created_at;

    @SerializedName("image")
    private String image;

    @SerializedName("prix")
    private Float prix;
    @SerializedName("firebaseid")
    private String firebaseid;



    @SerializedName("token")
    private String token;



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", encrypted_password='" + encrypted_password + '\'' +
                ", salt='" + salt + '\'' +
                ", numtel='" + numtel + '\'' +
                ", adresse='" + adresse + '\'' +
                ", metier='" + metier + '\'' +
                ", ville='" + ville + '\'' +
                ", codepostale='" + codepostale + '\'' +
                ", created_at='" + created_at + '\'' +
                ", image='" + image + '\'' +
                ", prix=" + prix +
                ", firebaseid='" + firebaseid + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public User() {
    }

    public User(int id, String email, String name, String username, String encrypted_password, String salt, String numtel, String adresse, String metier, String ville, String codepostale, String created_at, String image, Float prix, String firebaseid) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.username = username;
        this.encrypted_password = encrypted_password;
        this.salt = salt;
        this.numtel = numtel;
        this.adresse = adresse;
        this.metier = metier;
        this.ville = ville;
        this.codepostale = codepostale;
        this.created_at = created_at;
        this.image = image;
        this.prix = prix;
        this.firebaseid = firebaseid;
    }

    public String getFirebaseid() {
        return firebaseid;
    }

    public void setFirebaseid(String firebaseid) {
        this.firebaseid = firebaseid;
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

    public String getEncrypted_password() {
        return encrypted_password;
    }

    public void setEncrypted_password(String encrypted_password) {
        this.encrypted_password = encrypted_password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }
}
