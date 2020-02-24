package br.com.wirecard.payments.dto.errors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe de serializacao {@link Errors}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Errors {

	@JsonProperty("field")
	private String field;

	@JsonProperty("message")
	private String message;

	@JsonProperty("value")
	private String value;

	public Errors() {
		super();
	}

	public Errors(String field, String message, String value) {
		super();
		this.field = field;
		this.message = message;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
