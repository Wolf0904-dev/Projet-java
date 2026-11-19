package javaproject;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INITIALISATION DE LA BANQUE ===");
        Banque banque = new Banque();

        
        banque.ajouterRegle(op -> op.getMontant().compareTo(BigDecimal.valueOf(100000)) <= 0);

        
        Guichetier guichetier = new Guichetier("Ali Benani", "ali", "pass123");
        directeur dir = new directeur("Karim Idrissi", "karim", "adminpass");
        administrateur admin = new administrateur("Sara Mansouri", "sara", "rootpass");

        System.out.println("Guichetier : " + guichetier.getNomcomplet() + " - Role : " + guichetier.getRole());
        System.out.println("Menu Guichetier : " + guichetier.getMenu());
        System.out.println("Menu Directeur : " + dir.getMenu());
        System.out.println("Menu Administrateur : " + admin.getMenu());

        
        Client client1 = guichetier.creerClient(banque, "Alami", "Yassine", "AB123456");
        Client client2 = guichetier.creerClient(banque, "Bennani", "Fatima", "CD789012");
        System.out.println("\nClients créés : ");
        banque.getClients().forEach(c -> System.out.println(" - " + c));

        
        Compte cc1 = guichetier.ouvrirCompteCourant(banque, client1, BigDecimal.valueOf(500)); 
        Compte ce2 = guichetier.ouvrirCompteEpargne(banque, client2, BigDecimal.valueOf(2.5)); 

        System.out.println("\nComptes ouverts : ");
        banque.getComptes().forEach(c -> System.out.println(" - " + c));

        
        System.out.println("\n=== OPERATIONS BANCAIRES ===");
        guichetier.versement(banque, cc1, BigDecimal.valueOf(2000));
        guichetier.versement(banque, ce2, BigDecimal.valueOf(5000));
        guichetier.retrait(banque, cc1, BigDecimal.valueOf(300));
        guichetier.virement(banque, cc1, ce2, BigDecimal.valueOf(500));

        System.out.println("Historique des opérations : ");
        banque.getHistorique().forEach(op -> System.out.println(" - " + op));

        System.out.println("\nSoldes après opérations : ");
        System.out.println(" - " + cc1);
        System.out.println(" - " + ce2);
        System.out.println("Total des dépôts : " + banque.totalDepots() + " DH");

        
        System.out.println("\n=== GESTION DES CREDITS ===");
        BigDecimal mensualite = guichetier.simulerCredit(client1, BigDecimal.valueOf(100000), 12, BigDecimal.valueOf(5.0));
        System.out.println("Mensualité estimée (100000 DH sur 12 mois à 5%) : " + mensualite + " DH");

        Credit credit = guichetier.enregistrerDemandeCredit(banque, client1, BigDecimal.valueOf(100000), 12, BigDecimal.valueOf(5.0));
        System.out.println("Demande créée : " + credit);
        System.out.println("Crédits en attente : " + banque.creditsEnAttente().size());

        
        dir.validerCredit(credit);
        System.out.println("Statut après validation directeur : " + credit.getStatut());
        System.out.println("Nombre d'échéances générées : " + credit.getEcheances().size());
        System.out.println("Première échéance : " + credit.getEcheances().get(0));

        System.out.println("\n=== GESTION DES FICHIERS (I/O) ===");
        
        // 1. Sauvegarde des clients dans un fichier texte
        banque.sauvegarderClients("clients.txt");

        // 2. Génération d'un relevé bancaire pour le compte cc1
        banque.exporterReleve(cc1, "releve_cc1.txt");

        // 3. Export du rapport global de la banque
        banque.exporterRapport("rapport_banque.txt");

        System.out.println("[Info] Le journal automatique a également été alimenté dans 'journal_operations.txt'.");
    }
}
