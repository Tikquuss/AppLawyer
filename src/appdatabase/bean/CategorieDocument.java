
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
@Table(name = "categorieDocument")
public class CategorieDocument implements Serializable {
    private long id;
    private String nom;
    private static Manager DAO;

    public CategorieDocument() {}
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static void setDAO(Manager DAO) {
        CategorieDocument.DAO = DAO;
    }
    
    public CategorieDocument(String nom) {
        this.nom = nom;
    }

    @Column(name = "nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public static Manager getDAO() {
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
    
    public static List<CategorieDocument> all() {
        return getDAO().all(CategorieDocument.class);
    }
    
    public static void deleteAll() {
        List<CategorieDocument> list = all();
        if(!list.isEmpty()){
            list.forEach((a) -> {
                a.delete();
            });
        }
    }

    public static CategorieDocument getByNom(String otherNom){
        List<CategorieDocument> list = getDAO().LoadByAtt(CategorieDocument.class, "nom", otherNom);
        if(list != null){
            if(!list.isEmpty()){
                return list.get(0);
            }
        }
        return new CategorieDocument();
    }
    
    @Override
    public String toString(){
        return this.nom;
    }
}
