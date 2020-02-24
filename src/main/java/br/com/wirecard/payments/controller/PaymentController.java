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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import br.com.wirecard.payments.dto.ObjectData;
import br.com.wirecard.payments.dto.errors.ExceptionResponse;
import br.com.wirecard.payments.enumeration.PaymentStatus;
import br.com.wirecard.payments.exception.CustomException;
import br.com.wirecard.payments.model.Billet;
import br.com.wirecard.payments.model.CreditCard;
import br.com.wirecard.payments.model.Payment;
import br.com.wirecard.payments.repository.PaymentRepository;

/**
 * Classe {@link PaymentController} que salva Pagamentos em uma base de dados.
 * 
 * @author andrei-lopes - 2020-02-21
 */
@RestController
@Validated
@RequestMapping("/v1/payment/")
public class PaymentController {

	String resp = "";
	StringBuilder builder = new StringBuilder(resp);

	private static final String UNEXPECTED_ERROR = "Unexpected error: ";
	private static final String MESSAGE_ERROR = "Error: ";

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private BilletController billetDto;

	@Autowired
	private CreditCardController creditCardController;

	/**
	 * Metodo que gera uma operacao de pagamento
	 * 
	 * @param payment
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@PostMapping
	public String createPayment(@RequestBody @Valid Payment payment) throws Exception, CustomException {

		this.builder.delete(0, builder.length());

		if (payment.getPaymentMethod().getMethod().name().equalsIgnoreCase("billet")) {

			payment.getPaymentMethod().setBillet(this.transferBillet());
			payment.setStatusPayment(PaymentStatus.CREATED);

			try {
				builder.append("/ Payment successfully registered. //");

				return this.builder + " \nCode Billet: "
						+ paymentRepository.save(payment).getPaymentMethod().getBillet().getCode()
						+ " \nExpiration Date: " + payment.getPaymentMethod().getBillet().getExpirationDate();

			} catch (HttpStatusCodeException statusCode) {
				return extractedString(statusCode);

			} catch (Exception e) {
				return extractedString(e);
			}

		} else if (!(creditCardExits(payment.getPaymentMethod().getCreditCard())) && validAmount(payment.getAmount())
				&& validCard(payment.getPaymentMethod().getCreditCard().getCardNumber(),
						payment.getPaymentMethod().getCreditCard().getCvv())) {

			try {
				payment.setStatusPayment(PaymentStatus.CREATED);
				builder.append("/ Payment successfully registered. //");

				return this.builder + "Status: " + paymentRepository.save(payment).getStatusPayment().name();

			} catch (HttpStatusCodeException statusCode) {
				return extractedString(statusCode);

			} catch (Exception e) {
				return extractedString(e);
			}
		}
		return MESSAGE_ERROR + this.builder;
	}

	/**
	 * Metodo que deleta uma operacao de pagamento
	 * 
	 * @param payment
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@DeleteMapping
	public ResponseEntity<?> deletePayment(@RequestBody Payment payment) throws Exception, CustomException {

		try {
			paymentRepository.delete(payment);
			return new ResponseEntity<>(payment, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que deleta uma operacao de pagamento pelo @param id
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletePaymentId(@PathVariable("id") Long id) throws Exception, CustomException {

		try {
			paymentRepository.deleteById(id);
			return new ResponseEntity<>(obterTodosPayments(), HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que gera uma lista de operacao de pagamento pelo @param id
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@GetMapping("list/{id}")
	public ResponseEntity<?> getPaymentId(@PathVariable("id") Long id) throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();
			data.setData(paymentRepository.findById(id));

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que gera uma lista de operacao de pagamento
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@GetMapping("list")
	public ResponseEntity<?> obterTodosPayments() throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();
			Iterable<Payment> listPayment = paymentRepository.findAll();
			data.setData(listPayment);

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	public boolean validCard(String cardNumber) {
		if (creditCardController.validNumberCard(cardNumber)) {
			return true;
		}
		builder.append(" /Invalid card. Please, the code must be 16 numeric digits. ");
		return false;
	}

	public boolean validCard(String cardNumber, String cvv) {
		return (validNumberCard(cardNumber) && (validDV(cvv)));
	}

	private boolean validDV(String cvv) {
		if (creditCardController.validDV(cvv)) {
			return true;
		}
		builder.append("/ Invalid Digit /");
		return false;
	}

	private boolean validNumberCard(String cardNumber) {
		if (creditCardController.validNumberCard(cardNumber)) {
			return true;
		}
		return false;
	}

	public boolean validAmount(Double amount) throws InvalidFormatException {
		if (amount < 10000000) {

		}
		return true;
	}

	public boolean creditCardExits(CreditCard creditCard) {

		if (creditCardController.creditCardExits(creditCard)) {
			builder.append("Operation not performed. Card already registered.");
			return true;
		}
		return false;
	}

	public Billet transferBillet() {
		return billetDto.transfBillet("123456789123456789123456789123456789123456789", "2020-02-21T17:45:55.9483536");
	}

	private String extractedString(HttpStatusCodeException statusCode)
			throws JsonProcessingException, JsonMappingException {
		ExceptionResponse exception = new ObjectMapper().readValue(statusCode.getResponseBodyAsString(),
				ExceptionResponse.class);
		CustomException custom = new CustomException();
		return custom.responseExceptionInteger(exception);
	}

	private String extractedString(Exception e) {
		CustomException custom = new CustomException(UNEXPECTED_ERROR + e.getMessage());
		return custom.responseExceptionString(HttpStatus.INTERNAL_SERVER_ERROR);
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