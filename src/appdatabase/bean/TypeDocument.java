package appdatabase.bean;

import static appdatabase.bean.Client.getDAO;
import appdatabase.manager.Manager;
import java.io.Serializable;
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
@Table(name = "type")
public class TypeDocument implements Serializable {
    
    private long id;
    private String nom;
    private CategorieDocument categorie; 
    private static Manager DAO;

    public TypeDocument() {
    }

    public TypeDocument(String nom, CategorieDocument categorie) {
        this.nom = nom;
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

    @ManyToOne
    public CategorieDocument getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieDocument categorie) {
        this.categorie = categorie;
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
    
    public static List<TypeDocument> all() {
        return getDAO().all(TypeDocument.class);
    }
    
    public static void deleteAll() {
        List<TypeDocument> list = all();
        if(!list.isEmpty()){
            list.forEach((a) -> {
                a.delete();
            });
        }
    }

    public static List<TypeDocument> listByNom(String otherNom){
        return getDAO().LoadByAtt(TypeDocument.class, "nom", otherNom);
    }
    
    public static List<TypeDocument> listByCategorie(CategorieDocument cd){
        return getDAO().LoadByAtt(TypeDocument.class, "categorie", cd);
    }
    
    @Override
    public String toString(){
        return this.nom;
    }
}


