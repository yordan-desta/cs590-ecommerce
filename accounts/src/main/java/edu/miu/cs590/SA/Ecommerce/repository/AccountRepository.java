package edu.miu.cs590.SA.Ecommerce.repository;

import edu.miu.cs590.SA.Ecommerce.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> getUserByUsername(String username);
    Optional<Account> getAccountById(Long id);
}
