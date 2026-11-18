package javaproject;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Credit {
    private Client client;
    private BigDecimal montant;
    private int duree;            // en mois
    private BigDecimal taux;      // % par an
    private String statut = "EN_ATTENTE";   // EN_ATTENTE, VALIDE, REFUSE
    private String motifRefus;
    private List<Echeance> echeances = new ArrayList<>();

    public Credit(Client client, BigDecimal montant, int duree, BigDecimal taux) {
        this.client = client;
        this.montant = montant;
        this.duree = duree;
        this.taux = taux;
    }

    // Mensualité = P*r / (1 - (1+r)^-n) avec r = taux annuel / 12
    public BigDecimal calculerMensualite() {
        BigDecimal r = taux.divide(BigDecimal.valueOf(1200), MathContext.DECIMAL64);
        if (r.signum() == 0) return montant.divide(BigDecimal.valueOf(duree), 2, RoundingMode.HALF_UP);
        BigDecimal p = BigDecimal.ONE.add(r).pow(duree, MathContext.DECIMAL64);
        return montant.multiply(r).multiply(p).divide(p.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
    }

    // Stream : on génère les échéances de 1 à durée
    public void valider() {
        statut = "VALIDE";
        BigDecimal mensualite = calculerMensualite();
        echeances = IntStream.rangeClosed(1, duree)
                .mapToObj(i -> new Echeance(i, LocalDate.now().plusMonths(i), mensualite))
                .collect(Collectors.toList());
    }

    public void refuser(String motif) {
        statut = "REFUSE";
        this.motifRefus = motif;
    }

    public long nombreEcheancesEnRetard() {
        return echeances.stream().filter(Echeance::isEnRetard).count();
    }

    public Client getClient() { return client; }
    public BigDecimal getMontant() { return montant; }
    public int getDuree() { return duree; }
    public BigDecimal getTaux() { return taux; }
    public String getStatut() { return statut; }
    public String getMotifRefus() { return motifRefus; }
    public List<Echeance> getEcheances() { return echeances; }

    @Override
    public String toString() {
        return "Credit [client=" + client + ", montant=" + montant + ", duree=" + duree + " mois, taux=" + taux + "%, statut=" + statut + "]";
    }
}
