package br.com.wirecard.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wirecard.payments.model.Payment;

/**
 * Interface de repositorio {@link PaymentRepository}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
