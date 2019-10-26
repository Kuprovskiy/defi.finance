package finance.defi.service.impl;

import finance.defi.contracts.DaiCErc20;
import finance.defi.contracts.UsdcCErc20;
import finance.defi.domain.Wallet;
import finance.defi.repository.WalletRepository;
import finance.defi.security.SecurityUtils;
import finance.defi.service.WalletService;
import finance.defi.service.dto.WalletDTO;
import finance.defi.service.mapper.WalletMapper;
import finance.defi.service.util.ConverterUtil;
import finance.defi.web.rest.errors.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.parity.Parity;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.NoOpProcessor;
import org.web3j.utils.Convert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Wallet}.
 */
@Service
@Transactional
public class WalletServiceImpl implements WalletService {

    private final Logger log = LoggerFactory.getLogger(WalletServiceImpl.class);

    private static final String SYSTEM_KEY = "5006783686A1756C8861A8C24DF5487691ACD8E22D008DED391C9B4FA221A0C3";

    private final WalletRepository walletRepository;

    private final WalletMapper walletMapper;

    private static Web3jService service;

    private static Web3j web3j;

    private static Parity parity;

    NoOpProcessor processor;

    private static String usdcContractAddress;

    private static String daiContractAddress;

    public WalletServiceImpl(WalletRepository walletRepository,
                             WalletMapper walletMapper) {
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;

        this.service = new HttpService("https://rinkeby.infura.io/v3/4750a8f14c37429687f5229ff94e4e56");
        this.web3j = Web3j.build(this.service);
        this.parity = Parity.build(this.service);
        processor = new NoOpProcessor(this.web3j);
        this.usdcContractAddress = "0x4dbcdf9b62e891a7cec5a2568c3f4faf9e8abe2b";
        this.daiContractAddress = "0x5592ec0cfb4dbc12d3ab100b257153436a1f0fea";
    }

    /**
     * Get all the wallets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WalletDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Wallets");
        return walletRepository.findAll(pageable)
            .map(walletMapper::toDto);
    }

    /**
     * Get one wallet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WalletDTO> findOne(Long id) {
        log.debug("Request to get Wallet : {}", id);
        return walletRepository.findById(id)
            .map(walletMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Wallet findByCurrentUser() {
        log.debug("Request to get Wallet : {}");
        String currentUserLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(
            () -> new EntityNotFoundException("User not found"));
        List<Wallet> addressList = walletRepository.findByUser(currentUserLogin, new PageRequest(0, 1));

        return (addressList.size() > 0) ? addressList.get(0) : null;
    }

    public BigDecimal ethBalanceOf(Wallet wallet) {

        BigDecimal balance = BigDecimal.ZERO;
        try {
            EthGetBalance ethGetBalance=
                this.web3j.ethGetBalance(wallet.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();

            BigInteger wei = ethGetBalance.getBalance();
            balance = Convert.fromWei(String.valueOf(wei), Convert.Unit.ETHER);
        } catch (Exception e) {
            log.error("Request to mint failed : {}", e);
        }

        return balance;
    }

    public BigDecimal usdcBalanceOf(Wallet wallet) {

        BigInteger balance = BigInteger.ZERO;
        try {
            UsdcCErc20 token = loadUsdcContract(SYSTEM_KEY);
            balance = token.balanceOf(wallet.getAddress()).send();
        } catch (Exception e) {
            log.error("Request to mint failed : {}", e);
        }

        return new BigDecimal(ConverterUtil.fromWei(balance, Convert.Unit.MWEI));
    }

    public BigDecimal daiBalanceOf(Wallet wallet) {

        BigInteger balance = BigInteger.ZERO;
        try {
            DaiCErc20 token = loadDaiContract(SYSTEM_KEY);
            balance = token.balanceOf(wallet.getAddress()).send();
            // get transaction result
        } catch (Exception e) {
            log.error("Request to mint failed : {}", e);
        }

        return new BigDecimal(ConverterUtil.fromWei(balance, Convert.Unit.ETHER));
    }

    public BigDecimal balanceOfUnderlying(Wallet wallet, String asset) {
        log.debug("Request to get balance of underlying: {}");

        String s;
        BigDecimal balanceOfUnderlying = new BigDecimal(0);
        try {
            String strExec = String.format(
                "node /home/user/apps/CompoundFinance/%s.js --key=%s --addr=%s",
                asset.toLowerCase(),
                SYSTEM_KEY,
                wallet.getAddress()
            );
            Process p = Runtime.getRuntime().exec(strExec);
            BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));

            // read the output from the command
            if ((s = stdInput.readLine()) != null) {
                balanceOfUnderlying = new BigDecimal(s);
            }

            // read any errors from the attempted command
            if ((s = stdError.readLine()) != null) {
                log.error(s);
            }

        } catch (Exception e) {
            log.error("exception happened - here's what I know: ", e.getMessage());
        }

        return balanceOfUnderlying;
    }


    private UsdcCErc20 loadUsdcContract(String pKey) {
        TransactionManager txManager = new FastRawTransactionManager(
            this.web3j,
            getAccountCredentials(pKey),
            this.processor);

        // load existing contract by address
        return UsdcCErc20.load(this.usdcContractAddress, this.web3j, txManager, DefaultGasProvider.GAS_PRICE,
            DefaultGasProvider.GAS_LIMIT);
    }

    private DaiCErc20 loadDaiContract(String pKey) {
        TransactionManager txManager = new FastRawTransactionManager(
            this.web3j,
            getAccountCredentials(pKey),
            this.processor);

        // load existing contract by address
        return DaiCErc20.load(this.daiContractAddress, this.web3j, txManager, DefaultGasProvider.GAS_PRICE,
            DefaultGasProvider.GAS_LIMIT);
    }

    // load private key
    public Credentials getAccountCredentials(String pKey) {
        BigInteger key = new BigInteger(pKey, 16);
        ECKeyPair ecKeyPair = ECKeyPair.create(key);
        Credentials credentials = Credentials.create(ecKeyPair);
        return credentials;
    }
}
