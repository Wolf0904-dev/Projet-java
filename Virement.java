package javaproject;
import java.math.BigDecimal;

public class Virement extends Operation {
    private Compte destination;

    public Virement(Compte source, Compte destination, BigDecimal montant) {
        super(source, montant);
        this.destination = destination;
    }

    @Override
    public boolean executer() {
        if (compte == destination) return false;

        boolean sourceOk = compte.getStatut().equals("ACTIF") && montant.signum() > 0 && compte.peutRetirer(montant);
        boolean destOk = destination.getStatut().equals("ACTIF");
        if (!sourceOk || !destOk) return false;
        compte.debiter(montant);
        destination.crediter(montant);
        return true;
    }

    @Override
    public Operation creerInverse() { return new Virement(destination, compte, montant); }

    @Override
    public String getType() { return "VIREMENT"; }

    public Compte getDestination() { return destination; }
}
