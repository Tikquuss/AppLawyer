/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdatabase.interfaces;

import java.time.LocalDate;
import java.util.List;
import org.hibernate.Criteria;

/**
 *
 * @author kritikos
 */
public interface ManagerI {
    
    public boolean save(Object o);
    
    public boolean save_only(Object o);
    
    public boolean update(Object o);
    
    public boolean delete(Object o);
    
    public List filter(String hql_query);
    
    public Object get(int id , Class Object_type);
    
    public List all(Class c);
    
    public Criteria execute_criteria(Class c);
    
    public List fetchByDate(Class ObjectType, LocalDate start , LocalDate end);
    public List fetchByDate(String date_attribute, Class ObjectType, LocalDate start , LocalDate end);
    
    public Object LoadByName(Class ObjectType , String att , String name);
    
    
    public List LoadByAtt(Class ObjectType , String attribute_name , Object value);
    public List LoadByDifAtt(Class ObjectType , String attribute_name , Object value);
    
}
