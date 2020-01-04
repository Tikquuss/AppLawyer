/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import appdatabase.bean.Operation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Utilisateur
 */
public class ConcatList {
    
    
    public static List<Operation> concatLists(List <Operation> a1, List <Operation> a2){        
        List a = new ArrayList();
        for(int ind = 0; ind<a1.size(); ind++){
                a.add(a1.get(ind));
        }
        
        for(int ind = 0; ind<a2.size(); ind++)
            a.add(a2.get(ind));

        return a;
    } 
}
