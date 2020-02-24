package br.com.wirecard.payments.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.wirecard.payments.dto.errors.ExceptionResponse;

/**
 * Classe de tratamento de excecao {@link CustomException}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;

	private String text;

	public CustomException() {
		super();
	}

	public CustomException(String text) {
		super();
		this.text = text;
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity responseException(HttpStatus status) {
		ExceptionResponse resp = new ExceptionResponse();
		resp.setStatus(status.value());
		resp.setMessage(text);
		return new ResponseEntity<>(resp, status);
	}

	public String responseExceptionString(HttpStatus status) {
		ExceptionResponse resp = new ExceptionResponse();
		resp.setStatus(status.value());
		resp.setMessage(text);
		return resp.toString();
	}

	@SuppressWarnings("rawtypes")
	public ResponseEntity responseException(ExceptionResponse resp) {
		return new ResponseEntity<>(resp, HttpStatus.valueOf(resp.getStatus()));
	}

	public String responseExceptionInteger(ExceptionResponse resp) {
		return resp.toString();
	}

}