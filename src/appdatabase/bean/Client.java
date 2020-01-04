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
@Table(name = "client")
public class Client extends Personne implements Serializable{
    
    private long id;
    private String telephone;
    private String email;
    private String adresse;
    private static Manager DAO;
    
    public Client() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "telephone") 
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Column(name = "email") 
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "adresse") 
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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
    
    public static List<Client> all() {
        return getDAO().all(Adversaire.class);
    }
    
    public static void deleteAll() {
        List<Client> list = all();
        if(!list.isEmpty()){
            list.forEach((a) -> {
                a.delete();
            });
        }
    }

    public static List<Client> listByNom(String otherNom){
        return getDAO().LoadByAtt(Client.class, "nom", otherNom);
    }
    
    public static List<Client> listByPrenom(String otherPrenom){
        return getDAO().LoadByAtt(Client.class, "prenom", otherPrenom);
    }
    
    public static Client getByCNI(String otherCNI){
        List<Client> list = getDAO().LoadByAtt(Client.class, "CNI", otherCNI);
        if(list != null){
            if(!list.isEmpty()){
                return list.get(0);
            }
        }
        return new Client();
    }
    
    
    public static Client getByTelephone(String otherTelephone){
        List<Client> list = getDAO().LoadByAtt(Client.class, "telephone", otherTelephone);
        if(list != null){
            if(!list.isEmpty()){
                return list.get(0);
            }
        }
        return new Client();
    }
    
    public static Client getByMail(String otherMail){
        List<Client> list = getDAO().LoadByAtt(Client.class, "CN", otherMail);
        if(list != null){
            if(!list.isEmpty()){
                return list.get(0);
            }
        }
        return new Client();
    }
    
    public static List<Client> listByAdresse(String otherAdresse){
        return getDAO().LoadByAtt(Client.class, "adresse", otherAdresse);
    }
}
