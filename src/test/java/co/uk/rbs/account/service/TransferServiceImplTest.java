package co.uk.rbs.account.service;

import co.uk.rbs.account.entity.Account;
import co.uk.rbs.account.entity.Transaction;
import co.uk.rbs.account.handler.exception.NotFoundException;
import co.uk.rbs.account.repository.AccountRepository;
import co.uk.rbs.account.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TransferServiceImplTest {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private TransferService service;

    @Before
    public void setUp() {
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        service = new TransferServiceImpl(accountRepository, transactionRepository);
    }

    @Test(expected = NotFoundException.class)
    public void givenInvalidAccountId_whenCallPerformTransfer_thenReturnsException() {
        when(accountRepository.findById("111-000-000")).thenReturn(Optional.empty());

        Transaction transaction = service.performTransfer("111-000-000", "111-000-001", new BigDecimal(50));
        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test(expected = NotFoundException.class)
    public void givenInvalidDestAccountId_whenCallPerformTransfer_thenReturnsException() {
        when(accountRepository.findById("111-000-000")).thenReturn(Optional.ofNullable(Account.builder().build()));
        when(accountRepository.findById("111-000-001")).thenReturn(Optional.empty());

        Transaction transaction = service.performTransfer("111-000-000", "111-000-001", new BigDecimal(50));
        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenAmountIsNull_whenCallPerformTransfer_thenReturnsException() {
        when(accountRepository.findById(anyString())).thenReturn(Optional.ofNullable(Account.builder().build()));

        Transaction transaction = service.performTransfer("111-000-000", "111-000-001", null);
        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenAmountIsLessThenZero_whenCallPerformTransfer_thenReturnsException() {
        when(accountRepository.findById(anyString())).thenReturn(Optional.ofNullable(Account.builder().build()));

        Transaction transaction = service.performTransfer("111-000-000", "111-000-001", new BigDecimal(-1));
        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenAmountIsEqualZero_whenCallPerformTransfer_thenReturnsException() {
        when(accountRepository.findById(anyString())).thenReturn(Optional.ofNullable(Account.builder().build()));

        Transaction transaction = service.performTransfer("111-000-000", "111-000-001", new BigDecimal(0));
        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenBalanceIsLessThanAmount_whenCallPerformTransfer_thenReturnsException() {
        when(accountRepository.findById("111-000-000")).thenReturn(Optional.ofNullable(Account.builder().balance(new BigDecimal(10)).build()));
        when(accountRepository.findById("111-000-001")).thenReturn(Optional.ofNullable(Account.builder().build()));

        Transaction transaction = service.performTransfer("111-000-000", "111-000-001", new BigDecimal(11));
        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    public void givenValidAccountAndDto_whenCallPerformTransfer_thenReturnsOK() {
        when(accountRepository.findById("111-000-000")).thenReturn(Optional.ofNullable(Account.builder().accountNumber("111-000-000").balance(new BigDecimal(100)).build()));
        when(accountRepository.findById("111-000-001")).thenReturn(Optional.ofNullable(Account.builder().accountNumber("111-000-001").balance(new BigDecimal(100)).build()));

        Transaction transaction = service.performTransfer("111-000-000", "111-000-001", new BigDecimal(50));
        verify(accountRepository, times(1)).save(Account.builder().accountNumber("111-000-000").balance(new BigDecimal(50)).build());
        verify(accountRepository, times(1)).save(Account.builder().accountNumber("111-000-001").balance(new BigDecimal(150)).build());
        verify(transactionRepository, times(1)).save(Transaction.builder().sourceAccountNumber("111-000-000").destinationAccountNumber("111-000-001").amount(new BigDecimal(50)).build());
    }
}