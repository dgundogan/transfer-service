package co.uk.rbs.account.controller;


import co.uk.rbs.account.dto.TransferDTO;
import co.uk.rbs.account.entity.Transaction;
import co.uk.rbs.account.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TransferServiceController {

    private final TransferService service;

    public TransferServiceController(TransferService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/accounts/{accountId}/transfer",consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
    public Transaction performTransfer(@PathVariable String accountId,
                                       @Valid @RequestBody TransferDTO request) {
        return service.performTransfer(accountId, request.getToAccountNumber(), request.getAmount());
    }
}