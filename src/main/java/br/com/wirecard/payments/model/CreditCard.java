package br.com.wirecard.payments.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * Classe entidade {@link CreditCard}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@Entity
@Table(name = "credit_card")
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "creditCard_id")
	private Long id;

	@Column(name = "brand")
	private String brand;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "holder_id")
	private Holder holder;

	@NotBlank(message = "The card number must be completed.")
	@Column(name = "card_number")
	private String cardNumber;

	@Column(name = "expiration_data")
	private String expirationDate;

	@NotBlank(message = "The CVV must be completed.")
	@Column(name = "cvv")
	private String cvv;

	@Override
	public String toString() {
		return "CreditCard [id=" + id + ", brand=" + brand + ", holder=" + holder + ", cardNumber=" + cardNumber
				+ ", expirationDate=" + expirationDate + ", cvv=" + cvv + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Holder getHolder() {
		return holder;
	}

	public void setHolder(Holder holder) {
		this.holder = holder;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

}
