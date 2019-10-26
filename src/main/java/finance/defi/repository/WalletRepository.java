package finance.defi.repository;

import finance.defi.domain.User;
import finance.defi.domain.Wallet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Wallet entity.
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query("select wallet from Wallet wallet where wallet.user.login = :login ORDER BY wallet.id DESC")
    List<Wallet> findByUser(@Param("login") String login, Pageable pageable);

}
