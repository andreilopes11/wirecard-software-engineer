package br.com.wirecard.payments.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wirecard.payments.model.Billet;

/**
 * Classe {@link BilletController} que seta valores de Boletos.
 * 
 * @author andrei-lopes - 2020-02-21
 */
@RestController
@Validated
@RequestMapping("/v1/billet")
public class BilletController {

	@PostMapping
	public Billet transfBillet(String code, String expirationDate) {

		Billet billet = new Billet();

		billet.setCode(code);
		billet.setExpirationDate(expirationDate);

		return billet;
	}
}