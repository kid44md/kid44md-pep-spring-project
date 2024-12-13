package com.example.repository;
import com.example.entity.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AccountRepository extends JpaRepository <Account, Integer> {

    Optional<Account> findAccountByUsername(String username);


    Optional<Account> findAccountByUsernameAndPassword(String username, String password );

}
