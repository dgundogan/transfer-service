package co.uk.rbs.account.repository;

import co.uk.rbs.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}