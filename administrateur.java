package javaproject;

import java.time.LocalDate;
import java.util.List;

public class administrateur extends Utilisateur {

    public administrateur() {
        super();
        setRole(Role.ADMINISTRATEUR);
    }

    public administrateur(String nom, String login, String motDePasse) {
        super(nom, login, motDePasse, Role.ADMINISTRATEUR);
    }

    public administrateur(int id, LocalDate datederecrutemment, String nomcomplet, String status, String email, int numero) {
        super(id, datederecrutemment, nomcomplet, status, email, numero, "", "", Role.ADMINISTRATEUR, true);
    }

    public administrateur(int id, LocalDate datederecrutemment, String nomcomplet, String status, String email, int numero,
                          String login, String motDePasseHash, boolean actif) {
        super(id, datederecrutemment, nomcomplet, status, email, numero, login, motDePasseHash, Role.ADMINISTRATEUR, actif);
    }

    @Override
    public Role getRole() { return Role.ADMINISTRATEUR; }

    @Override
    public List<String> getMenu() {
        return List.of("Gestion des utilisateurs", "Gestion des regles", "Parametres systeme");
    }
}
