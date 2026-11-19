package javaproject;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire pour la gestion des fichiers (I/O) :
 * - Sauvegarde et chargement des clients (format texte / CSV)
 * - Journalisation automatique des opérations (log en mode ajout/append)
 * - Génération de relevés de compte et rapports bancaires
 */
public class GestionnaireFichier {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Sauvegarde la liste des clients dans un fichier texte délimité par des points-virgules.
     */
    public static void sauvegarderClients(List<Client> clients, String cheminFichier) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(cheminFichier))) {
            for (Client c : clients) {
                // Format : nom;prenom;cin;actif
                String ligne = c.getNom() + ";" + c.getPrenom() + ";" + c.getCin() + ";" + c.isActif();
                bw.write(ligne);
                bw.newLine();
            }
            System.out.println("[Fichier] " + clients.size() + " client(s) sauvegardé(s) dans : " + cheminFichier);
        } catch (IOException e) {
            System.err.println("[Erreur Fichier] Impossible d'écrire les clients : " + e.getMessage());
        }
    }

    /**
     * Charge les clients depuis un fichier texte délimité par des points-virgules.
     */
    public static List<Client> chargerClients(String cheminFichier) {
        List<Client> clients = new ArrayList<>();
        File fichier = new File(cheminFichier);
        if (!fichier.exists()) {
            System.out.println("[Fichier] Le fichier " + cheminFichier + " n'existe pas encore.");
            return clients;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty()) continue;

                String[] parts = ligne.split(";");
                if (parts.length >= 3) {
                    String nom = parts[0];
                    String prenom = parts[1];
                    String cin = parts[2];
                    Client c = new Client(nom, prenom, cin);
                    if (parts.length >= 4 && !Boolean.parseBoolean(parts[3])) {
                        c.archiver();
                    }
                    clients.add(c);
                }
            }
            System.out.println("[Fichier] " + clients.size() + " client(s) chargé(s) depuis : " + cheminFichier);
        } catch (IOException e) {
            System.err.println("[Erreur Fichier] Impossible de lire les clients : " + e.getMessage());
        }
        return clients;
    }

    /**
     * Journalise une opération dans un fichier journal (mode append / ajout sans écraser).
     */
    public static void journaliserOperation(Operation op, String cheminJournal) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(cheminJournal, true))) {
            String dateStr = op.getDate() != null ? op.getDate().format(FORMATTER) : LocalDateTime.now().format(FORMATTER);
            String log = String.format("[%s] ID:%d | Type:%s | Montant:%s DH | Compte:%s | Statut:%s",
                    dateStr,
                    op.getId(),
                    op.getType(),
                    op.getMontant(),
                    op.getCompte() != null ? op.getCompte().getNumero() : "N/A",
                    op.getStatut());
            pw.println(log);
        } catch (IOException e) {
            System.err.println("[Erreur Fichier] Échec d'écriture dans le journal : " + e.getMessage());
        }
    }

    /**
     * Génère un relevé de compte officiel au format texte.
     */
    public static void genererReleveCompte(Compte compte, List<Operation> operations, String cheminFichier) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(cheminFichier))) {
            pw.println("================================================================");
            pw.println("                     RELEVE DE COMPTE                           ");
            pw.println("================================================================");
            pw.println("Date d'édition : " + LocalDateTime.now().format(FORMATTER));
            pw.println("Titulaire      : " + (compte.getTitulaire() != null ? compte.getTitulaire().getPrenom() + " " + compte.getTitulaire().getNom() : "Inconnu"));
            pw.println("CIN            : " + (compte.getTitulaire() != null ? compte.getTitulaire().getCin() : "N/A"));
            pw.println("Numéro Compte  : " + compte.getNumero());
            pw.println("Type de Compte : " + compte.getType());
            pw.println("Statut Compte  : " + compte.getStatut());
            pw.println("Solde Actuel   : " + compte.getSolde() + " DH");
            pw.println("----------------------------------------------------------------");
            pw.println("HISTORIQUE DES OPERATIONS DU COMPTE :");
            pw.println("----------------------------------------------------------------");

            boolean auMoinsUne = false;
            for (Operation op : operations) {
                if (op.getCompte() != null && op.getCompte().getNumero().equals(compte.getNumero())) {
                    String dateOp = op.getDate() != null ? op.getDate().format(FORMATTER) : "";
                    pw.printf(" %-19s | %-12s | Montant: %10s DH | [%s]%n",
                            dateOp, op.getType(), op.getMontant(), op.getStatut());
                    auMoinsUne = true;
                }
            }

            if (!auMoinsUne) {
                pw.println(" Aucune opération enregistrée pour ce compte.");
            }

            pw.println("================================================================");
            pw.println("                  FIN DU RELEVE DE COMPTE                       ");
            pw.println("================================================================");
            System.out.println("[Fichier] Relevé de compte généré avec succès : " + cheminFichier);
        } catch (IOException e) {
            System.err.println("[Erreur Fichier] Impossible de générer le relevé : " + e.getMessage());
        }
    }

    /**
     * Exporte un rapport global d'audit de la banque dans un fichier texte.
     */
    public static void exporterRapportBanque(Banque banque, String cheminFichier) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(cheminFichier))) {
            pw.println("================================================================");
            pw.println("                  RAPPORT GLOBAL DE LA BANQUE                   ");
            pw.println("================================================================");
            pw.println("Date du rapport   : " + LocalDateTime.now().format(FORMATTER));
            pw.println("Nombre de clients : " + banque.getClients().size());
            pw.println("Nombre de comptes : " + banque.getComptes().size());
            pw.println("Total des dépôts  : " + banque.totalDepots() + " DH");
            pw.println("Nb d'opérations   : " + banque.getHistorique().size());
            pw.println("Comptes à découvert : " + banque.comptesADecouvert().size());
            pw.println("----------------------------------------------------------------");
            pw.println("LISTE DES COMPTES ET LEURS SOLDES :");
            for (Compte c : banque.getComptes()) {
                pw.printf(" - %-20s | Titulaire: %-20s | Solde: %10s DH%n",
                        c.getNumero(),
                        c.getTitulaire() != null ? c.getTitulaire().getNom() : "N/A",
                        c.getSolde());
            }
            pw.println("================================================================");
            System.out.println("[Fichier] Rapport global de la banque généré : " + cheminFichier);
        } catch (IOException e) {
            System.err.println("[Erreur Fichier] Échec d'export du rapport : " + e.getMessage());
        }
    }
}
