package br.com.wirecard.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wirecard.payments.model.CreditCard;

/**
 * Interface de repositorio {@link CreditCardRepository}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

}
