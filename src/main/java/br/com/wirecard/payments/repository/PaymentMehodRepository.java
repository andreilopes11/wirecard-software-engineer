package br.com.wirecard.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wirecard.payments.model.PaymentMethod;

/**
 * Interface de repositorio {@link PaymentMehodRepository}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@Repository
public interface PaymentMehodRepository extends JpaRepository<PaymentMethod, Long> {

}
