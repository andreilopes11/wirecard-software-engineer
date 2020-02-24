package br.com.wirecard.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wirecard.payments.model.Client;

/**
 * Interface de repositorio {@link ClientRepository}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
