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

import br.com.wirecard.payments.enumeration.MethodPayment;

/**
 * Classe entidade {@link PaymentMethod}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@Entity
@Table(name = "payment_method")
public class PaymentMethod {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pay_meth_id")
	private Long id;

	@Column(name = "method")
	private MethodPayment method;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "creditCard_id")
	private CreditCard creditCard;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "billet_id")
	private Billet billet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MethodPayment getMethod() {
		return method;
	}

	public void setMethod(MethodPayment method) {
		this.method = method;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Billet getBillet() {
		return billet;
	}

	public void setBillet(Billet billet) {
		this.billet = billet;
	}

	@Override
	public String toString() {
		return "PaymentMethod [id=" + id + ", method=" + method + ", creditCard=" + creditCard + ", billet=" + billet
				+ "]";
	}

}
