package com.dummy.nevmo.repository;

import com.dummy.nevmo.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<BankAccount, String> {

}

