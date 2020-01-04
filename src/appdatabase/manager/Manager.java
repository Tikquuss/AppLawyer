package appdatabase.manager;

import appdatabase.HibernateUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import appdatabase.interfaces.ManagerI;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kritikos
 */
public class Manager implements ManagerI{

    @Override
    public boolean save(Object o) {
        Transaction t = null;
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
        
        t = session.beginTransaction();
        session.saveOrUpdate(o);
        t.commit();
        return result;
        } catch (HibernateException he) {
            if(t != null){
                t.rollback();
            }
            he.printStackTrace();
            return !result;
        }
        finally{
            this.close_session(session);
        }
    }

    @Override
    public boolean save_only(Object o) {
        Transaction t = null;
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            t = session.beginTransaction();
            session.save(o);
            t.commit();
            this.close_session(session);
            return result;
        } catch (HibernateException he) {
            if(t != null){
                t.rollback();
            }
            return !result;
        }
        finally{
            this.close_session(session);
        }
    }

    @Override
    public boolean update(Object o) {
        Transaction t = null;
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            t = session.beginTransaction();
            session.saveOrUpdate(o);
            t.commit();
            this.close_session(session);
        return result;
        } catch (HibernateException he) {
            if(t != null){
                t.rollback();
            }
            return !result;
        }
        finally{
            this.close_session(session);
        }
    }

    @Override
    public boolean delete(Object o) {
        Transaction t = null;
        boolean result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            t = session.beginTransaction();
            session.delete(o);
            t.commit();
            this.close_session(session);
            return result;
        } catch (HibernateException he) {
            if(t != null){
                t.rollback();
            }
            return !result;
        }
        finally{
            this.close_session(session);
        }
    }

    @Override
    public List filter(String hql_query) {
        Transaction t = null;
        List result = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            t = session.beginTransaction();
            Query c = session.createQuery(hql_query);
            result = c.list();
            t.commit();
            this.close_session(session);
            return result;
        } catch (HibernateException he) {
            if(t != null){
                t.rollback();
            }
            System.out.println(he);
            return null;
        }
        finally{
            this.close_session(session);
        }
    }

    @Override
    public Object get(int id , Class Object_type) {
        Transaction t = null;
        Object result = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            t = session.beginTransaction();
            result = session.load(Object_type, id);
            Hibernate.initialize(result);
            t.commit();
            this.close_session(session);
            return result;
        } catch (HibernateException he) {
            if(t != null){
                t.rollback();
            }
            return null;
        }
        finally{
            this.close_session(session);
        }
    }
    
    public void close_session(Session session){
        if(session.isOpen()){
            session.close();
        }
    }

    @Override
    public List all(Class c) {
        return this.execute_criteria(c).list();
    }

    @Override
    public Criteria execute_criteria(Class c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.createCriteria(c);
    }

    @Override
    public List fetchByDate(Class ObjectType, LocalDate start, LocalDate end) {
        Criteria c = this.execute_criteria(ObjectType);
        c = c.add(Restrictions.ge("date", start)).add(Restrictions.le("date", end));
        return c.list();
    }
    @Override
    public List fetchByDate(String date_attribute, Class ObjectType, LocalDate start, LocalDate end) {
        Criteria c = this.execute_criteria(ObjectType);
        return c.add(Restrictions.between(date_attribute, start, end)).list();
    }

    @Override
    public Object LoadByName(Class ObjectType, String att , String name) {
        Criteria c = this.execute_criteria(ObjectType);
        List list = c.add(Restrictions.eq(att, name)).list();
        
        if(list.isEmpty()){
            return null;
        }
        else{
            return list.get(0);
        }
    } 

    @Override
    public List LoadByAtt(Class ObjectType, String attribute_name, Object value) {
        List result = null;
        try{
            Criteria c = this.execute_criteria(ObjectType);
            result = c.add(Restrictions.eq(attribute_name, value)).list();
        }
        catch(Exception e){
            return result;
        }
        
        return result;   
    }
 

    @Override
    public List LoadByDifAtt(Class ObjectType, String attribute_name, Object value) {
        List result = null;
        try{
            Criteria c = this.execute_criteria(ObjectType);
            result = c.add(Restrictions.ne(attribute_name, value)).list();
        }
        catch(Exception e){
            return result;
        }
        
        return result;       }
}
