package br.com.wirecard.payments.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe entidade {@link Buyer}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@Entity
@Table(name = "buyer")
public class Buyer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "buyer_id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "cpf")
	private String cpf;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "Buyer [id=" + id + ", name=" + name + ", email=" + email + ", cpf=" + cpf + "]";
	}

}
