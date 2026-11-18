package javaproject;

import java.time.LocalDate;
import java.util.List;

public class directeur extends Utilisateur {

    public directeur() {
        super();
        setRole(Role.DIRECTEUR);
    }

    public directeur(String nom, String login, String motDePasse) {
        super(nom, login, motDePasse, Role.DIRECTEUR);
    }

    public directeur(int id, LocalDate datederecrutemment, String nomcomplet, String status, String email, int numero) {
        super(id, datederecrutemment, nomcomplet, status, email, numero, "", "", Role.DIRECTEUR, true);
    }

    public directeur(int id, LocalDate datederecrutemment, String nomcomplet, String status, String email, int numero,
                     String login, String motDePasseHash, boolean actif) {
        super(id, datederecrutemment, nomcomplet, status, email, numero, login, motDePasseHash, Role.DIRECTEUR, actif);
    }

    @Override
    public Role getRole() { return Role.DIRECTEUR; }

    @Override
    public List<String> getMenu() {
        return List.of("Valider un credit", "Refuser un credit", "Comptes a decouvert", "Statistiques", "Historique");
    }

    public void validerCredit(Credit credit) {
        if (credit != null) {
            credit.valider();
        }
    }

    public void refuserCredit(Credit credit, String motif) {
        if (credit != null) {
            credit.refuser(motif);
        }
    }
}
