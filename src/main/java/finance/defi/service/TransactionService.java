package finance.defi.service;

import finance.defi.domain.Transaction;
import finance.defi.service.dto.RawTransactionDTO;
import finance.defi.service.dto.TransactionDTO;
import finance.defi.service.dto.TransactionHashDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link finance.defi.domain.Transaction}.
 */
public interface TransactionService {

    /**
     * Get all the transactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransactionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" transaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionDTO> findOne(Long id);

    TransactionHashDTO processRawTransaction(RawTransactionDTO rawTransactionDTO);
}
