package edu.miu.cs590.SA.Ecommerce.service;

import edu.miu.cs590.SA.Ecommerce.domain.Account;
import edu.miu.cs590.SA.Ecommerce.dto.AccountDTO;
import edu.miu.cs590.SA.Ecommerce.dto.AccountRegistrationDTO;
import edu.miu.cs590.SA.Ecommerce.dto.LoginDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<Account> findById(Long id);
    List<AccountDTO> findAll();
    AccountDTO save(AccountRegistrationDTO accountBody);
    Account update(Long id, AccountRegistrationDTO accountBody);
    ResponseEntity<?> authenticate(LoginDTO credentialsBody);
    void deleteById(Long id);
}
