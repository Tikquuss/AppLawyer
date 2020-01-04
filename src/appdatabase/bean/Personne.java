/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdatabase.bean;

/**
 *
 * @author User
 */
public class Personne {
    private String nom;
    private String prenom;
    private String CNI; // numero de la Carte Nationale d'identité 
    
    public Personne() {}

    public Personne(String nom, String prenom, String CNI) {
        this.nom = nom;
        this.prenom = prenom;
        this.CNI = CNI;
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

    public String getCNI() {
        return CNI;
    }

    public void setCNI(String CNI) {
        this.CNI = CNI;
    }    
}
