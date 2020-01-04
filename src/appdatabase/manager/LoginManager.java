/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdatabase.manager;

import appdatabase.bean.*;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author User
 */
public class LoginManager {
    
    public static LoginResponse login(String username , String password){
        LoginResponse lr = new LoginResponse();
        Manager m = new Manager();
        List<Utilisateur> liste = m.execute_criteria(Utilisateur.class)
                .add(Restrictions.eq("username", username))
                .add(Restrictions.eq("password", password))
                .list();
        if(liste.size()>=1){
            lr.setUser(liste.get(0));
            lr.setWin(true);
            return lr;
        }
        return lr;    
    }
}



