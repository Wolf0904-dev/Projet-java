package javaproject;
import java.math.BigDecimal;

public class Versement extends Operation {
    public Versement(Compte compte, BigDecimal montant) { super(compte, montant); }

    @Override
    public boolean executer() { return compte.crediter(montant); }

    @Override
    public Operation creerInverse() { return new Retrait(compte, montant); }

    @Override
    public String getType() { return "VERSEMENT"; }
}
