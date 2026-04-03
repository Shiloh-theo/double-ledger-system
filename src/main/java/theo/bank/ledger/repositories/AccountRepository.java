package theo.bank.ledger.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import theo.bank.ledger.models.Accounts;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Integer> {

    Accounts findByEmail(String email);

    Accounts findByAccountNo(String creditAccount);

}
