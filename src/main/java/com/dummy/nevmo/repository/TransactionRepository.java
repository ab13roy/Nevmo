package com.dummy.nevmo.repository;

import com.dummy.nevmo.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, String> {

    @Query("SELECT t FROM Transactions t where t.fromAccount = ?1 or t.toAccount = ?1")
    List<Transactions> findForUser(String user);
}
