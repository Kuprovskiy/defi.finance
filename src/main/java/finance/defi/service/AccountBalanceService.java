package finance.defi.service;

import finance.defi.service.dto.AccountTotalBalanceDTO;

/**
 * Service Interface for managing {@link finance.defi.domain.AccountBalance}.
 */
public interface AccountBalanceService {

    /**
     * Get total accountBalances.
     *
     * @return AccountTotalBalanceDTO.
     */
    AccountTotalBalanceDTO findAll();
}
