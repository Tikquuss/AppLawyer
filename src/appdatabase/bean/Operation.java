/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdatabase.bean;

import appdatabase.manager.Manager;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private Dossier dossier;
    private static Manager DAO;

    public Operation() {
    }
    
    public Operation(String tache, LocalDateTime date, Dossier dossier){
        this.tache = tache;
        this.etat = "En attente";
        this.date = date;
        this.depenses = 0;
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
    
    @Column(name = "tache")
    public String getTache() {
        return tache;
    }

    public void setTache(String tache) {
        this.tache = tache;
    }

    @ManyToOne
    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
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
    public static List<Operation> listByTache(String otherTache, Dossier dossier){
        return getDAO().LoadByAttrs(Operation.class, "tache", otherTache, "dossier", dossier);
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
    
    public static List<Operation > listByEtat(String otherEtat, Dossier dossier){
        return getDAO().LoadByAttrs(Operation.class, "etat", otherEtat , "dossier", dossier);
    }

    public static List<Operation> listByDifEtat(String otherEtatString, Dossier dossier){
        return getDAO().LoadByDifAtt(Operation.class, "etat", otherEtatString, "dossier", dossier);
    }
    
    public static List<Operation> listByDossier(String otherDossier){
        return getDAO().LoadByAtt(Operation.class, "dossier", otherDossier);
    }
    
    public static Operation getById(long id){
        List <Operation> list = getDAO().LoadByAtt(Operation.class, "id", id);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return new Operation();
    }
    @Override
    public String toString(){
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("dd-MM-yyyy à HH:mm");
        String operation;
        if(!this.etat.equals("effectuée"))
            operation = this.tache.toUpperCase() + " , prévu pour le "+this.date.format(formatter)+"  ("+ this.etat+")";
        else{
            operation = this.tache.toUpperCase() +" ,  a été prévue pour le "+this.date.format(formatter)+ ", a débuté le "+this.getDateDebut().format(formatter);
        }
        return operation;
    }
    
}
