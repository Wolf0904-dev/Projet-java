package javaproject;
import java.time.LocalDate;

public class personne {
	private int id ;
	private LocalDate datederecrutemment;
	private String nomcomplet;
	private String status;
	private String email;
	private int numero;
	public personne() {
		super();
	}

	public personne(String nomcomplet) {
		this.nomcomplet = nomcomplet;
	}

	public personne(int id, LocalDate datederecrutemment, String nomcomplet, String status, String email, int numero) {
		super();
		this.id = id;
		this.datederecrutemment = datederecrutemment;
		this.nomcomplet = nomcomplet;
		this.status = status;
		this.email = email;
		this.numero = numero;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getDatederecrutemment() {
		return datederecrutemment;
	}
	public void setDatederecrutemment(LocalDate datederecrutemment) {
		this.datederecrutemment = datederecrutemment;
	}
	public String getNomcomplet() {
		return nomcomplet;
	}
	public void setNomcomplet(String nomcomplet) {
		this.nomcomplet = nomcomplet;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	@Override
	public String toString() {
		return "personne [id=" + id + ", datederecrutemment=" + datederecrutemment + ", nomcomplet=" + nomcomplet
				+ ", status=" + status + ", email=" + email + ", numero=" + numero + "]";
	}

	
}
