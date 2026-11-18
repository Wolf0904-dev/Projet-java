package javaproject;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Echeance {
    private int numero;
    private LocalDate date;
    private BigDecimal montant;
    private boolean payee = false;

    public Echeance(int numero, LocalDate date, BigDecimal montant) {
        this.numero = numero;
        this.date = date;
        this.montant = montant;
    }

    public void payer() { payee = true; }
    public boolean isEnRetard() { return !payee && date.isBefore(LocalDate.now()); }

    public int getNumero() { return numero; }
    public LocalDate getDate() { return date; }
    public BigDecimal getMontant() { return montant; }
    public boolean isPayee() { return payee; }

    @Override
    public String toString() { return "Echeance " + numero + " : " + montant + " le " + date + (payee ? " (payée)" : ""); }
}