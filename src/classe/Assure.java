/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

import java.sql.Date;

/**
 *
 * @author ULRICH TEDONGMO
 */
public class Assure extends Personne{
    
    int num;
    String nom;
    String prenom;
    Date date_naissance;
    String sexe;
    String adresse;
    String telephone;

    public Assure(int num, String nom, String prenom, Date date_naissance, String sexe, String adresse, String telephone) {
        this.num = num;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.sexe = sexe;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
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

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
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

   
}
