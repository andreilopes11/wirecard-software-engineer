package br.com.wirecard.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe de inicializacao da Application {@link WirecardPaymentsApplication}
 * 
 * @author andrei-lopes - 2020-02-22
 */
@SpringBootApplication
public class WirecardPaymentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WirecardPaymentsApplication.class, args);
	}

}
