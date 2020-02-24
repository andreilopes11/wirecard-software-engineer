package br.com.wirecard.payments.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe de Serialização de {@link ObjectData}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ObjectData {

	@JsonProperty("data")
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
