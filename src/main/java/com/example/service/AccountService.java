package com.example.service;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

private AccountRepository accountRepository;

@Autowired
public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
}

public Account saveNewAccount(Account account){

    Optional<Account> accountOptional = accountRepository.findAccountByUsername(account.getUsername());
    System.out.println(accountOptional);
    
    if(!accountOptional.isPresent()){
        System.out.println("NOT PRESENT"); 
     return accountRepository.save(account);
    }else {
        System.out.println("USER IS PRESENT DUP"); 
        return null; 
    }
}


public Account login (Account account){
    Optional<Account> accountOptional = accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
    if(accountOptional.isPresent()){
        Account acct = accountOptional.get();
        return acct;
    }
      return null; 
    }
}
