/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdatabase.bean;

import appdatabase.manager.Manager;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author User
 */
@Entity
@Table(name = "operation")
public class Operation implements Serializable {
    private long id;
    private String tache;
    private LocalDateTime date; 
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private double depenses;
    private String compteRendu;
    private String etat;
    private static Manager DAO;

    public Operation() {
    }
    
    public Operation(String tache, LocalDateTime date){
        this.tache = tache;
        this.etat = "En attente";
        this.date = date;
        this.depenses = 0;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    @Column(name = "tache")
    public String getTache() {
        return tache;
    }

    public void setTache(String tache) {
        this.tache = tache;
    }

    
    @Column(name = "date")
    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Column(name = "dateDebut")
    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    @Column(name = "dateFin")
    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    @Column(name = "depenses")
    public double getDepenses() {
        return depenses;
    }

    public void setDepenses(double depenses) {
        this.depenses = depenses;
    }

    @Column(name = "compterendu")
    public String getCompteRendu() {
        return compteRendu;
    }

    public void setCompteRendu(String compteRendu) {
        this.compteRendu = compteRendu;
    }

    @Column(name = "etat")
    public String getEtat() {
        return etat;
    }
    
    public void setEtat(String etat) {
        this.etat = etat;
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
    
    public static List<Operation > all() {
        return getDAO().all(Operation.class);
    }
    
    public static void deleteAll() {
        List<Operation> list = all();
        if(!list.isEmpty()){
            list.forEach((a) -> {
                a.delete();
            });
        }
    }
    public static List<Operation> listByTache(String otherTache){
        return getDAO().LoadByAtt(Operation.class, "tache", otherTache);
    }
    public static List<Operation> listByDate(String otherDate){
        return getDAO().LoadByAtt(Operation.class, "date", otherDate);
    }
    
    public static List<Operation> listByDepenses(String otherDepenses){
        return getDAO().LoadByAtt(Operation.class, "depenses", otherDepenses);
    }
    
    public static List<Operation> listByCompteRendu(String otherCompteRendu){
        return getDAO().LoadByAtt(Operation.class, "compteRendu", otherCompteRendu);
    }
    
    public static List<Operation > listByEtat(String otherEtat){
        return getDAO().LoadByAtt(Operation.class, "etat", otherEtat);
    }

    public static List<Operation> listByDifEtat(String otherEtat){
        return getDAO().LoadByDifAtt(Operation.class, "etat", otherEtat);
    }
    @Override
    public String toString(){
        String operation = new String();
        if(this.etat != "effectuée")
            operation = this.tache + "  Le "+dateFormatter(this.date)+"  ("+ this.etat+")";
        else{
            operation = this.tache +" ;  a été prévue pour le "+this.dateFormatter(this.date)+ ", a débuté le "+this.dateFormatter(this.getDateDebut());
        }
        return operation;
    }
    
    public String dateFormatter(LocalDateTime date){
        return this.date.getDayOfMonth()+"/"+this.date.getMonthValue()+
                    "/"+this.date.getYear()+" à "+this.date.getHour()+":"+this.date.getMinute();
    }
}
