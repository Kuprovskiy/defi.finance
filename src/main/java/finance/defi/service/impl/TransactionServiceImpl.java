package finance.defi.service.impl;

import com.google.gson.Gson;
import finance.defi.config.Constants;
import finance.defi.domain.*;
import finance.defi.domain.enumeration.TransactionType;
import finance.defi.repository.AssetRepository;
import finance.defi.repository.TransactionRepository;
import finance.defi.security.SecurityUtils;
import finance.defi.service.TransactionService;
import finance.defi.service.TrustedDeviceService;
import finance.defi.service.UserService;
import finance.defi.service.WalletService;
import finance.defi.service.dto.*;
import finance.defi.service.mapper.TransactionMapper;
import finance.defi.service.util.ConverterUtil;
import finance.defi.service.util.NumberUtil;
import finance.defi.service.util.TransactionDecoderUtil;
import finance.defi.web.rest.errors.EntityNotFoundException;
import org.jboss.aerogear.security.otp.Totp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.parity.Parity;
import org.web3j.utils.Convert;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Transaction}.
 */
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Value("${ether.network.provider}")
    private String networkProvider;

    private static Web3jService service;

    private static Web3j web3j;

    private static Parity parity;

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private final UserService userService;

    private final AssetRepository assetRepository;

    private final TrustedDeviceService trustedDeviceService;

    private final WalletService walletService;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TransactionMapper transactionMapper,
                                  UserService userService,
                                  AssetRepository assetRepository,
                                  TrustedDeviceService trustedDeviceService,
                                  WalletService walletService) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.userService = userService;
        this.assetRepository = assetRepository;
        this.trustedDeviceService = trustedDeviceService;
        this.walletService = walletService;

        this.service = new HttpService("https://rinkeby.infura.io/v3/4750a8f14c37429687f5229ff94e4e56");
        this.web3j = Web3j.build(this.service);
        this.parity = Parity.build(this.service);
    }

    /**
     * Save a transaction.
     *
     * @param transaction the entity to save.
     * @return the persisted entity.
     */
    public TransactionDTO save(Transaction transaction) {
        log.debug("Request to save Transaction : {}", transaction);
        transaction = transactionRepository.save(transaction);
        return transactionMapper.toDto(transaction);
    }

    /**
     * Get all the transactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Transactions");
        return transactionRepository.findAll(pageable)
            .map(transactionMapper::toDto);
    }

    /**
     * Get one transaction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionDTO> findOne(Long id) {
        log.debug("Request to get Transaction : {}", id);
        return transactionRepository.findById(id)
            .map(transactionMapper::toDto);
    }

    @Override
    public TransactionHashDTO processRawTransaction(HttpServletRequest request,
                                                    RawTransactionDTO rawTransactionDTO) {

        Gson gson = new Gson();
        EthSendTransaction ethSendTx = null;
        RawTransaction encodedRawTx = TransactionDecoderUtil.decode(rawTransactionDTO.getTx());
//        BigDecimal amount = new BigDecimal(ConverterUtil.fromWei(encodedRawTx.getValue(), Convert.Unit.ETHER));

        User currentUser = userService.getUserWithAuthorities().orElseThrow(
            () -> new EntityNotFoundException("User not found"));

        Wallet wallet = walletService.findByCurrent();

        if (wallet == null) {
            throw new EntityNotFoundException("wallet not found");
        }

        //TODO validate daily transfer limit

        // validate trust device
        validateTrustDevice(request, currentUser);

        // validate google 2FA
        if (currentUser.getUsing2FA()) {
            Totp totp = new Totp(currentUser.getSecret2fa());
            if (!NumberUtil.isValidLong(rawTransactionDTO.getCode()) || !totp.verify(rawTransactionDTO.getCode())) {
                throw new BadCredentialsException("Invalid verification code");
            }
        }

        // validate balance
        validateBalance(wallet, rawTransactionDTO.getAmount(), rawTransactionDTO.getAsset());

        Asset asset = assetRepository.findByNameAndIsVisible(rawTransactionDTO.getAsset(), true).orElseThrow(
            () -> new EntityNotFoundException("Asset not found"));

        // send the signed transaction to the ethereum client
        try {
            ethSendTx = web3j
                .ethSendRawTransaction(rawTransactionDTO.getTx())
                .sendAsync()
                .get();

            if (null != ethSendTx) {
                log.info("saving transaction to db");

                // save transaction
                saveTransaction(rawTransactionDTO.getAmount(),
                    asset,
                    currentUser,
                    rawTransactionDTO.getType(),
                    ethSendTx.getTransactionHash(),
                    gson.toJson(encodedRawTx));
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new TransactionHashDTO(ethSendTx.getTransactionHash());
    }

    public Page<TransactionDTO> findByUserAndAssetId(Pageable pageable, User user, Long assetId) {
        log.debug("find by assetId: {}, page: {}", assetId, pageable);

        return transactionRepository.findByUserAndAssetOrderByIdDesc(pageable, user, assetId)
            .map(transactionMapper::toDto);
    }

    public Page<TransactionDTO> findByUserAndType(Pageable pageable, User user, List<TransactionType> types) {
        log.debug("find by user and type: {}", pageable);

        return transactionRepository.findByUserAndTransactionTypeInOrderByIdDesc(pageable, user, types)
            .map(transactionMapper::toDto);
    }

    public Page<TransactionDTO> findByUser(Pageable pageable, User user) {
        log.debug("find by page: {}", pageable);

        return transactionRepository.findByUserOrderByIdDesc(pageable, user)
            .map(transactionMapper::toDto);
    }

    private void saveTransaction(BigDecimal amount,
                                 Asset asset,
                                 User currentUser,
                                 TransactionType type,
                                 String transactionHash,
                                 String encodedTx) {

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setAsset(asset);
        transaction.setCreatedAt(Instant.now());
        transaction.setTransactionType(type);
        transaction.setUser(currentUser);
        transaction.setTxHash(transactionHash);
        transaction.setTxRaw(encodedTx);

        save(transaction);
    }

    private void validateTrustDevice(HttpServletRequest request, User currentUser) {
        TrustedDeviceDTO newTrustedDevice = SecurityUtils.getCurrentDevice(request, currentUser, false);
        newTrustedDevice.setCreatedAt(Instant.now().minusSeconds(2592000)); // 2592000 = 1 month

        List<TrustedDevice> trustedDevices =
            this.trustedDeviceService.findByUserIsCurrentUserAndDeviceAndDeviceOsAndTrustedAndDateAfter(
                newTrustedDevice,
                currentUser);
        if (trustedDevices.size() == 0) {
            throw new EntityNotFoundException("There is some abnormal activity with your withdrawals. We require you to verify your identity before we can re-enable your withdrawal functionality");
        }
    }

    private void validateBalance(Wallet wallet, BigDecimal amount, String asset) {

        BigDecimal balance = getBalance(wallet, asset);
        log.info("-----------------------------------");
        log.info("balance: " + balance.toString());
        log.info("amount: " + amount);
        if (balance.compareTo(amount) == -1) {
            throw new EntityNotFoundException("Balance is not enough");
        }
    }

    private BigDecimal getBalance(Wallet wallet, String asset) {

        BigDecimal balance = BigDecimal.ZERO;
        switch (asset) {
            case Constants.ETH:
                balance = walletService.ethBalanceOf(wallet);
                break;
            case Constants.USDC:
                balance = walletService.usdcBalanceOf(wallet);
                break;
            case Constants.DAI:
                balance = walletService.daiBalanceOf(wallet);
                break;
        }

        return balance;
    }
}
