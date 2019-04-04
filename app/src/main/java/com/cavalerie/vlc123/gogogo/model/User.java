package com.cavalerie.vlc123.gogogo.model;

public class User {

    private String nom;
    private String prenom;
    private String dateNaissance;
    private String username;
    private String adresse;
    private String telephone;
    private String email;
    private String statut;
    private String cni;

    public User(String nom, String prenom, String username, String telephone, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.telephone = telephone;
        this.email = email;
    }

    public User(String nom, String prenom, String dateNaissance, String adresse, String telephone, String email, String username) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.username = username;
    }

    public User(String nom, String prenom, String username, String email, String telephone, String cni, String adresse, String statut, String dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.statut = statut;
        this.cni = cni;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
