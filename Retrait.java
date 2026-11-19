package javaproject;
import java.math.BigDecimal;

public class Retrait extends Operation {
    public Retrait(Compte compte, BigDecimal montant) { super(compte, montant); }

    @Override
    public boolean executer() { return compte.debiter(montant); }

    @Override
    public Operation creerInverse() { return new Versement(compte, montant); }

    @Override
    public String getType() { return "RETRAIT"; }
}
