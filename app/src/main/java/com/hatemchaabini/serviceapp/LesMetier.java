package com.hatemchaabini.serviceapp;

public class LesMetier {

    private int id;
    private String metier;

    public LesMetier(int id, String metier) {
        this.id = id;
        this.metier = metier;
    }

    public LesMetier() {
    }

    @Override
    public String toString() {
        return "LesMetier{" +
                "id=" + id +
                ", metier='" + metier + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMetier() {
        return metier;
    }

    public void setMetier(String metier) {
        this.metier = metier;
    }
}
