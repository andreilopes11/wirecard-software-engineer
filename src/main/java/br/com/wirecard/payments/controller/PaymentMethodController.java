package br.com.wirecard.payments.controller;

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
import br.com.wirecard.payments.model.PaymentMethod;
import br.com.wirecard.payments.repository.PaymentMehodRepository;

/**
 * Classe {@link PaymentMethodController} que salva Métodos de Pagamentos em uma
 * base de dados.
 * 
 * @author andrei-lopes - 2020-02-21
 */
@RestController
@Validated
@RequestMapping("/v1/payment-method/")
public class PaymentMethodController {

	private static final String UNEXPECTED_ERROR = "Unexpected error: ";

	@Autowired
	private PaymentMehodRepository repository;

	/**
	 * Metodo que gera uma operacao de método pagamento
	 * 
	 * @param paymentMethod
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@PostMapping
	public ResponseEntity<?> createPaymentMethod(@RequestBody @Valid PaymentMethod paymentMethod)
			throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();
			data.setData(repository.save(paymentMethod));

			return new ResponseEntity<>(repository.save(paymentMethod), HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que atualiza uma operacao de método pagamento
	 * 
	 * @param newPaymentMethod
	 * @param id
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@PutMapping("{id}")
	public ResponseEntity<?> updatePaymentMethod(@RequestBody PaymentMethod newPaymentMethod,
			@PathVariable("id") Long id) throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();
			PaymentMethod update = repository.findById(id).map(paymentMethod -> {
				paymentMethod.setMethod(newPaymentMethod.getMethod());
				paymentMethod.setCreditCard(newPaymentMethod.getCreditCard());
				paymentMethod.setBillet(newPaymentMethod.getBillet());
				return repository.save(paymentMethod);
			}).orElseGet(() -> {
				newPaymentMethod.setId(id);
				return repository.save(newPaymentMethod);
			});
			data.setData(update);
			return new ResponseEntity<>(update, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que deleta uma operacao de método pagamento
	 * 
	 * @param paymentMethod
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@DeleteMapping
	public ResponseEntity<?> deletePaymentMethod(@RequestBody PaymentMethod paymentMethod)
			throws Exception, CustomException {

		try {
			repository.delete(paymentMethod);
			return new ResponseEntity<>(paymentMethod, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que deleta uma operacao de método pagamento pelo @param id
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<?> DeletePaymentMethodId(@PathVariable("id") Long id) throws Exception, CustomException {

		try {
			repository.deleteById(id);
			return new ResponseEntity<>(obterTodosPaymentMethod(), HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que gera uma lista de operacao de método pagamento
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@GetMapping("list")
	public ResponseEntity<?> obterTodosPaymentMethod() throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();
			Iterable<PaymentMethod> listPaymentMethod = repository.findAll();
			data.setData(listPaymentMethod);

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que gera uma lista de operacao de método pagamento pelo @param id
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@GetMapping("list/{id}")
	public ResponseEntity<?> obterPaymentMethodId(@PathVariable("id") Long id) throws Exception, CustomException {

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

}
