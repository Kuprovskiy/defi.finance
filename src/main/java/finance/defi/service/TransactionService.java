package finance.defi.service;

import finance.defi.domain.User;
import finance.defi.domain.enumeration.TransactionType;
import finance.defi.service.dto.RawTransactionDTO;
import finance.defi.service.dto.TransactionDTO;
import finance.defi.service.dto.TransactionHashDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

    TransactionHashDTO  processRawTransaction(HttpServletRequest request, RawTransactionDTO rawTransactionDTO);

    Page<TransactionDTO> findByUserAndAssetId(Pageable pageable, User user, Long assetId);

    Page<TransactionDTO> findByUserAndType(Pageable pageable, User user, List<TransactionType> types);

    Page<TransactionDTO> findByUser(Pageable pageable, User user);
}
