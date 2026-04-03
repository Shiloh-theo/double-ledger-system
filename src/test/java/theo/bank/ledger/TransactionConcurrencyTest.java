package theo.bank.ledger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import theo.bank.ledger.controllers.TransactionController;
import theo.bank.ledger.models.Accounts;
import theo.bank.ledger.repositories.AccountRepository;
import theo.bank.ledger.repositories.TransactionRepository;
import theo.bank.ledger.services.TransactionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import theo.bank.ledger.dto.TransactionDto;

import java.math.BigDecimal;

@SpringBootTest
public class TransactionConcurrencyTest {

    @Autowired
    TransactionService service;

    @Autowired
    AccountRepository accountRepository;

    @Test
    void shouldHandleConcurrentTransfersWithoutCorruption() throws InterruptedException {
        Accounts sender = new Accounts();
        sender.setAccountNo("0000000001");
        sender.setBalance(new BigDecimal("10000.00"));
        sender.setEmail("sender@test.com");
        sender.setPassword("test");
        sender.setCustomerId(30);
        accountRepository.save(sender);

        Accounts receiver = new Accounts();
        receiver.setAccountNo("0000000002");
        receiver.setBalance(BigDecimal.ZERO);
        receiver.setEmail("receiver@test.com");
        receiver.setPassword("test");
        receiver.setCustomerId(31);
        accountRepository.save(receiver);

        // Stage 2 - Set up concurrency tools
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger successCount = new AtomicInteger(0);

// Stage 3 - Submit 100 threads
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    latch.await(); // all threads wait here
                    TransactionDto dto = new TransactionDto();
                    dto.setDebitAccount("0000000001");
                    dto.setCreditAccount("0000000002");
                    dto.setAmount(new BigDecimal("100.00"));
                    ResponseEntity<String> response = service.transfer(dto);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        successCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    System.out.println("Transfer failed: " + e.getMessage());
                }
            });
        }

// Stage 4 - Release all threads simultaneously
        latch.countDown();
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);

// Stage 5 - Assert correctness
        Accounts finalSender = accountRepository.findByAccountNo("0000000001");
        BigDecimal expectedBalance = new BigDecimal("10000.00")
                .subtract(new BigDecimal("100.00").multiply(new BigDecimal(successCount.get())));
        System.out.println("Successful transfers: " + successCount.get());
        System.out.println("Expected balance: " + expectedBalance);
        System.out.println("Actual balance: " + finalSender.getBalance());
        assertEquals(0, finalSender.getBalance().compareTo(expectedBalance));

// Stage 6 - Cleanup
// Stage 6 - Cleanup (re-fetch to get current version)
        accountRepository.findById(sender.getAccountId())
                .ifPresent(accountRepository::delete);
        accountRepository.findById(receiver.getAccountId())
                .ifPresent(accountRepository::delete);
    }
}
