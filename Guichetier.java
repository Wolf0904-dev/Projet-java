package javaproject;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Guichetier extends Utilisateur {

    public Guichetier(String nom, String login, String motDePasse) {
        super(nom, login, motDePasse, Role.GUICHETIER);
    }

    public Guichetier(int id, LocalDate datederecrutemment, String nomcomplet, String status, String email, int numero,
                      String login, String motDePasseHash, Role role, boolean actif) {
        super(id, datederecrutemment, nomcomplet, status, email, numero, login, motDePasseHash, Role.GUICHETIER, actif);
    }

    @Override
    public Role getRole() { return Role.GUICHETIER; }

    @Override
    public List<String> getMenu() {
        return List.of("Rechercher un client", "Creer un client", "Ouvrir un compte",
                       "Versement", "Retrait", "Virement",
                       "Historique", "Simuler un credit", "Demande de credit");
    }

    public Client creerClient(Banque banque, String nom, String prenom, String cin) {
        Client c = new Client(nom, prenom, cin);
        banque.ajouterClient(c);
        return c;
    }

    public Compte ouvrirCompteCourant(Banque banque, Client client, BigDecimal decouvert) {
        Compte c = new CompteCourant(genererNumero(banque), client, decouvert);
        banque.ajouterCompte(c);
        return c;
    }

    public Compte ouvrirCompteEpargne(Banque banque, Client client, BigDecimal taux) {
        Compte c = new CompteEpargne(genererNumero(banque), client, taux);
        banque.ajouterCompte(c);
        return c;
    }

    public boolean versement(Banque banque, Compte compte, BigDecimal montant) {
        return banque.effectuer(new Versement(compte, montant));
    }

    public boolean retrait(Banque banque, Compte compte, BigDecimal montant) {
        return banque.effectuer(new Retrait(compte, montant));
    }

    public boolean virement(Banque banque, Compte source, Compte dest, BigDecimal montant) {
        return banque.effectuer(new Virement(source, dest, montant));
    }

    public BigDecimal simulerCredit(Client client, BigDecimal montant, int duree, BigDecimal taux) {
        return new Credit(client, montant, duree, taux).calculerMensualite();
    }

    public Credit enregistrerDemandeCredit(Banque banque, Client client, BigDecimal montant,
                                           int duree, BigDecimal taux) {
        Credit credit = new Credit(client, montant, duree, taux);
        banque.ajouterCredit(credit);
        return credit;
    }

    private String genererNumero(Banque banque) {
        return "MA" + String.format("%04d", banque.getComptes().size() + 1);
    }
}
