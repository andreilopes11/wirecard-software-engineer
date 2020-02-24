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
import br.com.wirecard.payments.model.Holder;
import br.com.wirecard.payments.repository.HoldersRepository;

/**
 * Classe {@link HolderController} que salva Holders em uma base de dados.
 * 
 * @author andrei-lopes - 2020-02-21
 */
@RestController
@Validated
@RequestMapping("/v1/holder/")
public class HolderController {

	private static final String UNEXPECTED_ERROR = "Unexpected error: ";

	@Autowired
	private HoldersRepository repository;

	/**
	 * Metodo que gera um Holder
	 * 
	 * @param holder
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@PostMapping
	public ResponseEntity<?> createHolder(@RequestBody @Valid Holder holder) throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();
			data.setData(repository.save(holder));

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que atualiza um Holder
	 * 
	 * @param newHolder
	 * @param id
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@PutMapping("{id}")
	public ResponseEntity<?> updateHolder(@RequestBody Holder newHolder, @PathVariable("id") Long id)
			throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();

			Holder update = repository.findById(id).map(holder -> {
				holder.setName(newHolder.getName());
				holder.setDocumentNumber(newHolder.getDocumentNumber());
				holder.setBirthDate(newHolder.getBirthDate());
				return repository.save(holder);
			}).orElseGet(() -> {
				newHolder.setId(id);
				return repository.save(newHolder);
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
	 * Metodo que deleta um Holder
	 * 
	 * @param holder
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@DeleteMapping
	public ResponseEntity<?> deleteHolder(@RequestBody Holder holder) throws Exception, CustomException {

		try {
			repository.delete(holder);
			return new ResponseEntity<>(holder, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que deleta um Holder pelo @param holder
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<?> DeleteHolderId(@RequestBody Holder holder, @PathVariable("id") Long id)
			throws Exception, CustomException {

		try {
			repository.deleteById(id);
			return new ResponseEntity<>(obterTodosHolder(holder), HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que gera uma lista de Holder
	 * 
	 * @param holder
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@GetMapping("list")
	public ResponseEntity<?> obterTodosHolder(@RequestBody Holder holder) throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();
			Iterable<Holder> listHolder = repository.findAll();
			data.setData(listHolder);

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que gera uma lista de Holder pelo @param id
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@GetMapping("list/{id}")
	public ResponseEntity<?> obterHolderId(@PathVariable("id") Long id) throws Exception, CustomException {

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
