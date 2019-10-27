package finance.defi.service;

import finance.defi.domain.Wallet;
import finance.defi.service.dto.WalletDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Service Interface for managing {@link finance.defi.domain.Wallet}.
 */
public interface WalletService {

    /**
     * Get all the wallets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WalletDTO> findAll(Pageable pageable);

    /**
     * Get the "id" wallet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WalletDTO> findOne(Long id);

    Optional<WalletDTO> findOneByUser(Long id);

    BigDecimal ethBalanceOf(Wallet wallet);

    BigDecimal usdcBalanceOf(Wallet wallet);

    BigDecimal daiBalanceOf(Wallet wallet);

    BigDecimal balanceOfUnderlying(Wallet wallet, String asset);

    WalletDTO findByCurrentUser();

    Wallet findByCurrent();
}
