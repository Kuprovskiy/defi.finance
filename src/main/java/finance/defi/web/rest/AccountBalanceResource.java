package finance.defi.web.rest;

import finance.defi.service.AccountBalanceService;
import finance.defi.service.dto.AccountTotalBalanceDTO;
import finance.defi.web.rest.errors.BadRequestAlertException;
import finance.defi.service.dto.AccountBalanceDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

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
