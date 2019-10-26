package finance.defi.service.impl;

import finance.defi.domain.Asset;
import finance.defi.domain.User;
import finance.defi.domain.enumeration.BalanceType;
import finance.defi.repository.AssetRepository;
import finance.defi.service.AccountBalanceService;
import finance.defi.domain.AccountBalance;
import finance.defi.repository.AccountBalanceRepository;
import finance.defi.service.UserService;
import finance.defi.service.dto.AccountBalanceDTO;
import finance.defi.service.dto.AccountTotalBalanceDTO;
import finance.defi.service.dto.BalanceDTO;
import finance.defi.service.mapper.AccountBalanceMapper;
import finance.defi.web.rest.AccountResource;
import finance.defi.web.rest.errors.BadRequestAlertException;
import finance.defi.web.rest.errors.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link AccountBalance}.
 */
@Service
@Transactional
public class AccountBalanceServiceImpl implements AccountBalanceService {

    private final Logger log = LoggerFactory.getLogger(AccountBalanceServiceImpl.class);

    private final AccountBalanceRepository accountBalanceRepository;

    private final AccountBalanceMapper accountBalanceMapper;

    private final AssetRepository assetRepository;

    public AccountBalanceServiceImpl(AccountBalanceRepository accountBalanceRepository,
                                     AccountBalanceMapper accountBalanceMapper,
                                     AssetRepository assetRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
        this.accountBalanceMapper = accountBalanceMapper;
        this.assetRepository = assetRepository;
    }

    /**
     * Get all the accountBalances.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public AccountTotalBalanceDTO findAll() {
        log.debug("Request to get total AccountBalances");

        BigDecimal totalBalance = BigDecimal.ZERO;
        List<BalanceDTO> balances = new ArrayList<>();
        List<BalanceDTO> lendBalances = new ArrayList<>();
        List<BalanceDTO> borrowBalances = new ArrayList<>();

        //TODO remove hardcoded 1L
        Asset baseAsset = assetRepository.findById(1L).orElseThrow(
            () -> new BadRequestAlertException("Asset not found", null, "assetnull"));

        Page<AccountBalance> accountBalances = accountBalanceRepository.findByUserIsCurrentUser(new PageRequest(0, 9));

        for (AccountBalance balance : accountBalances.getContent()) {
            totalBalance = totalBalance.add(
                balance.getBalanceAmount().multiply(balance.getAsset().getPrice()));

            if (BalanceType.WALLET.equals(balance.getBalanceType())) {
                balances.add(new BalanceDTO(balance.getBalanceAmount(), null, balance.getAsset()));
            }
            else if (BalanceType.SUPPLY.equals(balance.getBalanceType())) {
                lendBalances.add(new BalanceDTO(balance.getBalanceAmount(), null, balance.getAsset()));
            }
            else if (BalanceType.BORROW.equals(balance.getBalanceType())) {
                borrowBalances.add(new BalanceDTO(balance.getBalanceAmount(), null, balance.getAsset()));
            }
        }
        totalBalance = totalBalance.divide(baseAsset.getPrice(), 2, RoundingMode.HALF_UP);

        AccountTotalBalanceDTO accountTotalBalance = new AccountTotalBalanceDTO();
        accountTotalBalance.setTotalBalance(totalBalance);
        accountTotalBalance.setAsset(baseAsset);
        // balances
        accountTotalBalance.setBalances(balances);
        // lend
        accountTotalBalance.setSupplyBalances(lendBalances);
        // borrow
        accountTotalBalance.setBorrowBalances(borrowBalances);

        return accountTotalBalance;
    }
}
