package javaproject;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Utilisateur extends personne {
    private String login;
    private String motDePasseHash;   
    private Role role;
    private boolean actif = true;

    public Utilisateur() {
        super();
    }

    public Utilisateur(String nom, String login, String motDePasse) {
        super(nom);
        this.login = login;
        this.motDePasseHash = motDePasse;
    }

    public Utilisateur(String nom, String login, String motDePasse, Role role) {
        this(nom, login, motDePasse);
        this.role = role;
    }

    public Utilisateur(int id, LocalDate datederecrutemment, String nomcomplet, String status, String email, int numero,
                       String login, String motDePasseHash, Role role, boolean actif) {
        super(id, datederecrutemment, nomcomplet, status, email, numero);
        this.login = login;
        this.motDePasseHash = motDePasseHash;
        this.role = role;
        this.actif = actif;
    }

    public boolean aLeRole(Role r) { return this.getRole() == r; }

    public List<String> getMenu() {
        return Collections.emptyList();
    }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getMotDePasseHash() { return motDePasseHash; }
    public void setMotDePasseHash(String motDePasseHash) { this.motDePasseHash = motDePasseHash; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getRoleName() { return getRole() != null ? getRole().name() : ""; }
    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }
}
