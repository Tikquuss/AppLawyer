/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdatabase.bean;

import appdatabase.manager.Manager;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author User
 */
@Entity
@Table(name = "payement")
public class Payement implements Serializable {
    private long id;
    private double montant;
    private LocalDate date; 
    private LocalTime heure;
    private Dossier dossier;
    private static Manager DAO;

    public Payement() {
    }

    public Payement( double montant,  Dossier dossier, LocalDate date, LocalTime heure) {
        this.montant = montant;
        this.date = date;
        this.heure = heure;
        this.dossier = dossier;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "heure")
    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    @ManyToOne
    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    @Column(name = "montant")
    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Column(name = "date")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public static Manager getDAO() {
        if(DAO == null){
            DAO = new Manager();
        }
        return DAO;
    }
    
    public boolean save() {
        return getDAO().save(this);
    }

    public boolean update() {
        return getDAO().update(this);
    }

    public boolean delete() {
        return getDAO().delete(this);
    }
    
    public static List<Payement> all() {
        return getDAO().all(Payement.class);
    }
    
    public static void deleteAll() {
        List<Payement> list = all();
        if(!list.isEmpty()){
            list.forEach((a) -> {
                a.delete();
            });
        }
    }
    
    public String toString(){
    
        return this.montant+" "+this.date;
    }
 
    public static List<Payement> listByMontant(String otherMontant){
        return getDAO().LoadByAtt(Payement.class, "montant", otherMontant);
    }
    
    public static List<Payement> listByDate(String otherDate){
        return getDAO().LoadByAtt(Payement.class, "date", otherDate);
    }
    
    public static List<Payement> listByDossier(Dossier otherDossier){
        return getDAO().LoadByAtt(Payement.class, "dossier", otherDossier);
    }
}
