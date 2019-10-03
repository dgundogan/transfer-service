package co.uk.rbs.account.service;

import co.uk.rbs.account.entity.Account;
import co.uk.rbs.account.entity.Transaction;
import co.uk.rbs.account.handler.exception.NotFoundException;
import co.uk.rbs.account.repository.AccountRepository;
import co.uk.rbs.account.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransferServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public Transaction performTransfer(String srcAccNum, String destAccNum, BigDecimal amount) {

        final Account srcAcc = accountRepository.findById(srcAccNum)
                .orElseThrow(() -> new NotFoundException("Account not found."));

        final Account destAcc = accountRepository.findById(destAccNum)
                .orElseThrow(() -> new NotFoundException("Destination Account not found."));

        validateAmount(amount);

        validateTransfer(srcAcc.getBalance(), amount);

        accountRepository.save(Account.builder()
                .accountNumber(srcAccNum)
                .balance(srcAcc.getBalance().subtract(amount))
                .build());

        accountRepository.save(Account.builder()
                .accountNumber(destAccNum)
                .balance(destAcc.getBalance().add(amount))
                .build());

        return transactionRepository.save(Transaction.builder()
                .sourceAccountNumber(srcAccNum)
                .destinationAccountNumber(destAccNum)
                .amount(amount).build());
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("The amount is missing.");
        }
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("The amount must be greater then zero.");
        }
    }

    private void validateTransfer(BigDecimal balance, BigDecimal amount) {

        if (balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Cannot perform the transfer. The balance not sufficient.");
        }
    }
}