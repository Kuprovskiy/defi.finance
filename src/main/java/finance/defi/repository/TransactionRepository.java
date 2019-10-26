package finance.defi.repository;

import finance.defi.domain.Transaction;
import finance.defi.domain.User;
import finance.defi.domain.enumeration.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Transaction entity.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select transaction from Transaction transaction where transaction.user.login = ?#{principal.username}")
    List<Transaction> findByUserIsCurrentUser();

    @Query("select transaction from Transaction transaction where transaction.recipient.login = ?#{principal.username}")
    List<Transaction> findByRecipientIsCurrentUser();

    Page<Transaction> findByUserAndAssetOrderByIdDesc(Pageable page, User user, Long assetId);

    Page<Transaction> findByUserAndTransactionTypeInOrderByIdDesc(Pageable page, User user, List<TransactionType> types);

    Page<Transaction> findByUserOrderByIdDesc(Pageable page, User user);
}
