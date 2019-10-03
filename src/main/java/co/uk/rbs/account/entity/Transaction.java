package co.uk.rbs.account.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="ACCOUNT_TRANSACTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "SOURCE_ACCOUNT_NUMBER")
    private String sourceAccountNumber;

    @Column(name = "DESTINATION_ACCOUNT_NUMBER")
    private String destinationAccountNumber;

    @Column(name = "AMOUNT")
    private BigDecimal amount;
}