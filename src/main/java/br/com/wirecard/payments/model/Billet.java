package br.com.wirecard.payments.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe entidade {@link Billet}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@Entity
@Table(name = "billet")
public class Billet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "billet_id")
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "expiration_date")
	private String expirationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public String toString() {
		return "Billet [id=" + id + ", code=" + code + ", expirationDate=" + expirationDate + "]";
	}
}