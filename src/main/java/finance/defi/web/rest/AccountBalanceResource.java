package finance.defi.web.rest;

import finance.defi.service.AccountBalanceService;
import finance.defi.service.dto.AccountTotalBalanceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link finance.defi.domain.AccountBalance}.
 */
@RestController
@RequestMapping("/api")
public class AccountBalanceResource {

    private final Logger log = LoggerFactory.getLogger(AccountBalanceResource.class);

    private static final String ENTITY_NAME = "accountBalance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccountBalanceService accountBalanceService;

    public AccountBalanceResource(AccountBalanceService accountBalanceService) {
        this.accountBalanceService = accountBalanceService;
    }

    /**
     * {@code GET  /account/balance} : get all the accountBalances.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accountBalances in body.
     */
    @GetMapping("/account/balance")
    public ResponseEntity<AccountTotalBalanceDTO> getAllAccountBalances(Pageable pageable) {
        log.debug("REST request to get a page of AccountBalances");
        AccountTotalBalanceDTO balance = accountBalanceService.findAll();
        return ResponseEntity.ok().body(balance);
    }
}
