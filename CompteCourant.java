package javaproject;
import java.math.BigDecimal;

public class CompteCourant extends Compte {
    private BigDecimal decouvertAutorise;

    public CompteCourant(String numero, Client titulaire, BigDecimal decouvertAutorise) {
        super(numero, titulaire);
        this.decouvertAutorise = decouvertAutorise;
    }

    @Override
    public boolean peutRetirer(BigDecimal montant) {
        return solde.add(decouvertAutorise).compareTo(montant) >= 0;
    }

    @Override
    public String getType() { return "COURANT"; }

    public BigDecimal getDecouvertAutorise() { return decouvertAutorise; }
}
