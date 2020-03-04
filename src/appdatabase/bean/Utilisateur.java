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
 * @author Notsawo
 */
@Entity
@Table(name = "utilisateur")
public class Utilisateur extends Personne implements Serializable{
    private long id;
    private String username;
    private String password;
    private String typeUser; //Admin, SuperAdmin, User (ceux qui ne sont dans aucun typeUser)...
    private static Manager DAO;

    public Utilisateur(String username, String password, String typeUser) {
        this.username = username;
        this.password = password;
        this.typeUser = typeUser;
    }
    
    
    
    public Utilisateur(){
        
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static void setDAO(Manager DAO) {
        Utilisateur.DAO = DAO;
    }

    @Column(name="username")
    public String getUsername() {
        return username;
    }
    
    
    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(name = "typeUser")
    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
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
    
    public static List<Utilisateur> all() {
        return getDAO().all(Utilisateur.class);
    }
    
    public static void deleteAll() {
        List<Utilisateur> list = all();
        if(!list.isEmpty()){
            list.forEach((a) -> {
                a.delete();
            });
        }
    }
    
  
    public static Utilisateur getByUsername(String otherUsername){
        List<Utilisateur> list = getDAO().LoadByAtt(Utilisateur.class, "username", otherUsername);
        if(list != null){
            if(!list.isEmpty()){
                return list.get(0);
            }
        }
        return new Utilisateur();
    }
    
    public static List<Utilisateur> listByTypeUser(String otherTypeUser){
        return getDAO().LoadByAtt(Utilisateur.class, "typeUser", otherTypeUser);
    }
}
