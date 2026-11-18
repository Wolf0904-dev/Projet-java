package javaproject;
import java.math.BigDecimal;

public abstract class Compte {
    protected String numero;
    protected Client titulaire;
    protected BigDecimal solde = BigDecimal.ZERO;
    protected String statut = "ACTIF";          

    public Compte(String numero, Client titulaire) {
        this.numero = numero;
        this.titulaire = titulaire;
    }

   
    public abstract boolean peutRetirer(BigDecimal montant);
    public abstract String getType();

    public boolean crediter(BigDecimal montant) {
        if (montant.signum() <= 0 || !statut.equals("ACTIF")) return false;
        solde = solde.add(montant);
        return true;
    }

    public boolean debiter(BigDecimal montant) {
        if (montant.signum() <= 0 || !statut.equals("ACTIF")) return false;
        if (!peutRetirer(montant)) return false;
        solde = solde.subtract(montant);
        return true;
    }

    public void bloquer() { statut = "BLOQUE"; }
    public void debloquer() { statut = "ACTIF"; }

    public boolean cloturer() {
        if (solde.signum() != 0) return false;   // solde nul obligatoire
        statut = "CLOTURE";
        return true;
    }

    public String getNumero() { return numero; }
    public Client getTitulaire() { return titulaire; }
    public BigDecimal getSolde() { return solde; }
    public String getStatut() { return statut; }

    @Override
    public String toString() { return getType() + " " + numero + " | solde = " + solde + " | " + statut; }
}