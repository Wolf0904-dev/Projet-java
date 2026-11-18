package javaproject;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Banque {
    private List<Client> clients = new ArrayList<>();
    private List<Compte> comptes = new ArrayList<>();
    private List<Operation> historique = new ArrayList<>();
    private List<RegleOperation> regles = new ArrayList<>();  
    private List<Credit> credits = new ArrayList<>();

    public void ajouterClient(Client c) { clients.add(c); }
    public void ajouterCompte(Compte c) { comptes.add(c); }
    public void ajouterRegle(RegleOperation r) { regles.add(r); }

    
    public boolean effectuer(Operation op) {
        boolean reglesOk = regles.stream().allMatch(r -> r.estValide(op));
        if (reglesOk && op.executer()) {
            historique.add(op);
            return true;
        }
        return false;
    }

    public boolean annuler(Operation op, String justification) {
        Operation inverse = op.annuler(justification);
        if (inverse != null && inverse.executer()) {
            historique.add(inverse);
            return true;
        }
        return false;
    }

    public List<Client> rechercherClient(String texte) {
        String t = texte.toLowerCase();
        return clients.stream()
                .filter(Client::isActif)
                .filter(c -> c.getNom().toLowerCase().contains(t) || c.getCin().toLowerCase().contains(t))
                .collect(Collectors.toList());
    }

    public List<Compte> comptesDe(Client client) {
        return comptes.stream().filter(c -> c.getTitulaire() == client).collect(Collectors.toList());
    }

    public Optional<Compte> trouverCompte(String numero) {
        return comptes.stream().filter(c -> c.getNumero().equals(numero)).findFirst();
    }

    
    public List<Operation> filtrerOperations(Predicate<Operation> filtre) {
        return historique.stream().filter(filtre).collect(Collectors.toList());
    }

    public BigDecimal totalDepots() {
        return comptes.stream().map(Compte::getSolde).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<String, Long> nombreOperationsParType() {
        return historique.stream().collect(Collectors.groupingBy(Operation::getType, Collectors.counting()));
    }

    public List<Compte> comptesTriesParSolde() {
        return comptes.stream()
                .sorted(Comparator.comparing(Compte::getSolde).reversed())
                .collect(Collectors.toList());
    }

    public List<Compte> comptesADecouvert() {
        return comptes.stream().filter(c -> c.getSolde().signum() < 0).collect(Collectors.toList());
    }

    public List<Operation> getHistorique() { return historique; }
    public List<Client> getClients() { return clients; }
    public List<Compte> getComptes() { return comptes; }
    public List<Credit> getCredits() { return credits; }
    public void ajouterCredit(Credit credit) {
        credits.add(credit);
    }

    public List<Credit> creditsEnAttente() {
        return credits.stream()
                .filter(c -> "EN_ATTENTE".equals(c.getStatut()))
                .collect(Collectors.toList());
    }
}