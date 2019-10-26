package finance.defi.repository;

import finance.defi.domain.Wallet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Wallet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query("select wallet from Wallet wallet where wallet.user.login = ?#{principal.username}")
    List<Wallet> findByUserIsCurrentUser();

}
