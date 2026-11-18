package javaproject;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CompteEpargne extends Compte {
    private BigDecimal tauxInteret;  

    public CompteEpargne(String numero, Client titulaire, BigDecimal tauxInteret) {
        super(numero, titulaire);
        this.tauxInteret = tauxInteret;
    }

    @Override
    public boolean peutRetirer(BigDecimal montant) {
        return solde.compareTo(montant) >= 0;  
    }

    @Override
    public String getType() { return "EPARGNE"; }

    public BigDecimal calculerInterets() {
        return solde.multiply(tauxInteret).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTauxInteret() { return tauxInteret; }
}