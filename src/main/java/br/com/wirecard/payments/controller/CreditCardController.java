package br.com.wirecard.payments.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.wirecard.payments.dto.ObjectData;
import br.com.wirecard.payments.dto.errors.ExceptionResponse;
import br.com.wirecard.payments.exception.CustomException;
import br.com.wirecard.payments.model.CreditCard;
import br.com.wirecard.payments.repository.CreditCardRepository;

/**
 * Classe {@link CreditCardController} que salva Cartoes de Credito em uma base
 * de dados.
 * 
 * @author andrei-lopes - 2020-02-21
 */
@RestController
@Validated
@RequestMapping("/v1/credit-card/")
public class CreditCardController {

	private static final String UNEXPECTED_ERROR = "Unexpected error: ";

	@Autowired
	private CreditCardRepository repository;

	/**
	 * Metodo que gera uma operacao de cartao de credito
	 * 
	 * @param creditCard
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@PostMapping
	public ResponseEntity<?> createCreditCard(@RequestBody @Valid CreditCard creditCard)
			throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();
			data.setData(repository.save(creditCard));

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que atualiza uma operacao de cartao de credito
	 * 
	 * @param newCreditCard
	 * @param id
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@PutMapping("{id}")
	public ResponseEntity<?> updateCreditCard(@RequestBody CreditCard newCreditCard, @PathVariable("id") Long id)
			throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();

			CreditCard update = repository.findById(id).map(creditCard -> {
				creditCard.setBrand(newCreditCard.getBrand());
				creditCard.setCardNumber(newCreditCard.getCardNumber());
				creditCard.setCvv(newCreditCard.getCvv());
				creditCard.setExpirationDate(newCreditCard.getExpirationDate());
				creditCard.setHolder(newCreditCard.getHolder());
				return repository.save(creditCard);
			}).orElseGet(() -> {
				newCreditCard.setId(id);
				return repository.save(newCreditCard);
			});
			data.setData(update);
			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que deleta uma operacao de cartao de credito
	 * 
	 * @param creditCard
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@DeleteMapping
	public ResponseEntity<?> deleteCreditCard(@RequestBody CreditCard creditCard) throws Exception, CustomException {

		try {
			repository.delete(creditCard);
			return new ResponseEntity<>(creditCard, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que deleta uma operacao de cartao de credito pelo @param id
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteCreditCardId(@PathVariable("id") Long id) throws Exception, CustomException {

		try {
			repository.deleteById(id);
			return new ResponseEntity<>(obterTodosCreditCard(), HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que gera uma lista operacao de cartao de credito
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@GetMapping("list")
	public ResponseEntity<?> obterTodosCreditCard() throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();
			Iterable<CreditCard> listCreditCard = repository.findAll();
			data.setData(listCreditCard);

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que gera uma lista operacao de cartao de credito pelo @param id
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@GetMapping("list/{id}")
	public ResponseEntity<?> obterCreditCardId(@PathVariable("id") Long id) throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();
			data.setData(repository.findById(id));
			
			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	private ResponseEntity<?> extracted(HttpStatusCodeException statusCode)
			throws JsonProcessingException, JsonMappingException {
		ExceptionResponse exception = new ObjectMapper().readValue(statusCode.getResponseBodyAsString(),
				ExceptionResponse.class);
		CustomException custom = new CustomException();
		return custom.responseException(exception);
	}

	private ResponseEntity<?> extracted(Exception e) {
		CustomException custom = new CustomException(UNEXPECTED_ERROR + e.getMessage());
		return custom.responseException(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public boolean creditCardExits(CreditCard creditCard) {
		List<CreditCard> findAll = repository.findAll();

		for (CreditCard card : findAll) {
			if (card.getCardNumber().equals(creditCard.getCardNumber())) {
				return true;
			}
		}
		return false;
	}

	public boolean validNumberCard(String cardNumber) {
		if (cardNumber.length() == 16 && cardNumber.matches("[0-9]*")) {
			return true;
		}
		return false;
	}

	public boolean validDV(String cvv) {
		if (cvv.length() == 3 && cvv.matches("[0-9]*")) {
			return true;
		}
		return false;
	}

}
