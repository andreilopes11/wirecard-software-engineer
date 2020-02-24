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
import br.com.wirecard.payments.model.Buyer;
import br.com.wirecard.payments.repository.BuyerRepository;

/**
 * Classe {@link BuyerController} que salva Compradores em uma base de dados.
 * 
 * @author andrei-lopes - 2020-02-21
 */
@RestController
@Validated
@RequestMapping("/v1/buyer/")
public class BuyerController {

	private static final String UNEXPECTED_ERROR = "Unexpected error: ";

	@Autowired
	private BuyerRepository repository;

	/**
	 * Metodo que gera um comprador
	 * 
	 * @param buyer
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@PostMapping
	public ResponseEntity<?> createBuyer(@RequestBody @Valid Buyer buyer) throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();
			data.setData(repository.save(buyer));

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que atualiza um comprador
	 * 
	 * @param newBuyer
	 * @param id
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@PutMapping("{id}")
	public ResponseEntity<?> updateBuyer(@RequestBody Buyer newBuyer, @PathVariable("id") Long id)
			throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();

			Buyer update = repository.findById(id).map(buyer -> {
				buyer.setCpf(newBuyer.getCpf());
				buyer.setEmail(newBuyer.getEmail());
				buyer.setName(newBuyer.getName());
				return repository.save(buyer);
			}).orElseGet(() -> {
				newBuyer.setId(id);
				return repository.save(newBuyer);
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
	 * Metodo que deleta um comprador
	 * 
	 * @param buyer
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@DeleteMapping
	public ResponseEntity<?> deleteBuyer(@RequestBody Buyer buyer) throws Exception, CustomException {

		try {
			return new ResponseEntity<>(buyer, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que deleta um comprador pelo @param id
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<?> DeleteBuyerId(@PathVariable("id") Long id) throws Exception, CustomException {

		try {
			repository.deleteById(id);

			return new ResponseEntity<>(obterTodosBuyer(), HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que gera uma lista de comprador
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@GetMapping("list")
	public ResponseEntity<?> obterTodosBuyer() throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();
			Iterable<Buyer> listBuyer = repository.findAll();

			data.setData(listBuyer);

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que obtem um comprador pelo @param id
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@GetMapping("list/{id}")
	public ResponseEntity<?> obterBuyerId(@PathVariable("id") Long id) throws Exception, CustomException {

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
