package finance.defi.repository;

import finance.defi.domain.AccountBalance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AccountBalance entity.
 */
@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {

    @Query("select accountBalance from AccountBalance accountBalance where accountBalance.user.login = ?#{principal.username}")
    Page<AccountBalance> findByUserIsCurrentUser(Pageable pageable);

}
