/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appdatabase.bean;

import appdatabase.manager.Manager;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author User
 */
@Entity
@Table(name = "dossier")
public class Dossier implements Serializable {
    private long id;
    private LocalDate dateOuverture;
    private Client client;
    private Adversaire adversaire;
    private String qualite;
    private String juridiction;
    private String typeAffaire;
    private double honoraires;
    private double provisions;
    private String statut;
    private static Manager DAO;

    public Dossier() {
        this.dateOuverture = LocalDate.now();
    }

    public Dossier(Client client, Adversaire adversaire, String qualite, String juridiction, String typeAffaire, double honoraires, double provisions) {
        this.client = client;
        this.adversaire = adversaire;
        this.qualite = qualite;
        this.juridiction = juridiction;
        this.typeAffaire = typeAffaire;
        this.honoraires = honoraires;
        this.dateOuverture = LocalDate.now();
        this.provisions = provisions;
        this.statut = "En cours";
    }
    
    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static void setDAO(Manager DAO) {
        Dossier.DAO = DAO;
    }

    @Column(name="statut")
    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
    
    
    @Column(name = "dateOuverture")
    public LocalDate getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
    }
    
    
    @ManyToOne // un même adversaire peut faire partir de plusieus dossiers, mais la reciproque est fausse
    public Adversaire getAdversaire() {
        return adversaire;
    }

    public void setAdversaire(Adversaire adversaire) {
        this.adversaire = adversaire;
    }

    @ManyToOne
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Column(name = "typeAffaire")
    public String getTypeAffaire() {
        return typeAffaire;
    }

    public void setTypeAffaire(String typeAffaire) {
        this.typeAffaire = typeAffaire;
    }

    
    @Column(name = "qualite")
    public String getQualite() {
        return qualite;
    }

    public void setQualite(String qualite) {
        this.qualite = qualite;
    }

    @Column(name = "juridiction")
    public String getJuridiction() {
        return juridiction;
    }

    public void setJuridiction(String juridiction) {
        this.juridiction = juridiction;
    }

    @Column(name = "honoraires")
    public double getHonoraires() {
        return honoraires;
    }

    public void setHonoraires(double honoraires) {
        this.honoraires = honoraires;
    }

    @Column(name = "provisions")
    public double getProvisions() {
        return provisions;
    }

    public void setProvisions(double provisions) {
        this.provisions = provisions;
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
    
    public static List<Dossier> all() {
        return getDAO().all(Dossier.class);
    }
    
    public static void deleteAll() {
        List<Dossier> list = all();
        if(!list.isEmpty()){
            list.forEach((a) -> {
                a.delete();
            });
        }
    }
    
    public static List<Dossier> listByDateOuverture(String otherDateOuverture){
        return getDAO().LoadByAtt(Dossier.class, "dateOuverture", otherDateOuverture);
    }
    
    public static List<Dossier> listByAdversaire(String otherAdversaire){
        return getDAO().LoadByAtt(Dossier.class, "adversaire", otherAdversaire);
    }
    
    public static List<Dossier> listByQualite(String otherQualite){
        return getDAO().LoadByAtt(Dossier.class, "qualite", otherQualite);
    }
    
    public static List<Dossier> listByJuridiction(String otherJuridiction){
        return getDAO().LoadByAtt(Dossier.class, "juridiction", otherJuridiction);
    }
    
    public static List<Dossier> listByHonoraires(String otherHonoraires){
        return getDAO().LoadByAtt(Dossier.class, "honoraires", otherHonoraires);
    }
    
    public static List<Dossier> listByProvisions(String otherProvisions){
        return getDAO().LoadByAtt(Dossier.class, "provisions", otherProvisions);
    }
    public static List<Dossier> listByClient(Client otherClient){
        return getDAO().LoadByAtt(Dossier.class, "client", otherClient);
    }
    public static List<Dossier> listByStatus(String otherStatus){
        return getDAO().LoadByAtt(Dossier.class, "statut", otherStatus);
    }

    @Override
    public String toString() {
        return "DOSSIER " + client.getNom().toUpperCase()+" "+client.getPrenom().toUpperCase()+" "+this.getId();
    }
    
    
}
