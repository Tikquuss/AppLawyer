/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdatabase.bean;

import appdatabase.manager.Manager;
import java.io.Serializable;
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
@Table(name = "adversaire")
public class Adversaire extends Personne implements Serializable{
    private long id;
    private Dossier dossier;
    private static Manager DAO;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Adversaire(String nom, String prenom, String CNI) {
        super(nom, prenom, CNI);
    }

    public Adversaire(){
        
    }
    
    
    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }
    
    @Column(name = "nom")
    @Override
    public String getNom() {
        return super.getNom();
    }

    
    @Column(name = "prenom")
    @Override
    public String getPrenom() {
        return  super.getPrenom();
    }

    @Column(name = "CNI")
    @Override
    public String getCNI() {
        return super.getCNI();
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
    
    public static List<Adversaire> all() {
        return getDAO().all(Adversaire.class);
    }
    
    public static void deleteAll() {
        List<Adversaire> list = all();
        if(!list.isEmpty()){
            list.forEach((a) -> {
                a.delete();
            });
        }
    }

    public static List<Adversaire> listByNom(String otherNom){
        return getDAO().LoadByAtt(Adversaire.class, "nom", otherNom);
    }
    
    public static List<Adversaire> listByPrenom(String otherPrenom){
        return getDAO().LoadByAtt(Adversaire.class, "prenom", otherPrenom);
    }
    
    public static Adversaire getByCNI(String otherCNI){
        List<Adversaire> list = getDAO().LoadByAtt(Adversaire.class, "CNI", otherCNI);
        if(list != null){
            if(!list.isEmpty()){
                return list.get(0);
            }
        }
        return new Adversaire();
    }
}
