package finance.defi.service.dto;

import finance.defi.domain.Asset;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class BalanceDTO implements Serializable {

    private BigDecimal amount;

    private BigDecimal earned;

    private Asset asset;

    public BalanceDTO(BigDecimal amount, BigDecimal earned, Asset asset) {
        this.amount = amount;
        this.earned = earned;
        this.asset = asset;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getEarned() {
        return earned;
    }

    public void setEarned(BigDecimal earned) {
        this.earned = earned;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceDTO that = (BalanceDTO) o;
        return Objects.equals(amount, that.amount) &&
            Objects.equals(earned, that.earned) &&
            Objects.equals(asset, that.asset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, earned, asset);
    }

    @Override
    public String toString() {
        return "BalancesDTO{" +
            "amount=" + amount +
            ", earned=" + earned +
            ", asset=" + asset +
            '}';
    }
}
