package finance.defi.service.dto.coin_market_cap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    @JsonProperty("USD")
    private Usd usd;


    public Usd getUSD() {
        return usd;
    }

    public void setUSD(Usd usd) {
        this.usd = usd;
    }
}
