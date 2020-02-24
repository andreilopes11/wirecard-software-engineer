package br.com.wirecard.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wirecard.payments.model.Buyer;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {

}
