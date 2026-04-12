package theo.bank.ledger.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import theo.bank.ledger.models.Accounts;
import theo.bank.ledger.repositories.AccountRepository;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Accounts account = accountRepository.findByEmail(email);

        if (account == null) {
            throw new UsernameNotFoundException("No account found for email: " + email);
        }

        return new User(account.getEmail(), account.getPassword(), List.of());
    }
}
