/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdatabase.bean;

import appdatabase.manager.Manager;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author User
 */
@Entity
@Table(name = "document")
@Access(AccessType.PROPERTY)
public class Document implements Serializable {
    private long id;
    private String nom;
    private String type;
    private String fichier;
    private CategorieDocument categorie;
    private static Manager DAO;

    public Document() {}

    public Document(String nom, String type, String fichier, CategorieDocument categorie){
        this.nom = nom;
        this.type = type;
        this.fichier = fichier;
        this.categorie = categorie;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    
    @Column(name = "nom") 
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Column(name = "type") 
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "fichier") 
    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    @ManyToOne
    public CategorieDocument getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieDocument categorie) {
        this.categorie = categorie;
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
    
    public static List<Document> all() {
        return getDAO().all(Document.class);
    }
    
    public static void deleteAll() {
        List<Document> list = all();
        if(!list.isEmpty()){
            list.forEach((a) -> {
                a.delete();
            });
        }
    }

    public static List<Document> listByNom(String otherNom){
        return getDAO().LoadByAtt(Document.class, "nom", otherNom);
    }
    
    public static List<Document> listByType(String otherType){
        return getDAO().LoadByAtt(Document.class, "type", otherType);
    }

    public static List<Document> listByFichier(String otherFichier){
        return getDAO().LoadByAtt(Document.class, "fichier", otherFichier);
    }
    
    public static List<Document> listByCategorie(String otherCategorie){
        return getDAO().LoadByAtt(Document.class, "categorie", otherCategorie);
    }
}
