/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbsimulator;

import appdatabase.bean.CategorieDocument;
import appdatabase.bean.Document;
import appdatabase.bean.Operation;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Utilisateur
 */
public class BeansObjects {
    
    public static ArrayList <Operation> taches = new ArrayList<>();   
    public static ArrayList <Document> docs = new ArrayList<>(); 
    public static ArrayList <CategorieDocument> ctgdocs = new ArrayList<> ();  
    public static ArrayList <String> typesDoc = new ArrayList<>();
    public static ArrayList <Operation> tasksDone = new ArrayList<>();
    public static ArrayList<String> qualiteAvo = new ArrayList<>();
    public static ArrayList<String> typeAff = new ArrayList<>();
    public static ArrayList<String> juridictions = new ArrayList<>();
    public static void initObjects(){
      /*  Operation op = new Operation("Audiance", LocalDateTime.of(2020, 11, 01, 14, 30));
        Operation op1 = new Operation("Reunion au cabinet", LocalDateTime.of(2024, 03, 14, 11, 20));
        Operation op2 = new Operation("Paiement assurances", LocalDateTime.of(2020, 07, 07, 10, 25));
      
        taches.add(op);
        taches.add(op1);
        taches.add(op2);*/
      
      
        CategorieDocument cd1 = new CategorieDocument("Acte de procedure");
        CategorieDocument cd2 = new CategorieDocument("Conclusion");
        CategorieDocument cd3 = new CategorieDocument("Plainte");

        ctgdocs.add(cd1);
        ctgdocs.add(cd2);
        ctgdocs.add(cd3);

        typesDoc.add("Assignation");
        typesDoc.add("Plaintes");
        typesDoc.add("Revue d'audiance");
                
        
        qualiteAvo.add("Diligeant");
        qualiteAvo.add("Avocat de la défense");
        qualiteAvo.add("Inpropriapersona");
        
        juridictions.add("Yaoundé 1er");
        juridictions.add("Yaoundé 2e");
        juridictions.add("Yaoundé 3e");
        juridictions.add("Yaoundé 4e");
        juridictions.add("Yaoundé 5e");
        
        
        typeAff.add("Meurtre au 1er dégré");
        typeAff.add("Meurtre au second dégré");
        typeAff.add("Vol juvenil" );
        typeAff.add("Divorce");
        typeAff.add("Partage de biens");
        typeAff.add("Aggression");


        
    }
     
}
