package co.uk.rbs.account.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferDTO {
    private String toAccountNumber;
    private BigDecimal amount;
}