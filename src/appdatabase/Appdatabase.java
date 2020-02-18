
package appdatabase;

import appdatabase.bean.*;
/**
 *
 * @author User
 */
public class Appdatabase {
    public static void main(String[] args) {
        CategorieDocument.all().forEach(d -> {
            if(d.getNom() == null){
                Document.listByCategorie(d).forEach(doc -> {
                    doc.delete();
                });
                TypeDocument.listByCategorie(d).forEach(td -> {
                    td.delete();
                });
                d.delete();
            }
        });
    }
}
