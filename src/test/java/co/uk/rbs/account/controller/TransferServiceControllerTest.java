package co.uk.rbs.account.controller;

import co.uk.rbs.account.dto.TransferDTO;
import co.uk.rbs.account.entity.Transaction;
import co.uk.rbs.account.service.TransferService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TransferServiceControllerTest {

    private TransferServiceController controller;
    private TransferService service;

    @Before
    public void setUp(){
        service = mock(TransferService.class);
        controller = new TransferServiceController(service);
    }

    @Test
    public void givenDto_whenCallPerformTransfer_thenReturnsOK(){
        TransferDTO request = TransferDTO.builder().toAccountNumber("111-222-333").amount(new BigDecimal(50)).build();

        when(service.performTransfer(anyString(),anyString(),any()))
                .thenReturn(Transaction.builder().sourceAccountNumber("111-222-444").destinationAccountNumber("111-222-333").amount(new BigDecimal(50)).build());

        Transaction transaction =  controller.performTransfer("111-222-444",request);

        verify(service,times(1)).performTransfer(anyString(),anyString(),any());
        assertEquals("111-222-333", transaction.getDestinationAccountNumber());
        assertEquals("111-222-444", transaction.getSourceAccountNumber());
        assertEquals(transaction.getAmount(), new BigDecimal(50));
    }
}