
package appdatabase;

import appdatabase.bean.*;
/**
 *
 * @author User
 */
public class Appdatabase {
    public static void main(String[] args) {
        QualiteAvocat q1 = new QualiteAvocat("Inpropriapersona");
        QualiteAvocat q2 = new QualiteAvocat("Avocatde la défense");
        QualiteAvocat q3 = new QualiteAvocat("Artilleur Ketcheur-Bullet");
        
        Juridiction j1 = new Juridiction("Yaounde 1er");
        Juridiction j2 = new Juridiction("Yaounde 2e");
        Juridiction j3 = new Juridiction("Yaounde 3e");
        Juridiction j4 = new Juridiction("Yaounde 4e");
        Juridiction j5 = new Juridiction("Yaounde 5e");
        Juridiction j6 = new Juridiction("Yaounde 6e");

        TypeAffaire t1 = new TypeAffaire("Meurtre au 1er dégré");
        TypeAffaire t2 = new TypeAffaire("Agression");
        TypeAffaire t3 = new TypeAffaire("Meurtre au second dégré");
        TypeAffaire t4 = new TypeAffaire("Bagare");
        TypeAffaire t5 = new TypeAffaire("Violation de domicle");
        TypeAffaire t6 = new TypeAffaire("Vole");
        
        q1.save();
        q2.save();
        q3.save();
        j1.save();
        j2.save();
        j3.save();
        j4.save();
        j5.save();
        j6.save();
        t1.save();
        t2.save();
        t3.save();
        t4.save();
        t5.save();
        t6.save();

    }
}
