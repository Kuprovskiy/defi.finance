package finance.defi.service.dto.coin_market_cap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties
public class CoinMarketCapApi implements Serializable {

    private List<CryptoCurrency> data;

    private Status status;

    public List<CryptoCurrency> getData() {
        return data;
    }

    public void setData(List<CryptoCurrency> data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
