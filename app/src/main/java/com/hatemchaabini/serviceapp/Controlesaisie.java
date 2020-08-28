package com.hatemchaabini.serviceapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controlesaisie {

    public Controlesaisie() {
    }

    ///////////////////////////////////// controle de saisie
    public boolean verifisTel(String text) {
        if (text.matches("^[0-9]+$")&& text.length()==8) {
            return true;
        } else {
            return false;
        }
    }
    public boolean verifcodepostale(String text) {
        if (text.matches("^[0-9]+$")&& text.length()==4) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifusername(String text) {
        if (text.matches("^[a-zA-Z]+$")) {

            return true;
        }
        return false;

    }

    public boolean verifnom(String text) {
        if (text.matches("^[a-zA-Z]+$")) {

            return true;

        }

        return false;

    }

    public boolean verifadresse(String text){
        if (text.matches("^[A-Z a-z 0-9]+$")) {

            return true;

        }

        return false;

    }
    public boolean verifmdp(String text){
        if (text.matches("^[A-Z a-z 0-9]+$")&& text.length()>=8) {

            return true;

        }

        return false;

    }
    private String EMAIL_PATTERN

            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"

            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Matcher matcher;

    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);


    public boolean verifemail(String hex) {

        matcher = pattern.matcher(hex);

        return matcher.matches();

    }
    ///////////////////////////////////// controle de saisie

}
