package finance.defi.cron;

import com.fasterxml.jackson.databind.ObjectMapper;
import finance.defi.config.Constants;
import finance.defi.domain.AccountBalance;
import finance.defi.domain.Asset;
import finance.defi.domain.enumeration.BalanceType;
import finance.defi.repository.AccountBalanceRepository;
import finance.defi.repository.AssetRepository;
import finance.defi.repository.WalletRepository;
import finance.defi.service.WalletService;
import finance.defi.service.dto.AccountBalanceDTO;
import finance.defi.service.dto.coin_market_cap.CoinMarketCapApi;
import finance.defi.service.dto.coin_market_cap.CryptoCurrency;
import finance.defi.web.rest.errors.EntityNotFoundException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class PriceOracle {

    private static final Logger log = LoggerFactory.getLogger(PriceOracle.class);

    private final AssetRepository assetRepository;

    private final WalletRepository walletRepository;

    private final AccountBalanceRepository accountBalanceRepository;

    private final WalletService walletService;

    private static String apiKey = "76da8a6c-d2b9-4ee4-9012-ccfc339715f5";// "97ada2c2-0c51-4443-8070-b289797d4a45";

    public PriceOracle(AssetRepository assetRepository,
                       WalletRepository walletRepository,
                       AccountBalanceRepository accountBalanceRepository,
                       WalletService walletService) {
        this.assetRepository = assetRepository;
        this.walletRepository = walletRepository;
        this.accountBalanceRepository = accountBalanceRepository;
        this.walletService = walletService;
    }

    @Scheduled(cron = "* 1 * * * ?")
    public void priceOracleSync() {

        Asset usdc = assetRepository.findByNameAndIsVisible(Constants.USDC, true).orElseThrow(
            () -> new EntityNotFoundException("Asset not found"));
        Asset dai = assetRepository.findByNameAndIsVisible(Constants.DAI, true).orElseThrow(
            () -> new EntityNotFoundException("Asset not found"));
        Asset eth = assetRepository.findByNameAndIsVisible(Constants.ETH, true).orElseThrow(
            () -> new EntityNotFoundException("Asset not found"));

        HashMap<String, BigDecimal> prices = getPricesFromCMC();

        // save usdc last oracle price
        try {
            usdc.setPrice(prices.get(Constants.USDC));
            assetRepository.save(usdc);
        } catch (Exception e) {
            log.error("Cannot proccess {}", e);
        }

        // save dai last oracle price
        try {
            dai.setPrice(prices.get(Constants.DAI));
            assetRepository.save(dai);
        } catch (Exception e) {
            log.error("Cannot proccess {}", e);
        }

        // save eth last oracle price
        try {
            eth.setPrice(prices.get(Constants.ETH));
            assetRepository.save(eth);
        } catch (Exception e) {
            log.error("Cannot proccess {}", e);
        }
    }

    /**
     * Smart contract auto sync for each address.
     * <p>
     * This is scheduled to get fired every hour.
     */
    @Scheduled(cron = "1 * * * * ?")
    public void syncBalancesWithNode() {
        log.debug("Start synchronization balance with smart contract: {}");

        Asset usdc = assetRepository.findByNameAndIsVisible(Constants.USDC, true).orElseThrow(
            () -> new EntityNotFoundException("Asset not found"));
        Asset dai = assetRepository.findByNameAndIsVisible(Constants.DAI, true).orElseThrow(
            () -> new EntityNotFoundException("Asset not found"));
        Asset eth = assetRepository.findByNameAndIsVisible(Constants.ETH, true).orElseThrow(
            () -> new EntityNotFoundException("Asset not found"));

        walletRepository
            .findAll()
            .forEach(wallet -> {
                log.debug("Sync balance for wallet {}", wallet.getUser().getLogin());
                log.info("-----------------------------------------------------");
                // save USDC
                AccountBalance usdcBalance = accountBalanceRepository.findByUserAndAssetAndBalanceType(wallet.getUser(), usdc, BalanceType.WALLET)
                    .orElseGet(() -> new AccountBalance());
                usdcBalance.setAsset(usdc);
                usdcBalance.setBalanceAmount(walletService.usdcBalanceOf(wallet));
                usdcBalance.setBalanceType(BalanceType.WALLET);
                usdcBalance.setUser(wallet.getUser());
                usdcBalance.setCreatedAt(Instant.now());
                usdcBalance.setUpdatedAt(Instant.now());

                accountBalanceRepository.save(usdcBalance);
                log.info("usdcBalance: " + usdcBalance.toString());

                // save DAI
                AccountBalance daiBalance = accountBalanceRepository.findByUserAndAssetAndBalanceType(wallet.getUser(), dai, BalanceType.WALLET)
                    .orElseGet(() -> new AccountBalance());
                daiBalance.setAsset(dai);
                daiBalance.setBalanceAmount(walletService.daiBalanceOf(wallet));
                daiBalance.setBalanceType(BalanceType.WALLET);
                daiBalance.setUser(wallet.getUser());
                daiBalance.setCreatedAt(Instant.now());
                daiBalance.setUpdatedAt(Instant.now());

                accountBalanceRepository.save(daiBalance);
                log.info("daiBalance: " + daiBalance.toString());

                // save DAI
                AccountBalance ethBalance = accountBalanceRepository.findByUserAndAssetAndBalanceType(wallet.getUser(), eth, BalanceType.WALLET)
                    .orElseGet(() -> new AccountBalance());
                ethBalance.setAsset(eth);
                ethBalance.setBalanceAmount(walletService.balanceOf(wallet));
                ethBalance.setBalanceType(BalanceType.WALLET);
                ethBalance.setUser(wallet.getUser());
                ethBalance.setCreatedAt(Instant.now());
                ethBalance.setUpdatedAt(Instant.now());

                accountBalanceRepository.save(ethBalance);
                log.info("ethBalance: " + ethBalance.toString());
            });
        log.debug("End synchronization balance with smart contract: {}");
    }

    /**
     * Smart contract auto sync for each address.
     * <p>
     * This is scheduled to get fired every hour.
     */
    @Scheduled(cron = "1 * * * * ?")
    public void syncBalancesOfUnderlyingWithNode() {
        log.debug("Start synchronization balance of underlying with smart contract: {}");

        Asset usdc = assetRepository.findByNameAndIsVisible(Constants.USDC, true).orElseThrow(
            () -> new EntityNotFoundException("Asset not found"));
        Asset dai = assetRepository.findByNameAndIsVisible(Constants.DAI, true).orElseThrow(
            () -> new EntityNotFoundException("Asset not found"));
        Asset eth = assetRepository.findByNameAndIsVisible(Constants.ETH, true).orElseThrow(
            () -> new EntityNotFoundException("Asset not found"));

        walletRepository
            .findAll()
            .forEach(wallet -> {
                log.debug("Sync balance of underlying for wallet {}", wallet.getUser().getLogin());

                // save USDC
                AccountBalance usdcBalance = accountBalanceRepository.findByUserAndAssetAndBalanceType(wallet.getUser(), usdc, BalanceType.SUPPLY)
                    .orElseGet(() -> new AccountBalance());
                usdcBalance.setAsset(usdc);
                usdcBalance.setBalanceAmount(walletService.balanceOfUnderlying(wallet, usdc.getName()));
                usdcBalance.setBalanceType(BalanceType.SUPPLY);
                usdcBalance.setUser(wallet.getUser());
                usdcBalance.setCreatedAt(Instant.now());
                usdcBalance.setUpdatedAt(Instant.now());

                log.info("usdcBalance: " + usdcBalance.getBalanceAmount());

                accountBalanceRepository.save(usdcBalance);

                // save DAI
                AccountBalance daiBalance = accountBalanceRepository.findByUserAndAssetAndBalanceType(wallet.getUser(), dai, BalanceType.SUPPLY)
                    .orElseGet(() -> new AccountBalance());
                daiBalance.setAsset(dai);
                daiBalance.setBalanceAmount(walletService.balanceOfUnderlying(wallet, dai.getName()));
                daiBalance.setBalanceType(BalanceType.SUPPLY);
                daiBalance.setUser(wallet.getUser());
                daiBalance.setCreatedAt(Instant.now());
                daiBalance.setUpdatedAt(Instant.now());

                log.info("daiBalance: " + daiBalance.getBalanceAmount());

                accountBalanceRepository.save(daiBalance);

                // save ETH
                AccountBalance ethBalance = accountBalanceRepository.findByUserAndAssetAndBalanceType(wallet.getUser(), eth, BalanceType.SUPPLY)
                    .orElseGet(() -> new AccountBalance());
                ethBalance.setAsset(eth);
                ethBalance.setBalanceAmount(walletService.balanceOfUnderlying(wallet, eth.getName()));
                ethBalance.setBalanceType(BalanceType.SUPPLY);
                ethBalance.setUser(wallet.getUser());
                ethBalance.setCreatedAt(Instant.now());
                ethBalance.setUpdatedAt(Instant.now());

                log.info("ethBalance: " + ethBalance.getBalanceAmount());

                accountBalanceRepository.save(ethBalance);
            });
        log.debug("End synchronization balance of underlying with smart contract: {}");
    }

    private HashMap<String, BigDecimal> getPricesFromCMC() {
        String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
        paratmers.add(new BasicNameValuePair("start","1"));
        paratmers.add(new BasicNameValuePair("limit","500"));
        paratmers.add(new BasicNameValuePair("convert","USD"));
        List<String> supportedCurrencies = Arrays.asList(Constants.ETH, Constants.DAI, Constants.USDC);
        HashMap<String, BigDecimal> prices = new HashMap<String, BigDecimal>();

        try {
            String result = makeAPICall(uri, paratmers);

            ObjectMapper mapper = new ObjectMapper();
            CoinMarketCapApi readValue = mapper.readValue(result, CoinMarketCapApi.class);

            for (CryptoCurrency cryptoCurrency : readValue.getData()) {
                if (inArray(cryptoCurrency.getSymbol(), supportedCurrencies)) {
                    prices.put(cryptoCurrency.getSymbol(), cryptoCurrency.getQuote().getUSD().getPrice());
                }
            }

        } catch (IOException e) {
            System.out.println("Error: cannont access content - " + e.toString());
        } catch (URISyntaxException e) {
            System.out.println("Error: Invalid URL " + e.toString());
        }
        return prices;
    }

    private boolean inArray(String search, List<String> list) {
        for(String str: list) {
            if(str.trim().equalsIgnoreCase(search))
                return true;
        }
        return false;
    }

    private static String makeAPICall(String uri, List<NameValuePair> parameters)
        throws URISyntaxException, IOException {
        String response_content = "";

        URIBuilder query = new URIBuilder(uri);
        query.addParameters(parameters);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(query.build());

        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader("X-CMC_PRO_API_KEY", apiKey);

        CloseableHttpResponse response = client.execute(request);

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            response_content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }

        return response_content;
    }
}
