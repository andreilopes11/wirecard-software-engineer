package br.com.wirecard.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wirecard.payments.model.Holder;

/**
 * Interface de repositorio {@link HoldersRepository}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@Repository
public interface HoldersRepository extends JpaRepository<Holder, Long> {

}
