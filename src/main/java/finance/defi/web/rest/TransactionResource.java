package finance.defi.web.rest;

import finance.defi.domain.User;
import finance.defi.domain.enumeration.TransactionType;
import finance.defi.service.TransactionService;
import finance.defi.service.UserService;
import finance.defi.service.dto.RawTransactionDTO;
import finance.defi.service.dto.TransactionDTO;
import finance.defi.service.dto.TransactionHashDTO;
import finance.defi.web.rest.errors.EntityNotFoundException;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link finance.defi.domain.Transaction}.
 */
@RestController
@RequestMapping("/api")
public class TransactionResource {

    private final Logger log = LoggerFactory.getLogger(TransactionResource.class);

    private static final String ENTITY_NAME = "transaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionService transactionService;

    private final UserService userService;

    public TransactionResource(TransactionService transactionService,
                               UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    /**
     * {@code GET  /transactions} : get all the transactions.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactions in body.
     */
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(value = "assetId", required = false) Long assetId, @RequestParam(value = "type", required = false) List<TransactionType> types) {
        log.debug("REST request to get a page of Transactions");

        User currentUser = userService.getUserWithAuthorities().orElseThrow(
            () -> new EntityNotFoundException("User not found"));

        Page<TransactionDTO> page = null;
        if (assetId != null) {
            page = transactionService.findByUserAndAssetId(pageable, currentUser, assetId);
        } else if (types != null && types.size() > 0) {
            page = transactionService.findByUserAndType(pageable, currentUser, types);
        } else {
            page = transactionService.findByUser(pageable, currentUser);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transactions/:id} : get the "id" transaction.
     *
     * @param id the id of the transactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transactions/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) {
        log.debug("REST request to get Transaction : {}", id);
        Optional<TransactionDTO> transactionDTO = transactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionDTO);
    }

    @PostMapping("/transactions/process")
    public ResponseEntity<TransactionHashDTO> processRawTransaction(HttpServletRequest request,
                                                                    @Valid @RequestBody RawTransactionDTO rawTransactionDTO) {
        log.debug("REST request to proceed rawTransactionDTO : {}", rawTransactionDTO);

        TransactionHashDTO txHash = transactionService.processRawTransaction(request, rawTransactionDTO);
        return ResponseEntity.ok().body(txHash);
    }
}
