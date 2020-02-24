package br.com.wirecard.payments.enumeration;

/**
 * Enum {@link MethodPayment}
 * 
 * @author andrei-lopes - 2020-02-22
 */
public enum MethodPayment {

	CREDIT_CARD("credit_card"), BILLET("billet");

	private String value;

	public String getValue() {
		return value;
	}

	private MethodPayment(String value) {
		this.value = value;
	}

}
