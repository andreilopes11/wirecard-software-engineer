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
import br.com.wirecard.payments.model.Client;
import br.com.wirecard.payments.repository.ClientRepository;

/**
 * Classe {@link ClientController} que salva Clientes em uma base de dados.
 * 
 * @author andrei-lopes - 2020-02-21
 */
@RestController
@Validated
@RequestMapping("/v1/client/")
public class ClientController {

	private static final String UNEXPECTED_ERROR = "Unexpected error: ";

	@Autowired
	private ClientRepository repository;

	/**
	 * Metodo que gera um Cliente
	 * 
	 * @param client
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@PostMapping
	public ResponseEntity<?> createClient(@RequestBody @Valid Client client) throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();
			data.setData(repository.save(client));
			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que atualiza um Cliente
	 * 
	 * @param newClient
	 * @param id
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@PutMapping("{id}")
	public ResponseEntity<?> updateClient(@RequestBody Client newClient, @PathVariable("id") Long id)
			throws Exception, CustomException {

		try {
			Client update = repository.findById(id).map(client -> {
				client.setName(newClient.getName());
				return repository.save(client);
			}).orElseGet(() -> {
				newClient.setId(id);
				return repository.save(newClient);
			});
			ObjectData data = new ObjectData();
			data.setData(update);
			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que deleta um Cliente
	 * 
	 * @param client
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@DeleteMapping
	public ResponseEntity<?> deleteClient(@RequestBody Client client) throws Exception, CustomException {

		try {
			repository.delete(client);
			return new ResponseEntity<>(client, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que deleta um Cliente pelo @param id
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<?> DeleteClientId(@PathVariable("id") Long id) throws Exception, CustomException {

		try {
			repository.deleteById(id);
			return new ResponseEntity<>(obterTodosClient(), HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que gera uma lista de Cliente
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@GetMapping("list")
	public ResponseEntity<?> obterTodosClient() throws Exception, CustomException {

		try {
			ObjectData data = new ObjectData();
			Iterable<Client> listClient = repository.findAll();
			data.setData(listClient);

			return new ResponseEntity<>(data, HttpStatus.OK);

		} catch (HttpStatusCodeException statusCode) {
			return extracted(statusCode);

		} catch (Exception e) {
			return extracted(e);
		}
	}

	/**
	 * Metodo que gera uma lista de Cliente pelo @param id
	 * 
	 * @return
	 * @throws Exception
	 * @throws CustomException
	 */
	@GetMapping("list/{id}")
	public ResponseEntity<?> obterClientId(@PathVariable("id") Long id) throws Exception, CustomException {

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
