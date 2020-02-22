/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdatabase.bean;

import static appdatabase.bean.Juridiction.all;
import appdatabase.manager.Manager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Utilisateur
 */
@Entity
@Table(name = "qualiteAvocat")
public class QualiteAvocat implements Serializable {
   
    private String label;
    private static Manager DAO;

    public QualiteAvocat() {
    }

    public QualiteAvocat(String label) {
        this.label = label;
    }

    @Id
    @Column(name = "label") 
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
    
    public static List<QualiteAvocat> all() {
        return getDAO().all(QualiteAvocat.class);
    }
    
    public static List<String> allQualities(){
        List <String> list = new ArrayList();
        for(int i=0; i<all().size(); i++){
            list.add(all().get(i).getLabel());
        }
        return list;
    }
    
    public static void deleteAll() {
        List<QualiteAvocat> list = all();
        if(!list.isEmpty()){
            list.forEach((a) -> {
                a.delete();
            });
        }
    }
    
    @Override
    public String toString(){
        return this.label;
    }
  
}
