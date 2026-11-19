package javaproject;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Operation {
    private static int compteur = 0;

    private int id;
    protected Compte compte;
    protected BigDecimal montant;
    private LocalDateTime date = LocalDateTime.now();
    private String statut = "VALIDEE";
    private String motif;

    public Operation(Compte compte, BigDecimal montant) {
        this.id = ++compteur;
        this.compte = compte;
        this.montant = montant;
    }

    public abstract boolean executer();
    public abstract Operation creerInverse();
    public abstract String getType();

    public Operation annuler(String justification) {
        if (statut.equals("ANNULEE") || justification == null || justification.isBlank()) return null;
        statut = "ANNULEE";
        motif = justification;
        return creerInverse();
    }

    public int getId() { return id; }
    public Compte getCompte() { return compte; }
    public BigDecimal getMontant() { return montant; }
    public LocalDateTime getDate() { return date; }
    public String getStatut() { return statut; }

    @Override
    public String toString() {
        return "#" + id + " " + getType() + " " + montant + " sur " + compte.getNumero() + " [" + statut + "]";
    }
}
