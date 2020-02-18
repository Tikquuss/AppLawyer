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
    private TypeDocument type;
    private String fichier;
    private Dossier dossier;
    private CategorieDocument categorie;
    private static Manager DAO;

    public Document() {}

    public Document(String nom, TypeDocument type, String fichier, CategorieDocument categorie, Dossier dossier){
        this.nom = nom;
        this.type = type;
        this.fichier = fichier;
        this.categorie = categorie;
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

   
    
    @Column(name = "nom") 
    public String getNom() {
        return nom;
    }
    
    @ManyToOne
    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    @ManyToOne
    public CategorieDocument getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieDocument categorie) {
        this.categorie = categorie;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @ManyToOne
    public TypeDocument getType() {
        return type;
    }

    public void setType(TypeDocument type) {
        this.type = type;
    }

    
    

    @Column(name = "fichier") 
    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
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

    public static List<Document> listByNom(String otherNom, Dossier dossier){
        return getDAO().LoadByAttrs(Document.class, "nom", otherNom, "dossier", dossier);
        
    }
    
    public static List<Document> listByType(String otherType,Dossier dossier){
        return getDAO().LoadByAttrs(Document.class, "type", otherType, "dossier", dossier);
    }

    public static List<Document> listByFichier(String otherFichier, Dossier dossier){
        return getDAO().LoadByAttrs(Document.class, "fichier", otherFichier, "dossier", dossier);
    }
    
    public static List<Document> listByCategorie(String otherCategorie, Dossier dossier){
        return getDAO().LoadByAttrs(Document.class, "categorie", otherCategorie, "dossier", dossier);
    }
    public static List<Document> listByCategorie(CategorieDocument otherCategorie){
        return getDAO().LoadByAtt(Document.class, "categorie", otherCategorie);
    }
    public static List<Document> listByDossier(Dossier otherDossier){
        return getDAO().LoadByAtt(Document.class, "dossier", otherDossier);
    }
}
