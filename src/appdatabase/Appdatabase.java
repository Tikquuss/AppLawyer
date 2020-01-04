/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdatabase;

import appdatabase.bean.*;
/**
 *
 * @author User
 */
public class Appdatabase {
    public static void main(String[] args) {
        Groupe groupe  = new Groupe ("Administrateur");
        //groupe.save();ù
        Adversaire adv = new Adversaire();
        adv.save();
    }
}
