package br.com.wirecard.payments.dto.errors;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Classe de serializacao {@link ErrorResponse}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {

	@JsonProperty("code")
	private String code;

	@JsonProperty("message")
	private String message;

	@JsonProperty("errors")
	List<Errors> errors;

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(String code, String message, List<Errors> errors) {
		super();
		this.code = code;
		this.message = message;
		this.errors = errors;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Errors> getErrors() {
		return errors;
	}

	public void setErrors(List<Errors> errors) {
		this.errors = errors;
	}

}
