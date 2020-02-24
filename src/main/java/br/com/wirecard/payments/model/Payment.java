package br.com.wirecard.payments.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import br.com.wirecard.payments.enumeration.PaymentStatus;

/**
 * Classe entidade {@link Payment}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@Entity
@Table(name = "payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "payment_id")
	private Long id;

	@NotNull
	@Column(name = "amount")
	private Double amount;

	@NotNull
	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "pay_meth_id")
	private PaymentMethod paymentMethod;

	@Column(name = "payment_status")
	private PaymentStatus statusPayment;

	@ManyToOne
	@JoinColumn(name = "buyer_id", referencedColumnName = "buyer_id")
	private Buyer buyer;

	@ManyToOne
	@JoinColumn(name = "client_id", referencedColumnName = "client_id")
	private Client client;

	public Payment(double d, PaymentStatus created, Buyer buyer2, Client client2) {
		// TODO Auto-generated constructor stub
	}

	public Payment() {
		super();
	}

	public Payment(Long id, Double amount, PaymentMethod paymentMethod, PaymentStatus statusPayment, Buyer buyer,
			Client client) {
		super();
		this.id = id;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.statusPayment = statusPayment;
		this.buyer = buyer;
		this.client = client;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PaymentStatus getStatusPayment() {
		return statusPayment;
	}

	public void setStatusPayment(PaymentStatus statusPayment) {
		this.statusPayment = statusPayment;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", amount=" + amount + ", paymentMethod=" + paymentMethod + ", statusPayment="
				+ statusPayment + ", buyer=" + buyer + ", client=" + client + "]";
	}

}