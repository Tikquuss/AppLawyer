/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdatabase;

import appdatabase.bean.*;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author User
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            sessionFactory = new AnnotationConfiguration()
                    .addAnnotatedClass(Client.class)
                    .addAnnotatedClass(Document.class)
                    .addAnnotatedClass(Dossier.class)
                    .addAnnotatedClass(Operation.class)
                    .addAnnotatedClass(Payement.class)
                    .addAnnotatedClass(TypeDocument.class)
                    .addAnnotatedClass(CategorieDocument.class)
                    .addAnnotatedClass(Adversaire.class)
                    .addAnnotatedClass(Groupe.class)
                    .addAnnotatedClass(Payement.class)                    
                    .configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
