package javaproject;

import java.time.LocalDate;

public class Client {
    private String nom;
    private String prenom;
    private String cin;
    private boolean actif = true;

    public Client(String nom, String prenom, String cin) {
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
    }

    public void archiver() { actif = false; }

    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getCin() { return cin; }
    public boolean isActif() { return actif; }

    @Override
    public String toString() { return prenom + " " + nom + " (" + cin + ")"; }
}

