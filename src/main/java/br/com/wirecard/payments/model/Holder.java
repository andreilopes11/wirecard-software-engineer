package br.com.wirecard.payments.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe entidade {@link Holder}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@Entity
@Table(name = "holder")
public class Holder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "holder_id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "birth_date")
	private String birthDate;

	@Column(name = "document_number")
	private String documentNumber;

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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	@Override
	public String toString() {
		return "Holder [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", documentNumber=" + documentNumber
				+ ", getId()=" + getId() + ", getName()=" + getName() + ", getBirthDate()=" + getBirthDate()
				+ ", getDocumentNumber()=" + getDocumentNumber() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
