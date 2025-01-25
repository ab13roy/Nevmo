package com.dummy.nevmo;

import com.dummy.nevmo.entity.*;
import com.dummy.nevmo.repository.BankRepository;
import com.dummy.nevmo.repository.TransactionRepository;
import com.dummy.nevmo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class NevmoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NevmoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, BankRepository bankRepository, TransactionRepository transactionRepository) {
		return args -> {

			UserAccount user = new UserAccount("u123", "danny lorega", "danny@o.com", "password", AccountStatus.ACTIVE);
			user.setBalance(new BigDecimal("1234.33"));
			BankAccount bankAccount = new BankAccount(user, "123456789", "123456789", "Chase", new BigDecimal(1000), AccountType.CHECKING);
			user.getBankAccounts().add(bankAccount);
			userRepository.save(user);

			UserAccount user1 = new UserAccount("u122", "flo rida", "dfasdy@o.com", "password", AccountStatus.LOCKED);
			userRepository.save(user1);
			UserAccount user2 = new UserAccount("u124", "asda", "asdady@o.com", "password", AccountStatus.INACTIVE);
			userRepository.save(user2);

			Transactions transactions = new Transactions("123", LocalDate.now().toString(), new BigDecimal(10), "test", user1.getUserId(), user2.getUserId(), "nevmo");
			Transactions transactions1 = new Transactions("124", LocalDate.now().toString(), new BigDecimal(11), "test", user2.getUserId(), user1.getUserId(), "nevmo");
			Transactions transactions2 = new Transactions("125", LocalDate.now().toString(), new BigDecimal(12), "test", user.getUserId(), user2.getUserId(), user.getBankAccounts().get(0).getBankName());
			transactionRepository.save(transactions);
			transactionRepository.save(transactions1);
			transactionRepository.save(transactions2);

		};
	}

}
